package com.rabbiter.bms.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 生成Token的工具类  
 *
 */
public class TokenProcessor {

    private TokenProcessor(){};
    private static final TokenProcessor instance = new TokenProcessor();
    private static final Logger logger = LoggerFactory.getLogger(TokenProcessor.class);

    public static TokenProcessor getInstance() {
        return instance;
    }

    /**
     * 生成Token  
     * @return
     */
    public String makeToken() {
        String token = (System.currentTimeMillis() + new Random().nextInt(999999999)) + "";
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte md5[] =  md.digest(token.getBytes());
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(md5);
        } catch (NoSuchAlgorithmException e) {
            // 记录错误并返回空值
            logger.error("Token生成失败: ", e);
            return null;
        }
    }
}  