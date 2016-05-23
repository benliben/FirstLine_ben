package com.example.benben.firstline.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benben.ben_poster_library.PosterLayout;
import com.example.benben.firstline.R;
import com.example.benben.firstline.ui.fragment.LeftFragment;
import com.example.benben.firstline.ui.fragment.RightFragment;

import java.util.ArrayList;
import java.util.List;

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


    /**
     * 两组滑动的广告
     */
    @InjectView(R.id.first_posterLayout)
    PosterLayout mPosterLayout;
    @InjectView(R.id.first_posterLayout2)
    PosterLayout mPosterLayout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initView();
        /**广告活动*/
        initData();
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





/*********************************************广告活动***********************************************/
    private void initData() {

        /**第一组广告*/
        final List<String> urls = new ArrayList<>();
        urls.add("http://fj.heze.cc/attachments/forum/month_0907/20090726_3a990fc8bcb6fa1d3878pAMBPjA75B65.jpg");
        urls.add("http://img04.tooopen.com/images/20131211/sy_51301885361.jpg");
        urls.add("http://gb.cri.cn/mmsource/images/2007/07/03/el070703186.jpg");
        urls.add("http://pic.3h3.com/up/2012-12/20121249537219310.jpg");
        urls.add("http://hiphotos.baidu.com/xjpgh/pic/item/b1f850314236c62debc4afb7.jpg");
        urls.add("http://img04.tooopen.com/images/20121219/tooopen_18092442.jpg");
        urls.add("http://imgsrc.baidu.com/forum/pic/item/dcc451da81cb39dbf3c1c7c4d0160924ab18301a.jpg");
        mPosterLayout.setViewUrls(urls);
        /**监听事件*/

        mPosterLayout.setOnBannerItemClickListener(new PosterLayout.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(MainActivity.this, String.valueOf(position),Toast.LENGTH_SHORT).show();
            }
        });

        mPosterLayout2.setOnBannerItemClickListener(new PosterLayout.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(MainActivity.this, String.valueOf(position),Toast.LENGTH_SHORT).show();
            }
        });

        /**第二组广告*/
        final List<String> urls2 = new ArrayList<>();
        urls2.add("http://images.ali213.net/picfile/pic/2013/12/07/927_20131207153100256.jpg");
        urls2.add("http://pic10.nipic.com/20101004/3320946_021726451306_2.jpg");
        mPosterLayout2.setViewUrls(urls2);
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.i("lyx", "暂停了: ");
        PosterLayout posterLayout = new PosterLayout(MainActivity.this);
        posterLayout.stopAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("lyx", "停止了: ");
        PosterLayout posterLayout = new PosterLayout(MainActivity.this);
        posterLayout.stopAutoPlay();
    }
}
