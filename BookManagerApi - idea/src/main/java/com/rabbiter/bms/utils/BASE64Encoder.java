package com.rabbiter.bms.utils;

import java.util.Base64;

public class BASE64Encoder {
    public String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }
}
