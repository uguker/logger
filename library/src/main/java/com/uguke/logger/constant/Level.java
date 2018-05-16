package com.uguke.logger.constant;

public enum Level {

    VERBOSE(0),
    DEBUG(1),
    INFO(2),
    WARN(3),
    ERROR(4),
    ASSERT(5),
    NONE(6);

    int code;

    Level(int code) {
        this.code = code;
    }

    public static boolean isLow(Level one, Level two) {
        return one.code < two.code;
    }

}
