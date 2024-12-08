package com.rabbiter.bms.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 图书类型实体类
 */
@Getter
@Setter
@ToString
public class BookType {
    
    /**
     * 图书类型ID
     */
    private Integer bookTypeId;

    /**
     * 图书类型名称
     */
    private String bookTypeName;

    /**
     * 图书类型描述
     */
    private String bookTypeDesc;
}