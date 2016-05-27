package com.example.benben.firstline.ui.activity.baidumap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.benben.firstline.R;
import com.example.benben.firstline.model.LeftTagModel;
import com.example.benben.firstline.ui.activity.BaseActivity;
import com.example.benben.firstline.ui.activity.map.BaiDuMapActivity;
import com.example.benben.firstline.ui.adapter.LeftAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by benben on 2016/5/27.
 * 百度地图的各种demo
 */
public class BaiDuMapDemoActivity extends BaseActivity {
    @InjectView(R.id.topLeft)
    ImageView mLeft;
    @InjectView(R.id.topTitle)
    TextView mTitle;
    @InjectView(R.id.sensor_content)
    RecyclerView mContent;

    public static void startBaiDuMapDemoActivity(Activity activity) {
        Intent intent = new Intent(activity, BaiDuMapDemoActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    private List<LeftTagModel> mModel = new ArrayList<>();
    private LeftAdapter mAdapter;
    private String[] mData = {"演示MapView的基本用法","基于MapFragment的基本地图","多地图展示",
            "自定义绘制功能","周边雷达功能", "点收藏功能",
            "定位图层展示","地图操作功能","UI操作功能",
            "图层展示","覆盖物功能","离线地图功能",
            "POI搜索功能","路径规划功能","地理编码功能",
            "公交路线查询功能","热力图层功能","短串分享",
            "短串分享","调启百度地图", "LBS云检索功能",
            "云检索使用介绍","OpenCl绘制功能","地图由TextUreView渲染",
            "瓦片图功能","点聚合功能", "行政区检索"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        ButterKnife.inject(this);
        initData();
        initView();
    }

    private void initView() {
        mContent.setLayoutManager(new LinearLayoutManager(BaiDuMapDemoActivity.this));
        mAdapter = new LeftAdapter(mData);
        mContent.setAdapter(mAdapter);
        mAdapter.setItemClickListener(new LeftAdapter.OnItemClickListener() {
            @Override
            public void ItemClickListener(View view, int position) {
                switch (position) {
                    case 0:
                        /**基本地图*/
                        BaseMapDemo.startBaseMapDemo(BaiDuMapDemoActivity.this);
                        break;
                    case 1:
                        /**基于MapFragment的基本地图*/
                        MapFragmentDemo.startMapFragmentDemo(BaiDuMapDemoActivity.this);
                        break;
                    case 2:
                        /**多地图展示*/
                        MultiMapViewDemo.startMultiMapViewDemo(BaiDuMapDemoActivity.this);
                        break;
                    case 3:
                        /**自定义绘制功能*/
                        GeometryDemo.startGeometryDemo(BaiDuMapDemoActivity.this);
                        break;
                    case 4:
                        /**周边雷达功能*/
                        RadarDemo.startRadarDemo(BaiDuMapDemoActivity.this);
                        break;
                    case 5:
                        /**点收藏功能*/
                        FavoriteDemo.startFavoriteDemo(BaiDuMapDemoActivity.this);
                        break;
                    case 6:
                        /**定位图层展示*/
                        LocationDemo.startLocationDemo(BaiDuMapDemoActivity.this);
                        break;
                    case 7:
                        /**地图操作功能*/
                        MapControlDemo.startMapControlDemo(BaiDuMapDemoActivity.this);
                        break;
                    case 8:
                        /**UI操作功能*/
                        UISettingDemo.startUISettingDemo(BaiDuMapDemoActivity.this);
                        break;
                    case 9:
                        /**图层展示*/
                        LayersDemo.startLayersDemo(BaiDuMapDemoActivity.this);
                        break;
                    case 10:
                        /**覆盖物功能*/
                        OverlayDemo.startOverlayDemo(BaiDuMapDemoActivity.this);
                        break;
                    case 11:
                        /**离线地图功能*/
                        OfflineDemo.startOfflineDemo(BaiDuMapDemoActivity.this);
                        break;
                    case 12:
                        /**POI搜索功能*/
                        PoiSearchDemo.startPoiSearchDemo(BaiDuMapDemoActivity.this);
                        break;
                    case 13:
                        /**路径规划功能*/
                        RoutePlanDemo.startRoutePlanDemo(BaiDuMapDemoActivity.this);
                        break;
                    case 14:
                        /**地理编码功能*/
                        GeoCoderDemo.startGeoCoderDemo(BaiDuMapDemoActivity.this);
                        break;
                    case 15:
                        /**公交路线查询功能*/
                        BusLineSearchDemo.startBusLineSearchDemo(BaiDuMapDemoActivity.this);
                        break;
                    case 16:
                        /**热力图层功能*/
                        HeatMapDemo.startHeatMapDemo(BaiDuMapDemoActivity.this);
                        break;
                    case 17:
                        /**短串分享*/
                        ShareDemo.startShareDemo(BaiDuMapDemoActivity.this);
                        break;
                    case 18:
                        /**短串分享*/
                        ShareDemoActivity.startShareDemoActivity(BaiDuMapDemoActivity.this);
                        break;
                    case 19:
                        /**调启百度地图*/
                        OpenBaiduMap.startOpenBaiduMap(BaiDuMapDemoActivity.this);
                        break;
                    case 20:
                        /**LBS云检索功能*/
                        CloudSearchActivity.startCloudSearchActivityo(BaiDuMapDemoActivity.this);
                        break;
                    case 21:
                        /**云检索使用介绍*/
                        CloudSearchDemo.startCloudSearchDemo(BaiDuMapDemoActivity.this);
                        break;
                    case 22:
                        /**OpenCl绘制功能*/
                        OpenglDemo.startOpenglDemo(BaiDuMapDemoActivity.this);
                        break;
                    case 23:
                        /**地图由TextUreView渲染*/
                        TextureMapViewDemo.startTextureMapViewDemo(BaiDuMapDemoActivity.this);
                        break;
                    case 24:
                        /**瓦片图功能*/
                        TileOverlayDemo.startTileOverlayDemo(BaiDuMapDemoActivity.this);
                        break;
                    case 25:
                        /**点聚合功能*/
                        MarkerClusterDemo.startMarkerClusterDemo(BaiDuMapDemoActivity.this);
                        break;
                    case 26:
                        /**行政区检索*/
                        DistrictSearchDemo.startDistrictSearchDemo(BaiDuMapDemoActivity.this);
                        break;



                }
            }

            @Override
            public void ItemLongClickListener(View view, int position) {

            }
        });
    }

    private void initData() {
        mLeft.setImageResource(R.mipmap.returns);
        mTitle.setText("百度Demo");
        for (int i = 0; i < mData.length; i++) {
            LeftTagModel model = new LeftTagModel();
            model.setName(mData[i]);
            mModel.add(model);
        }
    }

    @OnClick(R.id.topLeft)
    public void onClick() {
        finish();
    }
}
