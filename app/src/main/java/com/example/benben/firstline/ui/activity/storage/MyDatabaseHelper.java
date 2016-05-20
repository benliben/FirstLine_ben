package com.example.benben.firstline.ui.activity.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by benben on 2016/5/20.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_BOOK = "create table book ("
            + "id integer primary key autoincrement,"
            + "author text, "
            + "price real, "
            + "pages integer, "
            + "name text "
            + "category_id integer )";

    public static final String CREATE_CATEGORY = "create table Category ("
            + "id integer primary key autoincrement,"
            + "category_name text,"
            + "category_code integer)";


    private Context mContext;


    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }


    /**
     * 创建或查找
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_CATEGORY);
        Toast.makeText(mContext, "创建成功", Toast.LENGTH_SHORT).show();
    }

    /**
     * 升级
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        switch (oldVersion) {
            case 1:
                db.execSQL(CREATE_CATEGORY);
            case 2:
                db.execSQL("alter table Book add column category_id integer");
            default:
        }
    }
}
