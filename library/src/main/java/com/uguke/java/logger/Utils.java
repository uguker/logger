package com.uguke.java.logger;

import java.util.Locale;

import static com.uguke.java.logger.Printer.TABLE_CORNER;
import static com.uguke.java.logger.Printer.TABLE_LINE;
import static com.uguke.java.logger.Printer.TABLE_SIDE;

/**
 * 功能描述：
 *
 * @author LeiJue
 * @date 2018/11/19
 */
class Utils {



    static boolean isEmpty(String text) {
        return text == null || text.length() == 0;
    }

    static String sub(String text, int length) {

        int index = 0;
        int len = 0;
        for(int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            int code = (int) c;
            len += code > 32 && code < 127 ? 1 : 2;
            if (len <= length) {
                index ++;
            }
        }
        if (index == 0) {
            return null;
        }
        return text.substring(0, index);
    }

    static Object format(Object msg, Object... args) {
        if (args.length == 0 || !(msg instanceof CharSequence)) {
            return msg;
        }
        return String.format(Locale.getDefault(), (String) msg, args);
    }

    static String tag(String str1, String str2) {
        String tag = "";
        if (!isEmpty(str1)) {
            tag += str1;
        }
        if (!isEmpty(str2)) {
            tag += "-";
            tag += str2;
        }
        return tag;
    }

    /**
     * 功能描述：生成顶部
     * @param strategy  配置策略
     * @return 顶部字符串
     */
    static String getTop(LoggerStrategy strategy) {
        int len = strategy.getLength() * 2;
        StringBuilder builder = new StringBuilder();
        builder.setLength(0);

        for(int i = 0; i < len; i++) {
            builder.append(TABLE_LINE);
        }
        return TABLE_CORNER[0] + builder.toString() + TABLE_CORNER[1];
    }

    /**
     * 功能描述：生成底部
     * @param strategy  配置策略
     * @return 底部字符串
     */
    static String getBottom(LoggerStrategy strategy) {
        int len = strategy.getLength() * 2;
        StringBuilder builder = new StringBuilder();
        builder.setLength(0);

        for(int i = 0; i < len; i++) {
            builder.append(TABLE_LINE);
        }
        return TABLE_CORNER[3] + builder.toString() + TABLE_CORNER[2];
    }

    /**
     * 功能描述：生成分割线
     * @param strategy  配置策略
     * @return 分割线字符串
     */
    static String getDivider(LoggerStrategy strategy) {
        int len = strategy.getLength() * 2;
        StringBuilder builder = new StringBuilder();
        builder.setLength(0);
        for(int i = 0; i < len; i++) {
            builder.append(TABLE_LINE);
        }
        return TABLE_SIDE[0] + builder.toString() + TABLE_SIDE[1];
    }

}
