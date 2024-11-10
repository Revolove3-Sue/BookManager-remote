package com.rabbiter.bms.model;

import lombok.Data;

import java.io.Serializable;

@Data  // Lombok 自动生成 getter、setter、toString、equals、hashCode 和构造函数
public class User implements Serializable {
    private Integer userId;      // 使用 camelCase 命名风格
    private String userName;
    private String userPassword;
    private Byte isAdmin;

    // Lombok 会自动生成无参构造函数和包含所有属性的构造函数
}
