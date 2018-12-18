package com.uguke.java.logger.adapter;

import com.uguke.java.logger.Level;
import com.uguke.java.logger.strategy.FormatStrategy;

/**
 * 功能描述：System.out.println()打印
 * @author LeiJue
 */
public class SystemAdapter extends BaseAdapter {

    private static final String [] METHOD_NAMES = {"V", "D", "I", "W", "E", "E"};

    public SystemAdapter(FormatStrategy strategy) {
        super(strategy);
    }

    @Override
    public void log(Level level, String tag, String msg) {
        if (level == Level.NONE) {
            return;
        }
        System.out.println(METHOD_NAMES[level.getCode()] + "/" + tag + ":" + msg);
    }
}
