package com.uguke.java.logger;

/**
 * 功能描述：
 *
 * @author LeiJue
 * @date 2018/11/19
 */
final class Constants {

    static final String [] DOUBLE = {"╔", "╗", "╚", "╝", "╠", "╣", "═", "║", "═", "\u3000"};
    static final String [] SINGLE = {"┏", "┓", "┗", "┛", "┣", "┫", "━", "┃", "━", "\u3000"};

    /** 制表符四角 **/
    static final String [] [] TABLE_CORNER = {
            {"╔", "╗", "╝", "╚"},
            {"┏", "┓", "┛", "┗"}
    };
    /** 制表符边 **/
    static final String [] [] TABLE_SIDE = {
            {"╠", "╣", "║"},
            {"┣", "┫", "┃"}
    };
    /** 制表符线 **/
    static final String [] TABLE_LINE = {"═", "━"};
    /** 间隔 **/
    static final String TABLE_INDENT = "\u3000";

    /** **/
    static final String [] LANG_THREAD = {"线程：", "Thread: "};
    static final String [] LANG_INDEX = {"索引：", "Index: "};
    static final String [] LANG_KEY = {"键：", "Key: "};
    static final String [] LANG_TIME = {"日期：", "Time: "};
    static final String [] LANG_VALUE = {"值：", "Value: "};
    static final String ENTER = System.getProperty("line.separator");

}
