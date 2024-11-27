package com.rabbiter.bms.model;

import java.util.Date;

public class Borrow {
    private Integer borrowId;

    private Integer userId;

    private String userName;

    private Integer bookId;

    private String bookName;

    private Date borrowTime;

    private String borrowTimeStr;

    private Date returnTime;

    private String returnTimeStr;

    public Integer getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(Integer borrowId) {
        this.borrowId = borrowId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Date getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(Date borrowTime) {
        this.borrowTime = borrowTime;
    }

    public String getBorrowTimeStr() {
        return borrowTimeStr;
    }

    public void setBorrowTimeStr(String borrowTimeStr) {
        this.borrowTimeStr = borrowTimeStr;
    }

    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    public String getReturnTimeStr() {
        return returnTimeStr;
    }

    public void setReturnTimeStr(String returnTimeStr) {
        this.returnTimeStr = returnTimeStr;
    }
}
