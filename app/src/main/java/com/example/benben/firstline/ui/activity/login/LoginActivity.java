package com.example.benben.firstline.ui.activity.login;

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
import android.widget.Toast;

import com.example.benben.firstline.R;
import com.example.benben.firstline.ui.activity.BaseActivity;
import com.example.benben.firstline.ui.activity.MainActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by benebn on 2016/5/18.
 * 登录页面
 */
public class LoginActivity extends BaseActivity {
    @InjectView(R.id.topLeft)
    ImageView mLeft;
    @InjectView(R.id.topTitle)
    TextView mTitle;
    @InjectView(R.id.login_userName)
    EditText mUserName;
    @InjectView(R.id.login_passWord)
    EditText mPassWord;
    @InjectView(R.id.login_logIn)
    Button mLogIn;
    @InjectView(R.id.login_register)
    Button mRegister;

    public static void startLoginActivity(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        initData();
    }

    private void initData() {
        mLeft.setImageResource(R.mipmap.returns);
        mTitle.setText("登录");
    }

    @OnClick({R.id.topLeft, R.id.login_logIn, R.id.login_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topLeft:
                finish();
                break;
            case R.id.login_logIn:
                String username = mUserName.getText().toString();
                String password = mPassWord.getText().toString();
                /**如果账号为benben并且密码为5201314就登录成功*/
                if (username.equals("benben") && password.equals("5201314")) {
                    QuitActivity.startQuitActiviy(LoginActivity.this);
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(LoginActivity.this,"请输入正确的账号和密码",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.login_register:
                break;
        }
    }
}
