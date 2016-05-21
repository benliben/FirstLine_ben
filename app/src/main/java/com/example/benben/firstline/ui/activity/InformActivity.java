package com.example.benben.firstline.ui.activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.benben.firstline.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by benben on 2016/5/11.
 * 使用通知
 */
public class InformActivity extends BaseActivity {
    @InjectView(R.id.topLeft)
    ImageView mLeft;
    @InjectView(R.id.topTitle)
    TextView mTitle;
    @InjectView(R.id.inform_send_notice)
    Button mSendNotice;
    private int i;
    private Resources res;

    public static void startInformActivity(Activity activity) {
        Intent intent = new Intent(activity, InformActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inform);
        ButterKnife.inject(this);
        initData();
    }

    private void initData() {
        mLeft.setImageResource(R.mipmap.returns);
        mTitle.setText("通知");
    }

    @OnClick({R.id.topLeft, R.id.inform_send_notice})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topLeft:
                finish();
                break;
            case R.id.inform_send_notice:
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Intent intent = new Intent(InformActivity.this, MainActivity.class);
                PendingIntent contentIndent = PendingIntent.getActivity(InformActivity.this, 0, intent
                        , PendingIntent.FLAG_UPDATE_CURRENT);

                Notification.Builder builder = new Notification.Builder(InformActivity.this);
                builder.setContentIntent(contentIndent).setSmallIcon(R.mipmap.ic_launcher)//设置状态栏里面的图标（小图标） 　　　　　　　　　　　　　　　　　　　　
                        .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.hehe))//下拉下拉列表里面的图标（大图标） 　　　
                        .setTicker("this is bitch!") //设置状态栏的显示的信息
                        .setWhen(System.currentTimeMillis())//设置时间发生时间
                        .setAutoCancel(true)//设置可以清除
                        .setContentTitle("This is ContentTitle")//设置下拉列表里的标题
                        .setContentText("this is ContentText");//设置上下文内容
                Notification notification = builder.getNotification();
                //加i是为了显示多条Notification

                notificationManager.notify(i, notification);

                break;
        }
    }
}
