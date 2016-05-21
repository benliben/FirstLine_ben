package com.example.benben.firstline.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.TextView;


import com.example.benben.firstline.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by benben on 2016/5/12.
 * 照相机和相册
 */
public class CameraActivity extends BaseActivity {


    public static void startCameraActivity(Activity activity) {
        Intent intent = new Intent(activity, CameraActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    @InjectView(R.id.topLeft)//返回
            ImageView mLeft;
    @InjectView(R.id.topTitle)//头部文字
            TextView mTitle;
    @InjectView(R.id.camera_button)//照相按钮
            Button mButton;
    @InjectView(R.id.camera_imageView)//照相的照片展示
            ImageView cameraImageView;
    @InjectView(R.id.camera_from_album)//相册按钮
            Button mFromAlbum;


    private Uri imageUri;

    public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO = 2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        mLeft.setImageResource(R.mipmap.returns);
        mTitle.setText("照相机");
    }

    @OnClick({R.id.topLeft, R.id.camera_button, R.id.camera_from_album})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topLeft:
                finish();
                break;
            case R.id.camera_button:
                /**创建File对象，用于储存拍照后的照片,并将他存放在手机的SD卡的根目录了下*/
                File outputImage = new File(Environment.getExternalStorageDirectory(), "tempImage1"+System.currentTimeMillis()+".jpg");//调用Environment的getExternalStorageDirectory()方法可以获取时间SD卡的根目录
                Log.i("lyx", "onClick: " + Environment.getExternalStorageDirectory());
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    } else {
                        outputImage.createNewFile();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageUri = Uri.fromFile(outputImage);//将File对象转换成Uri对象，表示这张图片的唯一地址
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//指定图片输出地址
                startActivityForResult(intent, TAKE_PHOTO);//启动相机程序
                /**由于是用startActivityForResult（）来启动活动的，因此拍完照后有结果返回到onActivityResult（）中*/
                break;
            case R.id.camera_from_album:
                /**创建File对象，用于储存选择的照片*/
                File outPutImage1 = new File(Environment.getExternalStorageDirectory(), "output_image"+System.currentTimeMillis()+".jpg");
                try {
                    if (outPutImage1.exists()) {
                        outPutImage1.delete();
                    }
                    outPutImage1.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageUri = Uri.fromFile(outPutImage1);
                Intent intent1 = new Intent("android.intent.action.GET_CONTENT");
                intent1.setType("image/*");
                intent1.putExtra("crop", true);
                intent1.putExtra("scale", true);
                intent1.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent1, CROP_PHOTO);//打开相册程序选择照片
                /**第二个参数为常量，这样的好处是从相册选择好照片后可以直接进入到CROP_PHOTO的case下将照片显示出来*/
                break;
        }
    }

    /**
     * 拍完照片后对照片进行剪切
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    /**如果拍照成功*/
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, CROP_PHOTO);//启动剪裁程序
                    /**程序又会会掉到onActivityResult（）方法中*/
                }
                break;

//            case CROP_PHOTO:
//                if (resultCode == RESULT_OK) {
//                    try {
//                        Bitmap bitmap = BitmapFactory.decodeStream
//                                (getContentResolver().openInputStream(imageUri));
//                        cameraImageView.setImageBitmap(bitmap); // 将裁剪后的照片显示出来
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                }
//                break;


            case CROP_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        /**将照片解析成Bitmap对象*/
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        Log.i("lyx", "onActivityResult: " + bitmap);
                        cameraImageView.setImageBitmap(bitmap);//将剪切后的照片显示出来
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
