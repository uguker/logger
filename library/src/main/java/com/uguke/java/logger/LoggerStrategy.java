package com.uguke.java.logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 功能描述：格式化配置
 * @author Admin
 * @date 2018/12/18
 */
public class LoggerStrategy {

    private static final int MIN_LENGTH = 30;

    private boolean mHasDate;
    private boolean mHasThread;
    private int mLength;
    private int mMethodCount;
    private int mMethodOffset;
    private int mLang;
    private String mTag;
    private String mSaveDir;
    private String mSaveName;
    private Level mLevel;
    private SimpleDateFormat mDateFormat;

    LoggerStrategy() {
        mHasThread = true;
        mLength = 60;
        mMethodCount = 1;
        mMethodOffset = 1;
        mTag = "日志";
        mLevel = Level.D;
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

    public void setLevel(Level level) {
        mLevel = level;
    }

    public void setSaveDir(String saveDir) {
        mSaveDir = saveDir;
    }

    public void setSaveName(String saveName) {
        mSaveName = saveName;
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

    public Level getLevel() {
        return mLevel;
    }

    public String getSaveDir() {
        return mSaveDir;
    }

    public String getSaveName() {
        return mSaveName;
    }

    public SimpleDateFormat getDateFormat() {
        return mDateFormat;
    }

    public static class Builder {

        private boolean mHasThread;
        private int mLength;
        private int mMethodCount;
        private int mMethodOffset;
        private int mLang;
        private String mTag;
        private String mSaveDir;
        private String mSaveName;
        private Level mLevel;
        private SimpleDateFormat mDateFormat;

        public Builder() {
            mHasThread = false;
            mLength = 40;
            mMethodCount = 1;
            mMethodOffset = 1;
            mTag = "日志";
            mLevel = Level.D;
            mLang = 0;
            mDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault());
            mSaveName = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()) + ".log";
        }

        public Builder hasThread(boolean has) {
            mHasThread = has;
            return this;
        }

        public Builder length(int length) {
            mLength = length;
            if (mLength < MIN_LENGTH) {
                mLength = MIN_LENGTH;
            }
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

        public Builder langCn() {
            mLang = 0;
            return this;
        }

        public Builder langEn() {
            mLang = 1;
            return this;
        }

        public Builder level(Level level) {
            mLevel = level;
            return this;
        }

        public Builder saveDir(String dir) {
            mSaveDir = dir;
            return this;
        }

        public Builder saveName(String name) {
            mSaveName = name;
            return this;
        }

        public Builder dateFormat(SimpleDateFormat format) {
            mDateFormat = format;
            return this;
        }

        public LoggerStrategy build() {
            LoggerStrategy strategy = new LoggerStrategy();
            strategy.mTag = mTag;
            strategy.mHasThread = mHasThread;
            strategy.mLength = mLength;
            strategy.mMethodCount = mMethodCount;
            strategy.mMethodOffset = mMethodOffset;
            strategy.mLang = mLang;
            strategy.mLevel = mLevel;
            strategy.mSaveDir = mSaveDir;
            strategy.mSaveName = mSaveName;
            strategy.mDateFormat = mDateFormat;
            return strategy;
        }
    }
}
