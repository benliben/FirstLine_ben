package com.example.benben.firstline.utils;

/**
 * Created by benebn on 2016/6/1.
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
