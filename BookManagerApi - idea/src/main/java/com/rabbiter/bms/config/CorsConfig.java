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
        // 配置跨域请求
        registry.addMapping("/**")
                // 是否允许发送 Cookie
                .allowCredentials(true)
                // 设置允许的原始域，使用 * 表示允许所有域
                .allowedOriginPatterns("*")
                // 允许的请求方式，使用 * 表示允许所有方式
                .allowedMethods("*")
                // 允许暴露的响应头
                .exposedHeaders("*")
                // 允许的请求头
                .allowedHeaders("*");
    }

    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        // 获取静态文件的路径
        String winPath = PathUtils.getClassLoadRootPath() + "/src/main/resources/static/files/";

        // 设置资源处理器，配置访问路径和资源路径
        registry.addResourceHandler("/files/**")
                .addResourceLocations("file:" + winPath);
        
        // 调用父类的方法以确保其他资源处理器的配置
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}