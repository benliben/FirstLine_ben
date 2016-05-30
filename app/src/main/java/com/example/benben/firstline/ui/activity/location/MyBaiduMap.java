package com.example.benben.firstline.ui.activity.location;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.benben.firstline.R;
import com.example.benben.firstline.ui.activity.BaseActivity;

import java.util.List;

/**
 * Created by benben on 2016/5/25.
 */
public class MyBaiduMap extends BaseActivity {
    private static final String TAG = "lyx";

    public static void startMyBaiduMap(Activity activity) {
        Intent intent = new Intent(activity, MyBaiduMap.class);
        ActivityCompat.startActivity(activity, intent, null);
    }


//    private NotifyLister mNotifyer;

    private LocationClient mLocationClient = null;// LocationClient类是定位sdk的核心类
    private BDLocationListener myListener = new MyLocationListener();// 注册定位监听，返回定位的结果
    private LocationClientOption.LocationMode mCurrentMode;// 定位模式


    public MapView mapView = null;
    public BaiduMap baiduMap = null;

    /**声明相关声明*/
    public LocationClient locationClient = null;
    /**自定义图标*/
    BitmapDescriptor mCurrentMarker = null;
    /**是否首次定位*/
    boolean isFirstLoc = true;

    /**定位SDK监听函数*/
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {

            /**nap view 销毁后不在处理新接收到的位置*/
            if (location == null || mapView == null) {
                return;
            }

            /**构造定位数据*/
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    /**此处设置开发者获取到的方向信息，顺时针0-360*/
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude())
                    .build();
            baiduMap.setMyLocationData(locData);//设置定位数据


            if (isFirstLoc) {//判断是否是第一次定位
                isFirstLoc = false;

                // 获取精度维度坐标
                LatLng ll = new LatLng(location.getLatitude(),
                location.getLongitude());

                MapStatus.Builder builder = new MapStatus.Builder();

                builder.target(ll).zoom(19.0f);
                // animationMapStatus()方法把定位到的点移动到地图中心
                baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//                 Receive Location
                StringBuffer sb = new StringBuffer(256);
                sb.append("time : ");
                sb.append(location.getTime());
                sb.append("\nerror code : ");
                sb.append(location.getLocType());
                sb.append("\nlatitude : ");
                sb.append(location.getLatitude());
                sb.append("\nlontitude : ");
                sb.append(location.getLongitude());
                sb.append("\nradius : ");
                sb.append(location.getRadius());
                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    sb.append("\nspeed : ");
                    sb.append(location.getSpeed());// 速度 单位：公里每小时
                    sb.append("\nsatellite : ");
                    sb.append(location.getSatelliteNumber());//卫星数量
                    sb.append("\nheight : ");
                    sb.append(location.getAltitude());// 海拔 单位：米
                    sb.append("\ndirection : ");
                    sb.append(location.getDirection());// 单位度
                    sb.append("\naddr : ");
                    sb.append(location.getAddrStr());//地址
                    sb.append("\ndescribe : ");
                    sb.append("gps定位成功");
                    Log.i(TAG, "sb: " + sb);

                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    sb.append("\naddr : ");
                    sb.append(location.getAddrStr());
                    // 运营商信息
                    sb.append("\noperationers : ");
                    sb.append(location.getOperators());
                    sb.append("\ndescribe : ");
                    sb.append("网络定位成功");
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    sb.append("\ndescribe : ");
                    sb.append("离线定位成功，离线定位结果也是有效的");
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    sb.append("\ndescribe : ");
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    sb.append("\ndescribe : ");
                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    sb.append("\ndescribe : ");
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }
                sb.append("\nlocationdescribe : ");
                sb.append(location.getLocationDescribe());// 位置语义化信息
                List<Poi> list = location.getPoiList();// POI数据
                if (list != null) {
                    sb.append("\npoilist size = : ");
                    sb.append(list.size());
                    for (Poi p : list) {
                        sb.append("\npoi= : ");
                        sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                    }
                }
//                System.out.println("BaiduLocationApiDem" + sb.toString());
                Log.i(TAG, "onReceiveLocation: "+sb.toString());
                new AlertDialog.Builder(MyBaiduMap.this).setTitle("定位").setMessage(sb.toString()).show();
            }
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_baidumap);

        mapView = (MapView) this.findViewById(R.id.bmapView);
        baiduMap = mapView.getMap();
        /**开启定位图层*/
        baiduMap.setMyLocationEnabled(true);
        /**初始化定位*/
//        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient = new LocationClient(this);
        /**注册监听事件*/
        mLocationClient.registerLocationListener(myListener);
        Log.i(TAG, "--------------------------6---------------------------------" + myListener);
        /**配置定位SDK参数*/
        initLocation();
    }

    private void initLocation() {
        Log.i(TAG, "____________________________2_____________________________________");
        LocationClientOption option = new LocationClientOption();
        // option.setLocationMode(LocationMode.Hight_Accuracy);//
        // 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        // 坐标类型分为三种：国测局经纬度坐标系(gcj02)，百度墨卡托坐标系(bd09)，百度经纬度坐标系(bd09ll)。
        option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);// 可选，默认false,设置是否使用gps
        option.setNeedDeviceDirect(true);// 返回的结果包含手机的方向
        // option.setLocationNotify(true);//
        // 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        // option.setIsNeedLocationDescribe(true);//
        // 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//
        // 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        // option.setIgnoreKillProcess(false);//
        // 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        // option.SetIgnoreCacheException(false);//
        // 可选，默认false，设置是否收集CRASH信息，默认收集
        // option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
        mLocationClient.start();// 开始定位
    }


    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocationClient.stop();
        // 关闭定位图层
        baiduMap.setMyLocationEnabled(true);
        //取消位置提醒
//        mLocationClient.removeNotifyEvent(mNotifyer);
        mapView.onDestroy();
        mapView = null;
        super.onDestroy();
    }


}
