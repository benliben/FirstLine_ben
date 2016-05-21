package com.example.benben.firstline.ui.activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.benben.firstline.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by tangjunjie on 2016/5/12.
 */
public class SMSActivity extends BaseActivity {

    public static void startSMSActivity(Activity activity) {
        Intent intent = new Intent(activity, SMSActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    private static final String TAG = "lyx";
    @InjectView(R.id.sms_to)
    EditText mTo;
    @InjectView(R.id.sms_input)
    EditText mInput;
    @InjectView(R.id.sms_send)
    Button mSend;
    @InjectView(R.id.topLeft)
    ImageView mLeft;
    @InjectView(R.id.topTitle)
    TextView mTitle;
    @InjectView(R.id.sms_sender)
    TextView mSender;
    @InjectView(R.id.sms_content)
    TextView mContent;

    private SendStatusReceiver sendStatusRecelver;

    private IntentFilter sendFilter;
    private MessageReceiver mMessageReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        ButterKnife.inject(this);
        initView();
        initData();
    }

    private void initData() {
        mLeft.setImageResource(R.mipmap.returns);
        mTitle.setText("短信");
    }


    private void initView() {
        /**启动广播接收*/
        sendFilter = new IntentFilter();
        sendFilter.addAction("SENT_SMS_ACTION");
        sendStatusRecelver=new SendStatusReceiver();
        registerReceiver(sendStatusRecelver, sendFilter);

        sendFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        sendFilter.setPriority(1000);//提高messageReceive的优先等级，让他能够先于系统短息程序收到短信广播
        mMessageReceiver = new MessageReceiver();
        registerReceiver(mMessageReceiver, sendFilter);
    }

    @OnClick({R.id.topLeft, R.id.sms_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topLeft:
                finish();
                break;
            case R.id.sms_send:
                SmsManager manager = SmsManager.getDefault();
                Intent sentIntent = new Intent("SENT_SMA_ACTION");
                PendingIntent pi = PendingIntent.getBroadcast(SMSActivity.this, 0, sentIntent, 0);

                manager.sendTextMessage(mTo.getText().toString(), null, mInput.getText().toString(), pi, null);//发送短信
                break;
        }
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**关闭广播接收*/
        unregisterReceiver(mMessageReceiver);
        unregisterReceiver(sendStatusRecelver);
    }

    /**截获短息*/
    class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            Bundle bundle = intent.getExtras();
            Object[] pdus = (Object[]) bundle.get("pdus");//提取短息信息
            SmsMessage[] messages = new SmsMessage[pdus.length];
            for (int i = 0; i < messages.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);//将每个pdu字节数组转换为SmsMessage对象
            }
            String address = messages[0].getOriginatingAddress();//获取发送号码
            String fullMessage = "";
            for (SmsMessage message : messages) {
                fullMessage += message.getMessageBody();//获取短息内容
            }
            Log.i(TAG, "电话号码是:"+address);
            Log.i(TAG, "内容为:"+fullMessage);
            mSender.setText(address);
            mContent.setText(fullMessage);

            abortBroadcast();//中止掉广播的继续传递
        }
    }

    /**发送短信*/
    class SendStatusReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (getResultCode() == RESULT_OK) {
                Toast.makeText(SMSActivity.this,"发送成功",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(SMSActivity.this,"发送成功",Toast.LENGTH_SHORT).show();
            }
        }
    }


}
