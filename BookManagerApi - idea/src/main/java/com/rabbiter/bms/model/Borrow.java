package com.rabbiter.bms.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 图书借阅记录实体类
 */
@Getter
@Setter
@ToString
public class Borrow {
    
    /**
     * 借阅记录ID
     */
    private Integer borrowId;

    /**
     * 借阅用户ID
     */
    private Integer userId;

    /**
     * 借阅用户名称
     */
    private String userName;

    /**
     * 借阅图书ID
     */
    private Integer bookId;

    /**
     * 借阅图书名称
     */
    private String bookName;

    /**
     * 借阅时间
     */
    private Date borrowTime;

    /**
     * 借阅时间字符串形式
     * 格式：yyyy-MM-dd HH:mm:ss
     */
    private String borrowTimeStr;

    /**
     * 归还时间
     */
    private Date returnTime;

    /**
     * 归还时间字符串形式
     * 格式：yyyy-MM-dd HH:mm:ss
     */
    private String returnTimeStr;
}