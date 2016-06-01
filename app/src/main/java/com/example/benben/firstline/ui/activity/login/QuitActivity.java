package com.example.benben.firstline.ui.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.benben.firstline.R;
import com.example.benben.firstline.ui.activity.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by beneben on 2016/5/18.
 */
public class QuitActivity extends BaseActivity {
    @InjectView(R.id.topLeft)
    ImageView mLeft;
    @InjectView(R.id.topTitle)
    TextView mTitle;
    @InjectView(R.id.inform_send_notice)
    Button mSendNotice;

    public static void startQuitActiviy(Activity activity) {
        Intent intent = new Intent(activity, QuitActivity.class);
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
        mTitle.setText("强制退出程序员");

    }

    @OnClick({R.id.topLeft, R.id.inform_send_notice})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topLeft:
                finish();
                break;
            case R.id.inform_send_notice:
                Intent intent = new Intent("com.example.benben.firstline.FORCE_OFFLINE");
                sendBroadcast(intent);
                break;
        }
    }
}
