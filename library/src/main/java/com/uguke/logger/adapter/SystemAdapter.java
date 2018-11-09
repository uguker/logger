package com.uguke.logger.adapter;

import com.uguke.logger.constant.Level;
import com.uguke.logger.strategy.FormatStrategy;

/**
 * 功能描述：System.out.print()打印
 * @author LeiJue
 * @date 2018/11/7
 */
public class SystemAdapter extends LogAdapter {


    public SystemAdapter(FormatStrategy strategy) {
        super(strategy);
    }

    @Override
    public void log(Level level, String tag, String msg) {
        System.out.print(tag + ":" + msg);
    }
}
