package com.example.benben.firstline.ui.activity.activity.location;

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
 * Created by benben on 2016/5/25.
 * 定位
 */
public class LocationActivity extends BaseActivity {
    @InjectView(R.id.topLeft)
    ImageView mLeft;
    @InjectView(R.id.topTitle)
    TextView mTitle;
    @InjectView(R.id.content_content)
    RecyclerView mContent;


    private List<LeftTagModel> mModel = new ArrayList<>();
    private LeftAdapter mAdapter;
    private String[] mDate = {"百度地图", "定位","定位2","路线","路线2","poi搜索功能"};

    public static void startLocationActivity(Activity activity) {
        Intent intent = new Intent(activity, LocationActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        ButterKnife.inject(this);
        initData();
        initView();
    }

    private void initData() {
        for (int i = 0; i < mDate.length; i++) {
            LeftTagModel left = new LeftTagModel();
            left.setName(mDate[i]);
            mModel.add(left);
        }
    }

    private void initView() {
        mLeft.setImageResource(R.mipmap.returns);
        mTitle.setText("位置服务");

        mContent.setLayoutManager(new LinearLayoutManager(LocationActivity.this));
        mAdapter = new LeftAdapter(mDate);
        mContent.setAdapter(mAdapter);
        mAdapter.setItemClickListener(new LeftAdapter.OnItemClickListener() {
            @Override
            public void ItemClickListener(View view, int position) {
                switch (position) {
                    case 0:
                        /*百度地图*/
                        MyLocationBasedServiceActivity.startMyLocationBasedServiceActivity(LocationActivity.this);
                        break;
                    case 1:
                        /*百度定位*/
                        MyPositioningActivity.startMyPositioningActivity(LocationActivity.this);
                        break;
                    case 2:
                        /*定位2*/
                        MyBaiduMap.startMyBaiduMap(LocationActivity.this);
                        break;
                    case 3:
                        /*路线*/
                        MapPathActivity.startMapPathActivity(LocationActivity.this);
                        break;
                    case 4:
                    /*路线2*/
                        MapPathActivity2.startMapPathActivity2(LocationActivity.this);
                        break;
                    case 5:
                        PoiSearchActivity.startPoiSearchActivity(LocationActivity.this);
                        break;
                }
            }

            @Override
            public void ItemLongClickListener(View view, int position) {

            }
        });
    }

    @OnClick(R.id.topLeft)
    public void onClick() {
        finish();
    }
}
