package com.example.benben.firstline.model;

/**
 * Created by benben on 2016/5/17.
 * 聊天的数据
 */
public class ChatModel {
    public static final int CHAT_LEFT = 0;
    public static final int CHAT_RIGHT = 1;

    private String content;
    private int type;

    public ChatModel(String content, int type) {
        this.content = content;
        this.type = type;
    }


    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }
}
