package com.rabbiter.bms.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 用户实体类
 * 实现Serializable接口以支持Redis序列化
 */
@Getter
@Setter
@ToString
public class User implements Serializable {
    
    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户密码
     */
    private String userPassword;

    /**
     * 是否为管理员
     * 0: 普通用户
     * 1: 管理员
     */
    private Byte isAdmin;
}