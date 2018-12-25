package com.uguke.java.logger;

/**
 * 功能描述：System.out.println()打印
 * @author LeiJue
 */
public class SystemAdapter extends BaseAdapter {

    public SystemAdapter(LoggerStrategy strategy) {
        super(strategy);
    }

    @Override
    public void log(Level level, String tag, String msg) {
        if (level == Level.NONE) {
            return;
        }
        System.out.println(METHOD_NAMES[level.mCode].toUpperCase() + "/" + tag + ": " + msg);
    }
}
