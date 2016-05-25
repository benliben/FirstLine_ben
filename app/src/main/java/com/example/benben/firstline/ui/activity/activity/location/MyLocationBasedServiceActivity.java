package com.example.benben.firstline.ui.activity.activity.location;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.example.benben.firstline.R;
import com.example.benben.firstline.ui.activity.BaseActivity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by benben on 2016/5/14.
 * <p/>
 * 定位系统
 * <p/>
 * GPS_PROVIDER  GPS定位（精度高，耗电）
 * NETWORK_PROVIDER  使用网络定位（精度低，耗电较少）
 * PASSIVE_PROVIDER
 */
public class MyLocationBasedServiceActivity extends BaseActivity {
    private static final String TAG = "lyx";
    @InjectView(R.id.topLeft)
    ImageView mLeft;
    @InjectView(R.id.topTitle)
    TextView mTitle;
    @InjectView(R.id.location_content)
    TextView mContent;
    int a=1;
    /**
     * 百度地图控件
     */
    @InjectView(R.id.location_map)
    MapView mMap;
    @InjectView(R.id.location_buttton)
    Button mButtton;

    private String provider;

    private LocationManager locationManager;


    public static void startMyLocationBasedServiceActivity(Activity activity) {
        Intent intent = new Intent(activity, MyLocationBasedServiceActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    /**百度地图控件*/
    /**
     * 百度地图对象
     */
    private BaiduMap mBaiduMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_locatin);
        ButterKnife.inject(this);
        initVeiw();
        initLBC();
        init();
    }

    private void init() {
        mMap.removeViewAt(1);
        mMap.removeViewAt(2);
        mBaiduMap = mMap.getMap();

    }


    /**
     * 初始化View
     */
    private void initVeiw() {
        mTitle.setText("定位中");
        mLeft.setImageResource(R.mipmap.returns);
    }

    private void initLBC() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);//获得实例化
        /**获得所有可能的位置提供器*/
        List<String> providerList = locationManager.getProviders(true);//调用getProviders（）方法得到所有可用的位置提供器
        if (providerList.contains(locationManager.GPS_PROVIDER)) {
            provider = locationManager.GPS_PROVIDER;
        } else {
            /**d当没有可用的位置提供器时，提示用户*/
            Toast.makeText(MyLocationBasedServiceActivity.this, "请打开手机的定位系统", Toast.LENGTH_SHORT).show();
            return;
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Location location = locationManager.getLastKnownLocation(provider);             //获取到记录当前位置信息的Location对象
        Log.i(TAG, "____________________6_____________________: " + location);
        if (location != null) {
            /**显示当前设备的位置信息*/
            Log.i(TAG, "____________________7_____________________: ");
            showLocation(location);//将Location对象传给showLocation
        }
        locationManager.requestLocationUpdates(provider, 5000, 1, locationListener);//每隔5秒会检测一下位置的变化情况，
        // 当移动超过1米的时候就会调用LocationListener
        // 的OnLocationChanged（）方法
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMap.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMap.onPause();
    }

    /**
     * 关闭程序
     */
    @Override
    protected void onDestroy() {
        mMap.onDestroy();
        mMap = null;
        super.onDestroy();
        if (locationManager != null) {
            /**关闭程序是将监听移动器*/
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.removeUpdates(locationListener);
        }
    }


    /**
     * 设备在移动的情况下
     */
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            /**更新当前设置的位置信息*/
            showLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            /**位置提供器的类型*/
        }

        @Override
        public void onProviderEnabled(String provider) {
            /**监听位置变化的时间间隔*/
        }

        @Override
        public void onProviderDisabled(String provider) {
            /**监听位置变化的距离间隔（以米为单位）*/
        }
    };

    private void showLocation(Location location) {
        String currentPosition = "当前的维度是" + location.getLatitude() + "\n" + " 经度是" + location.getLongitude();
        Log.i(TAG, "当前的维度是" + location.getLatitude());
        Log.i(TAG, " 经度是" + location.getLongitude());
        mContent.setText(currentPosition);
    }

    @OnClick({R.id.topLeft, R.id.location_buttton})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topLeft:
                finish();
                break;
            case R.id.location_buttton:
                if (a == 0) {
                    /**普通地图*/
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                    a = 1;
                    mButtton.setText("普通地图");
                } else if (a == 1) {
                    /**卫星地图*/
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                    a = 2;
                    mButtton.setText("卫星地图");
                } else if (a == 2) {
                    /**开启交通图*/
                    mBaiduMap.setTrafficEnabled(true);
                    a = 3;
                    mButtton.setText("交通地图");
                } else if (a == 3) {
                    /**开启热力图*/
                    mBaiduMap.setBaiduHeatMapEnabled(true);
                    a = 0;
                    mButtton.setText("热力地图");
                }
                break;

        }

    }


}
