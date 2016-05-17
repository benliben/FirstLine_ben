package com.example.benben.firstline.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.benben.firstline.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by benben on 2016/5/13.
 * WebView的用法
 */
public class WebViewActivity extends BaseActivity {
    @InjectView(R.id.topLeft)
    ImageView mLeft;
    @InjectView(R.id.topTitle)
    TextView mTitle;
    @InjectView(R.id.content)
    WebView content;

    public static void startWebViewActivity(Activity activity) {
        Intent intent = new Intent(activity, WebViewActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        mTitle.setText("WebView");
        mLeft.setImageResource(R.mipmap.returns);

        content.getSettings().setJavaScriptEnabled(true);//设置浏览器的属性
        content.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);//根据传入的参数去加载新的网页


                return true;//表示当前的webView就可以处理请求，不需要借助浏览器
            }
        });
        content.loadUrl("http://www.baidu.com");
    }


    @OnClick({R.id.topLeft})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topLeft:
                finish();
                break;

        }
    }
}
