package com.uguke.java.logger.strategy;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * 功能描述：格式化配置
 * @author Admin
 * @date 2018/12/18
 */
public class FormatStrategy {

    private boolean mHasDate;
    private boolean mHasThread;
    private int mLength;
    private int mMethodCount;
    private int mMethodOffset;
    private String mTag;
    private int mLang;
    private int mTable;
    private int mLevel;
    private SimpleDateFormat mDateFormat;

    FormatStrategy() {
        mHasThread = true;
        mLength = 60;
        mMethodCount = 1;
        mMethodOffset = 1;
        mTag = "日志";
        mLevel = 0;
        mTable = 0;
        mLang = 0;
    }

    public void setHasDate(boolean hasDate) {
        mHasDate = hasDate;
    }

    public void setHasThread(boolean hasThread) {
        mHasThread = hasThread;
    }

    public void setLength(int length) {
        mLength = length;
    }

    public void setMethodCount(int methodCount) {
        mMethodCount = methodCount;
    }

    public void setMethodOffset(int methodOffset) {
        mMethodOffset = methodOffset;
    }

    public void setTag(String tag) {
        mTag = tag;
    }

    public void setLangCn() {
        mLang = 0;
    }

    public void setLangEn() {
        mLang = 1;
    }

    public void setTableSingle() {
        mTable = 1;
    }

    public void setTableDouble() {
        mTable = 0;
    }


    public void setLevel(int level) {
        mLevel = level;
    }

    public void setDateFormat(SimpleDateFormat dateFormat) {
        mDateFormat = dateFormat;
    }

    public boolean isHasDate() {
        return mHasDate;
    }

    public boolean isHasThread() {
        return mHasThread;
    }

    public int getLength() {
        return mLength;
    }

    public int getMethodCount() {
        return mMethodCount;
    }

    public int getMethodOffset() {
        return mMethodOffset;
    }

    public String getTag() {
        return mTag;
    }

    public int getLang() {
        return mLang;
    }

    public int getTable() {
        return mTable;
    }

    public int getLevel() {
        return mLevel;
    }

    public static class Builder {

        private String mTag;
        private boolean mHasDate;
        private boolean mHasThread;
        private int mLength;
        private int mMethodCount;
        private int mMethodOffset;
        private int mLang;
        private int mTable;
        private int mLevel;
        private SimpleDateFormat mDateFormat;

        public Builder() {
            mHasThread = false;
            mHasDate = true;
            mLength = 40;
            mMethodCount = 1;
            mMethodOffset = 1;
            mTag = "日志";
            mTable = 0;
            mLevel = 0;
            mLang = 0;
            mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        }

        public Builder hasThread(boolean has) {
            mHasThread = has;
            return this;
        }

        public Builder hasDate(boolean has) {
            mHasDate = has;
            return this;
        }

        public Builder length(int length) {
            mLength = length;
            return this;
        }

        public Builder methodCount(int count) {
            mMethodCount = count;
            return this;
        }

        public Builder methodOffset(int offset) {
            mMethodOffset = offset;
            return this;
        }

        public Builder tag(String tag) {
            mTag = tag;
            return this;
        }

        public Builder tableDouble() {
            mTable = 0;
            return this;
        }

        public Builder tableSingle() {
            mTable = 1;
            return this;
        }

        public Builder langCn() {
            mLang = 0;
            return this;
        }

        public Builder langEn() {
            mLang = 1;
            return this;
        }

        public Builder dateFormat(SimpleDateFormat format) {
            mDateFormat = format;
            return this;
        }

        public FormatStrategy build() {
            FormatStrategy strategy = new FormatStrategy();
            strategy.mTag = mTag;
            strategy.mHasDate = mHasDate;
            strategy.mHasThread = mHasThread;
            strategy.mLength = mLength;
            strategy.mMethodCount = mMethodCount;
            strategy.mMethodOffset = mMethodOffset;
            strategy.mLang = mLang;
            strategy.mLevel = mLevel;
            strategy.mTable = mTable;
            strategy.mDateFormat = mDateFormat;
            return strategy;
        }
    }
}
