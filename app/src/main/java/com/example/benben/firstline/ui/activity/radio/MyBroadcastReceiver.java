package com.example.benben.firstline.ui.activity.radio;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by benben on 2016/5/18.
 * 自定义发送标准广播
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "lyx";

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "由myBroadcastReceiver发送", Toast.LENGTH_SHORT).show();
        /**截断广播*/
        abortBroadcast();
    }

}
