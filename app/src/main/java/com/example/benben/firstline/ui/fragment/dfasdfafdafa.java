package com.example.benben.firstline.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.benben.firstline.R;
import com.example.benben.firstline.ui.activity.BaseActivity;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by benben on 2016/5/20.
 */
public class dfasdfafdafa extends BaseActivity {


    @InjectView(R.id.topLeft)
    ImageView mLeft;
    @InjectView(R.id.topTitle)
    TextView mTitle;
    @InjectView(R.id.camera_button)
    Button mButton;
    @InjectView(R.id.camera_imageView)
    ImageView mImageView;
    @InjectView(R.id.camera_from_album)
    Button mFromAlbum;
    @InjectView(R.id.camera_picture)
    ImageView mPicture;


    private Uri imageUri;

    private static final int TAKE_PHOTO=1,CROP_PHOTO=2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.inject(this);
        initData();
    }

    private void initData() {

    }

    @OnClick({R.id.topLeft, R.id.camera_button, R.id.camera_from_album})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topLeft:
                break;
            case R.id.camera_button:
                /**创建File对象，用于存储拍照后的照片，并将他存放在手机内存的根目录下*/
                File outputImage=new File(Environment.getExternalStorageDirectory(),"tampImage,jpg");//
//                File outImage=new File(Environment.getExternalStorageDirectory()
//                   File outImage=new File( Environment.getExternalStorageDirectory().getRootDirectory(),"") ;
                break;
            case R.id.camera_from_album:
                break;
        }
    }
}

