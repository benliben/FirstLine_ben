package com.example.benben.firstline.ui.activity;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by benben on 2016/5/11.
 * 创建自己的内容提供器
 */


/**
 * 通配符的规则
 *
 * 1.*：表示匹配任意长度的任意字符
 * 2.#：表示匹配任意长度的数字，
 *      原URl为   content://com.example.app.provider/table1/1
 *      一个能够匹配任意表的内容URl格式可以为
 *          content://com.example.app.provider/*
 *       一个能够匹配table表中任意一行数据的内容URL格式可以为
 *       content://com.example.app.provider/table1/#
 *
 *
 */
public class MyProvider extends ContentProvider {


    public static final int TABLE1_DIR = 0;
    public static final int TABLE1_ITEM = 1;
    public static final int TABLE2_DIR = 2;
    public static final int TABLE2_ITEM = 3;

    private  static UriMatcher uriMatcher;

    static {
        uriMatcher=new UriMatcher(uriMatcher.NO_MATCH);
        uriMatcher.addURI("com.example.benben.thefirstlineofcode.ui", "table1", TABLE1_DIR);
        uriMatcher.addURI("com.example.benben.thefirstlineofcode.ui       ", "table1/#", TABLE1_ITEM);
        uriMatcher.addURI("com.example.benben.thefirstlineofcode.ui", "table2", TABLE2_DIR);
        uriMatcher.addURI("com.example.benben.thefirstlineofcode.ui       ", "table2/#", TABLE2_ITEM);
    }

    /**创建*/
    @Override
    public boolean onCreate() {
        return false;
    }


    /**查询*/
    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        switch (uriMatcher.match(uri)) {
            case TABLE1_DIR:
                /**查询table1表张所有的数据*/
                break;
            case TABLE1_ITEM:
                /**查询table1表中的单条数据*/
                break;
            case TABLE2_DIR:
                /**查询table2表张所有的数据*/
                break;
            case TABLE2_ITEM:
                /**查询table2表中的单条数据*/
                break;

        }
        return null;
    }

    /**根据传入的内容url来返回相应的MIME类型*/

    /**
     * 1.必须以vnd开头。
     * 2.如果内容URI以路径结尾，则后接android。cursor.dir/如果内容URI以id结尾，则后接android.cursor.item/。
     * 3.最后接上vnd.<authority>.<path>。
     *        原URl为   content://com.example.app.provider/table1 它对应的MIME类型可以写成
     *        vnd.android.cursor.dir/vnd.com.example.app.provider.table1
     *        原URl为   content://com.example.app.provider/table1/1 它对应的MINE类型可以写成
     *        vnd.android.cursor.item/vnd.com.example.app.provider.table1

     *
     * @param uri
     * @return
     */
    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case TABLE1_DIR:
                /**查询table1表张所有的数据*/
                return "vnd.android.cursor.dir/vnd.com.example.benben.thefirstlineofcode.ui.table1";
            case TABLE1_ITEM:
                /**查询table1表中的单条数据*/
                return "vnd.android.cursor.item/vnd.com.example.benben.thefirstlineofcode.ui.table1";
            case TABLE2_DIR:
                /**查询table2表张所有的数据*/
                return "vnd.android.cursor.dir/vnd.com.example.benben.thefirstlineofcode.ui.table2";
            case TABLE2_ITEM:
                /**查询table2表中的单条数据*/
                return "vnd.android.cursor.item/vnd.com.example.benben.thefirstlineofcode.ui.table2";
        }
        return null;
    }

    /**添加数据*/
    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    /**删除数据*/
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    /**更新数据*/
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
