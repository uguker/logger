package com.uguke.logger;

import com.uguke.logger.adapter.LogAdapter;
import com.uguke.logger.constant.Level;

/**
 * 功能描述：简单的打印
 */
public interface SimplePrinter {
    SimplePrinter t(String tag);
    SimplePrinter t(int methodCount);
    SimplePrinter t(String tag, int methodCount);
    void log(Level level, Object msg, Throwable t);
    void json(Level level, String json);
    void addLogAdapter(LogAdapter adapter);
    void removeLogAdapter(Class clazz);
    void removeAllLogAdapter();
}
