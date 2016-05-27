package com.example.benben.firstline.utils;

import android.text.style.ClickableSpan;
import android.view.View;

import com.example.benben.firstline.http.EventFinish;


/**
 * Created by benben on 2015/7/23.
 */
public class MyUrlSpan extends ClickableSpan {
    EventFinish event;
    public MyUrlSpan(EventFinish event) {
        this.event = event;
    }

    @Override
    public void onClick(View widget) {
        if (event!=null) {
            event.onFinish(widget);
        }
    }
}
