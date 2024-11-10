package com.rabbiter.bms.model;

import lombok.Data;

@Data  // 自动生成 getter、setter、toString、equals、hashCode 和构造函数
public class BookType {
    private Integer bookTypeId;  // 属性名采用 camelCase 风格
    private String bookTypeName; // 属性名采用 camelCase 风格
    private String bookTypeDesc; // 属性名采用 camelCase 风格

    // Lombok 会自动生成无参构造函数和包含所有属性的构造函数
}
