package com.uguke.java.logger.strategy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 功能描述：硬盘存储策略
 * @author LeiJue
 * @date 2018/12/18
 */
public class DiskStrategy extends FormatStrategy {

    private String mSaveDir;
    private String mSaveName;

    private DiskStrategy() {
        super();
    }

    public void setSaveDir(String saveDir) {
        mSaveDir = saveDir;
    }

    public void setSaveName(String saveName) {
        mSaveName = saveName;
    }

    public String getSaveDir() {
        return mSaveDir;
    }

    public String getSaveName() {
        return mSaveName;
    }

    public static final class Builder {

        private String mTag;
        private boolean mHasDate;
        private boolean mHasThread;
        private int mLength;
        private int mMethodCount;
        private int mMethodOffset;
        private int mLang;
        private int mTable;
        private int mLevel;
        private String mSaveDir;
        private String mSaveName;
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
            mSaveName = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    .format(new Date()) + ".log";
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

        public Builder saveDir(String dir) {
            mSaveDir = dir;
            return this;
        }

        public Builder saveName(String name) {
            mSaveName = name;
            return this;
        }

        public DiskStrategy build() {
            DiskStrategy strategy = new DiskStrategy();
            strategy.mSaveDir = mSaveDir;
            strategy.mSaveName = mSaveName;
            strategy.setTag(mTag);
            strategy.setHasDate(mHasDate);
            strategy.setHasThread(mHasThread);
            strategy.setLength(mLength);
            strategy.setMethodCount(mMethodCount);
            strategy.setMethodOffset(mMethodOffset);
            strategy.setDateFormat(mDateFormat);
            if (mLang == 0) {
                strategy.setLangCn();
            } else {
                strategy.setLangEn();
            }

            if (mTable == 0) {
                strategy.setTableSingle();
            } else {
                strategy.setTableDouble();
            }

            //strategy.mLevel = mLevel;
            return strategy;
        }
    }
}
