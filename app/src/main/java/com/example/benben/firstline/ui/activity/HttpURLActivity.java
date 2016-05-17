package com.example.benben.firstline.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.benben.firstline.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by benben on 2016/5/13.
 * 使用HttpURLConnection
 */
public class HttpURLActivity extends BaseActivity {
    @InjectView(R.id.topLeft)
    ImageView mLeft;
    @InjectView(R.id.topTitle)
    TextView mTitle;

    @InjectView(R.id.request_content)
    TextView mContent;

    public static final int SHOW_RESPONSE=0;

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what) {
                case SHOW_RESPONSE:
                    String response = (String) msg.obj;//在这里进行UI操作，将结果显示到界面上
                    mContent.setText(response);
            }
        }
    };


    public static void startRequestActivity(Activity activity) {
        Intent intent = new Intent(activity, HttpURLActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        mTitle.setText("HttpURLConnection");
        mLeft.setImageResource(R.mipmap.returns);
    }

    @OnClick({R.id.topLeft, R.id.request_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topLeft:
                finish();
                break;
            case R.id.request_button:
                sendRequestWithHttpUrlConnection();
                break;
        }
    }

    private void sendRequestWithHttpUrlConnection() {
        /**开启线程来发起网络请求*/
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url=new URL("http://www.baidu.com");//传入目标的网络地址
                    connection = (HttpURLConnection) url.openConnection();//得到实例化
                    /**请求数据*/
                    connection.setRequestMethod("GET");//设置HTTP请所使用的方法

                    /**************************************************************************************/
                        /**如果是上传数据（账号和密码）*/
//                    connection.setRequestMethod("POST");
//                    DataOutputStream out = DataOutputStream(connection, getOutputStream());
//                    out.writeBytes("username=name&password=123456");
                    /**************************************************************************************/
                    connection.setConnectTimeout(8000);//链接超时
                    connection.setReadTimeout(8000);//读取超时
                    InputStream in = connection.getInputStream();//获取到服务器返回的输入流
                    /**下面对获取到的输入流进行读取*/
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder builder = new StringBuilder();
                    String line;
                    while((line=reader.readLine())!=null){
                        builder.append(line);
                    }

                    Message message = new Message();
                    message.what = SHOW_RESPONSE;
                    /**将服务器返回的结果存放到Message中*/
                    message.obj = builder.toString();
                    handler.sendMessage(message);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if (connection != null) {
                        connection.disconnect();//关掉HTTP链接
                    }
                }
            }
        }).start();
    }
}
