package com.tlw.storagemanagement.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StorageShpDto {

    public StorageShpDto() {

    }

    /**
     * @param inputType 导入类型 (file/directory)
     * @param typeName 入库postgis后的图层名
     * @param shpPath shp文件完整路径
     */
    public StorageShpDto(String inputType, String typeName, String shpPath, String encoding, String espgCode) {
        this.inputType = inputType;
        this.shpPath = shpPath;
        this.typeName = typeName;
        this.encoding = encoding;
        this.espgCode = espgCode;
    }

    /**
     * 导入类别 file or directory
     */
    @ApiModelProperty(value = "导入类别 file or directory") // 对属性进行简要说明
    private String inputType;
    /**
     * shp完整路径
     */
    @ApiModelProperty(value = "shp完整路径")
    private String shpPath;
    /**
     * 入到postgis后的图层名
     */
    @ApiModelProperty(value = "入库postgis后的图层名")
    private String typeName;

    @ApiModelProperty(value = "shp属性文件的编码")
    private String encoding;

    @ApiModelProperty(value = "自定义坐标系编码")
    private String espgCode;
}
