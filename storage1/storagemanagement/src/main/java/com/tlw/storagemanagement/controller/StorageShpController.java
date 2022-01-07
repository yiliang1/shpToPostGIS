package com.tlw.storagemanagement.controller;

import com.tlw.storagemanagement.Properties.PostgisProperties;
import com.tlw.storagemanagement.dto.StorageShpDto;
import com.tlw.storagemanagement.service.StorageShpService;
import com.tlw.storagemanagement.utils.ResponseEntityResult;
import com.tlw.storagemanagement.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Api(value = "storageShp", tags = "存储shp到pg(StorageShpController)", description = "存储shp到pg")
@RestController
@RequestMapping(path = "/rest/storageshp")
public class StorageShpController {
    @Autowired
    PostgisProperties postgisProperties;

    @Autowired
    StorageShpService storageShpService;

    /**
     * 导入shp文件进入postgis数据库
     * @param inputType 导入类型 (file/directory)
     * @param typeName 入库postgis后的图层名
     * @param shpPath shp文件完整路径
     * @return
     */
    @ApiOperation(value = "导入shp文件进入postgis数据库", notes = "导入shp文件进入postgis数据库", produces = "")
    @RequestMapping(value = "/import",params = {"inputType", "typeName", "shpPath"}, method = {RequestMethod.POST, RequestMethod.GET})
    public  ResponseEntity<Result<String>> importDataFormShp(@ApiParam(value = "导入类型 (file/directory)") @RequestParam(value = "inputType") String inputType,
                                     @ApiParam(value = "入库postgis后的图层名") @RequestParam(value = "typeName") String typeName,
                                     @ApiParam(value = "shp文件完整路径") @RequestParam(value = "shpPath") String shpPath,
                                     @ApiParam(value = "shp属性文件编码") @RequestParam(value = "encoding") String encoding,
                                     @ApiParam(value = "自定义坐标系编码") @RequestParam(value = "espgCode") String espgCode) {
        StorageShpDto storageShpDto = new StorageShpDto(inputType, typeName, shpPath, encoding,espgCode);
        return importDataFromShp(storageShpDto);
    }

    /**
     * 导入shp文件进入postgis数据库
     * @param param
     * @return
     */
    @ApiOperation(value = "导入shp文件进入postgis数据库", notes = "导入shp文件进入postgis数据库", produces = "")
    @ApiImplicitParam(name = "StorageShpDto", value = "入库信息", dataTypeClass = StorageShpDto.class)
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public ResponseEntity<Result<String>> importDataFromShp(@RequestBody StorageShpDto param) {
        boolean ingestFilesBoolean = false;
        if (param.getInputType().equals("file")) {
            String[] files = param.getShpPath().split(",");
            ingestFilesBoolean = storageShpService.ingestFiles(postgisProperties, param.getTypeName(), files, "geom",  param.getEspgCode(), param.getEncoding());
        } else if (param.getInputType().equals("directory")) {
            File file = new File(param.getShpPath());
            List<File> fileList = new ArrayList<>();
            getFiles(file, fileList);
            String[] files = new String[fileList.size()];
            for(int i = 0 ; i < fileList.size() ; i++) {
                files[i] = fileList.get(i).getAbsolutePath();
            }
            ingestFilesBoolean = storageShpService.ingestFiles(postgisProperties,param.getTypeName(), files,"geom",  param.getEspgCode(),param.getEncoding());
        }
        if(ingestFilesBoolean) {
            return ResponseEntityResult.success("入库成功");
        } else {
            return ResponseEntityResult.error("入库失败，请联系管理员");
        }
    }

    private void getFiles(File file , List<File> files){
        if(file.isDirectory()) {
            String[] list = file.list();
            for (String name : list) {
                File child = new File(file, name);
                if(child.isFile() && name.endsWith(".shp")) {
                    files.add(child);
                } else if(child.isDirectory()){
                    getFiles(child, files);
                }
            }
        }
    }
}
