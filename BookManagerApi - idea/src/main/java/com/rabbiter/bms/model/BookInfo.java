package com.rabbiter.bms.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class BookInfo {
    private Integer bookId;

    private String bookName;

    private String bookAuthor;

    private BigDecimal bookPrice;

    private Integer bookTypeId;

    private String bookTypeName;

    private String bookDesc;

    private Byte isBorrowed;

    private String bookImg;

}
