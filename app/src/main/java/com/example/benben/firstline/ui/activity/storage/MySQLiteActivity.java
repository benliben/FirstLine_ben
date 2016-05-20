package com.example.benben.firstline.ui.activity.storage;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.benben.firstline.R;
import com.example.benben.firstline.ui.activity.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by beneben on 2016/5/20.
 */
public class MySQLiteActivity extends BaseActivity {

    @InjectView(R.id.topLeft)
    ImageView mLeft;
    @InjectView(R.id.topTitle)
    TextView mTitle;
    @InjectView(R.id.sqlite_create)
    Button mCreate;
    @InjectView(R.id.sqlite_add)
    Button mAdd;
    @InjectView(R.id.sqlite_remove)
    Button mRemove;
    @InjectView(R.id.sqlite_amend)
    Button mAmend;
    @InjectView(R.id.sqlite_find)
    Button mFind;
    @InjectView(R.id.sqlite_content)
    TextView mContent;


    private MyDatabaseHelper dbHelper;

    private double a = 10.99;

    public static void startMySQLiteActivity(Activity activity) {
        Intent intent = new Intent(activity, MySQLiteActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        ButterKnife.inject(this);
        initData();
        initCreate();
    }

    private void initData() {
        mTitle.setText("SQLite");
        mLeft.setImageResource(R.mipmap.returns);
    }

    /**
     * 创建数据库
     * <p/>
     * 数据库名称为BookStore.db
     * 版本号为 2
     */
    private void initCreate() {
        dbHelper = new MyDatabaseHelper(this, "BookStore.db", null, 2);
    }

    @OnClick({R.id.topLeft, R.id.sqlite_create, R.id.sqlite_add, R.id.sqlite_remove, R.id.sqlite_amend, R.id.sqlite_find,R.id.sqlite_replace})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topLeft:
                finish();
                break;
            case R.id.sqlite_create:
                dbHelper.getWritableDatabase();
                break;
            case R.id.sqlite_add:
                addData();
                break;
            case R.id.sqlite_remove:
                removeData();
                break;
            case R.id.sqlite_amend:
                amendData();
                break;
            case R.id.sqlite_find:
                findData();
                break;
            case R.id.sqlite_replace:
                replaceData();
                break;
        }
    }

    /**事务*/
    private void replaceData() {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();//开启事物
        try {

            db.delete("Book", null, null);
            if (false) {
                //在这里手动抛出异常
                throw new NullPointerException();
            }

            ContentValues values = new ContentValues();
            values.put("name", "Game of Thrones");
            values.put("author", "George Martin");
            values.put("pages", 720);
            values.put("price", 20.85);
            db.insert("Book", null, values);
            db.setTransactionSuccessful();//执行事物成功
            Log.i("lyx", "事务成功");
        } catch (Exception e) {

            e.printStackTrace();
        }finally {

            db.endTransaction();//结束事务
        }
    }

    /**
     * 查询数据
     */
    private void findData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //查询Book表中所有的数据
        Cursor cursor = db.query("Book", null, null, null, null, null, null);
//        Cursor cursor1=db.query("")
        if (cursor.moveToFirst()) {
            /*遍历cursor对象，取出数据*/
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String author = cursor.getString(cursor.getColumnIndex("author"));
            int pages = cursor.getInt(cursor.getColumnIndex("pages"));
            double price = cursor.getDouble(cursor.getColumnIndex("price"));

            mContent.setText("书名是:" + name + "作者是:" + author + "页数是:" + pages + "价格是:" + price);
        }


    }

    /**
     * 删除数据
     */
    private void removeData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        /**
         * 第一个参数是表名
         * 第二，三个参数是来约束删除某一行或某几行的数据，不指定的话就是删除全部数据（这里指定的是页数大于500的书籍）
         */
        db.delete("Book", "pages>?", new String[]{"500"});
    }

    /**
     * 修改/更新数据
     */
    private void amendData() {

        a++;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("price", a);
        /**
         * 第一个参数是表名
         * 第二个参数是ContentValues对象，要把更新的数据在这里组装进去
         * 第三，四个参数是用于去约束更新某一行或者是某几行中的数据，不指定的话就默认更新所有行
         */
        db.update("Book", values, "name=?", new String[]{"The Da Vinci Code"});
    }

    /**
     * 添加数据
     */
    private void addData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();//获取SQLiteDatabase对象
        ContentValues values = new ContentValues();
        //开始组装第一条数据
        values.put("name", "The Da Vinci Code");
        values.put("author", "Dan Brown");
        values.put("pages", 454);
        values.put("price", 16.96);
        db.insert("Book", null, values);//插入第一条数据

        values.clear();//开始组装第二条数据
        values.put("name", "The Lost Symbol");
        values.put("author", "Dan Brown");
        values.put("pages", 510);
        values.put("price", 19.95);
        /**
         * 这个方法是专门用于添加数据的
         *
         * 第一个参数是表名，传入我们希望传入数据的表名
         * 第三个参数是一个ContentValues对象
         */
        db.insert("Book", null, values);//插入第二条数据

    }
}
