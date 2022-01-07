package com.tlw.storagemanagement.controller;

import com.tlw.storagemanagement.utils.ResponseEntityResult;
import com.tlw.storagemanagement.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.HashSet;

/* 类注解 */
@Api (value = "hello 类示例")
@RestController
public class HelloController {
    /* 方法注解 */
    @ApiOperation(value = "hello类的方法注解")
    @GetMapping("/hello")
    public ResponseEntity<Result<String>> hello(/* 参数注解 */ @ApiParam(value = "hello方法参数" , required=false ) @RequestParam(required = false) String name){
        String res = "Hello " + (name == null ? "无名" : name);

        return ResponseEntityResult.success(res);
    }

    @GetMapping("/orders/findAll")
    public String findordersAll() {
        return "orders list";
    }

    @GetMapping("/users/findAll")
    public String findusersAll() {
        return "users list";
    }
}
