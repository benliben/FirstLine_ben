package com.example.benben.firstline.ui.activity.chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benben.firstline.R;
import com.example.benben.firstline.model.ChatModel;
import com.example.benben.firstline.ui.activity.BaseActivity;
import com.example.benben.firstline.ui.adapter.ChatAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by benben on 2016/5/17.
 * ；聊天界面
 */
public class ChatActivity extends BaseActivity {


    public static void startChatActivity(Activity activity) {
        Intent intent = new Intent(activity, ChatActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }


    @InjectView(R.id.topLeft)
    ImageView mLeft;
    @InjectView(R.id.topTitle)
    TextView mTitle;
    @InjectView(R.id.chat_content)
    ListView mContent;
    @InjectView(R.id.chat_text)
    EditText mText;
    private List<ChatModel> mModel = new ArrayList<>();
    private ChatAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.inject(this);
        initData();
    }

    private void initData() {
        mAdapter=new ChatAdapter(ChatActivity.this,R.layout.item_chat,mModel);
        mContent.setAdapter(mAdapter);
        mLeft.setImageResource(R.mipmap.returns);
        mTitle.setText("奔波霸与霸波奔");

        ChatModel model1 = new ChatModel("快点啊", ChatModel.CHAT_LEFT);
        mModel.add(model1);
        ChatModel model2 = new ChatModel("好", ChatModel.CHAT_RIGHT);
        mModel.add(model2);
        ChatModel model3 = new ChatModel("出门出的慢，开局输一半", ChatModel.CHAT_LEFT);
        mModel.add(model3);
        ChatModel model4 = new ChatModel("走上还是下", ChatModel.CHAT_RIGHT);
        mModel.add(model4);

    }

    @OnClick({R.id.topLeft, R.id.chat_button_left, R.id.chat_button_right})
    public void onClick(View view) {
        String content = mText.getText().toString();

        switch (view.getId()) {
            case R.id.topLeft:
                finish();
                break;
            case R.id.chat_button_left:
                if (!"".equals(content)) {
                    ChatModel model = new ChatModel(content, ChatModel.CHAT_LEFT);
                    mModel.add(model);
                    mAdapter.notifyDataSetChanged();//当有新信息时刷新
                    mContent.setSelection(mModel.size());//将listView定位到最后一行
                    mText.setText("");//清空数据
                }
                Toast.makeText(ChatActivity.this,"输入数据",Toast.LENGTH_SHORT).show();
                break;
            case R.id.chat_button_right:
                if (!"".equals(content)) {
                    ChatModel model = new ChatModel(content, ChatModel.CHAT_RIGHT);
                    mModel.add(model);
                    mAdapter.notifyDataSetChanged();//当有新信息时刷新
                    mContent.setSelection(mModel.size());//将listView定位到最后一行
                    mText.setText("");//清空数据
                }
                Toast.makeText(ChatActivity.this,"输入数据",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
