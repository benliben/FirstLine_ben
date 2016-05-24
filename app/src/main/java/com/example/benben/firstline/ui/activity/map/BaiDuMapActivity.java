package com.example.benben.firstline.ui.activity.map;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;

import com.example.benben.firstline.ui.activity.BaseActivity;

/**
 * Created by benben on 2016/5/24.
 */
public class BaiDuMapActivity extends BaseActivity {
    public static void startBaiDuMapActivity(Activity activity) {
        Intent intent = new Intent(activity, BaiDuMapActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }
}
