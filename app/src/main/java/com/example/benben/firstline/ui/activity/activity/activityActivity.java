package com.example.benben.firstline.ui.activity.activity;

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
 * Created by benben on 2016/5/16.
 */
public class activityActivity extends BaseActivity {
    @InjectView(R.id.topLeft)
    ImageView mLeft;
    @InjectView(R.id.topTitle)
    TextView mTitle;
    @InjectView(R.id.sensor_content)
    RecyclerView mContent;


    private List<LeftTagModel> mModel = new ArrayList<>();
    private LeftAdapter mAdapter;
    private String[] mData={"传递数据"};

    public static void startactivityActivity(Activity activity) {
        Intent intent = new Intent(activity, activityActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        ButterKnife.inject(this);
        initData();
        initView();
    }

    private void initView() {
        mLeft.setImageResource(R.mipmap.returns);
        mTitle.setText("活动");
        mContent.setLayoutManager(new LinearLayoutManager(activityActivity.this));
        mAdapter = new LeftAdapter(mData);
        mContent.setAdapter(mAdapter);
        mAdapter.setItemClickListener(new LeftAdapter.OnItemClickListener() {
            @Override
            public void ItemClickListener(View view, int position) {
                switch (position) {
                    case 0:
                        intent.startIntent(activityActivity.this);
                        break;
                }
            }

            @Override
            public void ItemLongClickListener(View view, int position) {

            }
        });
    }

    private void initData() {
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
