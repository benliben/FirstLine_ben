package com.example.benben.firstline.ui.activity.music;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.benben.firstline.R;

/**
 * 音乐播放器
 *
 *
 *
 *1. Open Core 是 Android 多媒体框架的核心，所有 Android 平台的音频、视频的采用以及播放等操作，都是通过它来实现。
 2、Open Core 的具体功能

     1、多媒体文件的播放、下载，包括：3GPP，MPEG-4，AAC 和 MP3 containers。
     2、流媒体文件的下载、实时播放，包括：3GPP，HTTP 和 RTSP/RTP。
     3、动态视频和静态图像的编码、解码，例如：MPEG-4，H.263 和 AVC （H.264），JPEG。
     4、语音编码格式：MP3，AAC，AAC+。
     5、视频和图像格式：3GPP，MPEG-4 和 JPEG。
     6、视频会议：基于 H324-M 标准。

 Open Core 是一个多媒体的框架，从宏观上来看，它主要包含了两大方面的内容：

     1、PVPPlayer：提供媒体播放器的功能，完成各种音频（Audio）、视频（Video）流的回放（Playback）功能。
     2、PVAuthor：提供媒体流记录的功能，完成各种音频、视频流以及静态图像的捕获功能。

 3、Mediaplayer 介绍　
     MediaPlayer 类可以用来播放音频、视频和流媒体，MediaPlayer 包含了 Audio 和 Video 的播放功能，在 Android 的界面上，
     Music 和 Video 两个应用程序都是调用 MediaPlayer 实现的


 3.1、MediaPlayer 常用方法介绍

     方法：create(Context context, Uri uri)
     解释：静态方法，通过Uri创建一个多媒体播放器。
     方法：create(Context context, int resid)
     解释：静态方法，通过资源ID创建一个多媒体播放器
     方法：create(Context context, Uri uri, SurfaceHolder holder)
     解释：静态方法，通过Uri和指定 SurfaceHolder 【抽象类】 创建一个多媒体播放器
     方法： getCurrentPosition()
     解释：返回 Int， 得到当前播放位置
     方法： getDuration()
     解释：返回 Int，得到文件的时间
     方法：getVideoHeight()
     解释：返回 Int ，得到视频的高度
     方法：getVideoWidth()
     解释：返回 Int，得到视频的宽度
     方法：isLooping()
     解释：返回 boolean ，是否循环播放
     方法：isPlaying()
     解释：返回 boolean，是否正在播放
     方法：pause()
     解释：无返回值 ，暂停
     方法：prepare()
     解释：无返回值，准备同步
     方法：prepareAsync()
     解释：无返回值，准备异步
     方法：release()
     解释：无返回值，释放 MediaPlayer  对象
     方法：reset()
     解释：无返回值，重置 MediaPlayer  对象
     方法：seekTo(int msec)
     解释：无返回值，指定播放的位置（以毫秒为单位的时间）
     方法：setAudioStreamType(int streamtype)
     解释：无返回值，指定流媒体的类型
     方法：setDataSource(String path)
     解释：无返回值，设置多媒体数据来源【根据 路径】
     方法：setDataSource(FileDescriptor fd, long offset, long length)
     解释：无返回值，设置多媒体数据来源【根据 FileDescriptor】
     方法：setDataSource(FileDescriptor fd)
     解释：无返回值，设置多媒体数据来源【根据 FileDescriptor】
     方法：setDataSource(Context context, Uri uri)
     解释：无返回值，设置多媒体数据来源【根据 Uri】
     方法：setDisplay(SurfaceHolder sh)
     解释：无返回值，设置用 SurfaceHolder 来显示多媒体
     方法：setLooping(boolean looping)
     解释：无返回值，设置是否循环播放
     事件：setOnBufferingUpdateListener(MediaPlayer.OnBufferingUpdateListener listener)
     解释：监听事件，网络流媒体的缓冲监听
     事件：setOnCompletionListener(MediaPlayer.OnCompletionListener listener)
     解释：监听事件，网络流媒体播放结束监听
     事件：setOnErrorListener(MediaPlayer.OnErrorListener listener)
     解释：监听事件，设置错误信息监听
     事件：setOnVideoSizeChangedListener(MediaPlayer.OnVideoSizeChangedListener listener)
     解释：监听事件，视频尺寸监听
     方法：setScreenOnWhilePlaying(boolean screenOn)
     解释：无返回值，设置是否使用 SurfaceHolder 显示
     方法：setVolume(float leftVolume, float rightVolume)
     解释：无返回值，设置音量
     方法：start()
     解释：无返回值，开始播放
     方法：stop()
     解释：无返回值，停止播放
 */

public class MusicActivity extends ListActivity {
    private static final String TAG = "lyx";

    public static void startMusicActivity(Activity activity) {
        Intent intent = new Intent(activity, MusicActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    /**
     * 播放对象
     */
    private MediaPlayer myMediaPlayer;
    /**
     * 播放列表
     */
    private List<String> myMusicList = new ArrayList<String>();
    /**
     * 当前播放歌曲的索引
     */
    private int currentListItem = 0;
    /**
     * 音乐路径
     */
    private static final String MUSIC_PATH = new String("/sdcard/");

    private TextView mContent;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playmusic);
        myMediaPlayer = new MediaPlayer();
        findView();
        musicList();
        listener();
        initData();
    }

    private void initData() {
        /**遍历文件里面的音乐文件*/
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

    }


    /**
     * 绑定音乐
     */
    void musicList() {
        File home = new File(MUSIC_PATH);
        if (home.listFiles(new MusicFilter()).length > 0) {
            for (File file : home.listFiles(new MusicFilter())) {
                myMusicList.add(file.getName());
            }
            ArrayAdapter<String> musicList = new ArrayAdapter<String>
                    (MusicActivity.this, R.layout.item_music, myMusicList);
            setListAdapter(musicList);
        }
    }

    /**
     * 获取按钮
     */
    void findView() {
        viewHolder.start = (Button) findViewById(R.id.start);
        viewHolder.stop = (Button) findViewById(R.id.stop);
        viewHolder.next = (Button) findViewById(R.id.next);
        viewHolder.pause = (Button) findViewById(R.id.pause);
        viewHolder.last = (Button) findViewById(R.id.last);
        viewHolder.content = (TextView) findViewById(R.id.play_music_content);

    }


    /**
     * 点击时间
     */
    void listener() {
        /**停止*/
        viewHolder.stop.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (myMediaPlayer.isPlaying()) {
                    myMediaPlayer.reset();
                }
            }
        });
        /**播放*/
        viewHolder.start.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                playMusic(MUSIC_PATH + myMusicList.get(currentListItem));
            }
        });
        /**下一首*/
        viewHolder.next.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                nextMusic();
            }
        });
        /**暂停*/
        viewHolder.pause.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (myMediaPlayer.isPlaying()) {
                    myMediaPlayer.pause();
                } else {
                    myMediaPlayer.start();
                }
            }
        });
        /**上一首*/
        viewHolder.last.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                lastMusic();
            }
        });

    }

    /**
     * 播放音乐
     */
    void playMusic(String path) {

        int a = myMediaPlayer.getDuration();
        int b = a % 1000;
        Log.i(TAG, "bbbbbbbbbbbbbbbbbbbbbbbbbbbbb"+b);
        viewHolder.content.setText("时间为"+b+"秒");
        try {
            myMediaPlayer.reset();
            myMediaPlayer.setDataSource(path);
            myMediaPlayer.prepare();
            myMediaPlayer.start();
            myMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    nextMusic();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下一首
     */
    void nextMusic() {
        if (++currentListItem >= myMusicList.size()) {
            currentListItem = 0;
        } else {
            playMusic(MUSIC_PATH + myMusicList.get(currentListItem));
        }
    }

    /**
     * 上一首
     */
    void lastMusic() {
        if (currentListItem != 0) {
            Log.i(TAG, "currentListItem: " + currentListItem);
            if (--currentListItem >= 0) {
                currentListItem = myMusicList.size();
            } else {
                playMusic(MUSIC_PATH + myMusicList.get(currentListItem));
            }
        } else {
            playMusic(MUSIC_PATH + myMusicList.get(currentListItem));
        }
    }

    /**
     * 当点击了back键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            myMediaPlayer.stop();
            myMediaPlayer.release();
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 从选择列表里面进行选择播放
     */
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        currentListItem = position;
        playMusic(MUSIC_PATH + myMusicList.get(currentListItem));
    }

}