package com.example.benben.firstline.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.benben.firstline.R;
import com.example.benben.firstline.model.LeftTagModel;
import com.example.benben.firstline.ui.activity.SDCard.GetStorageActivity;
import com.example.benben.firstline.ui.activity.chat.ChatActivity;
import com.example.benben.firstline.ui.activity.login.LoginActivity;
import com.example.benben.firstline.ui.activity.map.BaiDuMapActivity;
import com.example.benben.firstline.ui.activity.music.MusicActivity;
import com.example.benben.firstline.ui.activity.sensor.CompassActivity;
import com.example.benben.firstline.ui.adapter.LeftAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2016/5/10.
 */
public class RightFragment extends BaseFragment {
    @InjectView(R.id.right_toolbar)
    Toolbar rightToolbar;
    @InjectView(R.id.right_collapsing)
    CollapsingToolbarLayout rightCollapsing;
    @InjectView(R.id.right_recyclerView)
    RecyclerView rightRecyclerView;
    private View rootView;

    private String[] mData = {"指南针","聊天","登录","音乐播放","获取内存","百度地图"};
    private LeftAdapter mAdapter;
    private List<LeftTagModel> mModel = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_right, container, false);
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

    private void initData() {
        for (int i = 0; i < mData.length; i++) {
            LeftTagModel model = new LeftTagModel();
            model.setName(mData[i]);
            mModel.add(model);
        }

    }

    private void initView() {
        rightRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new LeftAdapter(mData);
        rightRecyclerView.setAdapter(mAdapter);
        mAdapter.setItemClickListener(new LeftAdapter.OnItemClickListener() {
            @Override
            public void ItemClickListener(View view, int position) {
                switch (position) {
                    case 0:
                        /**指南针*/
                        CompassActivity.startCompassActivity(getActivity());
                        break;
                    case 1:
                        /**聊天*/
                        ChatActivity.startChatActivity(getActivity());
                        break;
                    /**登录*/
                    case 2:
                        LoginActivity.startLoginActivity(getActivity());
                        break;
                    /**音乐*/
                    case 3:
                        MusicActivity.startMusicActivity(getActivity());
                        break;
                    /**获取内存*/
                    case 4:
                        GetStorageActivity.startGetStorgeActivity(getActivity());
                        break;
                    case 5:
                    /**百度地图*/
                        BaiDuMapActivity.startBaiDuMapActivity(getActivity());
                        break;
                }
            }

            @Override
            public void ItemLongClickListener(View view, int position) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
