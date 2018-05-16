package com.uguke.logger;

import com.uguke.logger.constant.Level;

/**
 * 功能描述：多功能的打印
 */
public interface MultiPrinter {
    void v(String msg, Object... args);
    void v(String msg, Throwable t, Object... args);
    void v(Object msg);
    void v(Object msg, Throwable t);
    void d(String msg, Object... args);
    void d(String msg, Throwable t, Object... args);
    void d(Object msg);
    void d(Object msg, Throwable t);
    void i(String msg, Object... args);
    void i(String msg, Throwable t, Object... args);
    void i(Object msg);
    void i(Object msg, Throwable t);
    void w(String msg, Object... args);
    void w(String msg, Throwable t, Object... args);
    void w(Object msg);
    void w(Object msg, Throwable t);
    void e(String msg, Object... args);
    void e(String msg, Throwable t, Object... args);
    void e(Object msg);
    void e(Object msg, Throwable t);
    void wtf(String msg, Object... args);
    void wtf(String msg, Throwable t, Object... args);
    void wtf(Object msg);
    void wtf(Object msg, Throwable t);
    void json(String json);
    void json(Level level, String json);
}
