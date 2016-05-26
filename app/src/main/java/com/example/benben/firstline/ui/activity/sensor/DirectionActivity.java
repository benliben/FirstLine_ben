package com.example.benben.firstline.ui.activity.sensor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.benben.firstline.R;
import com.example.benben.firstline.ui.activity.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by benben on 2016/5/14.
 * 方向传感器
 */
public class DirectionActivity extends BaseActivity {
    private static final String TAG = "lyx";
    @InjectView(R.id.topLeft)
    ImageView topLeft;
    @InjectView(R.id.topTitle)
    TextView topTitle;

    public static void startDirectionActivity(Activity activity) {
        Intent intent = new Intent(activity, DirectionActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    private SensorManager sensorManager;

    private Sensor accelerometerSensor;
    private Sensor magneticSensor;

    private TextView mContent;
    private float[] accelerometerValues = new float[3];
    private float[] magneticFieldValues = new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beam);
        ButterKnife.inject(this);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // 初始化地磁场传感器
        magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mContent = (TextView) findViewById(R.id.beam_content);
        initData();
        calculateOrientation();
    }

    private void initData() {
        topLeft.setImageResource(R.mipmap.returns);
        topTitle.setText("方向传感器");
    }

    // 计算方向
    private void calculateOrientation() {
        float[] values = new float[3];
        float[] R = new float[9];
        SensorManager.getRotationMatrix(R, null, accelerometerValues, magneticFieldValues);
        SensorManager.getOrientation(R, values);
        values[0] = (float) Math.toDegrees(values[0]);

        Log.i(TAG, values[0] + "");
        if (values[0] >= -5 && values[0] < 5) {
            mContent.setText("正北" + values[0]);
        } else if (values[0] >= 5 && values[0] < 85) {
            // Log.i(TAG, "东北");
            mContent.setText("东北" + values[0]);
        } else if (values[0] >= 85 && values[0] <= 95) {
            // Log.i(TAG, "正东");
            mContent.setText("正东" + values[0]);
        } else if (values[0] >= 95 && values[0] < 175) {
            // Log.i(TAG, "东南");
            mContent.setText("东南" + values[0]);
        } else if ((values[0] >= 175 && values[0] <= 180)
                || (values[0]) >= -180 && values[0] < -175) {
            // Log.i(TAG, "正南");
            mContent.setText("正南" + values[0]);
        } else if (values[0] >= -175 && values[0] < -95) {
            // Log.i(TAG, "西南");
            mContent.setText("西南" + values[0]);
        } else if (values[0] >= -95 && values[0] < -85) {
            // Log.i(TAG, "正西");
            mContent.setText("正西" + values[0]);
        } else if (values[0] >= -85 && values[0] < -5) {
            // Log.i(TAG, "西北");
            mContent.setText("西北" + values[0]);
        }
    }

    @OnClick(R.id.topLeft)
    public void onClick() {
    }

    class MySensorEventListener implements SensorEventListener {
        @Override
        public void onSensorChanged(SensorEvent event) {
            // TODO Auto-generated method stub
            /**判断是加速度传感器还是地磁传感器*/
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                accelerometerValues = event.values;
            }
            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                magneticFieldValues = event.values;
            }
            calculateOrientation();
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        // 注册监听
        sensorManager.registerListener(new MySensorEventListener(),
                accelerometerSensor, Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(new MySensorEventListener(), magneticSensor,
                Sensor.TYPE_MAGNETIC_FIELD);
        super.onResume();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        // 解除注册
        sensorManager.unregisterListener(new MySensorEventListener());
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (sensorManager != null) {
            sensorManager.unregisterListener(new MySensorEventListener());
        }
        super.onDestroy();
    }
}
