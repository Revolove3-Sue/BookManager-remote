package com.rabbiter.bms.model;

import java.math.BigDecimal;

public class BookInfo {
    private int bookId;
    private String bookName;
    private String bookAuthor;
    private BigDecimal bookPrice;
    private int bookTypeId;
    private String bookTypeName;
    private String bookDesc;
    private BorrowStatus isBorrowed;
    private String bookImg;

    public BookInfo(int bookId, String bookName, String bookAuthor, BigDecimal bookPrice) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookPrice = bookPrice;
    }

    // Getter and Setter methods...

    public enum BorrowStatus {
        AVAILABLE, BORROWED
    }
}
