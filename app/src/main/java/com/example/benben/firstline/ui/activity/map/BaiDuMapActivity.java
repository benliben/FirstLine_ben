package com.example.benben.firstline.ui.activity.map;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.example.benben.firstline.R;
import com.example.benben.firstline.ui.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by benben on 2016/5/24.
 * 定位
 */
public class BaiDuMapActivity extends BaseActivity {

    public static void startBaiDuMapActivity(Activity activity) {
        Intent intent = new Intent(activity, BaiDuMapActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }


    BitmapDescriptor BD_A = BitmapDescriptorFactory.fromResource(R.mipmap.icon_visitmap_biao);
    /*定位相关*/
    LocationClient mLocClient;
//    public MyLocationListener mListener = new MyLocationListener();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;


    BaiduMap mBaiduMap;

    boolean isFirstloc = true;//是否为首次定位

    LatLngBounds latLngBounds;

    Marker current_marker;


    @InjectView(R.id.map_view)
    MapView mMapView;
    @InjectView(R.id.topLeft)
    ImageView mLeft;
    @InjectView(R.id.topTitle)
    TextView mTitle;
    @InjectView(R.id.map_button1)
    ImageButton requestLocButton;
    @InjectView(R.id.map_content)
    RelativeLayout mContent;

    private LocationManager mManager;
    private String provider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**在使用SDK各组件之前初始化context信息，传入getApplicationContext
         * 该方法要在setContentView方法之前实现*/
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_baidu);
        ButterKnife.inject(this);


        initView();

    }


    private void initView() {
        mLeft.setImageResource(R.mipmap.returns);
        mTitle.setText("百度地图");
//        mManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        /*获取所有可用的位置提供器*/
//        List<String> providerlist = mManager.getProviders(true);
//        if (providerlist.contains(LocationManager.GPS_PROVIDER)) {
//            provider = LocationManager.GPS_PROVIDER;
//        } else if (providerlist.contains(LocationManager.NETWORK_PROVIDER)) {
//            provider = LocationManager.NETWORK_PROVIDER;
//        } else {
//            /**当没有可用的位置提供器时，弹出toast提示用户*/
//            Toast.makeText(BaiDuMapActivity.this, "没有位置可用，请GPS或者WiFi定位", Toast.LENGTH_SHORT).show();
//        }
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        Location location = mManager.getLastKnownLocation(provider);
//        if (location != null) {
//            navigateUpTo(location);
//        }

        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;

        View.OnClickListener btnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mCurrentMode) {
                    //                    case NORMAL:
//                        requestLocButton.setImageResource(R.drawable.main_icon_follow);
//                        mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;
//                        mBaiduMap
//                                .setMyLocationConfigeration(new MyLocationConfiguration(
//                                        mCurrentMode, true, mCurrentMarker));
//                        break;
                    case COMPASS:
                        requestLocButton.setImageResource(R.mipmap.main_icon_follow);
                        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
                        MapStatus ms = new MapStatus.Builder(mBaiduMap.getMapStatus()).rotate(0).overlook(0).build();
                        MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(ms);

                        mBaiduMap.animateMapStatus(u);
                        mBaiduMap
                                .setMyLocationConfigeration(new MyLocationConfiguration(
                                        mCurrentMode, true, mCurrentMarker));
                        break;
                    case NORMAL:
                        requestLocButton.setImageResource(R.mipmap.main_icon_compass);
                        mCurrentMode = MyLocationConfiguration.LocationMode.COMPASS;
                        mBaiduMap
                                .setMyLocationConfigeration(new MyLocationConfiguration(
                                        mCurrentMode, true, mCurrentMarker));
                        break;
                }
            }
        };
        requestLocButton.setOnClickListener(btnClickListener);


        mBaiduMap = mMapView.getMap();
        /**开启定位图层*/
        mBaiduMap.setMyLocationEnabled(true);
        /**开启定位图层*/
        mMapView.removeViewAt(1);
        mMapView.removeViewAt(2);
        View child = mMapView.getChildAt(1);
        //隐藏百度logo和缩放控件ZoomControl
        if (child instanceof ImageView || child instanceof ZoomControls) {
            child.setVisibility(View.INVISIBLE);
        }
        mBaiduMap.setMaxAndMinZoomLevel(20, 10);

        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(18.0f);
        mBaiduMap.setMapStatus(msu);
        mBaiduMap
                .setMyLocationConfigeration(new MyLocationConfiguration(
                        mCurrentMode,true,mCurrentMarker));
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            /**
             * 手势操作地图，设置地图状态等操作导致地图状态开始改变
             * @param mapStatus 地图状态改变开始的地图状态
             */
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {

            }

            /**
             * 地图状态变化中
             * @param mapStatus 当前地图状态
             */
            @Override
            public void onMapStatusChange(MapStatus mapStatus) {

            }

            /**
             * 地图状态改变结束
             * @param mapStatus 地图状态改变结束后的地图状态
             */
            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                latLngBounds = mapStatus.bound;
            }
        });

//        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                Bundle bundle = marker.getExtraInfo();
////                mBaiduMap.hideInfoWindow();
//                ArrayList<BitmapDescriptor> giflist = new ArrayList<BitmapDescriptor>();
//                if (current_marker != null) {
//                    if (current_marker.equals(marker))
//                        return true;
//                    giflist = current_marker.getIcons();
//                    /**定义Maker坐标点*/
////                    LatLng point=new LatLng()
//                    /**构建*/
//                }
//                return marker;
//            }
//
//        });
    }

    private void navigateUpTo(Location location) {
//        int count = mView.get();

// 删除缩放控件
        mMapView.removeViewAt(2);
// 删除百度地图logo
        mMapView.removeViewAt(1);

        GeoPoint point = new GeoPoint((int) (location.getLatitude() * 1E6), (int) (location.getLongitude() * 1E6));
//        controll
        MyLocationData.Builder locationData = new MyLocationData.Builder();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @OnClick({R.id.topLeft, R.id.map_button1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topLeft:
                finish();
                break;
            case R.id.map_button1:
                break;
        }
    }
}
