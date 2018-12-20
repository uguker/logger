package com.uguke.java.logger.adapter;

import com.uguke.java.logger.strategy.FormatStrategy;

/**
 * 功能描述：System.out.println()打印
 * @author LeiJue
 */
public class SystemAdapter extends BaseAdapter {

    private static final String [] METHOD_NAMES = {"D", "I", "W", "E"};

    public SystemAdapter(FormatStrategy strategy) {
        super(strategy);
    }

    @Override
    public void log(int level, String tag, String msg) {
        if (level == -1) {
            return;
        }
        System.out.println(METHOD_NAMES[level] + "/" + tag + ":" + msg);
    }
}
