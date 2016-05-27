package com.example.benben.firstline.ui.activity.baidumap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.baidu.mapapi.SDKInitializer;
import com.example.benben.firstline.R;

public class CloudSearchDemo extends Activity {

    public static void startCloudSearchDemo(Activity activity) {
        Intent intent = new Intent(activity, CloudSearchDemo.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_cloud_search_demo);
    }

    public void startCloudSearchDemo(View view) {
        Intent intent = new Intent();
        intent.setClass(this, CloudSearchActivity.class);
        startActivity(intent);

    }
}
