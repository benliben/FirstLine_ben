package com.example.benben.firstline.ui.activity.weather;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benben.firstline.R;
import com.example.benben.firstline.db.CoolWeatherDB;
import com.example.benben.firstline.http.HttpUtils;
import com.example.benben.firstline.http.LoadDialog;
import com.example.benben.firstline.model.CityModel;
import com.example.benben.firstline.model.CountyModel;
import com.example.benben.firstline.model.ProvinceModel;
import com.example.benben.firstline.ui.activity.BaseActivity;
import com.example.benben.firstline.ui.adapter.WeatherAdapter;
import com.example.benben.firstline.utils.HttpCallbackListener;
import com.example.benben.firstline.utils.Utility;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by benebn on 2016/5/31.
 */
public class WeatherActivity extends BaseActivity {
    @InjectView(R.id.topTitle)
    TextView mTitle;
    @InjectView(R.id.area_content)
    RecyclerView mContent;
    @InjectView(R.id.topLeft)
    ImageView mLeft;
    public static final int LEVEL_PROVINCE = 0, LEVEL_CITY = 1, LEVEL_COUNTY = 2;

    private CoolWeatherDB mDB;
    private List<String> dataList = new ArrayList<>();

    /**
     * 省列表
     */
    private List<ProvinceModel> provinceList;
    /**
     * 市列表
     */
    private List<CityModel> cityList;
    /**
     * 县列表
     */
    private List<CountyModel> countyList;

    /**
     * 选择的省
     */
    private ProvinceModel selectedProvince;
    /**
     * 选择的市
     */
    private CityModel selectedCity;
    /**
     * 当前选择的级别
     */
    private int currentLevel;

    /**
     * 是否从ChooseAreaActivity中跳转过来的
     */
    private boolean isFromChooseareaActivity;

    /**
     * 适配器
     */
    private WeatherAdapter mAdapter;

    public static void startWeatherActivity(Activity activity) {
        Intent intent = new Intent(activity, WeatherActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isFromChooseareaActivity = getIntent().getBooleanExtra("from_weather_activity", false);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        /**已经选择了城市且不是从WeatherActivity跳转过来，才会直接跳转到WeatherActivity*/
        if (prefs.getBoolean("city_selected", false)&&!isFromChooseareaActivity) {
            Intent intent = new Intent(this, ChooseAreaActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        setContentView(R.layout.choose_area);
        ButterKnife.inject(this);
        initData();
        initView();
    }

    private void initView() {
        mContent.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new WeatherAdapter(dataList);
        mContent.setAdapter(mAdapter);
        mDB = CoolWeatherDB.getInstance(this);
        mAdapter.setItemClickListener(new WeatherAdapter.OnItemClickListener() {
            @Override
            public void ItemClickListener(View view, int position) {
                if (currentLevel == LEVEL_PROVINCE) {
                    selectedProvince = provinceList.get(position);
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {
                    selectedCity = cityList.get(position);
                    queryCounty();
                } else if (currentLevel == LEVEL_COUNTY) {
                    String countyCode = countyList.get(position).getCountyCode();
                    Intent intent = new Intent(WeatherActivity.this, ChooseAreaActivity.class);
                    intent.putExtra("county_code", countyCode);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void ItemLongClickListener(View view, int position) {

            }
        });
        queryProvinces();//加载省级数据

    }

    /**
     * 查询全国的省，优先从数据库查询，如果没有查询到再到服务器上面去查询
     */
    private void queryProvinces() {
        provinceList = mDB.loadProvinces();
        if (provinceList.size() > 0) {
            dataList.clear();
            for (ProvinceModel model : provinceList) {
                dataList.add(model.getProvinceName());
            }
            mAdapter.notifyDataSetChanged();
            mContent.setSelected(true);
            mTitle.setText("中国");
            currentLevel = LEVEL_PROVINCE;
        } else {
            queryFromServer(null, "province");
        }
    }

    /**
     * 查询全省，优先从数据库查询，如果没有查询到再到服务器上面去查询
     */
    private void queryCities() {
        cityList = mDB.loadCities(selectedProvince.getId());
        if (cityList.size() > 0) {

            dataList.clear();
            for (CityModel model : cityList) {
                dataList.add(model.getCityName());
            }
            mAdapter.notifyDataSetChanged();
            mContent.setSelected(true);
            mTitle.setText(selectedProvince.getProvinceName());
            currentLevel = LEVEL_CITY;
        } else {
            queryFromServer(selectedProvince.getProvinceCode(), "city");
        }
    }

    /**
     * 查询全市，优先从数据库查询，如果没有查询到再到服务器上面去查询
     */
    private void queryCounty() {
        countyList = mDB.loadCounty(selectedCity.getId());
        if (countyList.size() > 0) {
            dataList.clear();
            for (CountyModel model : countyList) {
                dataList.add(model.getCountyName());
            }
            mAdapter.notifyDataSetChanged();
            mContent.setSelected(true);
            mTitle.setText(selectedCity.getCityName());
            currentLevel = LEVEL_COUNTY;
        } else {
            queryFromServer(selectedCity.getCityCode(), "county");
        }
    }

    private void queryFromServer(final String code, final String type) {

        String address;
        if (!TextUtils.isEmpty(code)) {

            address = "http://www.weather.com.cn/data/list3/city" + code + ".xml";
        } else {

            address = "http://www.weather.com.cn/data/list3/city.xml";
        }
        LoadDialog.Show(WeatherActivity.this, "查询中请等待...");
        HttpUtils.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                boolean result = false;
                if ("province".equals(type)) {
                    result = Utility.handleProvincesResponse(mDB, response);
                } else if ("city".equals(type)) {
                    result = Utility.handleCityResponse(mDB, response, selectedProvince.getId());
                } else if ("county".equals(type)) {
                    result = Utility.handleCountyResponse(mDB, response, selectedCity.getId());
                }
                if (result) {
                    /**通过runOnUIThread(）方法回到主线程处理逻辑*/
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            LoadDialog.Hide();
                            if ("province".equals(type)) {
                                queryProvinces();
                            } else if ("city".equals(type)) {
                                queryCities();
                            } else if ("county".equals(type)) {
                                queryCounty();
                            }
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                /**通过RunOnUIThread（）方法回到主线程处理逻辑*/
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadDialog.Hide();
                        Toast.makeText(WeatherActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

    private void initData() {
        mLeft.setImageResource(R.mipmap.returns);
    }

    @OnClick({R.id.topLeft})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topLeft:
                finish();
                break;
        }
    }

    /**
     * 捕获Back按键，根据当前的级别来判断，此时应该返回市列表、省列表、还是直接退出。
     */
    @Override
    public void onBackPressed() {
        if (currentLevel == LEVEL_COUNTY) {
            queryCities();
        } else if (currentLevel == LEVEL_CITY) {
            Intent intent = new Intent(this, WeatherActivity.class);
            startActivity(intent);
        } else {
            finish();
        }
    }
}
