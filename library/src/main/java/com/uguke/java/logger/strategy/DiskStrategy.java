package com.uguke.java.logger.strategy;

import android.annotation.SuppressLint;

import com.uguke.java.logger.Level;
import com.uguke.java.logger.Table;
import com.uguke.java.logger.Language;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能描述：硬盘存储策略
 * @author LeiJue
 * @date 2018/12/18
 */
public class DiskStrategy extends FormatStrategy {

    private String saveDir;
    private String saveName;

    private DiskStrategy() {
        super();
        equalLength = true;
    }

    public String getSaveDir() {
        return saveDir;
    }

    public void setSaveDir(String saveDir) {
        this.saveDir = saveDir;
    }

    public String getSaveName() {
        return saveName;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    public static class Builder {

        private boolean showThread;
        private int maxLength;
        private int methodCount;
        private int methodOffset;
        private String tag;
        private String saveDir;
        private String saveName;
        private Table table;
        private Level level;
        private Language language;

        @SuppressLint("SimpleDateFormat")
        public Builder() {
            showThread = true;
            maxLength = 40;
            methodCount = 3;
            methodOffset = 3;
            tag = "Android";
            table = Table.DOUBLE;
            level = Level.VERBOSE;
            language = Language.CN;
            saveName = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".log";
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

        public Builder saveDir(String saveDir) {
            this.saveDir = saveDir;
            return this;
        }

        public Builder saveName(String saveName) {
            this.saveName = saveName;
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

        public DiskStrategy build() {
            DiskStrategy strategy = new DiskStrategy();
            strategy.setShowThread(showThread);
            strategy.setMaxLength(maxLength);
            strategy.setMethodCount(methodCount);
            strategy.setMethodOffset(methodOffset);
            strategy.setTag(tag);
            strategy.setSaveDir(saveDir);
            strategy.setSaveName(saveName);
            strategy.setTable(table);
            strategy.setLevel(level);
            strategy.setLanguage(language);
            return strategy;
        }

    }
}
