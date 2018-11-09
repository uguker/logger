package com.uguke.logger.constant;

/**
 * 功能描述：语言
 * @author LeiJue
 * @date 2018/11/7
 */
public enum Language {
    /** 中文 **/
    CN("线程：", "位置：", "键：", "值：", "无效的JSON数据"),
    /** 英文 **/
    EN("Thread: ", "Position: ", "Key: ", "Value: ", "Invalid json data");
    private String thread;
    private String position;
    private String key;
    private String value;
    private String json;
    Language(String thread, String position, String key, String value, String json) {
        this.thread = thread;
        this.position = position;
        this.key = key;
        this.value = value;
        this.json = json;
    }

    public String getThread() {
        return thread;
    }

    public String getPosition() {
        return position;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getJson() {
        return json;
    }
}
