package com.example.benben.firstline.gps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;



import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * GPS定位服务类，封装了GPS类的细节，只向用户暴露简单的start()和stop()两个方法。需要用户实现{@link IGPSCallback}
 * 接口中的方法
 * <p/>
 * 使用方法： <br>
 * GPSService gpsService = new GPSService(gpsCallback, MainActivity.this, true);
 * <br>
 * gpsService.start();
 *
 * @author 优化设计，QQ:50199907 Email:yw1530@126.com
 * @see IGPSCallback
 */
public class GPSService {
    /**
     * 百度坐标转换服务地址
     */
    private final static String bmapBase64Encode = "http://api.map.baidu.com/geoconv/v1/?coords=%1$s,%2$s&from=1&to=5&ak=o0kz4oCBAOjz8qwqVNRK30YS";

    /**
     * 坐标转地理信息接口
     */
    private final static String bmapPoint2Address = "http://api.map.baidu.com/geocoder/v2/?ak=o0kz4oCBAOjz8qwqVNRK30YS&callback=renderReverse&location=%1$s,%2$s&output=json&pois=1";
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * GPS函数回调接口
     */
    // private IGPSCallback gpsCallback;
    private ArrayList<IGPSCallback> callbacks;
    private LocationManager loctionManager;

    private String provider;

    private Context context;
    private boolean isTimeOut = true;
    /**
     * GPSService是否运行
     */
    private boolean isRun;

    /**
     * @return GPS状态，true为正在运行，false已停止。
     */
    public boolean getIsRun() {
        return isRun;
    }

    /**
     * 超时停止
     */
    private boolean isAutoStop = false;

    /**
     * 定时器
     */
    private Timer timer;

    /**
     * 超时时间（秒）
     */
    private int outTime = 20;

    /**
     * 精度
     */
    private float accuracy = 300.0f;

    /**
     * 获取精度，单位米
     *
     * @return
     */
    public float getAccuracy() {
        return accuracy;
    }

    public long start = 0;
    public long end = 0;

    /**
     * 设置GPS精度（单位：米），默认为300，如需要更改此参数，必须在start方法前设置，并且请设置适合的精度，如果值太低，
     * GPS硬件本身达不到该要求，可能导致一直获取不到坐标信息。一般设置应不低于50。
     *
     * @param accuracy
     */
    public void setAccuracy(float accuracy) {
        if (accuracy < 5) {
            return;
        }
        this.accuracy = accuracy;
    }

    /**
     * GPS配置参数
     */
    private Criteria criteria;

    /**
     * @return 获取criteria
     */
    public Criteria getCriteria() {
        initCriteria();
        return criteria;
    }

    /**
     * 初始化GPS参数
     */
    private void initCriteria() {
        if (criteria == null) {
            criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE); // 高精度
            criteria.setAltitudeRequired(false); // 不要求海拔
            criteria.setBearingRequired(false); // 不要求方位
            criteria.setCostAllowed(true); // 允许有花费
            criteria.setPowerRequirement(Criteria.POWER_LOW);// 设置低功耗模式
        }
    }

    /**
     * 最后一次错误描述
     */
    private String lastErrorDescription = "";

    /**
     * @return 获取GPSSerivce最后一次出错的描述
     */
    public String getError() {
        return lastErrorDescription;
    }

    /**
     * 设置最后一次错误描述，该描述可以通过getError()方法获取。
     *
     * @param error 错误说明
     * @see GPSService#getError()
     */
    private void setError(String error) {
        if (error == null)
            return;
        this.lastErrorDescription = error;
    }

    /**
     * GPSService构造函数
     *
     * @param gpsCallback 回调函数接口
     * @param context     Context
     */
    public GPSService(IGPSCallback gpsCallback, Context context) {
        super();
        callbacks = new ArrayList<IGPSCallback>();
        callbacks.add(gpsCallback);
        this.context = context;
    }

    public void addIGPSCallback(IGPSCallback callback) {
        if (callbacks == null) {
            callbacks = new ArrayList<IGPSCallback>();
        }
        callbacks.add(callback);
        if (!isRun) {
            start();
        }
    }

    static BufferedWriter writer;
//    GeoCoder mSearch;

    public GPSService(Context context) {
        super();
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-MM");
//            File file = new File(StorageConfig.APP_ROOTPATH,sdf.format(new Date())+".txt");
//            Logs.e(file.getAbsolutePath());
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        log("GPSService is new");
//        mSearch = GeoCoder.newInstance();
//        mSearch.setOnGetGeoCodeResultListener(listener);
        try {
            EventBus.getDefault().register(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.context = context;
    }

    public static void log(String msg) {
//        Logs.e(msg);
//        try {
//            if (writer!=null) {
//                writer.write(sdf.format(new Date())+":"+msg+"\n");
//                writer.flush();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    /**
     * GPSService构造函数
     *
     * @param gpsCallback 回调函数接口
     * @param context     Context
     * @param isAutoStop  定位成功后是否自动停止GPS
     */
    public GPSService(IGPSCallback gpsCallback, Context context,
                      boolean isAutoStop) {
        super();
        callbacks = new ArrayList<IGPSCallback>();
        callbacks.add(gpsCallback);
        this.isAutoStop = isAutoStop;
        this.context = context;
    }

    /**
     * 返回超时时间（单位：秒）
     *
     * @return
     */
    public int getOutTime() {
        return outTime;
    }

    /**
     * 设置超时时间
     *
     * @param outTime 超时时间（单位：秒，范围：10—600），只可在Start()方法调用前使用，默认180秒，如果小于10秒则超时无效，
     *                只能手动调用Stop() 方法停止GPS。
     */
    public void setOutTime(int outTime) {
        if (outTime >= 0 && outTime <= 600) {
            this.outTime = outTime;
        }
    }


    /**
     *
     *
     */
    public boolean isOpen() {
        loctionManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);

        if (!loctionManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            return false;
        }
        return true;
    }


    /**
     * 开始GPS定位
     *
     * @return 返回false表示GPS打开失败，可能没有硬件打开（由手机用户控制硬件开关）。
     */
    public boolean start() {
        if (callbacks == null || callbacks.isEmpty()) {
            return false;
        }
        log("gps is start");
        try {
            start = System.currentTimeMillis();
            loctionManager = (LocationManager) context
                    .getSystemService(Context.LOCATION_SERVICE);
            initCriteria();

			/* 从可用的位置提供器中，匹配以上标准的最佳提供器 */
            provider = loctionManager.getBestProvider(criteria, true);

            if (!loctionManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                // GPS没有打开
                setError("GPS没有硬件打开");

                failCallBack(IGPSCallback.ERROR_CLOSE);
                return false;
            }

            if (this.outTime >= 10) {
                // 设置超时参数，启动定时器
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        // 停止GPS
                        timer.cancel();
                        stop();
                        handler.sendEmptyMessage(0);
                    }
                }, 1000 * outTime);
            }

            // 注册监听函数
            if (locationListener != null) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.

                }
                loctionManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 10, accuracy, locationListener);
                log("gps requestLocationUpdates locationListener");
            }

            isRun = true;
            return true;
        } catch (Exception e) {
            setError(e.getMessage());
            e.printStackTrace();
            log(e.getMessage().toString());
            return false;
        }
    }

    boolean failCallBack(String error) {
        if (callbacks == null || callbacks.isEmpty()) {
            stop();
            return true;
        } else {
            ArrayList<IGPSCallback> list = new ArrayList<IGPSCallback>();
            for (IGPSCallback callback : callbacks) {
                if (callback.failCallBack(error)) {
                    list.add(callback);
                }
            }
            callbacks.removeAll(list);
            if (callbacks.isEmpty()) {
                stop();
                return true;
            }
        }
        return false;
    }

    boolean gpsCallback(LocationInfo locationInfo) {
        if (callbacks == null || callbacks.isEmpty()) {
            stop();
            return true;
        } else {
            ArrayList<IGPSCallback> list = new ArrayList<IGPSCallback>();
            for (IGPSCallback callback : callbacks) {
                if (callback.gpsCallback(locationInfo)) {
                    list.add(callback);
                }
            }
            callbacks.removeAll(list);
            if (callbacks.isEmpty()) {
                stop();
                return true;
            }
        }
        return false;
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (isTimeOut) {
                        failCallBack(IGPSCallback.ERROR_OUTTIME);
                    }

                    break;
                case 1: {

                    if (msg.obj == null) {
                        failCallBack(IGPSCallback.ERROR_NO_SIGNAL);
                    } else {
                        gpsCallback((LocationInfo) msg.obj);
                    }
                }
                break;
            }
            super.handleMessage(msg);
        }
    };

    /**
     * 停止GPS定位
     *
     * @return
     */
    public boolean stop() {
        try {

            if (locationListener != null && loctionManager != null) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.

                }
                loctionManager.removeUpdates(locationListener);
            }
            isRun = false;
            EventBus.getDefault().unregister(this);
            return true;
        } catch (Exception e) {
            setError(e.getMessage());
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 位置监听器
     */
    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        // 当位置变化时触发
        @Override
        public void onLocationChanged(Location location) {
            isTimeOut = false;
            if (location != null) {
                log("GPS获取到location" + location.getLatitude() + "   " + location.getLongitude());
//				if (location.hasAccuracy()
//						&& location.getAccuracy() <= accuracy) {
                getBaiduLngLat(location.getLongitude(), location.getLatitude(),location.getAccuracy());

//					new GetLocation().execute(location);

//				}
            }
        }
    };
//
//	private class GetLocation extends
//			AsyncTask<Location, integer, LocationInfo> {
//
//		@Override
//		protected LocationInfo doInBackground(Location... locations) {
//			Location location = locations[0];
//            log("doInBackground="+location.getLatitude()+"   "+location.getLongitude());
//			// 是否自动停止
//            LocationInfo info = getBaiduLngLat(location.getLongitude(),
//					location.getLatitude());
////			LocationInfo info = new LocationInfo();
////			info.setLongitude(result[0]);
////			info.setLatitude(result[1]);
//            if (info==null) {
//                return info;
//            }
//			info.setDate(sdf.format(new Date()));
//			/*
//			 * 以下代码：如果网络正常，并且得到了正确的百度坐标，可以将百度坐标转换为地理信息
//			 */
////			if (result[0] != 0.0 && result[1] != 0.0) {
////				String addrJson = "";
////
////				try {
////					addrJson = getAddress(result[0], result[1]);
////					JSONObject jo = new JSONObject(addrJson);
////					jo = jo.getJSONObject("result");
////                    end = System.currentTimeMillis();
////					info.setAddr("GPS耗时"+((end-start)/1000)+"秒定位结果:"+jo.getString("formatted_address"));
////				} catch (Exception e) {
////                    log(e.getMessage().toString());
////					e.printStackTrace();
////				}
////			}
//			return info;
//		}
//
//		@Override
//		protected void onPostExecute(LocationInfo result) {
//			Message msg = handler.obtainMessage(2);
//			msg.obj = result;
//			handler.sendMessage(msg);
//			super.onPostExecute(result);
//		}
//	}

    /**
     * 使用Get方法获取网页数据
     *
     * @param webUrl      网址
     * @param charsetName 字符类型，null时默认使用“GB2312”，否则请传入“UTF-8”
     * @return 网页数据
     */
    private static String getPost(String webUrl, String charsetName) {
        if (!"UTF-8".equals(charsetName) && !"GB2312".equals(charsetName)) {
            charsetName = "GB2312";
        }
        log("getPost start");
        String result = "";
        URL url;
        BufferedReader bin;
        try {
            url = new URL(webUrl);
            InputStream in = url.openStream();
            bin = new BufferedReader(new InputStreamReader(in, charsetName));
            String s = null;
            while ((s = bin.readLine()) != null) {
                if ("".equals(result))
                    result = s;
                else
                    result = result + "\n" + s;
            }
            bin.close();
        } catch (Exception e) {
            log(e.getMessage().toString());
            e.printStackTrace();
            return null;
        }
        log("getPost end" + result);

        return result;
    }

    /**
     * 将GPS获取到的真实坐标转换为百度坐标
     *
     * @param lng 经度
     * @param lat 纬度
     * @return 返回一维数据，[0]存放转换后的经度，[1]存放纬度
     */
    public void getBaiduLngLat(final double lng, final double lat,final float accuracy) {
//		double[] result = new double[] { 0.0d, 0.0d };
        log("getBaiduLngLat start");
        new Thread(){
            @Override
            public void run() {
                try {
                    String jsons = getPost(
                            String.format(bmapBase64Encode, String.valueOf(lng),
                                    String.valueOf(lat)), null);
                    JSONObject json = new JSONObject(jsons);
                    int status = json.getInt("status");
                    if (status == 0) {
                        JSONArray jsr = json.getJSONArray("result");
                        json = jsr.getJSONObject(0);
                        double lng = json.getDouble("x");
                        double lat = json.getDouble("y");
                        LocationInfo info =  getAddress(lng, lat);
                        info.setFromGPS(true);
                        Message msg = handler.obtainMessage(1);
                        info.setRadius(accuracy);
                        msg.obj = info;
                        handler.sendMessage(msg);
                        log("发送成功消息");
                    } else {
                        throw new NullPointerException("GPS转百度坐标时发生错误");
                    }
                } catch (Exception e) {
                    log(e.toString());
                    failCallBack(IGPSCallback.ERROR_NO_SIGNAL);
                    e.printStackTrace();
                }
            }
        }.start();

            // 将GPS设备采集的原始GPS坐标转换成百度坐标
//            CoordinateConverter
//                    converter = new CoordinateConverter();
//            converter.from(CoordinateConverter.CoordType.GPS);
//            LatLng sourceLatLng = new LatLng(lat, lng);
            // sourceLatLng待转换坐标
//            converter.coord(sourceLatLng);
//            log("start converter.convert()");
//            LatLng desLatLng = converter.convert();
//
//            boolean b = mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(desLatLng));
//            log("start mSearch.reverseGeoCode");
//            if (!b) {
//                failCallBack(IGPSCallback.ERROR_NO_SIGNAL);
//            }
//            if (json.getString("status").equals("OK")) {
//                json = json.getJSONObject("result");
//                info.setLatitude(json.getJSONObject("location").getDouble("lat"));
//                info.setLongitude(json.getJSONObject("location").getDouble("lng"));
//                info.setAddr("GPS耗时"+((end-start)/1000)+"秒定位结果:"+json.getString("formatted_address"));
//                json = json.getJSONObject("addressComponent");
//                info.setProvince(json.getString("province"));
//                info.setCity(json.getString("city"));
//                info.setDistrict(json.getString("district"));
//            }
//
//			Pattern pattern = Pattern.compile("[0-9A-Za-z]{10,}");
//			Matcher matcher = pattern.matcher(json);
//			String[] point = new String[] { "", "" };
//			int resultTotal = 0;
//			while (matcher.find()) {
//				point[resultTotal] = matcher.group();
//				resultTotal++;
//			}
//			result[0] = Double.parseDouble(new String(Base64.decode(point[0]
//					.getBytes())));
//			result[1] = Double.parseDouble(new String(Base64.decode(point[1]
//					.getBytes())));
//            log("getBaiduLngLat end"+info.getAddr());
//        } catch (Exception e) {
//            log(e.getMessage().toString());
//            failCallBack(IGPSCallback.ERROR_NO_SIGNAL);
//            e.printStackTrace();
//        }
    }

//    OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
//        public void onGetGeoCodeResult(GeoCodeResult result) {
//            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
//                //没有检索到结果
//            }
//            //获取地理编码结果
//        }
//
//        @Override
//        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
//            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
//                //没有找到检索结果
//                log("没有找到检索结果");
//                Message msg = handler.obtainMessage(2);
//                msg.obj = null;
//                handler.sendMessage(msg);
//            } else {
//                log("检索结果" + result.getAddress());
//                LocationInfo info = new LocationInfo();
//                info.setAddr("GPS:" + result.getAddress());
//                info.setLongitude(result.getLocation().longitude);
//                info.setLatitude(result.getLocation().latitude);
//                info.setDistrict(result.getAddressDetail().district);
//                info.setCity(result.getAddressDetail().city);
//                info.setProvince(result.getAddressDetail().province);
//                info.setDate(sdf.format(new Date()));
//                Message msg = handler.obtainMessage(1);
//                msg.obj = info;
//                handler.sendMessage(msg);
//                log("发送成功消息");
//            }
//            //获取反向地理编码结果
//        }
//    };

    /**
     * 通过坐标获取地理位置信息
     *
     * @param lng 百度经度（如果是直接从GPS获取的坐标，需要使用{@link GPSService#getBaiduLngLat}方法转换）
     * @param lat 百度纬度
     * @return 地理位置JSON
     */
    public LocationInfo getAddress(double lng, double lat) throws Exception {
        String result = "";
        LocationInfo info = new LocationInfo();
        result = getPost(String.format(bmapPoint2Address,
                String.valueOf(lat), String.valueOf(lng)), "UTF-8");
        int start = result.indexOf("{");
        int end = result.lastIndexOf("}")+1;
        result = result.substring(start, end);

        JSONObject json = new JSONObject(result);
        if (json.getInt("status")==0) {
            json = json.getJSONObject("result");
            info.setAddr("GPS:" + json.getString("formatted_address"));
            info.setLongitude(lng);
            info.setLatitude(lat);
            json = json.getJSONObject("addressComponent");
            info.setDistrict(json.getString("district"));
            info.setCity(json.getString("city"));
            info.setProvince(json.getString("province"));
            info.setDate(sdf.format(new Date()));
        } else {
            throw new NullPointerException("获取地址信息出错");
        }
        return info;
    }

    public LocationManager getLoctionManager() {
        return loctionManager;
    }

    @Subscribe
    public void onEvent(Object event) {
        if (event instanceof Boolean) {
            boolean b = (boolean) event;
            if (!b) {
//                Logs.e("failCallBack(IGPSCallback.ERROR_CLOSE)");
                failCallBack(IGPSCallback.ERROR_CLOSE);
            }

        }
    }

}
