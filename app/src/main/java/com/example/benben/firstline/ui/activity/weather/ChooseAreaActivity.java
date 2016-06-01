package com.example.benben.firstline.ui.activity.weather;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.benben.firstline.R;
import com.example.benben.firstline.Service.AutoUpdateService;
import com.example.benben.firstline.http.HttpUtils;
import com.example.benben.firstline.ui.activity.BaseActivity;
import com.example.benben.firstline.utils.HttpCallbackListener;
import com.example.benben.firstline.utils.Utility;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by benebn on 2016/6/1.
 */
public class ChooseAreaActivity extends BaseActivity {

    @InjectView(R.id.Layout_info)
    LinearLayout mInfo;

    @InjectView(R.id.topLeft)//切换城市按钮
            ImageView mLeft;
    @InjectView(R.id.topRight)//更新天气按钮
            ImageView mRight;
    @InjectView(R.id.topTitle)//城市名
            TextView mTitle;
    @InjectView(R.id.layout_time)//更新时间
            TextView mTime;
    @InjectView(R.id.layout_date)//当前日期
            TextView mDate;
    @InjectView(R.id.layout_weather)//天气描述
            TextView mWeather;
    @InjectView(R.id.layout_temp1)//气温1
            TextView mTemp1;
    @InjectView(R.id.layout_temp2)//气温2
            TextView mTemp2;

    public static void startChooseAreaActivity(Activity activity, String county_code) {
        Intent intent = new Intent(activity, ChooseAreaActivity.class);
        intent.putExtra(ChooseAreaActivity.ACCOUNT_SERVICE, county_code);
        ActivityCompat.startActivity(activity, intent, null);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_layout);
        ButterKnife.inject(this);
        initData();
        initView();
    }

    private void initData() {
        mLeft.setImageResource(R.mipmap.home);
        mRight.setImageResource(R.mipmap.updeta);
    }

    private void initView() {
        String countyCode = getIntent().getStringExtra("county_code");
        if (!TextUtils.isEmpty(countyCode)) {
            //有县级代码就去查询天气
            mTime.setText("同步中...");
            mInfo.setVisibility(View.INVISIBLE);
            mTitle.setVisibility(View.INVISIBLE);
            queryWeatherCode(countyCode);
        } else {
            //没有县级代码就直接显示本地天气
            showWeather();
        }
    }

    @OnClick({R.id.topLeft, R.id.topRight})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topLeft:
                Intent intent = new Intent(this, WeatherActivity.class);
                intent.putExtra("from_weather_activity", true);
                startActivity(intent);
                finish();
                break;
            case R.id.topRight:
                Log.i("lyx", "点击同步: ");
                mTime.setText("同步中...");
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                String weatherCode = prefs.getString("weather_code", "");
                if (!TextUtils.isEmpty(weatherCode)) {
                    queryWeatherInfo(weatherCode);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 查询县级代号所对应的天气代号
     */
    private void queryWeatherCode(String countyCode) {

        String address = "http://www.weather.com.cn/data/list3/city" + countyCode + ".xml";
        Log.i("lyx", "address: "+address);
        queryFromServer(address, "countyCode");
    }

    /**
     * 查询天气代号所对应的天气
     */
    private void queryWeatherInfo(String weatherCode) {
        String address = "http://www.weather.com.cn/data/cityinfo/" + weatherCode + ".html";
        Log.i("lyx", "address: "+address);
        queryFromServer(address, "weatherCode");
    }

    /**
     * 根据传入的地址类型去向服务器查询天气代码或者天气信息
     */
    private void queryFromServer(final String address, final String type) {

        HttpUtils.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                if ("countyCode".equals(type)) {
                    if (!TextUtils.isEmpty(response)) {
                        String[] array = response.split("\\|");
                        if (array != null && array.length == 2) {
                            String weatherCode = array[1];
                            queryWeatherInfo(weatherCode);
                        }
                    }
                } else if ("weatherCode".equals(type)) {
                    Log.i("lyx", "___________1__________");
                    /*处理服务器返回的天气信息*/
                    Utility.handleWeatherResponse(ChooseAreaActivity.this, response);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showWeather();
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTime.setText("同步失败");
                    }
                });
            }
        });
    }

    private void showWeather() {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        mTitle.setText(prefs.getString("city_name", ""));
        mTitle.setText(prefs.getString("city_name", ""));
        mTemp1.setText(prefs.getString("temp1", ""));
        mTemp2.setText(prefs.getString("temp2", ""));
        mWeather.setText(prefs.getString("weather_desp", ""));
        mTime.setText("今天" + prefs.getString("publish_time", "") + "发布");
        mDate.setText(prefs.getString("current_date", ""));
        mInfo.setVisibility(View.VISIBLE);
        mTitle.setVisibility(View.VISIBLE);
        startService(new Intent(this, AutoUpdateService.class));


    }
}
