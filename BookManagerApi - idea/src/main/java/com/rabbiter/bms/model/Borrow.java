package com.rabbiter.bms.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
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

}
