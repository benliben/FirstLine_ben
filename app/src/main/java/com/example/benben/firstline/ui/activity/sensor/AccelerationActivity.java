package com.example.benben.firstline.ui.activity.sensor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.benben.firstline.R;
import com.example.benben.firstline.ui.activity.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by benben on 2016/5/14.
 * 加速传感器（模仿微信摇一摇）
 */
public class AccelerationActivity extends BaseActivity {
    @InjectView(R.id.topLeft)
    ImageView mLeft;
    @InjectView(R.id.topTitle)
    TextView mTitle;
    @InjectView(R.id.beam_content)
    TextView mContent;


    private SensorManager sensorManager;

    public static void startAccelerationActivity(Activity activity) {
        Intent intent = new Intent(activity, AccelerationActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beam);
        ButterKnife.inject(this);
        initData();
        initView();
    }

    private void initView() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);//实例化
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);//设置为加速传感器
        sensorManager.registerListener(listener, sensor, sensorManager.SENSOR_DELAY_NORMAL);
    }

    private void initData() {
        mLeft.setImageResource(R.mipmap.returns);
        mTitle.setText("加速传感器（仿微信摇一摇");
    }


    @OnClick(R.id.topLeft)
    public void onClick() {
        finish();
    }

    private SensorEventListener listener=new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            /**加速度可能是负值*/
            float xValue = Math.abs(event.values[0]);
            float yValue = Math.abs(event.values[1]);
            float zValue = Math.abs(event.values[2]);

            if (xValue > 15 || yValue > 15 || zValue > 15) {
                //认为用户摇动了手机，触发摇一摇逻辑
                mContent.setText("恭喜你中奖了");
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sensorManager != null) {
            sensorManager.unregisterListener(listener);
        }
    }
}
