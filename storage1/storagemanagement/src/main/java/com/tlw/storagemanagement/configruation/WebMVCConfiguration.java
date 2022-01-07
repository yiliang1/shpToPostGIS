package com.tlw.storagemanagement.configruation;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfiguration implements WebMvcConfigurer {
//    //增加映射关系
//    @Override
//    public void addViewControllers (ViewControllerRegistry registry ) {
//        //使得/login/page 映射为login
//        registry.addViewController("/login/page").setViewName("login");
//        //使得/logout/page 映射为logout_welcome
//        registry.addViewController("/logout/page").setViewName("logout_welcome");
//         //使得/logout映射为logout
//        registry.addViewController ("/logout").setViewName("logout");
//        registry.addViewController("/").setViewName("redirect:/portal");
//    }
}
