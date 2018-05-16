package com.uguke.logger.strategy;

import com.uguke.logger.constant.Language;
import com.uguke.logger.constant.Level;
import com.uguke.logger.constant.Table;

/**
 * 功能描述：格式化配置
 */
public class FormatStrategy {

    private boolean showThread;
    private int maxLength;
    private int methodCount;
    private int methodOffset;
    private String tag;
    private Table table;
    private Level level;
    private Language language;

    FormatStrategy() {
        showThread = true;
        maxLength = 40;
        methodCount = 3;
        methodOffset = 3;
        tag = "Android";
        table = Table.DOUBLE;
        level = Level.VERBOSE;
        language = Language.CN;
    }

    public boolean isShowThread() {
        return showThread;
    }

    public void setShowThread(boolean showThread) {
        this.showThread = showThread;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getMethodCount() {
        return methodCount;
    }

    public void setMethodCount(int methodCount) {
        this.methodCount = methodCount;
    }

    public int getMethodOffset() {
        return methodOffset;
    }

    public void setMethodOffset(int methodOffset) {
        this.methodOffset = methodOffset;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

}
