package com.tlw.storagemanagement;

import com.tlw.storagemanagement.Properties.PostgisProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableConfigurationProperties({PostgisProperties.class})
@MapperScan(basePackages = {"com.tlw.storagemanagement.mapper"})
public class StoragemanagementApplication {
    public static void main(String[] args) {

        SpringApplication.run(StoragemanagementApplication.class, args);
    }

}
