package com.tlw.storagemanagement.controller;

import com.tlw.storagemanagement.entity.StorageRecord;
import com.tlw.storagemanagement.service.StorageRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/storage-record")
public class StorageRecordController {
    @Autowired
    StorageRecordService storageRecordService;
    @RequestMapping(value = "/{id}", method = {RequestMethod.GET})
    public String findById(@PathVariable int id){
        return storageRecordService.findById(id).toString();
    }

    @RequestMapping(value = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    public StorageRecord Create(@RequestBody StorageRecord storageRecord){
        int num = storageRecordService.createUser(storageRecord);
        if(num>0){
            return storageRecordService.findById(1);
        }
        return null;
    }

}
