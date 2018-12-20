package com.uguke.java.logger.adapter;

import com.uguke.java.logger.strategy.FormatStrategy;

/**
 * 数据日志适配器
 * @author LeiJue
 */
public abstract class BaseAdapter {

    protected FormatStrategy strategy;

    public BaseAdapter(FormatStrategy strategy) {
        this.strategy = strategy;
    }

    public FormatStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(FormatStrategy config) {
        this.strategy = config;
    }

    /**
     * 功能描述：日志输出
     * @param level 日志等级
     * @param tag   标签
     * @param msg   消息
     */
    public abstract void log(int level, String tag, String msg);
}
