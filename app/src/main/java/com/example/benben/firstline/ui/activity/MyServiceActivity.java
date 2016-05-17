package com.example.benben.firstline.ui.activity;

import android.app.Activity;
import android.app.Notification;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.benben.firstline.R;
import com.example.benben.firstline.Service.LongRunningService;
import com.example.benben.firstline.Service.MyIntentService;
import com.example.benben.firstline.Service.MyService;
import com.example.benben.firstline.model.LeftTagModel;
import com.example.benben.firstline.ui.adapter.LeftAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by benben on 2016/5/12.
 * 服务
 */
public class MyServiceActivity extends BaseActivity {
    private static final String TAG = "lyx";
    @InjectView(R.id.topLeft)
    ImageView mLeft;
    @InjectView(R.id.topTitle)
    TextView mTitle;
    @InjectView(R.id.service_content)
    RecyclerView mContent;
    @InjectView(R.id.service_start)
    Button mStart;
    @InjectView(R.id.service_stop)
    Button mStop;
    @InjectView(R.id.service_bind)
    Button mBind;
    @InjectView(R.id.service_unBind)
    Button mUnBind;
    @InjectView(R.id.service_intent_service)
    Button mIntentService;





    private MyService.DownloadBinder downloadBinder;
    private ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (MyService.DownloadBinder) service;
            downloadBinder.startDownload();
            downloadBinder.getProgreess();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private List<LeftTagModel> mModel = new ArrayList<>();
    private String[] mDatas = {"子线程更新UI", "AsyncTask"};
    private LeftAdapter mAdapter;

    public static void startMyServiceActiviy(Activity activity) {
        Intent intent = new Intent(activity, MyServiceActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myservice);
        ButterKnife.inject(this);
        initData();
        initView();

        /**启动定时器*/
        statTheTime();

    }

    private void statTheTime() {
        Intent intent = new Intent(this, LongRunningService.class);
        startService(intent);
    }


    private void startForeground(int i, Notification notification) {
    }

    private void initData() {
        for (int i = 0; i < mDatas.length; i++) {
            LeftTagModel model = new LeftTagModel();
            model.setName(mDatas[i]);
            mModel.add(model);
        }
    }

    private void initView() {
        mLeft.setImageResource(R.mipmap.returns);
        mTitle.setText("服务");

        mContent.setLayoutManager(new LinearLayoutManager(MyServiceActivity.this));
        mAdapter = new LeftAdapter(mDatas);
        mContent.setAdapter(mAdapter);

        mAdapter.setItemClickListener(new LeftAdapter.OnItemClickListener() {
            @Override
            public void ItemClickListener(View view, int position) {
                switch (position) {
                    case 0:
                        /**
                         * 1.Message 是在线程之间传递的信息，她可以子啊内部携带少量的信息，用于在不同线程之间交换数据
                         *              a.what字段
                         *              b.arg 来携带一些整型数据
                         *              c.ogj 来携带一个object对象
                         * 2.Handler  是处理者的意思，主要是利用发送和处理消息的。
                         *              a.发送消息一般使用Handler的sendMessage（）方法，而发送出的信息经过处理
                         *                 最后会传递到Handler的handleMessage（）方法中
                         * 3.MessageQueue  是信息队列的意思，它主要是用于存放所有通过Handler发送的信息。这部分信息会一直存在与信息队列中
                         *                  等待被处理，
                         *                  每个线程中只有一个MessageQueue
                         * 4.Lopper  是每个线程中的MessageQueue的管家，调用Looper的loop（）方法后，就会进入到一个无限循环当中，然后每当发现
                         *             MessageQueue中存在一条信息，就会将他取出，并传递到Handler的handleMessage（）方法中，每个线程只有一个
                         *             Looper对象
                         */
                        MoreThreadActivity.startMoreThreadActivity(MyServiceActivity.this);
                        break;
                    case 1:

                        break;
                }
            }

            @Override
            public void ItemLongClickListener(View view, int position) {

            }
        });
    }


    @OnClick({R.id.service_start, R.id.service_stop, R.id.topLeft,R.id.service_bind, R.id.service_unBind,R.id.service_intent_service})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topLeft:
                finish();
            case R.id.service_start:
                Intent startIntent = new Intent(this, MyService.class);
                startService(startIntent);
                break;
            case R.id.service_stop:
                Intent stopIntent = new Intent(this, MyService.class);
                stopService(stopIntent);
                break;
            case R.id.service_bind:
                Intent bindIntent = new Intent(this, MyService.class);
                /**
                 * 第一个参数是intent对象
                 * 第二个参数是ServiceConnection的实例
                 * 第三个参数是一个标志位，这里传入的BIND_AUTO_CREATE表示在活动和服务进行绑定后自动创建服务，
                 *              使得MyService中的onCreate（）方法得到执行，但onStartCommand（）方法不会执行
                 */
                bindService(bindIntent, connection, BIND_AUTO_CREATE);//绑定服务
                break;
            case R.id.service_unBind:
                unbindService(connection);//解除服务
                break;
            case R.id.service_intent_service:
                /**打开主线程的id*/
                Log.i(TAG, "onClick: Thread id is " + Thread.currentThread().getId());
                Intent intentService = new Intent(this, MyIntentService.class);
                startService(intentService);
                break;
            default:
                break;
        }
    }

}
