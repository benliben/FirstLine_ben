package com.example.benben.firstline.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.benben.firstline.R;
import com.example.benben.firstline.model.LeftTagModel;
import com.example.benben.firstline.ui.activity.ContentActivity;
import com.example.benben.firstline.ui.activity.MediaActivity;
import com.example.benben.firstline.ui.activity.MyNetWorkActivity;
import com.example.benben.firstline.ui.activity.MyServiceActivity;
import com.example.benben.firstline.ui.activity.activity.activityActivity;
import com.example.benben.firstline.ui.activity.lbs.MyLocationBasedServiceActivity;
import com.example.benben.firstline.ui.activity.radio.RadioActive;
import com.example.benben.firstline.ui.activity.sensor.TheSensorActivity;
import com.example.benben.firstline.ui.activity.storage.StorageActivity;
import com.example.benben.firstline.ui.adapter.LeftAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/5/10.
 */
public class LeftFragment extends BaseFragment {
    @InjectView(R.id.left_top)
    TextView mTop;
    @InjectView(R.id.left_content)
    RecyclerView mContent;
    @InjectView(R.id.left_button)
    TextView mButton;
    private View rootView;

    private List<LeftTagModel> mModel;
    private LeftAdapter mAdapter;
    private String[] mData = {"内容提供器", "运用手机多媒体", "服务", "网络", "位置的服务"
            , "传感器", "活动", "广播", "文件的存储"};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_left, container, false);
        }
        ButterKnife.inject(this, rootView);
        return rootView;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);

    }

    private void initView() {
        mContent.setLayoutManager(new LinearLayoutManager(getActivity()));
        mContent.setAdapter(mAdapter);
        mAdapter.setItemClickListener(new LeftAdapter.OnItemClickListener() {
            @Override
            public void ItemClickListener(View view, int position) {
                switch (position) {
                    case 0:
                        /**内容提供者*/
                        ContentActivity.startContentActivity(getActivity());
                        break;
                    case 1:
                        /**多媒体*/
                        MediaActivity.startMediaActivity(getActivity());
                        break;
                    case 2:
                        /**服务*/
                        MyServiceActivity.startMyServiceActiviy(getActivity());
                        break;
                    case 3:
                        /**网络*/
                        MyNetWorkActivity.startMyNetWorkActivity(getActivity());
                        break;
                    case 4:
                        /**位置服务*/
                        MyLocationBasedServiceActivity.startMyLocationBasedServiceActivity(getActivity());
                        break;
                    case 5:
                        /**传感器*/
                        TheSensorActivity.startTheSensorActivity(getActivity());
                        break;
                    case 6:
                        /**活动*/
                        activityActivity.startactivityActivity(getActivity());
                        break;
                         /**广播*/
                    case 7:
                        RadioActive.startRadioActivity(getActivity());
                        break;
                        /**广播*/
                    case 8:
                        StorageActivity.startStorageActivity(getActivity());
                        break;
                }
                Toast.makeText(getActivity(), "你点击了" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void ItemLongClickListener(View view, int position) {
                Toast.makeText(getActivity(), "你长按了" + position, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initData() {
        mModel = new ArrayList<>();
        for (int i = 0; i < mData.length; i++) {
            LeftTagModel model = new LeftTagModel();
            model.setName(mData[i]);
            mModel.add(model);
        }
        mAdapter = new LeftAdapter(mData);

    }

    @OnClick({R.id.left_top, R.id.left_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_top:
                break;
            case R.id.left_button:
                break;
        }
    }
}
