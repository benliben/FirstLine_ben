package com.example.benben.firstline.ui.activity.sensor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.benben.firstline.R;
import com.example.benben.firstline.model.LeftTagModel;
import com.example.benben.firstline.ui.activity.BaseActivity;
import com.example.benben.firstline.ui.adapter.LeftAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by benben on 2016/5/14.
 *传感器
 *
 *
 * 传感器              Java名称                  本地接口                数值
 *
 加速度           TYPE_ACCELEROMETER       SENSOR_TYPE_ACCELEROMETER      1
 磁力域           TYPE_MAGNETIC_FIELD      SENSOR_TYPE_MAGNETIC_FIELD     2
 方向             TYPE_ORIENTATION         SENSOR_TYPE_ORIENTATION        3
 陀螺             TYPE_GYROSCOPE           SENSOR_TYPE_GYROSCOPE          4
 光线（亮度）     TYPE_LIGHT               SENSOR_TYPE_LIGHT              5
 压力             TYPE_PRESSURE            SENSOR_TYPE_PRESSURE           6
 温度             TYPE_TEMPERATURE         SENSOR_TYPE_TEMPERATURE        7
 临近性           TYPE_PROXIMITY           SENSOR_TYPE_PROXIMITY          8
 *
 *
 */
public class TheSensorActivity extends BaseActivity {

    public static void startTheSensorActivity(Activity activity) {
        Intent intent = new Intent(activity, TheSensorActivity.class);
        ActivityCompat.startActivity(activity,intent,null);
    }

    @InjectView(R.id.topLeft)
    ImageView mLeft;
    @InjectView(R.id.topTitle)
    TextView mTitle;
    @InjectView(R.id.sensor_content)
    RecyclerView mContent;


    private String[] mData = {"光照传感器", "加速传感器", "方向传感器", ""};
    private LeftAdapter mAdapter;
    private List<LeftTagModel> mModel=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        ButterKnife.inject(this);
        initData();
        initView();
    }

    private void initView() {
        mContent.setLayoutManager(new LinearLayoutManager(TheSensorActivity.this));
        mAdapter = new LeftAdapter(mData);
        mContent.setAdapter(mAdapter);
        mAdapter.setItemClickListener(new LeftAdapter.OnItemClickListener() {
            @Override
            public void ItemClickListener(View view, int position) {
                switch (position) {
                    case 0:
                        BeamActivity.startBeamActivity(TheSensorActivity.this);
                        break;
                    case 1:
                        AccelerationActivity.startAccelerationActivity(TheSensorActivity.this);
                        break;
                    case 2:
                        DirectionActivity.startDirectionActivity(TheSensorActivity.this);
                        break;
                    case 3:

                        break;
                }
            }

            @Override
            public void ItemLongClickListener(View view, int position) {

                Toast.makeText(TheSensorActivity.this, "点一下就好，不需要长按", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {
        mLeft.setImageResource(R.mipmap.returns);
        mTitle.setText("传感器");
        for (int i = 0; i < mData.length; i++) {
            LeftTagModel model = new LeftTagModel();
            model.setName(mData[i]);
            mModel.add(model);
        }
    }

    @OnClick(R.id.topLeft)
    public void onClick() {
    }
}
