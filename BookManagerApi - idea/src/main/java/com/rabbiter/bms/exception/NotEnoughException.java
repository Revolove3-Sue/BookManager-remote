package com.rabbiter.bms.exception;

/**
 * 库存不足异常类
 * 当图书库存不足或图书已被全部借出时抛出此异常
 */
public class NotEnoughException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    /**
     * 无参构造函数
     * 创建一个不带错误信息的NotEnoughException
     */
    public NotEnoughException() {
        super();
    }

    /**
     * 带消息的构造函数
     * 
     * @param message 异常的具体描述信息
     */
    public NotEnoughException(String message) {
        super(message);
    }

    /**
     * 带消息和原因的构造函数
     * 
     * @param message 异常的具体描述信息
     * @param cause 导致当前异常的原因
     */
    public NotEnoughException(String message, Throwable cause) {
        super(message, cause);
    }
}
