package com.example.benben.firstline.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by benebn on 2016/5/13.
 * 处理定时任务的广播接收器
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, LongRunningService.class);//构建一个Intent对象
        context.startService(i);//启动LongRunningService
    }
}
