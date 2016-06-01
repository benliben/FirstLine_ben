package com.example.benben.firstline.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.benben.firstline.model.CityModel;
import com.example.benben.firstline.model.CountyModel;
import com.example.benben.firstline.model.ProvinceModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by benebn on 2016/5/31.
 * 这个类将会吧一些常用的数据库操作封装起来，方便后面的使用
 */
public class CoolWeatherDB {
    /**
     * 数据库名
     */
    public static final String DB_NAME = "cool_weather";

    /**
     * 数据版本库
     */
    public static final int VERSION = 1;//版本号
    private static CoolWeatherDB coolWeatherDB;
    private static SQLiteDatabase db;

    /**
     * 将构造方法私有化
     */
    private CoolWeatherDB(Context context) {
        CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(
                context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }

    /**
     * 获取CoolWeatherDB的实例
     */
    public synchronized static CoolWeatherDB getInstance(Context context) {
        if (coolWeatherDB == null) {
            coolWeatherDB = new CoolWeatherDB(context);
        }
        return coolWeatherDB;
    }

    /**
     * 将Province实例存储到数据库中
     */
    public void saveProvince(ProvinceModel model) {
        if (model != null) {
            ContentValues values = new ContentValues();
            values.put("province_name", model.getProvinceName());
            values.put("province_code", model.getProvinceCode());
            db.insert("Province", null, values);
        }
    }

    /**
     * 从数据库读取全国所有的省份信息
     */
    public List<ProvinceModel> loadProvinces() {
        List<ProvinceModel> list = new ArrayList<ProvinceModel>();
        Cursor cursor = db.query("Province", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                ProvinceModel province = new ProvinceModel();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                list.add(province);
            }
            while (cursor.moveToNext());
        }
        return list;
    }

    /**
     * 将City实例存储到数据库中
     */
    public void saveCity(CityModel model) {
        if (model != null) {
            ContentValues values = new ContentValues();
            values.put("city_name", model.getCityName());
            values.put("city_code", model.getCityCode());
            values.put("province_id", model.getProvinceId());
            db.insert("City", null, values);
            Log.i("lyx", "db: "+db);
        }
    }


    /**
     * 获取省份所有城市数据
     */
    public List<CityModel> loadCities(int provinceId) {
        List<CityModel> list = new ArrayList<CityModel>();


        Cursor cursor = db.query("City", null, "province_id=?"
                , new String[]{String.valueOf(provinceId)}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                CityModel city = new CityModel();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setProvinceId(provinceId);
                list.add(city);
            }
            while (cursor.moveToNext());
        }
        return list;
    }

    /**
     * 将County实例存储到数据库中
     */
    public void saveCounty(CountyModel model) {
        if (model != null) {
            ContentValues values = new ContentValues();
            values.put("county_name", model.getCountyName());
            values.put("county_code", model.getCountyCode());
            values.put("city_id", model.getCityId());
            db.insert("County", null, values);
        }
    }

    /**
     * 获取城市数据
     */
    public List<CountyModel> loadCounty(int cityId) {
        List<CountyModel> list = new ArrayList<CountyModel>();
        Cursor cursor = db.query("County", null, "city_id=?"
                , new String[]{String.valueOf(cityId)}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                CountyModel county = new CountyModel();
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
                county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
                county.setCityId(cityId);
                list.add(county);
            }
            while (cursor.moveToNext());
        }
        return list;
    }
}
