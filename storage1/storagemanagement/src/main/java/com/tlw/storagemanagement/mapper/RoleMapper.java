package com.tlw.storagemanagement.mapper;

import com.tlw.storagemanagement.entity.Role;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleMapper {
    // 查询
    @Select("SELECT * FROM common_role WHERE id = #{id}")
    Role findById(@Param("id") int id);
}
