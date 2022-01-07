package com.tlw.storagemanagement.service.impl;

import com.tlw.storagemanagement.entity.StorageRecord;
import com.tlw.storagemanagement.mapper.StorageRecordMapper;
import com.tlw.storagemanagement.service.StorageRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StorageRecordServiceImpl implements StorageRecordService {
    @Autowired
    private StorageRecordMapper storageRecordMapper;
    @Override
    public StorageRecord findById(int id) {
        return storageRecordMapper.findById(id);
    }

    @Override
    public int deleteUserById(int id) {
        return storageRecordMapper.deleteUserById(id);
    }

    @Override
    public int createUser(StorageRecord storageRecord) {
        return storageRecordMapper.insertUser(storageRecord);
    }
}
