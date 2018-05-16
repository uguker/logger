package com.uguke.logger.strategy;

import com.uguke.logger.constant.Language;
import com.uguke.logger.constant.Level;
import com.uguke.logger.constant.Table;

/**
 * 功能描述：格式化配置
 */
public class FormatStrategy {

    // 表格实际长度是否和文字相等
    boolean equalLength;

    private boolean showThread;
    private int maxLength;
    private int methodCount;
    private int methodOffset;
    private String tag;
    private Table table;
    private Level level;
    private Language language;

    FormatStrategy() {
        equalLength = true;
        showThread = true;
        maxLength = 40;
        methodCount = 3;
        methodOffset = 3;
        tag = "Android";
        table = Table.DOUBLE;
        level = Level.VERBOSE;
        language = Language.CN;
    }

    public boolean isEqualLength() {
        return equalLength;
    }

    public FormatStrategy setEqualLength(boolean equalLength) {
        this.equalLength = equalLength;
        return this;
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

    public static class Builder {

        private boolean showThread;
        private int maxLength;
        private int methodCount;
        private int methodOffset;
        private String tag;
        private Table table;
        private Level level;
        private Language language;

        public Builder() {
            showThread = true;
            maxLength = 40;
            methodCount = 3;
            methodOffset = 3;
            tag = "Android";
            table = Table.DOUBLE;
            level = Level.VERBOSE;
            language = Language.CN;
        }

        public Builder showThread(boolean show) {
            this.showThread = show;
            return this;
        }

        public Builder maxLength (int length) {
            this.maxLength = length;
            return this;
        }

        public Builder methodCount (int count) {
            this.methodCount = count;
            return this;
        }

        public Builder methodOffset (int offset) {
            this.methodOffset = offset;
            return this;
        }

        public Builder tag (String tag) {
            this.tag = tag;
            return this;
        }

        public Builder table(Table table) {
            this.table = table;
            return this;
        }

        public Builder level(Level level) {
            this.level = level;
            return this;
        }

        public Builder language(Language language) {
            this.language = language;
            return this;
        }

        public FormatStrategy build() {
            FormatStrategy strategy = new FormatStrategy();
            strategy.setShowThread(showThread);
            strategy.setMaxLength(maxLength);
            strategy.setMethodCount(methodCount);
            strategy.setMethodOffset(methodOffset);
            strategy.setTag(tag);
            strategy.setTable(table);
            strategy.setLevel(level);
            strategy.setLanguage(language);
            return strategy;
        }
    }

}
