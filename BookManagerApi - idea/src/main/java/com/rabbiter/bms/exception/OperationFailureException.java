package com.rabbiter.bms.exception;

/**
 * 操作失败异常
 * 用于处理业务操作过程中的失败情况
 * 
 * @author trz
 * @since 1.0.0
 */
public class OperationFailureException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    /**
     * 无参构造函数
     */
    public OperationFailureException() {
        super();
    }

    /**
     * 带有错误信息的构造函数
     *
     * @param message 错误信息
     */
    public OperationFailureException(String message) {
        super(message);
    }

    /**
     * 带有错误信息和原因的构造函数
     *
     * @param message 错误信息
     * @param cause 导致异常的原因
     */
    public OperationFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}
