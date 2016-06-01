package com.example.benben.firstline.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.example.benben.firstline.db.CoolWeatherDB;
import com.example.benben.firstline.model.CityModel;
import com.example.benben.firstline.model.CountyModel;
import com.example.benben.firstline.model.ProvinceModel;

import org.json.JSONException;
import org.json.JSONObject;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by beneben on 2016/6/1.
 */
public class Utility {

    /**
     * 解析和处理服务器返回的省级数据
     */
    public synchronized static boolean handleProvincesResponse(CoolWeatherDB mDB, String response) {
        if (!TextUtils.isEmpty(response)) {
            String[] allProvinces = response.split(",");
            if (allProvinces != null && allProvinces.length > 0) {
                for (String p : allProvinces) {
                    String[] array = p.split("\\|");
                    ProvinceModel model = new ProvinceModel();
                    model.setProvinceCode(array[0]);
                    model.setProvinceName(array[1]);
                    /**将解析出来的数据存储到Province表中*/
                    mDB.saveProvince(model);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 解析和处理服务器返回的市级数据
     */
    public synchronized static boolean handleCityResponse(CoolWeatherDB mDB, String response,
                                                          int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCity = response.split(",");
            if (allCity != null && allCity.length > 0) {
                for (String c : allCity) {
                    String[] array = c.split("\\|");
                    CityModel model = new CityModel();
                    model.setCityCode(array[0]);
                    model.setCityName(array[1]);
                    model.setProvinceId(provinceId);
                    /**将解析出来的数据存储到City表中*/
                    mDB.saveCity(model);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 解析和处理服务器返回的县级数据
     */
    public synchronized static boolean handleCountyResponse(CoolWeatherDB mDB, String response,
                                                            int cityId) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCounty = response.split(",");
            if (allCounty != null && allCounty.length > 0) {
                for (String c : allCounty) {
                    String[] array = c.split("\\|");
                    CountyModel model = new CountyModel();
                    model.setCountyCode(array[0]);
                    model.setCountyName(array[1]);
                    model.setCityId(cityId);
                    /**将解析出来的数据存放到County表中*/
                    mDB.saveCounty(model);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 解析服务器返回的JSON数据，并将解析的数据存储到本地
     */
    public static void handleWeatherResponse(Context context, String response) {
        Log.i("lyx", "________2_____________: ");
        try {
            Log.i("lyx", "________3____________: ");
            JSONObject object = new JSONObject(response);
            JSONObject weatherInfo = object.getJSONObject("weatherinfo");
            String cityName = weatherInfo.getString("city");
            String weatherCode = weatherInfo.getString("cityid");
            String temp1 = weatherInfo.getString("temp2");
            String temp2 = weatherInfo.getString("temp1");
            String weatherDesp = weatherInfo.getString("weather");
            String publishTime = weatherInfo.getString("ptime");
            saveWeatherInfo(context, cityName, weatherCode, temp1, temp2, weatherDesp, publishTime);
        } catch (JSONException e) {
            Log.i("lyx", "________4_____________: "+e);
            e.printStackTrace();
        }
    }

    /**
     * 将服务器返回的所有天气信息存储到SharedPreferences文件中。
     */
    public static void saveWeatherInfo(Context context, String cityName, String weatherCode, String temp1, String temp2, String weatherDesp, String publishTime) {
        Log.i("lyx", "________5_____________: ");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
        Log.i("lyx", "________6_____________: ");
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        Log.i("lyx", "________4_____________: ");
        Log.i("lyx", "saveWeatherInfo: "+cityName+weatherCode+temp1+temp2+weatherDesp+publishTime);
        editor.putBoolean("city_selected", true);
        editor.putString("city_name", cityName);
        editor.putString("weather_code", weatherCode);
        editor.putString("temp1", temp1);
        editor.putString("temp2", temp2);
        editor.putString("weather_desp", weatherDesp);
        editor.putString("publish_time", publishTime);
        editor.putString("current_date", sdf.format(new Date()));
        editor.commit();
    }
}
