package com.tlw.storagemanagement.mapper;

import com.tlw.storagemanagement.entity.StorageRecord;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageRecordMapper {
    // 查询
    @Select("SELECT * FROM storage_record WHERE id = #{id}")
    @Results({
            @Result(column = "layer_name", property = "layerName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "file_position", property = "filePosition", jdbcType = JdbcType.VARCHAR)
    })
    StorageRecord findById(@Param("id") int id);

    // 删除
    @Delete("DELETE FROM T_USER WHERE id = #{id}")
    int deleteUserById(@Param("id") int id);

    // 添加
    @Insert("INSERT INTO storage_record(shpname,layer_name,file_position,encoding,time,status) VALUES (#{shpname},#{layerName}, #{filePosition}, #{encoding}, #{time}, #{status})")
    int insertUser(StorageRecord storageRecord);
}
