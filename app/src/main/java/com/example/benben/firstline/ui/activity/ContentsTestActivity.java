package com.example.benben.firstline.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.example.benben.firstline.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by benben on 2016/5/11.
 * 读取手机联系人
 */
public class ContentsTestActivity extends BaseActivity {

    public static void startContentsTestActivity(Activity activity) {
        Intent intent = new Intent(activity, ContentsTestActivity.class);
        ActivityCompat.startActivity(activity,intent,null);
    }
    @InjectView(R.id.topLeft)
    ImageView mLeft;
    @InjectView(R.id.topTitle)
    TextView mTitle;
    @InjectView(R.id.information_content)
    ListView mContent;

    ArrayAdapter<String> adapter;
    List<String> contactsList=new ArrayList<String>();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        ButterKnife.inject(this);

        readContacts();
        initView();
    }

    private void initView() {

        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,contactsList);
        mContent.setAdapter(adapter);
    }

    private void readContacts() {
        Cursor cursor = null;
        try {
            /**查询联系人数据*/
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null
                    , null, null, null);
            while (cursor.moveToNext()) {
                /**获取联系人姓名*/
                String displayName = cursor.getString(cursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                ));

                /**获取联系人电话号码*/
                String number = cursor.getString(cursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                ));
                contactsList.add(displayName +"\n"+ number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @OnClick(R.id.topLeft)
    public void onClick() {
    }


}
