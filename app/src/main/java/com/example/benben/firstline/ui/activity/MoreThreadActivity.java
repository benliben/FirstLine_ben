package com.example.benben.firstline.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
 * Created by benben on 2016/5/12.
 * 在子线程中更新UI
 */
public class MoreThreadActivity extends BaseActivity {
    @InjectView(R.id.topLeft)
    ImageView mLeft;
    @InjectView(R.id.topTitle)
    TextView mTitle;
    @InjectView(R.id.thread_change)
    Button mChange;
    @InjectView(R.id.thread_text)
    TextView mText;

    public static final int UPDATE_TEXT = 1;//表示更新textView动作

    private Handler headler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TEXT:
                    mText.setText("Nice to meet you");
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);

        }
    };

    public static void startMoreThreadActivity(Activity activity) {
        Intent intent = new Intent(activity, MoreThreadActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        mLeft.setImageResource(R.mipmap.returns);
        mTitle.setText("在子线程中更新UI");
    }

    @OnClick({R.id.topLeft, R.id.thread_change})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topLeft:
                finish();
                break;
            case R.id.thread_change:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                       Message message=new Message();//
                        message.what = UPDATE_TEXT;//将what字段指定为 UPDATE_TEXT
                        headler.sendMessage(message);//将这条message发送出去
                    }
                }).start();
                break;
        }
    }
}
