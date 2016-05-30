package com.example.benben.firstline.ui.activity.map;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ZoomControls;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.example.benben.firstline.R;
import com.example.benben.firstline.ui.activity.BaseActivity;
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
    private MyLocationConfiguration.LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;
    BaiduMap mBaiduMap;
    LatLngBounds latLngBounds;



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

        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        View.OnClickListener btnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mCurrentMode) {
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
