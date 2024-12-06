package com.rabbiter.bms.config;

import com.rabbiter.bms.utils.PathUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.lang.NonNull;


@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/**")
                //是否发送Cookie
                .allowCredentials(true)
                //设置放行哪些原始域   SpringBoot2.4.4下低版本使用.allowedOrigins("*")
                .allowedOriginPatterns("*")
                //放行哪些请求方式
                //.allowedMethods("GET","POST","PUT","DELETE")
                .allowedMethods("*") //或者放行全部
                //暴露哪些原始请求头部信息
                .allowedHeaders("*");
    }

    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        String winPath = PathUtils.getClassLoadRootPath() + "/src/main/resources/static/files/";

        //第一个方法设置访问路径前缀，第二个方法设置资源路径
        registry.addResourceHandler("/files/**").
                addResourceLocations("file:" + winPath);
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
