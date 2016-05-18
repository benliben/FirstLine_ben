package com.example.benben.firstline.ui.activity.login;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.WindowManager;

import com.example.benben.firstline.ui.activity.ActivityCollector;
import com.example.benben.firstline.ui.activity.MainActivity;

/**
 * Created by benebn on 2016/5/18.
 */
public class ForceOfflineReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(final Context context, Intent intent) {
        AlertDialog.Builder dialBuilder = new AlertDialog.Builder(context);
        dialBuilder.setTitle("警告");
        dialBuilder.setMessage("你被迫下线，请重新登录.");
        dialBuilder.setCancelable(false);
        dialBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCollector.finishAll();//销毁所有活动
                Intent intent1 = new Intent(context, MainActivity.class);
                //由于是在广播里面启动活动所有一定给Intent加入FLAG_ACTIVITY_NEW_TASK这个标准
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);//重启MainActivity
            }
        });

        AlertDialog alertDialog = dialBuilder.create();
        //需要设置AlertDialog的类型，保证在广播接收器中可以正常的弹出
        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alertDialog.show();
    }
}
