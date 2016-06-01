package com.example.benben.firstline.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.benben.firstline.Service.AutoUpdateService;

/**
 * Created by benebne on 2016/6/1.
 */
public class AutoUpdateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, AutoUpdateService.class));
    }
}
