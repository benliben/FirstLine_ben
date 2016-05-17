package com.example.benben.firstline.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.benben.firstline.R;

import java.io.File;
import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by benebn on 2016/5/12.
 * 播放音频
 * <p/>
 * <p/>
 * MediaPlayer类中一下较为常用的控制方法
 * 1.setDataSource（）  设置要播放的音频文件的位置
 * 2.prepare（） 在开始播放之前调用这个方法完成准备工作
 * 3.start（） 开始或者继续播放音频
 * 4.pause（） 暂停播放音频
 * 5.reset（） 将MediaPlayer对象重置打牌刚刚创建的状态
 * 6.seekTo（） 从指定的位置开始播放音频
 * 7.stop（） 停止播放音频，调用这个方法后的MediaPlayer对象无法再播放音频
 * 8.release（） 释放掉与MediaPlayer对象相关的资源
 * 9.isPlaying（） 判断当前前MediaPlayer释放音频
 * 10.getDuration（） 获取载入的音频文件的时长
 */
public class PlayMusicActivity extends BaseActivity {

    private static final String TAG = "lyx";

    public static void startPlayMusicActivity(Activity activity) {
        Intent intent = new Intent(activity, PlayMusicActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }
    @InjectView(R.id.topLeft)
    ImageView mLeft;
    @InjectView(R.id.topTitle)
    TextView mTitle;
    @InjectView(R.id.music_play)
    Button mPlay;
    @InjectView(R.id.music_Pause)
    Button mPause;
    @InjectView(R.id.music_stop)
    Button mStop;

    private MediaPlayer mPlayer = new MediaPlayer();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        ButterKnife.inject(this);
        initVeiw();
        initMediaPlayer();
    }

    private void initMediaPlayer() {
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "music.mp3");
//            File file = new File(String.valueOf(R.drawable.hehehehe));

            mPlayer.setDataSource(file.getPath());//指定音频播放的路径
            mPlayer.prepare();//让MediaPlayer进入发哦准备状态
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initVeiw() {
        mLeft.setImageResource(R.mipmap.returns);
        mTitle.setText("音乐播放器");
    }

    @OnClick({R.id.topLeft, R.id.music_play, R.id.music_Pause, R.id.music_stop})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topLeft:
                finish();
                break;
            case R.id.music_play:
                if (!mPlayer.isPlaying()){//如果不是播放状态
                    mPlayer.start();
                    Log.i(TAG, "播放");
                }
                break;
            case R.id.music_Pause:
                if (mPlayer.isPlaying()) {//如果现在是播放状态
                    mPlayer.pause();
                    Log.i(TAG, "暂停");
                }
                break;
            case R.id.music_stop:
                if (mPlayer.isPlaying()) {//如果现在是播放状态
                    mPlayer.reset();
                    Log.i(TAG, "停止");
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            mPlayer.stop();//停止播放
            mPlayer.release();//释放资源
        }
    }
}
