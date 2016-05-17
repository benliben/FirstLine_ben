package com.example.benben.firstline.Service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by benben on 2016/5/13.
 */
public class MyIntentService extends IntentService {
    private static final String TAG = "lyx";

    public MyIntentService() {
        super("MyIntentService");
        //调用父类的有参构造函数
    }




    @Override
    protected void onHandleIntent(Intent intent) {
        //处理逻辑事情
        Log.i(TAG, "Thread id is"+Thread.currentThread().getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: executed");
    }
}
