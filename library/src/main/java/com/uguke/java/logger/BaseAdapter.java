package com.uguke.java.logger;

/**
 * 数据日志适配器
 * @author LeiJue
 */
public abstract class BaseAdapter {

    protected final static String [] METHOD_NAMES = {"d", "i", "w", "e"};

    protected LoggerStrategy mStrategy;

    public BaseAdapter(LoggerStrategy strategy) {
        this.mStrategy = strategy;
    }

    public LoggerStrategy getStrategy() {
        return mStrategy;
    }

    public void setStrategy(LoggerStrategy config) {
        this.mStrategy = config;
    }

    /**
     * 功能描述：日志输出
     * @param level 日志等级
     * @param tag   标签
     * @param msg   消息
     */
    public abstract void log(Level level, String tag, String msg);
}
