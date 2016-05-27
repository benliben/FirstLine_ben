package com.example.benben.firstline.Service;

import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.example.benben.firstline.gps.IGPSCallback;
import com.example.benben.firstline.http.LoadDialog;
import com.example.benben.firstline.utils.StringUtil;

import java.io.BufferedWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by beneben on 2016/5/27.
 */
public class CoreService {
    public final static String LOCATION_LISTENER_CHANGED = "location_listener_changed";

    private static ArrayList<IGPSCallback> igpsCallbacks;
    private static LocationClient mLocationClient = null;// 百度定位的操作类
    static GeoCoder mSearch;
    static boolean writefile = false;
    static BufferedWriter writer;
    private static BDLocationListener bdLocationListener = new MyLocationListener();// BDLocationListener位置监听
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//    public synchronized static void requestLocation(final IGPSCallback igpsCallback) {
//
//        if (igpsCallbacks == null || igpsCallbacks.isEmpty()) {
//            igpsCallbacks = new ArrayList<IGPSCallback>();
//
//        }
//        if (igpsCallback != null)
//            igpsCallbacks.add(igpsCallback);
//
////        initLocation();
//        if (!mLocationClient.isStarted()) {
//            mLocationClient.start();
//            LocationListenerChanged(1);
//
//            log("开启定位服务---inputLocation模块");
//        }
//
//        mLocationClient.requestLocation();
//        log("mLocationClient.requestLocation();");
//    }
//    private static void initLocation() {
//        if (mLocationClient == null) {
////             SDKInitializer.initialize(getApplicationContext()); // 声明LocationClient类
//            mLocationClient.registerLocationListener(bdLocationListener); // 注册监听函数
//            LocationClientOption option = new LocationClientOption();
//            option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);// 设置定位模式
//            option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
//            option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
//            option.setScanSpan(0);
//            option.setOpenGps(false);//可选，默认false,设置是否使用gps
//            mLocationClient.setLocOption(option);
//            mLocationClient.start();
//            LocationListenerChanged(1);
//
//            log("开启定位服务---inputLocation模块");
//        }
//        LocationClientOption option = new LocationClientOption();
//        // option.setLocationMode(LocationMode.Hight_Accuracy);//
//        // 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
//        // 坐标类型分为三种：国测局经纬度坐标系(gcj02)，百度墨卡托坐标系(bd09)，百度经纬度坐标系(bd09ll)。
//        option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
//        int span = 1000;
//        option.setScanSpan(span);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
//        option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
//        option.setOpenGps(true);// 可选，默认false,设置是否使用gps
//        option.setNeedDeviceDirect(true);// 返回的结果包含手机的方向
//        // option.setLocationNotify(true);//
//        // 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
//        // option.setIsNeedLocationDescribe(true);//
//        // 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
//        option.setIsNeedLocationPoiList(true);//
//        // 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
//        // option.setIgnoreKillProcess(false);//
//        // 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
//        // option.SetIgnoreCacheException(false);//
//        // 可选，默认false，设置是否收集CRASH信息，默认收集
//        // option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
//        mLocationClient.setLocOption(option);
//        LoadDialog.Hide();
//        mLocationClient.start();// 开始定位
//    }
    public synchronized static void LocationListenerChanged(int state) {
        if (state == 0) {
            if (igpsCallbacks != null && !igpsCallbacks.isEmpty()) {
                ArrayList<IGPSCallback> list = new ArrayList<IGPSCallback>();

                for (IGPSCallback callback : igpsCallbacks) {

                    if (callback.failCallBack("")) {
                        list.add(callback);
                    }
                }
                if (!list.isEmpty()) {
                    igpsCallbacks.removeAll(list);
                }
                locationerror++;
            }
        }
        Intent intent = new Intent(LOCATION_LISTENER_CHANGED);
        intent.putExtra("state", state);


    }

    public static void log(String msg) {
//        Logs.e(msg);
        try {
            if (writefile && writer != null) {
                writer.write(sdf.format(new Date()) + ":" + msg + "\n");
                writer.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static BDLocation noaddress;
    static BDLocation oldBdLocation;
    static int locationerror = 0;

    /**
     * 位置改变时的监听函数
     *
     * @author tangjunjie
     */
    public static class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null || (location.getLocType() != 161 && location.getLocType() != 61 && location.getLocType() != 66)
                    ) {
                log("定位失败，错误代码="
                        + (location == null ? 0 : location.getLocType()));
                synchronized (igpsCallbacks) {
                    if (igpsCallbacks != null && !igpsCallbacks.isEmpty()) {
                        ArrayList<IGPSCallback> list = new ArrayList<IGPSCallback>();

                        for (IGPSCallback callback : igpsCallbacks) {

                            if (callback.failCallBack("定位失败")) {
                                list.add(callback);
                            }
                        }
                        if (!list.isEmpty()) {
                            igpsCallbacks.removeAll(list);
                        }
                        locationerror++;
                    }
                }
            } else {

                if (StringUtil.isEmpty(location.getAddrStr()) || location.getAddrStr().contains("null")) {
                    location.setAddrStr("未获取到位置信息");
                    noaddress = location;
                    if (mSearch == null)
                        return;
                    mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                            .location(new LatLng(noaddress.getLatitude(), noaddress.getLongitude())));
                    return;
                } else {
                    location.setAddrStr((location.getLocType() == 61 ? "GPS:" : "") + location.getAddrStr());
                }
                handleLocation(location);

            }

        }
    }

    public static void handleLocation(BDLocation location) {
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

//        new AlertDialog.Builder(CoreService.this).setTitle("定位").setMessage(sb.toString()).show();

    }
}
