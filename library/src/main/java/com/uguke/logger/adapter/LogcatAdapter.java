package com.uguke.logger.adapter;

import android.util.Log;

import com.uguke.logger.constant.Level;
import com.uguke.logger.strategy.LogcatStrategy;
import com.uguke.logger.strategy.FormatStrategy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 功能描述：Logcat日志
 * @author LeiJue
 * @date 2018/11/7
 */
public class LogcatAdapter extends LogAdapter {

    private final static String [] METHOD_NAMES = {"v", "d", "i", "w", "e", "wtf"};

    private Class logcatClass;
    private Map<Level, Method> methodMap;

    public LogcatAdapter() {
        super(new LogcatStrategy.Builder().build());
        methodMap = new ConcurrentHashMap<>();
        try {
            logcatClass = Class.forName("android.util.Log");
        } catch (ClassNotFoundException e) {
            //e.printStackTrace();
        }

    }

    public LogcatAdapter(FormatStrategy strategy) {
        super(strategy);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void log(Level level, String tag, String message) {
        // 如果日志等级为不打印，则不进行后续操作
        if (level == Level.NONE) {
            return;
        }
        // 如果没有找到Logcat类，则不进行后续操作
        if (logcatClass == null) {
            return;
        }

        Method method = methodMap.get(level);
        if (method == null) {
            try {
                method = logcatClass.getMethod(METHOD_NAMES[level.getCode()], String.class, String.class);
            } catch (NoSuchMethodException e) {
                return;
            }
        }
        try {
            method.invoke(null, tag, message);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
