package com.tlw.storagemanagement.controller;

import com.tlw.storagemanagement.service.UserService;
import com.tlw.storagemanagement.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("{id}")
    public String GetUser(@PathVariable int id){
        return userService.getUserById(id).toString();
    }
}
