package com.tlw.storagemanagement.mapper;

import com.tlw.storagemanagement.entity.Role;
import com.tlw.storagemanagement.entity.User;
import com.tlw.storagemanagement.entity.UserRole;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserRoleMapper {
    @Select("select * from common_user_role where user_id=#{userId}")
    @Results(id="UserRoleMap",  value = {
            @Result(property = "id",column = "id"),
            @Result(property = "userId",column = "user_id"),
            @Result(property = "roleId",column = "role_id"),
            @Result(
                    property = "user",
                    column = "user_id",
                    javaType = User.class,
                    one= @One(select = "com.tlw.storagemanagement.mapper.UserMapper.getUserById")
            ),
            @Result(
                    property = "role",
                    column = "role_id",
                    javaType = Role.class,
                    one= @One(select = "com.tlw.storagemanagement.mapper.RoleMapper.findById")
            )
    })
    List<UserRole> findByUserId(Integer userId) throws  Exception;

}
