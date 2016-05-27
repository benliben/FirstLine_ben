package com.example.benben.firstline.ui.activity.baidumap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.baidu.mapapi.SDKInitializer;
import com.example.benben.firstline.R;

public class ShareDemo extends Activity {


    public static void startShareDemo(Activity activity) {
        Intent intent = new Intent(activity, ShareDemo.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_share_demo);
    }

    public void startShareDemo(View view) {
        Intent intent = new Intent();
        intent.setClass(this, ShareDemoActivity.class);
        startActivity(intent);

    }

}
