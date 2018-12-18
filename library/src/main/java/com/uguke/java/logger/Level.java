package com.uguke.java.logger;

/**
 * 功能描述：日志等级
 * @author LeiJue
 * @date 2018/11/7
 */
public enum Level {
    /** **/
    VERBOSE(0),
    DEBUG(1),
    INFO(2),
    WARN(3),
    ERROR(4),
    NONE(5);

    int code;

    Level(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static boolean isLow(Level one, Level two) {
        return one.code < two.code;
    }

}
