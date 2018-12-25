package com.uguke.java.logger;

/**
 * 功能描述：
 *
 * @author LeiJue
 * @date 2018/11/19
 */
public enum Level {
    /** 日志级别Debug **/
    D(0),
    /** 日志级别Info **/
    I(1),
    /** 日志级别Warn **/
    W(2),
    /** 日志级别Error **/
    E(3),
    /** 关闭日志 **/
    NONE(4);
    protected int mCode;
    Level(int code) {
        mCode = code;
    }
}
