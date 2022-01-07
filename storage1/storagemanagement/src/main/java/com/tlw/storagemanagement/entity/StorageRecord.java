package com.tlw.storagemanagement.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StorageRecord {
    private Integer id;
    private String shpName;
    private String layerName;
    private String filePosition;
    private String encoding;
    private String time;
    private String status;
}
