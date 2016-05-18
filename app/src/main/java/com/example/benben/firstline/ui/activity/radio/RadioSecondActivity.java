package com.example.benben.firstline.ui.activity.radio;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benben.firstline.R;
import com.example.benben.firstline.ui.activity.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by benben on 2016/5/18.
 * 使用本地广播
 *
 *
 * 使用本地广播的优点有
 *      1.可以明确的知道正在发送的广播不会离开我们的程序，因此不需要但心机密数据泄露的问题
 *      2.其他程序无法将广播发送到我们程序的内部，因此不用但心有安全泄露的隐患
 *      3.发送本地广播比起发送系统全局广播将会更高效
 */
public class RadioSecondActivity extends BaseActivity {


    @InjectView(R.id.topLeft)
    ImageView mLeft;
    @InjectView(R.id.topTitle)
    TextView mTitle;
    @InjectView(R.id.radio_button)
    Button mButton;
    private IntentFilter intentFilter;
    private LocalBroadcastManager localBroadcastManager;
    private LocalReceiver localReceiver;

    public static void startRadioSecondActivity(Activity activity) {
        Intent intent = new Intent(activity, RadioSecondActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);
        ButterKnife.inject(this);
        initData();
        initView();
    }

    private void initView() {
        localBroadcastManager = LocalBroadcastManager.getInstance(this);//获取实例
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.benben.firstline.LOCAL_BROADCAST");
        localReceiver = new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver,intentFilter); //注册本地广播监听器
    }

    private void initData() {
        mLeft.setImageResource(R.mipmap.returns);
        mTitle.setText("使用本地广播");

    }

    @OnClick({R.id.topLeft, R.id.radio_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topLeft:
                finish();
                break;
            case R.id.radio_button:
                Intent intent = new Intent("com.example.benben.firstline.LOCAL_BROADCAST");
                localBroadcastManager.sendBroadcast(intent);//发送本地广播
                break;
        }
    }


    class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "由RadioSecondActivity发送", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(localReceiver);
    }
}
