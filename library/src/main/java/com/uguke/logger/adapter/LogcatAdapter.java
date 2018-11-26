package com.uguke.logger.adapter;

import android.util.Log;

import com.uguke.logger.constant.Level;
import com.uguke.logger.strategy.LogcatStrategy;
import com.uguke.logger.strategy.FormatStrategy;

/**
 * 功能描述：Logcat日志
 * @author LeiJue
 * @date 2018/11/7
 */
public class LogcatAdapter extends LogAdapter {

    public LogcatAdapter() {
        super(new LogcatStrategy.Builder().build());
    }

    public LogcatAdapter(FormatStrategy strategy) {
        super(strategy);
    }

    @Override
    public void log(Level level, String tag, String message) {

        switch (level) {
            case VERBOSE:
                Log.v(tag, message);
                break;
            case DEBUG:
                Log.v(tag, message);
                break;
            case INFO:
                Log.v(tag, message);
                break;
            case WARN:
                Log.v(tag, message);
                break;
            case ERROR:
                Log.v(tag, message);
                break;
            case ASSERT:
                Log.v(tag, message);
                break;
            default:
        }
    }

}
