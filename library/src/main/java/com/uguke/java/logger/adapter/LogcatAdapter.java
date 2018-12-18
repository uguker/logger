package com.uguke.java.logger.adapter;

import com.uguke.java.logger.Level;
import com.uguke.java.logger.strategy.FormatStrategy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 功能描述：Logcat日志打印
 * @author LeiJue
 */
public class LogcatAdapter extends BaseAdapter {

    private final static String [] METHOD_NAMES = {"v", "d", "i", "w", "e", "wtf"};

    private boolean mFailed;
    private Class mLogcatClass;
    private Map<Level, Method> mMethodMap;

    public LogcatAdapter(FormatStrategy strategy) {
        super(strategy);
        mMethodMap = new ConcurrentHashMap<>();
        try {
            mLogcatClass = Class.forName("android.util.Log");
        } catch (ClassNotFoundException e) {
            mFailed = true;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void log(Level level, String tag, String message) {
        // 如果日志等级为不打印，则不进行后续操作
        if (level == Level.NONE) {
            return;
        }
        // 如果没有找到Logcat类，则不进行后续操作
        if (mLogcatClass == null || mFailed) {
            return;
        }

        Method method = mMethodMap.get(level);
        if (method == null) {
            try {
                method = mLogcatClass.getMethod(METHOD_NAMES[level.getCode()], String.class, String.class);
            } catch (NoSuchMethodException e) {
                return;
            }
        }

        try {
            method.invoke(null, tag, message);
        } catch (IllegalAccessException | InvocationTargetException e) {
            mFailed = true;
        }
    }
}
