package com.example.benben.firstline.ui.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.benben.firstline.R;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by benebn on 2016/5/12.
 * 播放音频
 * <p>
 * <p>
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

    private static final String TAG = "PlayMusicActivity";

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
    @InjectView(R.id.music_progress)
    ProgressBar mProgress;
    @InjectView(R.id.music_item)
    TextView mItem;

    private MediaPlayer mPlayer = new MediaPlayer();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        ButterKnife.inject(this);
        initVeiw();
        initMediaPlayer();
        initData();
    }

    private void initData() {

        /**遍历文件里面的音乐文件*/

        ContentResolver mResolver = getContentResolver();
        Log.i(TAG, "initData: "+mResolver);
        /**
         * 歌曲ID：MediaStore.Audio.Media._ID
         Int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));

         歌曲的名称：MediaStore.Audio.Media.TITLE
         String tilte = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));

         歌曲的专辑名：MediaStore.Audio.Media.ALBUM
         String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));

         歌曲的歌手名：MediaStore.Audio.Media.ARTIST
         String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));

         歌曲文件的路径：MediaStore.Audio.Media.DATA
         String url = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));

         歌曲的总播放时长：MediaStore.Audio.Media.DURATION
         Int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));

         歌曲文件的大小：MediaStore.Audio.Media.SIZE
         Int size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
         */
        Cursor cursor = mResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        Log.i(TAG, "cursor: "+cursor);

        String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));//歌曲名称
        int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));//歌曲id
        String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));//歌曲的专辑名称
        String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));//歌曲的歌手名
        String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));//歌曲的文件路径
        int duration = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));//歌曲的总播放时长
        int size = (int) cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));//歌曲文件的大小


        Log.i(TAG, "歌曲id"+id+"歌名"+title+"专辑名"+album+"歌手名"+artist+"文件路径"+url+"总播放时长"+duration+"文件的大小"+size);

    }

    private void initMediaPlayer() {
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "music.mp3");
//            File file = new File(Environment.getExternalStorageDirectory(), "goldfallen.mp3");
            Log.i(TAG, "file: " + file);

            mPlayer.setDataSource(file.getPath());//指定音频播放的路径
            mPlayer.prepare();//让MediaPlayer进入发哦准备状态
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void initVeiw() {
        mLeft.setImageResource(R.mipmap.returns);
        mTitle.setText("音乐播放器");
        mProgress.setMax(mPlayer.getDuration());
        int a=mPlayer.getDuration()%1000;
        int b=a%60;
        int c=b%60;
        mItem.setText("当前的时长为："+c+"分"+b+"秒");
    }

    @OnClick({R.id.topLeft, R.id.music_play, R.id.music_Pause, R.id.music_stop})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topLeft:
                finish();
                break;
            case R.id.music_play:
                if (!mPlayer.isPlaying()) {//如果不是播放状态
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
