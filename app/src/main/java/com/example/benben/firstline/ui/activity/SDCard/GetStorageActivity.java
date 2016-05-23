package com.example.benben.firstline.ui.activity.SDCard;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benben.firstline.R;
import com.example.benben.firstline.ui.activity.BaseActivity;

import java.io.File;
import java.text.DecimalFormat;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by benben on 2016/5/23.
 *
 * 获取手机自带内存的大小和已用的空间
 *
 *
 * Environment 是一个提供访问环境变量的类。
 * <p/>
 * Environment 包含常量：
 * <p/>
 * MEDIA_BAD_REMOVAL
 * 解释：返回getExternalStorageState() ，表明SDCard 被卸载前己被移除
 * MEDIA_CHECKING
 * 解释：返回getExternalStorageState() ，表明对象正在磁盘检查。
 * MEDIA_MOUNTED
 * 解释：返回getExternalStorageState() ，表明对象是否存在并具有读/写权限
 * MEDIA_MOUNTED_READ_ONLY
 * 解释：返回getExternalStorageState() ，表明对象权限为只读
 * MEDIA_NOFS
 * 解释：返回getExternalStorageState() ，表明对象为空白或正在使用不受支持的文件系统。
 * MEDIA_REMOVED
 * 解释：返回getExternalStorageState() ，如果不存在 SDCard 返回
 * MEDIA_SHARED
 * 解释：返回getExternalStorageState() ，如果 SDCard 未安装 ，并通过 USB 大容量存储共享 返回
 * MEDIA_UNMOUNTABLE
 * 解释：返回getExternalStorageState() ，返回 SDCard 不可被安装 如果 SDCard 是存在但不可以被安装
 * MEDIA_UNMOUNTED
 * 解释：返回getExternalStorageState() ，返回 SDCard 已卸掉如果 SDCard  是存在但是没有被安装
 * <p/>
 * Environment 常用方法：
 * <p/>
 * 方法：getDataDirectory()
 * 解释：返回 File ，获取 Android 数据目录。
 * 方法：getDownloadCacheDirectory()
 * 解释：返回 File ，获取 Android 下载/缓存内容目录。
 * 方法：getExternalStorageDirectory()
 * 解释：返回 File ，获取外部存储目录即 SDCard
 * 方法：getExternalStoragePublicDirectory(String type)
 * 解释：返回 File ，取一个高端的公用的外部存储器目录来摆放某些类型的文件
 * 方法：getExternalStorageState()
 * 解释：返回 File ，获取外部存储设备的当前状态
 * 方法：getRootDirectory()
 * 解释：返回 File ，获取 Android 的根目录
 * <p/>
 * 2、讲述 StatFs 类
 * <p/>
 * StatFs 一个模拟linux的df命令的一个类,获得SD卡和手机内存的使用情况
 * StatFs 常用方法:
 * <p/>
 * getAvailableBlocks()
 * 解释：返回 Int ，获取当前可用的存储空间
 * getBlockCount()
 * 解释：返回 Int ，获取该区域可用的文件系统数
 * getBlockSize()
 * 解释：返回 Int ，大小，以字节为单位，一个文件系统
 * getFreeBlocks()
 * 解释：返回 Int ，该块区域剩余的空间
 * restat(String path)
 * 解释：执行一个由该对象所引用的文件系统
 */
public class GetStorageActivity extends BaseActivity {
    @InjectView(R.id.topLeft)
    ImageView mLeft;
    @InjectView(R.id.topTitle)
    TextView mTitle;
    @InjectView(R.id.secard_button)
    Button mButton;
    @InjectView(R.id.secard_text)
    TextView mText;
    @InjectView(R.id.secard_progressBar)
    ProgressBar mProgressBar;

    public static void startGetStorgeActivity(Activity activity) {
        Intent intent = new Intent(activity, GetStorageActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secard);
        ButterKnife.inject(this);
        initData();
    }

    private void initData() {
        mLeft.setImageResource(R.mipmap.returns);
        mTitle.setText("内存的大小");
    }

    @OnClick({R.id.topLeft, R.id.secard_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topLeft:
                finish();
                break;
            case R.id.secard_button:
                getSize();
                break;
        }
    }

    void getSize() {
        mText.setText("");
        mProgressBar.setProgress(0);
        /**判断是否插入了内存卡*/
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File path=Environment.getExternalStorageDirectory();
            /**取得SDCard文件的路径*/
            StatFs statfs = new StatFs(path.getPath());
            /**获取block的size*/
            long blockSize = statfs.getBlockSize();
            /**获取Block的数量*/
            long totalBlocks = statfs.getBlockCount();
            /**已经使用的block数量*/
            long availBlocks = statfs.getAvailableBlocks();

            String[] total=filesize(totalBlocks*blockSize);
            String[] availale=filesize(availBlocks*blockSize);
            //设置进度条的最大值
            if (Integer.parseInt(availale[0])<Integer.parseInt(total[0])){
                int maxValue=Integer.parseInt(availale[0]) *mProgressBar.getMax() /Integer.parseInt(total[0]);
                mProgressBar.setProgress(maxValue);
            }else {
                int maxValue=Integer.parseInt(availale[0]) *mProgressBar.getMax() /(Integer.parseInt(total[0])*1024);
                mProgressBar.setProgress(maxValue);
            }
            String Text="总共："+total[0]+total[1]+"\n"
                    +"可用:"+availale[0]+availale[1];
            mText.setText(Text);
        }else if(Environment.getExternalStorageState().equals(Environment.MEDIA_REMOVED)){
            Toast.makeText(GetStorageActivity.this, "没有sdCard", Toast.LENGTH_SHORT).show();
        }
    }

    /**返回数组 下标1代表大小，下标2代表单位KB/MB*/
    String[] filesize(long size){
        String str="";
        if(size>=1024){
            str="KB";
            size/=1024;
            if(size>=1024){
                str="MB";
                size/=1024;
                if (size >= 1024) {
                    str = "GB";
                    size /= 1024;
                }
            }
        }
        DecimalFormat formatter=new DecimalFormat();
        formatter.setGroupingSize(3);
        String result[] =new String[2];
        result[0]=formatter.format(size);
        result[1]=str;
        return result;
    }
}
