package com.tlw.storagemanagement.common;

import  com.tlw.storagemanagement.utils.PinYinUtil;
import org.geotools.data.*;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.feature.SchemaException;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.locationtech.jts.geom.Geometry;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;
import org.opengis.feature.type.GeometryDescriptor;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.ReferenceIdentifier;
import org.opengis.referencing.operation.MathTransform;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class OperationFeatureClass {

    /**
     * 在postgis中创建数据表
     *
     * @param ds         当前使用的postgis存储
     * @param typeName   入库后的图层名
     * @param shpPath    shp文件的路径
     * @param spatilName 空间字段的名称 geom
     * @return
     */
    public static void createTableFromSHP(DataStore ds, String typeName, String shpPath, String spatilName, String defaultEpsgCode, String encoding) throws FactoryException, SchemaException, IOException {
        SimpleFeatureType shpSchema = getSimpleFeatureTypeFromShp(shpPath, encoding);
        // String identifier = CRS.lookupIdentifier(shpSchema.getCoordinateReferenceSystem(), true);
        Integer shpEspeCode = CRS.lookupEpsgCode(shpSchema.getCoordinateReferenceSystem(), true);
        if(shpEspeCode==null && defaultEpsgCode == null){
            throw new IOException("数据中不包含坐标系信息且未指定默认坐标系!");
        }
        String sftStr = GetSFTStr(shpSchema, spatilName, defaultEpsgCode != null ? defaultEpsgCode:shpEspeCode.toString() );
        SimpleFeatureType pgSFT = DataUtilities.createType(typeName, sftStr);
        ds.createSchema(pgSFT);
    }

    /**
     * 在postgis中创建数据表
     *
     * @param ds         当前使用的postgis存储
     * @param typeName   入库后的图层名
     * @param epsgCode    空间参考epsgCode代码
     * @param spatilName 空间字段的名称 geom
     * @return
     */
    public static void createTableFromSHP(DataStore ds, String typeName, String spatilName, SimpleFeatureType shpSchema, String epsgCode) throws  SchemaException, IOException {
        String sftStr = GetSFTStr(shpSchema, spatilName, epsgCode);
        SimpleFeatureType pgSFT = DataUtilities.createType(typeName, sftStr);
        ds.createSchema(pgSFT);
    }

    public static boolean createTableNormal(DataStore ds, String typeName, String sftStr) {
        try {
            SimpleFeatureType pgSFT = DataUtilities.createType(typeName, sftStr);
            pgSFT.getUserData().put("geomesa.xz.precision", 17);
            ds.createSchema(pgSFT);
            return true;
        } catch (Exception e) {
            // 异常处理
            e.printStackTrace();
            return false;
        }
    }

    /**
     * shp数据导入postGIS 数据库
     * @param ds
     * @param typeName 入库表名
     * @param filePath  shp文件路径
     * @throws IOException
     */
    public static void writeShpToPG(DataStore ds, String typeName, String filePath,String encoding ) throws Exception {
        Transaction transaction = new DefaultTransaction("writeShpToPG" + typeName);
        // Transaction.AUTO_COMMIT
        try (FeatureWriter<SimpleFeatureType, SimpleFeature> writer = ds.getFeatureWriter(typeName, transaction)) {
            File file = new File(filePath);
            FileDataStore store = FileDataStoreFinder.getDataStore(file);
            ((ShapefileDataStore) store).setCharset(Charset.forName(encoding));
            SimpleFeatureSource featureSource = store.getFeatureSource();
            SimpleFeatureType shpSchema = featureSource.getSchema();
            Integer shpEspeCode = CRS.lookupEpsgCode(shpSchema.getCoordinateReferenceSystem(), true);
            SimpleFeatureCollection coll = featureSource.getFeatures();
            // SimpleFeatureIterator iterator = coll.features();

            MathTransform transform = null;
            SimpleFeatureType pgSFT = ds.getSchema(typeName);
            Set<ReferenceIdentifier> identifiers = pgSFT.getCoordinateReferenceSystem().getIdentifiers();
            if(!identifiers.isEmpty()){
                String typeNameEspgCode = identifiers.iterator().next().getCode();
                if(!typeNameEspgCode.equals(shpEspeCode.toString())){
                    transform = CRS.findMathTransform(shpSchema.getCoordinateReferenceSystem(),  pgSFT.getCoordinateReferenceSystem(), false);
                }
            }

            try (SimpleFeatureIterator iterator = coll.features()) {
                while (iterator.hasNext()) {
                    writer.hasNext();
                    SimpleFeature shpFeature = iterator.next();
                    SimpleFeature feature = writer.next();
                    for (AttributeDescriptor ad : pgSFT.getAttributeDescriptors()) {
                        String name = ad.getName().toString();
                        if (ad instanceof GeometryDescriptor) {
                            Geometry geo = (Geometry) shpFeature.getDefaultGeometry();
                            if(transform!=null){
                                geo = JTS.transform(geo, transform);
                            }
                            feature.setDefaultGeometry(geo);
                        } else {
                            SimpleFeatureType simpleFeatureType = shpFeature.getFeatureType();
                            for (AttributeDescriptor attr: simpleFeatureType.getAttributeDescriptors()) {
                                if(name.equals(attr.getLocalName().toLowerCase())) {
                                    feature.setAttribute(name, shpFeature.getAttribute(attr.getLocalName()));
                                }
                            }
                        }
                    }
                    writer.write();
                }
                transaction.commit();
            }

        } catch (Exception ex) {
            transaction.rollback();
            throw  ex;
        } finally {
            transaction.close();
        }
    }

    public static void writeShpToPG(DataStore ds, String typeName, FileDataStore fileDataStore, String shpEpsgCode, String encoding) throws Exception {
        Transaction transaction = new DefaultTransaction("writeShpToPG" + typeName);
        // Transaction.AUTO_COMMIT
        try (FeatureWriter<SimpleFeatureType, SimpleFeature> writer = ds.getFeatureWriter(typeName, transaction)) {
            FileDataStore store = fileDataStore;
            ((ShapefileDataStore) store).setCharset(Charset.forName(encoding));
            SimpleFeatureSource featureSource = store.getFeatureSource();
            SimpleFeatureType shpSchema = featureSource.getSchema();
            SimpleFeatureCollection coll = featureSource.getFeatures();
            // SimpleFeatureIterator iterator = coll.features();

            MathTransform transform = null;
            SimpleFeatureType pgSFT = ds.getSchema(typeName);
            Set<ReferenceIdentifier> identifiers = pgSFT.getCoordinateReferenceSystem().getIdentifiers();
            if(!identifiers.isEmpty()){
                String typeNameEspgCode = identifiers.iterator().next().getCode();
                if(!typeNameEspgCode.equals(shpEpsgCode)){
                    transform = CRS.findMathTransform(shpSchema.getCoordinateReferenceSystem(),  pgSFT.getCoordinateReferenceSystem(), false);
                }
            }

            try (SimpleFeatureIterator iterator = coll.features()) {
                while (iterator.hasNext()) {
                    writer.hasNext();
                    SimpleFeature shpFeature = iterator.next();
                    SimpleFeature feature = writer.next();
                    for (AttributeDescriptor ad : pgSFT.getAttributeDescriptors()) {
                        String name = ad.getName().toString();
                        if (ad instanceof GeometryDescriptor) {
                            Geometry geo = (Geometry) shpFeature.getDefaultGeometry();
                            if(transform!=null){
                                geo = JTS.transform(geo, transform);
                            }
                            feature.setDefaultGeometry(geo);
                        } else {
                            SimpleFeatureType simpleFeatureType = shpFeature.getFeatureType();
                            for (AttributeDescriptor attr: simpleFeatureType.getAttributeDescriptors()) {
                                String fieldName = "";
                                if(PinYinUtil.isContainHanzi(attr.getLocalName())){
                                    fieldName = PinYinUtil.getPinyin(attr.getLocalName());
                                }else {
                                    fieldName = attr.getLocalName().toLowerCase();
                                }
                                if(name.equals(fieldName)) {
                                    feature.setAttribute(name, shpFeature.getAttribute(attr.getLocalName()));
                                }
                            }
                        }
                    }
                    writer.write();
                }
                transaction.commit();
            }

        } catch (Exception ex) {
            transaction.rollback();
            throw  ex;
        } finally {
            transaction.close();
        }
    }

    public static SimpleFeatureType getSimpleFeatureTypeFromShp(String filePath, String encoding) throws IOException {
        SimpleFeatureType sft = null;
        FileDataStore store =getDataStoreFromShp(filePath, encoding);
        SimpleFeatureSource featureSource = store.getFeatureSource();
        sft = featureSource.getSchema();
        return sft;
    }

    public static FileDataStore getDataStoreFromShp(String filePath, String encoding) throws IOException {
        File file = new File(filePath);
        FileDataStore store = FileDataStoreFinder.getDataStore(file);
        ((ShapefileDataStore) store).setCharset(Charset.forName(encoding));
        return store;
    }

    /**
     * @param sft
     * @param spatialName
     * @param epsgCode
     * @return
     */
    public static String GetSFTStr(SimpleFeatureType sft, String spatialName, String epsgCode) {
        String sftStr = "";
        if (sft == null)
            return null;
        List<AttributeDescriptor> shpAtts = sft.getAttributeDescriptors();
        int len = shpAtts.size();
        String temp = "";
        String fieldName = "";
        for (int i = 0; i < len; i++) {
            temp = shpAtts.get(i).getType().getBinding().getSimpleName();
            fieldName = shpAtts.get(i).getName().getLocalPart();
            if (shpAtts.get(i) instanceof GeometryDescriptor) {
                sftStr += spatialName + ":" + temp + ":srid=" + epsgCode;
            }else if(temp.equals("Long")) {
                if(PinYinUtil.isContainHanzi(fieldName)){
                    sftStr += PinYinUtil.getPinyin(fieldName) + ":" + "Integer";
                }else {
                    sftStr += fieldName.toLowerCase() + ":" + "Integer";
                }
            }
            else {
                if(PinYinUtil.isContainHanzi(fieldName)){
                    sftStr += PinYinUtil.getPinyin(fieldName) + ":" + temp;
                }else {
                    sftStr += fieldName.toLowerCase() + ":" + temp;
                }
            }
            if (i != len - 1) {
                sftStr += ",";
            }

        }

        return sftStr;
    }

    public static List<SimpleFeature> getFeatures(List<Geometry> geos, DataStore ds,String typeName){
        List<SimpleFeature> featureList=new ArrayList<>();
        try {
            SimpleFeatureType simpleFeatureType = ds.getSchema(typeName);
            SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(simpleFeatureType);
            for(Geometry geo:geos) {
                SimpleFeature feature = featureBuilder.buildFeature(null);
                feature.setDefaultGeometry(geo);
                featureList.add(feature);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return featureList;
    }
}
