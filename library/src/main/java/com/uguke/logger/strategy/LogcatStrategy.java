package com.uguke.logger.strategy;

import com.uguke.logger.constant.Language;
import com.uguke.logger.constant.Level;
import com.uguke.logger.constant.Table;

/**
 * 功能描述：Logcat日志打印
 * @author LeiJue
 * @date 2018/11/14
 */
public class LogcatStrategy extends FormatStrategy {

    private LogcatStrategy() {
        super();
        equalLength = false;
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

        public LogcatStrategy build() {
            LogcatStrategy strategy = new LogcatStrategy();
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
