package com.example.benben.firstline.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Administrator on 2016/5/10.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("BaseActivity", "当前的页面为: " + getClass().getCanonicalName());
        ActivityCollector.addActvitiy(this);
    }

    @Override
    protected void onDestroy() {



        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
