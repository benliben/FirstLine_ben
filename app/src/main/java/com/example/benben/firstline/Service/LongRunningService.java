package com.example.benben.firstline.Service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Date;

/**
 * Created by benben on 2016/5/13.
 */
public class LongRunningService extends Service {
    private static final String TAG = "lyx";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "executed at" + new Date().toString());
            }
        }).start();

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);//取得AlarmManager的实例
        int anHour =  60 * 1000;//这是一个分钟的毫秒数，即 任务的触发为一分钟后
        long triggerAtTime= SystemClock.elapsedRealtime()+anHour;
        Intent i = new Intent(this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);//指定处理器为AlarmReceiver
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);//调用set（）方法去完成设定
        /**从 Android 4.4 版本开始，Alarm 任务的触发时间将会变得不准确，
         有可能会延迟一段时间后任务才能得到执行。这并不是个 bug，而是系统在耗电性方面进行的优化。
         系统会自动检测目前有多少 Alarm 任务存在，然后将触发时间将近的几个任务放在一起执行，
         这就可以大幅度地减少 CPU 被唤醒的次数，从而有效延长电池的使用时间。当然，如果你要求 Alarm 任务的执行时间必须准备无误，
         Android 仍然提供了解决方案。使用 AlarmManager 的 setExact()方法来替代 set()方法，就可以保证任务准时执行了*/
        return super.onStartCommand(intent, flags, startId);
    }
}
