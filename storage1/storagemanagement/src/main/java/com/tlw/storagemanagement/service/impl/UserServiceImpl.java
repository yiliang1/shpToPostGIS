package com.tlw.storagemanagement.service.impl;


import com.tlw.storagemanagement.entity.User;
import com.tlw.storagemanagement.entity.UserEntity;
import com.tlw.storagemanagement.mapper.UserMapper;
import com.tlw.storagemanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    public User getUserById(int id) {
        return userMapper.getUserById(id);
    }

    @Override
    public User findByUsername(String name) {
        return userMapper.findByUsername(name);
    }
}
