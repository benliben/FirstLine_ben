package com.example.benben.firstline.ui.activity.storage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benben.firstline.R;
import com.example.benben.firstline.ui.activity.BaseActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by benebn on 2016/5/18.
 * 将数据储存到文件中
 * <p>
 * openFileOutput方法接收两个参数，
 * 第一个参数是文件名，在文件创建的时候使用的就是这个名称，注意这里指定的文件名不可以包含路径，
 * 因为所有的文件都是默认存储到/data/data/<package name>/files/ 目录下的。
 * 第二个参数是文件的操作模式，主要有两种模式可选，MODE_PRIVATE 和 MODE_APPEND。
 * 其中 MODE_PRIVATE 是默认的操作模式，表示当指定同样文件名的时候，所写入的内容将会覆盖原文件中的内容，
 * 而 MODE_APPEND 则表示如果该文件已存在就往文件里面追加内容，不存在就创建新文件
 */
public class FileActivity extends BaseActivity {
    private static final String TAG = "lyx";
    @InjectView(R.id.topLeft)
    ImageView mLeft;
    @InjectView(R.id.topTitle)
    TextView mTitle;
    @InjectView(R.id.file_content)
    EditText mContent;

    public static void startFileActivity(Activity activity) {
        Intent intent = new Intent(activity, FileActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        ButterKnife.inject(this);
        initData();
        initView();
    }

    private void initView() {
        String inputText = load();
        Log.i(TAG, "inputText"+inputText);
        if (!TextUtils.isEmpty(inputText)) {

            mContent.setText(inputText);
            mContent.setSelection(inputText.length());
            Toast.makeText(this, "从文件中去读取到了数据",Toast.LENGTH_SHORT).show();
        }
    }


    private void initData() {
        mLeft.setImageResource(R.mipmap.returns);
        mTitle.setText("将数据存储到文件中");

    }

    @OnClick(R.id.topLeft)
    public void onClick() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String data = mContent.getText().toString();
        Log.i(TAG, "onDestroy: "+data);
        save(data);
    }


    /**向文件中写入数据*/
    public void save(String data) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            Log.i(TAG, "13232132132132123123321321132321132");
            out=openFileOutput("data", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(data);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**从文件中读取数据*/
    @NonNull
    private String load() {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            in = openFileInput("data");
            reader = new BufferedReader(new InputStreamReader(in));
            String line ="";
            while ((line=reader.readLine())!=null){
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }

}
