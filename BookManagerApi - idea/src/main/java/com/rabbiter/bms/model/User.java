package com.rabbiter.bms.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class User implements Serializable {
    private Integer userId;

    private String userName;

    private String userPassword;

    private Byte isAdmin;

}