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


import com.example.benben.firstline.R;
import com.example.benben.firstline.model.LeftTagModel;
import com.example.benben.firstline.ui.adapter.LeftAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by benben on 2016/5/11.
 *
 *
 * <p>
 * 内容提供器
 * 1.ContentResolver
 * url；content：//com.example.app.provider/table1
 * content://  协议声明
 * com.example.app.provider 权限名
 * /table1  文件名称
 */
public class ContentActivity extends BaseActivity {
    public static void startContentActivity(Activity activity) {
        Intent intent = new Intent(activity, ContentActivity.class);
        ActivityCompat.startActivity(activity,intent,null);
    }
    @InjectView(R.id.topLeft)
    ImageView mLeft;
    @InjectView(R.id.topTitle)
    TextView mTitle;
    @InjectView(R.id.content_content)
    RecyclerView mContent;

    private List<LeftTagModel> mModel = new ArrayList<>();
    private LeftAdapter mAdapter;
    private String[] mDates = {"读取联系人", "读取其他程序的内容"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        ButterKnife.inject(this);
        initData();
        initView();
    }

    private void initView() {
        mContent.setLayoutManager(new LinearLayoutManager(ContentActivity.this));
        mAdapter = new LeftAdapter(mDates);
        mContent.setAdapter(mAdapter);
        mAdapter.setItemClickListener(new LeftAdapter.OnItemClickListener() {
            @Override
            public void ItemClickListener(View view, int position) {
                switch (position) {
                    case 0:
                        /**读取联系人*/
                        ContentsTestActivity.startContentsTestActivity(ContentActivity.this);
                        break;
                    case 1:
                        /**读取其他程序的内容*/
                        ProviderTestActivity.startProviderTestActivity(ContentActivity.this);
                        break;
                }
            }

            @Override
            public void ItemLongClickListener(View view, int position) {

            }
        });
    }

    private void initData() {
        for (int i = 0; i < mDates.length; i++) {
            LeftTagModel model = new LeftTagModel();
            model.setName(mDates[i]);
            mModel.add(model);
        }
    }

    @OnClick(R.id.topLeft)
    public void onClick() {
    }
}
