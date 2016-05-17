package com.example.benben.firstline.ui.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.benben.firstline.R;
import com.example.benben.firstline.ui.activity.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by benben on 2016/5/16.
 * 向上传递数据
 */
public class ReceiveActivity extends BaseActivity {
    @InjectView(R.id.topLeft)
    ImageView mLeft;
    @InjectView(R.id.topTitle)
    TextView mTitle;
    @InjectView(R.id.init_edit)
    EditText mEdit;
    @InjectView(R.id.intent_button)
    Button mButton;
    @InjectView(R.id.intent_text)
    TextView mText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);
        ButterKnife.inject(this);
        initData();
        receiveData();
    }

    /**接收数据*/
    private void receiveData() {
        Intent intent = getIntent();
        String data = intent.getStringExtra("extra_data");
        mText.setText(data);
    }

    private void initData() {
        mLeft.setImageResource(R.mipmap.returns);
        mTitle.setText("向上传输");
    }

    @OnClick({R.id.intent_button, R.id.topLeft})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.topLeft:
                finish();
                break;
            case R.id.intent_button:
                String data = mEdit.getText().toString();
//                Intent intent = new Intent();
//                intent.putExtra("data_return", data);
//                setResult(RESULT_OK, intent);


                setResult(RESULT_OK,new Intent().putExtra("data_return",data));
                finish();
                break;
        }
    }
}
