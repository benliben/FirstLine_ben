package com.example.benben.firstline.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.benben.firstline.R;
import com.example.benben.firstline.model.ChatModel;
import com.example.benben.firstline.ui.activity.chat.ChatActivity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by tangjunjie on 2016/5/17.
 */
public class ChatAdapter extends ArrayAdapter<ChatModel> {

    private int resourceId;


    public ChatAdapter(Context context, int resource, int textViewResourceId, List<ChatModel> objects) {
        super(context, resource, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    public ChatAdapter(ChatActivity chatActivity, int item_chat, List<ChatModel> mModel) {
        super(chatActivity, item_chat, mModel);
        resourceId = item_chat;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        if (msg.getType() == Msg.TYPE_RECEIVED) {
//// 如果是收到的消息，则显示左边的消息布局，将右边的消息布局隐藏
//            viewHolder.leftLayout.setVisibility(View.VISIBLE);
//            viewHolder.rightLayout.setVisibility(View.GONE);
//            viewHolder.leftMsg.setText(msg.getContent());
//        } else if (msg.getType() == Msg.TYPE_SENT) {
//// 如果是发出的消息，则显示右边的消息布局，将左边的消息布局隐藏
//            viewHolder.rightLayout.setVisibility(View.VISIBLE);
//            viewHolder.leftLayout.setVisibility(View.GONE);
//            viewHolder.rightMsg.setText(msg.getContent());
//        }
//        return view;
//    }


    ChatModel model = getItem(position);
    View view;
    ViewHolder viewHolder;

    if(convertView==null){
        view = LayoutInflater.from(getContext()).inflate(R.layout.item_chat, null);
        viewHolder = new ViewHolder();
        viewHolder.mLeftLinear = (LinearLayout) view.findViewById(R.id.item_left_linear);
        viewHolder.mLeftContent = (TextView) view.findViewById(R.id.chat_left_content);
        viewHolder.mRightLinear = (LinearLayout) view.findViewById(R.id.item_right_linear);
        viewHolder.mRightContent = (TextView) view.findViewById(R.id.item_right_content);
            view.setTag(viewHolder);
    } else {
        view = convertView;
        viewHolder = (ViewHolder) view.getTag();
    }

    if(model.getType()==ChatModel.CHAT_LEFT) {
            /*如果是收到的右边的信息，就显示右边隐藏左边，左边同理*/
        Log.i("lyx", "viewHolder: " + viewHolder);
        viewHolder
                .mRightLinear.setVisibility(View.VISIBLE);
        viewHolder.mLeftLinear.setVisibility(View.GONE);
        viewHolder.mRightContent.setText(model.getContent());
    }

    else if(model.getType()==ChatModel.CHAT_RIGHT)

    {
        viewHolder.mRightLinear.setVisibility(View.GONE);
        viewHolder.mLeftLinear.setVisibility(View.VISIBLE);
        viewHolder.mLeftContent.setText(model.getContent());
    }

    return view;

}


class ViewHolder {

    //        @InjectView(R.id.item_left_linear)
    LinearLayout mLeftLinear;
    //        @InjectView(R.id.item_right_content)
    TextView mRightContent;
    //        @InjectView(R.id.chat_left_content)
    TextView mLeftContent;
    //        @InjectView(R.id.item_right_linear)
    LinearLayout mRightLinear;

//        ViewHolder(View view) {
//            ButterKnife.inject(this, view);
//
//        }
}
}
