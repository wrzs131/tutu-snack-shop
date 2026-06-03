package com.itheima.exception;

/**
 * 业务异常
 * RuntimeException是Java标准库中的一个核心异常类，它是 BaseException 的父类
 */
public class BaseException extends RuntimeException{
    public BaseException() {
    }

    public BaseException(String msg) {
        super(msg);
    }
}
