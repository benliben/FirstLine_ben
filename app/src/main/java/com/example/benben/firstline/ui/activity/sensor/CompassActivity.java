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
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.benben.firstline.R;
import com.example.benben.firstline.ui.activity.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by benben on 2016/5/14.
 * 指南针
 */
public class CompassActivity extends BaseActivity {


    private static final String TAG = "lyx";
    @InjectView(R.id.topLeft)
    ImageView mLeft;
    @InjectView(R.id.topTitle)
    TextView mTitle;
    @InjectView(R.id.compass_img)
    ImageView mImg;
    private SensorManager sensorManager;

    public static void startCompassActivity(Activity activity) {
        Intent intent = new Intent(activity, CompassActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direstion);
        ButterKnife.inject(this);

        initData();
        initView();
    }

    private void initView() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);//实例化
//        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);//设置为方向传感器  但Android已经废弃了
        // 现在通过加速度传感器和地磁传感器共同计算出来的
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);//加速传感器
        Sensor magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);//地磁传感器
        sensorManager.registerListener(listener, accelerometerSensor, sensorManager.SENSOR_DELAY_GAME);//由于方向传感器的精度要求比较高，所有用SENSOR_DELAY_GAME来提高更新频率
        sensorManager.registerListener(listener, magneticSensor, sensorManager.SENSOR_DELAY_GAME);
    }

    private void initData() {
        mLeft.setImageResource(R.mipmap.returns);
        mTitle.setText("指南针");
    }

    @OnClick(R.id.topLeft)
    public void onClick() {
        finish();
    }

    private SensorEventListener listener = new SensorEventListener() {


        /**分别记录着加速度传感器和地磁传感器输出的值。
         * 然后将这两个值传入到 SensorManager 的 getRotationMatrix()方法中
         * 就可以得到一个包含旋转矩阵的 R 数组
         */
        float[] accelerometerValues = new float[3];
        float[] magneticValues = new float[3];

        private float lastRotateDegree;

        @Override
        public void onSensorChanged(SensorEvent event) {

            /**判断当前是加速度传感器还是地磁传感器*/
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                //注意赋值是要调用clone（）方法,不然accelerometerValues和magneticValues将会指向同一个引用
                accelerometerValues = event.values.clone();
            } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                magneticValues = event.values.clone();
            }
            float[] R = new float[9];//创建长度为9的values数租
            float  values[] = new float[3];//长度为3

            /**
             * 第一个参数 R 是一个长度为 9 的 float 数组，getRotationMatrix()方法计算出的旋转数据就会赋值到这个数组当中。
             * 第二个参数是一个用于将地磁向量转换成重力坐标的旋转矩阵，通常指定为 null 即可。
             * 第三和第四个参数则分别就是加速度传感器和地磁传感器输出的 values 值。
             */
            SensorManager.getRotationMatrix(R, null, accelerometerValues, magneticValues);
            SensorManager.getOrientation(R, values);

            /**将计算出的角度取反，用于指针旋转*/
            float rotateDegree = -(float) Math.toDegrees(event.values[0]);
            if (Math.abs(rotateDegree - lastRotateDegree) > 1) {
                RotateAnimation animation = new RotateAnimation(lastRotateDegree//实例
                        , rotateDegree//旋转的止角度
                        , Animation.RELATIVE_TO_SELF, 05f, Animation.RELATIVE_TO_SELF, 05f);//用于指定旋转的中心
                animation.setFillAfter(true);
                mImg.startAnimation(animation);//执行动画
                lastRotateDegree = rotateDegree;
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
