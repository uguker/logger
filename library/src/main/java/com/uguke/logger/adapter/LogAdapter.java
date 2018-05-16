package com.uguke.logger.adapter;

import com.uguke.logger.constant.Level;
import com.uguke.logger.strategy.FormatStrategy;

/**
 * 数据日志适配器
 */
public abstract class LogAdapter {

    protected FormatStrategy strategy;

    public LogAdapter(FormatStrategy strategy) {
        this.strategy = strategy;
    }

    public FormatStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(FormatStrategy config) {
        this.strategy = config;
    }

    public abstract void log(Level level, String tag, String msg);

}
