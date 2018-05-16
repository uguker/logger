package com.uguke.logger.adapter;

import android.util.Log;

import com.uguke.logger.constant.Level;
import com.uguke.logger.strategy.LogcatStrategy;
import com.uguke.logger.strategy.FormatStrategy;

public class LogcatAdapter extends LogAdapter {

    public LogcatAdapter() {
        super(LogcatStrategy.newBuilder().build());
    }

    public LogcatAdapter(FormatStrategy config) {
        super(config);
    }

    @Override
    public void log(Level level, String tag, String message) {
        switch (level) {
            case VERBOSE:
                Log.v(tag, message); break;
            case DEBUG:
                Log.d(tag, message); break;
            case INFO:
                Log.i(tag, message); break;
            case WARN:
                Log.w(tag, message); break;
            case ERROR:
                Log.e(tag, message); break;
            case ASSERT:
                Log.wtf(tag, message); break;
            case NONE: break;
        }
    }

}
