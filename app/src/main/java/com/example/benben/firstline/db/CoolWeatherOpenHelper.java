package com.example.benben.firstline.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by beneben on 2016/5/31.
 * 建立三张表
 * Province City County  分别用于存放省市县的各种数据
 */
public class CoolWeatherOpenHelper extends SQLiteOpenHelper {

    /**
     * Province表 建表语句
     * id是自增长主键
     * province_name 表省份
     * province_code 表省级代号
     */
    public static final String CREATE_PROVINCE = "create table Province(" +
            "id integer primary key autoincrement," +
            "province_name text," +
            "province_code text)";

    /**
     * City表 建表语句
     * id是自增长主键
     * city_name 表城市
     * city_code 表市级代号
     * province_id 是city表关联province表的外键
     */
    public static final String CREATE_CITY = "create table City("
            + "id integer primary key autoincrement,"
            + "city_name text,"
            + "city_code text,"
            + "province_id integer)";


    /**
     * County表 建表语句
     * id是自增长主键
     * county_name 表省份
     * county_code 表省级代号
     * city_id 是county表关联city表的外键
     */
    public static final String CREATE_COUNTY = "create table County(" +
            "id integer primary key autoincrement," +
            "county_name text," +
            "county_code text," +
            "city_id integer)";

    public CoolWeatherOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PROVINCE);
        db.execSQL(CREATE_CITY);
        db.execSQL(CREATE_COUNTY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
