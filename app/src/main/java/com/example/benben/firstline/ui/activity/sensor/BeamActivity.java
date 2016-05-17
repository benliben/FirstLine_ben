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
 * 光照传感器
 */
public class BeamActivity extends BaseActivity {
    @InjectView(R.id.topLeft)
    ImageView mLeft;
    @InjectView(R.id.topTitle)
    TextView mTitle;
    @InjectView(R.id.beam_content)
    TextView mContent;

    private SensorManager sensorManager;

    public static void startBeamActivity(Activity activity) {
        Intent intent = new Intent(activity, BeamActivity.class);
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
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);//选择传感器类型
        sensorManager.registerListener(listener, sensor, sensorManager.SENSOR_DELAY_NORMAL);
    }


    private void initData() {
        mTitle.setText("光线传感器");
        mLeft.setImageResource(R.mipmap.returns);
    }

    @OnClick(R.id.topLeft)
    public void onClick() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sensorManager != null) {
            sensorManager.unregisterListener(listener);
        }
    }

    /**
     * 借助SensorEventListener来对传感器输出的信号进行监听
     */
    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            /**当传感器检测到的数值发生变化时调用此方法（所有传感器输出的信息都存放在event里面）*/
            //values数组中第一个下标的值就是当前的关照强度
            float value =event.values[0];
            mContent.setText("Current light level is" + value + "lx");
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            /**当传感器精度发送变化是调用此方法*/
        }
    };
}
