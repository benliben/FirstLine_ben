package com.example.benben.firstline.Service;


import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.benben.firstline.R;


/**
 * Created by benben on 2016/5/13.
 * <p/>
 * 每一个服务都需要在AndroidManifest。xml文件中进行注册才能生效
 */
public class MyService extends Service {


    private Resources res;

    private static final String TAG = "lyx";

    private DownloadBinder mBinder = new DownloadBinder();

    public class DownloadBinder extends Binder {
        public void startDownload() {//开始下载的方法
            Log.i(TAG, "startDownload: executed");
        }

        public int getProgreess() {//查看下载进度的方法
            Log.i(TAG, "getProgreess: executed");
            return 0;

        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /**
     * 在服务创建的时候调用
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate() {


        Intent notificationIntent = new Intent(this, MyService.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);


        Notification builder = new Notification.Builder(MyService.this)
                .setAutoCancel(true)
                .setContentTitle("犇犇")
                .setContentText("请保持程序在后台运行")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.hehe)
                .setWhen(System.currentTimeMillis())
                .build();

        startForeground(1, builder);

        Log.i(TAG, "initService: ");

        super.onCreate();
        Log.i(TAG, "onCreate executed: ");
    }

    /**
     * 在每次服务启动的时候调用
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //具体处理的逻辑
                stopSelf();
            }
        }).start();
        Log.i(TAG, "onStartCommand: executed");
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 在服务销毁的时候调用
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy:  executed");
    }
}
