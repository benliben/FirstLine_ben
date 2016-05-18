package com.example.benben.firstline.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.benben.firstline.R;
import com.example.benben.firstline.ui.fragment.LeftFragment;
import com.example.benben.firstline.ui.fragment.RightFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    public static void startMainActivity(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    @InjectView(R.id.topLeft)
    ImageView mLeft;
    @InjectView(R.id.topRight)
    ImageView mRight;
    @InjectView(R.id.topTitle)
    TextView mTitle;
    @InjectView(R.id.main_content)
    FrameLayout mContent;
    @InjectView(R.id.main_left)
    FrameLayout Left;
    @InjectView(R.id.main_right)
    FrameLayout Right;

    @InjectView(R.id.main_drawerLayout)
    DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initView();
        initData();
    }

    private void initData() {

    }

    private void initView() {
        mLeft.setImageResource(R.mipmap.menu);
        mRight.setImageResource(R.mipmap.menuright);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.main_left, new LeftFragment());
        transaction.replace(R.id.main_right, new RightFragment());
        transaction.commit();

    }

    @OnClick({R.id.topLeft, R.id.topRight})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topLeft:
                mDrawerLayout.openDrawer(Left);
                break;
            case R.id.topRight:
                mDrawerLayout.openDrawer(Right);
                break;
        }
    }
}
