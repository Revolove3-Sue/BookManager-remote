package com.rabbiter.bms.model;

import lombok.Data;
import lombok.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data  // 自动生成 getter、setter、toString、equals、hashCode 和构造函数
public class Borrow {
    private Integer borrowId;  // 属性名采用 camelCase 风格
    private Integer userId;
    private String userName;
    private Integer bookId;
    private String bookName;

    @NonNull
    private Date borrowTime;  // 使用 Date 类型来表示借书时间
    private String borrowTimeStr;  // 存储格式化后的时间字符串

    private Date returnTime;
    private String returnTimeStr;  // 存储格式化后的还书时间字符串

    // Lombok 会自动生成无参构造函数和包含所有属性的构造函数

    // 格式化 borrowTime 为字符串
    public String getBorrowTimeStr() {
        if (borrowTime != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(borrowTime);
        }
        return null;
    }

    // 格式化 returnTime 为字符串
    public String getReturnTimeStr() {
        if (returnTime != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(returnTime);
        }
        return null;
    }
}
