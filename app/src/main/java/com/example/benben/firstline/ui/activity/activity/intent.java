package com.example.benben.firstline.ui.activity.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
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
 * 向下传递数据
 */
public class intent extends BaseActivity {


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

    public static void startIntent(Activity activity) {
        Intent intents = new Intent(activity, intent.class);
        ActivityCompat.startActivity(activity, intents, null);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);
        ButterKnife.inject(this);
        initData();
    }

    private void initData() {
        mTitle.setText("向下传递数据");
        mLeft.setImageResource(R.mipmap.returns);
    }

    @OnClick({R.id.topLeft, R.id.intent_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topLeft:
                finish();
                break;
            case R.id.intent_button:
                String data = mEdit.getText().toString();
                Intent intent = new Intent(intent.this, ReceiveActivity.class);
                intent.putExtra("extra_data", data);

                startActivityForResult(intent,1);

                break;
        }
    }


    /**
     *
     * @param requestCode 在启动活动时传入的请求码
     * @param resultCode 在返回数据时传入的处理结果
     * @param data 携带着返回数的intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String returnedData = data.getStringExtra("data_return");
                    mText.setText(returnedData);
                }
                break;
        }
    }
}
