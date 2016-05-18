package com.example.benben.firstline.ui.activity.radio;

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
import com.example.benben.firstline.ui.activity.BaseActivity;
import com.example.benben.firstline.ui.adapter.LeftAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by benben on 2016/5/18.
 */
public class RadioActive extends BaseActivity {
    @InjectView(R.id.topLeft)
    ImageView mLeft;
    @InjectView(R.id.topTitle)
    TextView mTitle;
    @InjectView(R.id.content_content)
    RecyclerView mContent;


    private List<LeftTagModel> mModel = new ArrayList<>();
    private LeftAdapter mAdapter;
    private String[] mData = {"检测网络变化"};

    public static void startRadioActivity(Activity activity) {
        Intent intent = new Intent(activity, RadioActive.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        ButterKnife.inject(this);
        initData();
        initView();
    }

    private void initView() {
        mContent.setLayoutManager(new LinearLayoutManager(RadioActive.this));
        mAdapter = new LeftAdapter(mData);
        mContent.setAdapter(mAdapter);
        mAdapter.setItemClickListener(new LeftAdapter.OnItemClickListener() {
            @Override
            public void ItemClickListener(View view, int position) {
                switch (position) {
                    case 0:
                        RadioFirstActivity.startRadioFirstActivity(RadioActive.this);
                        break;
                }
            }

            @Override
            public void ItemLongClickListener(View view, int position) {

            }
        });
    }

    private void initData() {
        mLeft.setImageResource(R.mipmap.returns);
        mTitle.setText("全局大喇叭，广播");
        for (String aMData : mData) {
            LeftTagModel model = new LeftTagModel();
            model.setName(aMData);
            mModel.add(model);

        }
    }

    @OnClick(R.id.topLeft)
    public void onClick() {
        finish();
    }
}
