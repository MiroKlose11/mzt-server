package com.example.mzt_server.common.vo;

/**
 * 键值对
 */
public class KeyValue {
    private String key;   // 键
    private String value; // 值

    public KeyValue() {
    }
    
    public KeyValue(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
} 