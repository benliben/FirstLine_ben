package com.example.benben.firstline.ui.activity.radio;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by tangjunjie on 2016/5/18.
 */
public class BootCompleteReceiver extends android.content.BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context," Boot Complete",Toast.LENGTH_SHORT).show();
    }
}
