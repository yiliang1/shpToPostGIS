package com.tlw.storagemanagement.mapper;


import com.tlw.storagemanagement.entity.User;
import com.tlw.storagemanagement.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface  UserMapper {
    User getUserById(int id);

    User findByUsername(String name);
}
