package com.tlw.storagemanagement.service;

import com.tlw.storagemanagement.entity.StorageRecord;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface StorageRecordService {
    // 查询
    StorageRecord findById(@Param("id") int id);

    // 删除
    int deleteUserById(@Param("id") int id);

    // 添加
    int createUser(StorageRecord storageRecord);
}
