package com.uguke.java.logger;

import com.uguke.java.logger.adapter.DiskAdapter;
import com.uguke.java.logger.adapter.LogcatAdapter;
import com.uguke.java.logger.adapter.SystemAdapter;
import com.uguke.java.logger.strategy.DiskStrategy;
import com.uguke.java.logger.strategy.FormatStrategy;

/**
 * Created by LeiJue on 2018/11/7.
 */

public class Test {

    public static void main(String[] args) {
        FormatStrategy strategy = new FormatStrategy.Builder()
                .hasThread(true)
                .length(20)
                .methodCount(1)
                .methodOffset(1)
                //.level(0)
                .langCn()
                .tableDouble()
                .tag("装机宝")
                .build();
        //Logger.addLogAdapter(new DiskAdapter(strategy));
        Logger.addLogAdapter(new SystemAdapter(strategy));

        Logger.t("数").e("你是哪个三卡卡出来的哦卡出来的哦卡出来卡出来的哦asdfasdfaefasdf卡出来的哦卡出来卡出来的哦卡出来的哦卡出来卡出来的哦卡出来的哦卡出来的哦");
        System.out.println("ss".getBytes().length);
        System.out.println("龙".getBytes().length);

        System.out.println(Utils.sub("天才ss", 8));
        System.out.println("123456789101111212121".getBytes().length);

    }

    private static void ss(int... s) {
        System.out.println("数" + s.length);
    }
}
