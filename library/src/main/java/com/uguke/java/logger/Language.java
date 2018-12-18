package com.uguke.java.logger;

/**
 * 功能描述：语言
 * @author LeiJue
 * @date 2018/11/7
 */
public enum Language {
    /** 中文 **/
    CN("线程：", "位置：", "键：", "值："),
    /** 英文 **/
    EN("Thread: ", "Position: ", "Key: ", "Value: ");

    String mThread;
    String mPosition;
    String mKey;
    String mValue;
    Language(String thread, String position, String key, String value) {
        this.mThread = thread;
        this.mPosition = position;
        this.mKey = key;
        this.mValue = value;
    }

    public String getThread() {
        return mThread;
    }

    public String getPosition() {
        return mPosition;
    }

    public String getKey() {
        return mKey;
    }

    public String getValue() {
        return mValue;
    }
}
