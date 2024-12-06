package com.rabbiter.bms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import lombok.extern.slf4j.Slf4j;

/**
 * 全局异常处理器
 * 用于统一处理系统中出现的各类异常，并返回规范化的错误响应
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理所有未被特定异常处理器捕获的异常
     * @param e 捕获到的异常
     * @return 包含错误信息的ResponseEntity对象
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<String> handleException(Exception e) {
        String message = e.getMessage();
        log.error("系统异常: {}", message, e);
        
        // 根据异常信息特征，返回对应的错误提示
        message = parseExceptionMessage(message);
        
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    /**
     * 解析异常信息，返回用户友好的错误提示
     * @param message 原始异常信息
     * @return 处理后的错误提示
     */
    private String parseExceptionMessage(String message) {
        if (message == null) {
            return "系统发生未知错误";
        }
        
        // 数据库连接相关异常
        if (message.contains("(using password: YES)")) {
            if (!message.contains("'root'@'")) {
                return "数据库用户认证失败 (500)";
            } else if (message.contains("'root'@'localhost'")) {
                return "本地数据库连接失败 (500)";
            }
        }
        
        // 数据库结构相关异常
        if (message.contains("Table") && message.contains("doesn't exist")) {
            return "数据表不存在 (500)";
        }
        if (message.contains("Unknown database")) {
            return "数据库不存在 (500)";
        }
        
        // Redis相关异常
        if (message.contains("edis")) {
            return "Redis服务连接异常 (500)";
        }
        
        // JDBC连接异常
        if (message.contains("Failed to obtain JDBC Connection")) {
            return "数据库连接获取失败 (500)";
        }
        
        // SQL语法异常
        if (message.contains("SQLSyntaxErrorException")) {
            return "SQL语法错误 (500)";
        }
        
        return message;
    }
}
