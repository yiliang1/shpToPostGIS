package com.tlw.storagemanagement.service;

import com.tlw.storagemanagement.entity.User;
import com.tlw.storagemanagement.entity.UserEntity;

public interface UserService {
     User getUserById(int id);
     User findByUsername(String name);
}
