package com.tlw.storagemanagement.service;

import com.tlw.storagemanagement.Properties.PostgisProperties;
import com.tlw.storagemanagement.common.OperationFeatureClass;
import com.tlw.storagemanagement.common.PostGisDataStore;
import org.apache.commons.lang3.StringUtils;
import org.geotools.data.DataStore;
import org.geotools.data.FileDataStore;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.referencing.CRS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class StorageShpService {
    private static final Logger logger = LoggerFactory.getLogger(StorageShpService.class);
    public boolean ingestFiles(PostgisProperties config, String tableName, String[] filePaths, String spatialName, String epsgCode, String encoding){
        try{
            FileDataStore dataStoreFromShp = OperationFeatureClass.getDataStoreFromShp(filePaths[0], encoding);
            DataStore ds= PostGisDataStore.getPostGISDS(config);
            boolean exist = false;
            String[] names = ds.getTypeNames();
            for (int i = 0; i < names.length; i++) {
                if (names[i].equals(tableName.toLowerCase())) {
                    exist = true;
                    break;
                }
            }
            Integer shpEpseCode = CRS.lookupEpsgCode(dataStoreFromShp.getSchema().getCoordinateReferenceSystem(), true);
            String epsgCodeNew = "";
            if(StringUtils.isNotBlank(epsgCode)){
                epsgCodeNew = epsgCode;
            } else if(shpEpseCode !=null){
                epsgCodeNew = shpEpseCode.toString();
            } else {
                ReferencedEnvelope bounds = dataStoreFromShp.getFeatureSource().getBounds();
                if(bounds==null){
                    bounds = dataStoreFromShp.getFeatureSource().getFeatures().getBounds();
                }
                if(bounds.getMaxX()<=180&&bounds.getMinX()>=-180){
                    epsgCodeNew = "4490";
                }
            }
            if(StringUtils.isBlank(epsgCodeNew)){
                throw new IOException("????????????????????????????????????????????????????????????!");
            }
            // ????????????????????? ????????????
            if(!exist){
                OperationFeatureClass.createTableFromSHP(ds,tableName.toLowerCase(),spatialName,dataStoreFromShp.getSchema(),epsgCodeNew);
            }
            // ?????????????????????8???????????????????????????8?????????  ??????
            ExecutorService executor = Executors.newFixedThreadPool(8);
            for (int i = 0; i < filePaths.length; i++) {
                String filePath = filePaths[i];
                String finalEpsgCode = epsgCodeNew;
                executor.execute(() -> {
                    try {
                        OperationFeatureClass.writeShpToPG(ds,tableName.toLowerCase(),dataStoreFromShp, finalEpsgCode,encoding);
                        return;
                    } catch (Exception e) {
                        logger.error(String.format("??????shp?????? :%s??????", filePath), e);
                        e.printStackTrace();
                    }
                });
            }
            executor.shutdown();
            while (!executor.isTerminated()) {
                try {
                    Thread.currentThread().sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return  true;
        }catch (Exception ex){
            // ex.printStackTrace();
            logger.error("?????????????????????",ex);
            return false;
        }
    }

//    public boolean ingestFiles(PostgisProperties config, String tableName, String[] filePaths, String spatialName, String epsgCode){
//        try{
//            DataStore ds= PostGisDataStore.getPostGISDS(config);
//            boolean exist = false;
//            String[] names = ds.getTypeNames();
//            for (int i = 0; i < names.length; i++) {
//                if (names[i].equals(tableName.toLowerCase())) {
//                    exist = true;
//                    break;
//                }
//            }
//            // ????????????????????? ????????????
//            if(!exist){
//                OperationFeatureClass.createTableFromSHP(ds,tableName.toLowerCase(),filePaths[0],spatialName,epsgCode);
//            }
//            // ?????????????????????8???????????????????????????8?????????  ??????
//            ExecutorService executor = Executors.newFixedThreadPool(8);
//            for (int i = 0; i < filePaths.length; i++) {
//                String filePath = filePaths[i];
//                executor.execute(() -> {
//                    try {
//                        OperationFeatureClass.writeShpToPG(ds,tableName.toLowerCase(),filePath);
//                        return;
//                    } catch (Exception e) {
//                        logger.error(String.format("??????shp?????? :%s??????", filePath), e);
//                        e.printStackTrace();
//                    }
//                });
//            }
//            executor.shutdown();
//            while (!executor.isTerminated()) {
//                try {
//                    Thread.currentThread().sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            return  true;
//        }catch (Exception ex){
//            // ex.printStackTrace();
//            logger.error("?????????????????????",ex);
//            return false;
//        }
//    }
}
