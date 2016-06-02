package com.example.benben.firstline.ui.activity;

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
import com.example.benben.firstline.ui.activity.music.MusicActivity;
import com.example.benben.firstline.ui.adapter.LeftAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by benebn on 2016/5/11.
 * 运用手机多媒体
 */
public class MediaActivity extends BaseActivity {
    @InjectView(R.id.topLeft)
    ImageView mLeft;
    @InjectView(R.id.topTitle)
    TextView mTitle;
    @InjectView(R.id.media_content)
    RecyclerView mContent;

    public static void startMediaActivity(Activity activity) {
        Intent intent = new Intent(activity, MediaActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    private String[] mData = {"通知","短息","照相机","音乐播放器","视频播放器"};
    private LeftAdapter mAdapter;
    private List<LeftTagModel> mModel = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        ButterKnife.inject(this);
        initData();
        initView();
    }

    private void initView() {
        mContent.setLayoutManager(new LinearLayoutManager(MediaActivity.this));
        mAdapter = new LeftAdapter(mData);
        mContent.setAdapter(mAdapter);
        mAdapter.setItemClickListener(new LeftAdapter.OnItemClickListener() {
            @Override
            public void ItemClickListener(View view, int position) {
                switch (position) {
                    case 0:
                        /**通知*/
                        InformActivity.startInformActivity(MediaActivity.this);
                        break;
                    case 1:
                        /**短信*/
                        SMSActivity.startSMSActivity(MediaActivity.this);
                        break;
                    case 2:
                        /**照相机*/
                        CameraActivity.startCameraActivity(MediaActivity.this);
                        break;
                    case 3:
                        /**音乐播放器*/
                        MusicActivity.startMusicActivity(MediaActivity.this);
                        break;
                    case 4:
                        /**视屏播放器*/
                        PlayMoveActivity.startPlayMoveActivity(MediaActivity.this);
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
