package com.uguke.java.logger;

import com.uguke.java.logger.strategy.FormatStrategy;

import java.util.Locale;

/**
 * 功能描述：
 *
 * @author LeiJue
 * @date 2018/11/19
 */
class Utils {

    static StringBuilder sBuilder = new StringBuilder();

    static boolean empty(String text) {
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

    static boolean isShortChar(char c) {
        int code = (int) c;
        return code > 32 && code < 127;
    }

    static String format(String msg, Object... args) {
        if (args.length == 0) {
            return msg;
        }
        return String.format(Locale.getDefault(), msg, args);
    }

    static String tag(String str1, String str2) {
        String tag = "";
        if (!empty(str1)) {
            tag += "[" + str1 + "]";
        }
        if (!empty(str2)) {
            tag += "[" + str2 + "]";
        }
        return tag;
    }

    static String getTop(FormatStrategy strategy) {
        int len = strategy.getLength() * 2;
        int table = strategy.getTable();

        StringBuilder builder = new StringBuilder();
        builder.setLength(0);

        for(int i = 0; i < len; i++) {
            builder.append(Constants.TABLE_LINE[table]);
        }
        return Constants.TABLE_CORNER[table][0] + builder.toString() +
                Constants.TABLE_CORNER[table][1];
    }

    static String getBottom(FormatStrategy strategy) {
        int len = strategy.getLength() * 2;
        int table = strategy.getTable();

        StringBuilder builder = new StringBuilder();
        builder.setLength(0);

        for(int i = 0; i < len; i++) {
            builder.append(Constants.TABLE_LINE[table]);
        }
        return Constants.TABLE_CORNER[table][3] + builder.toString() +
                Constants.TABLE_CORNER[table][2];
    }

    static String getDivider(FormatStrategy strategy) {
        int len = strategy.getLength() * 2;
        int table = strategy.getTable();
        StringBuilder builder = new StringBuilder();
        builder.setLength(0);
        for(int i = 0; i < len; i++) {
            builder.append(Constants.TABLE_LINE[table]);
        }
        return Constants.TABLE_SIDE[table][0] + builder.toString() +
                Constants.TABLE_SIDE[table][1];
    }
}
