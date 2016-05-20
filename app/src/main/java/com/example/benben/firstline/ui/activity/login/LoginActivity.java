package com.example.benben.firstline.ui.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benben.firstline.R;
import com.example.benben.firstline.ui.activity.BaseActivity;

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
    @InjectView(R.id.login_pass)
    CheckBox loginPass;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public static void startLoginActivity(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        pref = PreferenceManager.getDefaultSharedPreferences(this);

        initData();
        initView();
    }

    private void initView() {
        boolean isRemember = pref.getBoolean("remember_password", false);
        if (isRemember) {
            /*将账号和密码都设置到文本框中*/
            String username = pref.getString("username", "");
            String password = pref.getString("password", "");
            mUserName.setText(username);
            mPassWord.setText(password);
            loginPass.setChecked(true);

        }
    }

    private void initData() {
        mLeft.setImageResource(R.mipmap.returns);
        mTitle.setText("登录");

    }

    @OnClick({R.id.topLeft, R.id.login_logIn, R.id.login_register, R.id.login_pass})
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
                    editor = pref.edit();
                    if (loginPass.isChecked()) {//检查复选框是否被选中
                        editor.putBoolean("remember_password", true);
                        editor.putString("username", username);
                        editor.putString("password", password);
                    }else {
                        editor.clear();
                    }
                    editor.commit();
                        QuitActivity.startQuitActiviy(LoginActivity.this);
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "请输入正确的账号和密码", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.login_register:
                break;
            case R.id.login_pass:
                break;
        }
    }
}
