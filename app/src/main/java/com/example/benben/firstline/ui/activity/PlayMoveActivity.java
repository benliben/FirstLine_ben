package com.example.benben.firstline.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;


import com.example.benben.firstline.R;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by benben on 2016/5/12.
 * 音乐播放器
 * <p/>
 * 主要使用videoView
 * 1.setVideoPath（）  设置要播放的视频文件的位置
 * 2.start（)   开始或者继续播放视频
 * 3.pause（）   暂停播放视频
 * 4.resume（） 将视频重头开始播放
 * 5.seekTo（）  从指定位置开始播放
 * 6.isPlaying（）  判断氮气是否在播放视频
 * 7.getDuration（）  获取载入的视频文件的时长
 */
public class PlayMoveActivity extends BaseActivity {
    private static final String TAG = "lyx";
    @InjectView(R.id.topLeft)
    ImageView mLeft;
    @InjectView(R.id.topTitle)
    TextView mTitle;
    @InjectView(R.id.move_content)
    VideoView mContent;
    @InjectView(R.id.move_play)
    Button mPlay;
    @InjectView(R.id.move_pause)
    Button mPause;
    @InjectView(R.id.move_stop)
    Button mStop;

    private VideoView videoView;

    public static void startPlayMoveActivity(Activity activity) {
        Intent intent = new Intent(activity, PlayMoveActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move);
        ButterKnife.inject(this);
        initView();
        initVideoView();
    }

    private void initVideoView() {
        File file = new File(Environment.getExternalStorageDirectory(), "movie.3gp");//指定播放视频文件的路径
        videoView.setVideoPath(file.getPath());
    }

    private void initView() {
        mLeft.setImageResource(R.mipmap.returns);
        mTitle.setText("视频播放器");
    }

    @OnClick({R.id.topLeft, R.id.move_play, R.id.move_pause, R.id.move_stop})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topLeft:
                finish();
                break;
            case R.id.move_play:
                if (!videoView.isPlaying()) {
                    videoView.start();
                    Log.i(TAG, "播放视频");
                }
                break;
            case R.id.move_pause:
                if (videoView.isPlaying()) {
                    videoView.pause();
                    Log.i(TAG, "暂停播放视频");
                }
                break;
            case R.id.move_stop:
                if (videoView.isPlaying()) {
                    videoView.resume();
                    Log.i(TAG, "停止播放视频");
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoView.isPlaying()) {
            videoView.suspend();
        }
    }
}
