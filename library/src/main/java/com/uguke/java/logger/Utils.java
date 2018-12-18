package com.uguke.java.logger;

import java.util.Locale;

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

    static String format(String msg, Object... args) {
        if (args.length == 0) {
            return msg;
        }
        return String.format(Locale.getDefault(), msg, args);
    }
}
