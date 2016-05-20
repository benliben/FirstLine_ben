package com.example.benben.firstline.ui.activity.storage;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.benben.firstline.R;
import com.example.benben.firstline.ui.activity.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by benben on 2016/5/19.
 * <p>
 * 将数据储存到SharedPreferences中
 * <p>
 * android 中主要提供了三种方法用于得到sharedPreferences对象
 * <p>
 * 1.Context类中的getSharedPreferences（）方法
 * 此方法接收两个参数，
 * 第一个参数用于指定 SharedPreferences 文件的名称，如果指定的文件不存在则会创建一个，
 * SharedPreferences 文件都是存放在/data/data/<package name>/shared_prefs/目录下的。
 * 第二个参数用于指定操作模式，主要有两种模式可以选择，MODE_PRIVATE 和 MODE_MULTI_PROCESS。
 * MODE_PRIVATE 仍然是默认的操作模式，和直接传入 0 效果是相同的，
 * 表示只有当前的应用程序才可以对这个SharedPreferences 文件进行读写。
 * 2.Activity 类中的 getPreferences()方法
 * 这个方法和 Context 中的 getSharedPreferences()方法很相似，不过它只接收一个操作模式参数，
 * 因为使用这个方法时会自动将当前活动的类名作为 SharedPreferences 的文件名
 * 3.3.PreferenceManager 类中的 getDefaultSharedPreferences()方法
 * 这是一个静态方法，它接收一个 Context 参数，并自动使用当前应用程序的包名作为前缀来命名 SharedPreferences 文件。
 * 得到了 SharedPreferences 对象之后，就可以开始向 SharedPreferences 文件中存储数据了
 */
public class SharedActivity extends BaseActivity {


    @InjectView(R.id.topLeft)
    ImageView mLeft;
    @InjectView(R.id.topTitle)
    TextView mTitle;
    @InjectView(R.id.shared_button)
    Button mButton;
    @InjectView(R.id.shared_button1)
    Button mButton1;
    @InjectView(R.id.shared_content)
    TextView mContent;

    public static void startSharedActivity(Activity activity) {
        Intent intent = new Intent(activity, SharedActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared);
        ButterKnife.inject(this);
        initData();
    }

    private void initData() {
        mLeft.setImageResource(R.mipmap.returns);
        mTitle.setText("SharedPreferences");

    }



    @OnClick({R.id.topLeft, R.id.shared_button, R.id.shared_button1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topLeft:
                finish();
                break;
            case R.id.shared_button:
                SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();//指定文件名称为data
                editor.putString("name", "benebn");//添加数据姓名
                editor.putInt("age", 20);//添加数据年龄
                editor.putBoolean("married", false);//是否结婚
                editor.commit();//提交数据
                break;
            case R.id.shared_button1:
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                int age = pref.getInt("age", 0);
                String name = pref.getString("name", "");
                boolean married = pref.getBoolean("married", false);

                mContent.setText("姓名:"+name+"年龄:"+age+"是否结婚:"+married);
                break;
        }
    }
}
