package com.rabbiter.bms.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 图书信息实体类
 */
@Setter
@Getter
@ToString
public class BookInfo {
    
    /**
     * 图书ID
     */
    private Integer bookId;

    /**
     * 图书名称
     */
    private String bookName;

    /**
     * 图书作者
     */
    private String bookAuthor;

    /**
     * 图书价格
     */
    private BigDecimal bookPrice;

    /**
     * 图书类型ID
     */
    private Integer bookTypeId;

    /**
     * 图书类型名称
     */
    private String bookTypeName;

    /**
     * 图书描述
     */
    private String bookDesc;

    /**
     * 是否被借出
     * 0: 未借出
     * 1: 已借出
     */
    private Byte isBorrowed;

    /**
     * 图书封面图片路径
     */
    private String bookImg;
}
