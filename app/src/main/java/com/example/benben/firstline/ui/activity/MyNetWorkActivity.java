package com.example.benben.firstline.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.benben.firstline.R;
import com.example.benben.firstline.model.LeftTagModel;
import com.example.benben.firstline.ui.adapter.LeftAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by benebn on 2016/5/13.
 * 网络的学习
 */
public class MyNetWorkActivity extends BaseActivity {
    @InjectView(R.id.topLeft)
    ImageView mLeft;
    @InjectView(R.id.topTitle)
    TextView mTitle;
    @InjectView(R.id.network_content)
    RecyclerView mContent;

    private List<LeftTagModel> mModel = new ArrayList<>();
    private LeftAdapter mAdapter;
    private String[] mDatas = {"WebView", "HttpURLConnection", "JSONObject"};

    public static void startMyNetWorkActivity(Activity activity) {
        Intent intent = new Intent(activity, MyNetWorkActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
        ButterKnife.inject(this);
        initData();
        initView();
    }

    private void initData() {
        for (int i = 0; i < mDatas.length; i++) {
            LeftTagModel model = new LeftTagModel();
            model.setName(mDatas[i]);
            mModel.add(model);
        }

    }

    private void initView() {
        mLeft.setImageResource(R.mipmap.returns);
        mTitle.setText("网络");

        mContent.setLayoutManager(new LinearLayoutManager(MyNetWorkActivity.this));
        mAdapter = new LeftAdapter(mDatas);
        mContent.setAdapter(mAdapter);
        mAdapter.setItemClickListener(new LeftAdapter.OnItemClickListener() {
            @Override
            public void ItemClickListener(View view, int position) {
                switch (position) {
                    case 0:
                        /**WebView*/
                        WebViewActivity.startWebViewActivity(MyNetWorkActivity.this);
                        break;
                    case 1:
                        HttpURLActivity.startRequestActivity(MyNetWorkActivity.this);
                        break;
                }
            }

            @Override
            public void ItemLongClickListener(View view, int position) {
                Toast.makeText(MyNetWorkActivity.this, "点一下就好，不需要长按", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.topLeft)
    public void onClick() {
        finish();
    }

}
