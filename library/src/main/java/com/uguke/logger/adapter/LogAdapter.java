package com.uguke.logger.adapter;

import com.uguke.logger.constant.Level;
import com.uguke.logger.strategy.FormatStrategy;

/**
 * 功能描述：数据日志适配器
 * @author LeiJue
 * @date 2018/11/14
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

    /**
     * 功能描述：日志打印
     * @param level 日志等级
     * @param tag   标签
     * @param msg   消息
     */
    public abstract void log(Level level, String tag, String msg);

}
