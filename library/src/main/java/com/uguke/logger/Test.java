package com.uguke.logger;

import com.uguke.logger.adapter.LogcatAdapter;
import com.uguke.logger.adapter.SystemAdapter;
import com.uguke.logger.constant.Language;
import com.uguke.logger.constant.Level;
import com.uguke.logger.constant.Table;
import com.uguke.logger.strategy.FormatStrategy;
import com.uguke.logger.strategy.LogcatStrategy;

/**
 * Created by LeiJue on 2018/11/7.
 */

public class Test {

    public static void main(String[] args) {
        FormatStrategy strategy = new LogcatStrategy.Builder()
                //.showThread(false)
                .maxLength(40)
                .methodCount(2)
                .methodOffset(1)
                .level(Level.VERBOSE)
                .language(Language.CN)
                .table(Table.DOUBLE)
                .tag("你好")
                .build();
        Logger.addLogAdapter(new LogcatAdapter(strategy));
        Logger.addLogAdapter(new SystemAdapter(strategy));

        Logger.e("你是哪个");
    }
}
