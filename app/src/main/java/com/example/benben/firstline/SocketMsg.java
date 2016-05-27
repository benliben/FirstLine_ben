package com.example.benben.firstline;



public class SocketMsg {

    /**
     * 消息体
     */
    private String content;


    /**
     * 消息类型
     */
    private Integer msg_type;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(Integer msg_type) {
        this.msg_type = msg_type;
    }

}
