package com.example.benben.firstline.ui.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.benben.firstline.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by benben on 2016/5/11.
 * 实现跨程序数据共享
 */
public class ProviderTestActivity extends BaseActivity {

    public static void startProviderTestActivity(Activity activity) {
        Intent intent = new Intent(activity, ProviderTestActivity.class);
        ActivityCompat.startActivity(activity,intent,null);
    }
    private static final String TAG = "lyx";
    @InjectView(R.id.topLeft)
    ImageView mLeft;
    @InjectView(R.id.topTitle)
    TextView mTitle;
    @InjectView(R.id.provider_add_data)
    Button mAddData;
    @InjectView(R.id.provider_query_data)
    Button mQueryData;
    @InjectView(R.id.provider_update_data)
    Button mUpdateData;
    @InjectView(R.id.provider_delete_data)
    Button mDeleteData;

    private String newId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);
        ButterKnife.inject(this);
        initData();
    }

    private void initData() {
        mLeft.setImageResource(R.mipmap.returns);
    }

    @OnClick({R.id.topLeft, R.id.provider_add_data, R.id.provider_query_data, R.id.provider_update_data, R.id.provider_delete_data})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topLeft:
                finish();
                break;
            /**添加数据*/
            case R.id.provider_add_data:

                Uri uri = Uri.parse("content://com.example.benben.sqlite.provider/book");
                ContentValues values = new ContentValues();
                values.put("name", "自传");
                values.put("author", "犇犇");
                values.put("pages", "888888");
                values.put("price", "8888.88");
                Uri newUri=getContentResolver().insert(uri,values);
                Log.i(TAG, "values: "+values);
                Log.i(TAG, "uri: "+uri);
                Log.i(TAG, "newUri: "+newUri);
                newId = newUri.getPathSegments().get(1);

                break;
            /**查询数据*/
            case R.id.provider_query_data:
                Uri uri1=Uri.parse("content://com.example.benben.sqlite.provider/book");
                Cursor cursor = getContentResolver().query(uri1, null, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.i(TAG, "sfasdfadfa111: ");
                        Log.d("lyx", "book name is " + name);
                        Log.d("lyx", "book author is " + author);
                        Log.d("lyx", "book pages is " + pages);
                        Log.d("lyx", "book price is " + price);
                        Log.i(TAG, "sfasdfadfa: ");
                    }
                    cursor.close();
                }
                break;
            /**更新数据*/
            case R.id.provider_update_data:
                Uri uri2= Uri.parse("content://com.example.benben.sqlite.provider/book/"+newId);
                ContentValues values1 = new ContentValues();
                values1.put("naem", "A Storm Of Swords");
                values1.put("author", "敏");
                values1.put("pages", "66666");
                values1.put("price", "666.66");
                getContentResolver().update(uri2, values1, null, null);
                break;
            /**删除数据*/
            case R.id.provider_delete_data:
                Uri uri3=Uri.parse("content://com.example.benben.sqlite.provider/book/"+newId);
                getContentResolver().delete(uri3, null, null);
                break;
        }
    }
}
