package com.example.benben.firstline.utils;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;


import com.example.benben.firstline.http.EventFinish;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * string工具类
 */
public class StringUtil {
	public static boolean isEmpty(String str) {
		if (str == null)
			return true;
		else if (str.equals(""))
			return true;
		else if (str.equals("null"))
			return true;
        else if (str.equals("无"))
            return true;
        else
			return false;
	}

    public static boolean isEmpty(Object str) {
        if (str == null)
            return true;
        else if (str.equals(""))
            return true;
        else if (str.equals("null"))
            return true;
        else
            return false;
    }

    public static String getTime(String date) {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            long time = sdf.parse(date).getTime();
            long now = System.currentTimeMillis();
            time = now - time;
            if (time < 60 * 1000) {
                str = "刚刚";
            } else if (time < 60 * 60 * 1000) {
                str = time / (60 * 1000) + "分钟前";
            } else if (time < 86400000) {
                str = time / (60 * 60 * 1000) + "小时前";
            } else if (time< 2592000000L){
                str = time / (86400000) + "天前";
            } else if (time< 31536000000L){
                str = time / (2592000000L) + "月前";
            } else {
                str = time / (31536000000L) + "年前";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date + "(<font color='blue'>" + str + "</font>)";
    }

    public static String getTime(long time) {
        String str = "";

            if (time < 60 * 1000) {
                str = "刚刚";
            } else if (time < 60 * 60 * 1000) {
                str = time / (60 * 1000) + "分钟";
            } else if (time < 86400000) {
                str = time / (60 * 60 * 1000) + "小时";
            } else if (time< 2592000000L){
                str = time / (86400000) + "天";
            } else if (time< 31536000000L){
                str = time / (2592000000L) + "月";
            } else {
                str = time / (31536000000L) + "年";
            }


        return "" + str + "";
    }

    public static String getShortTime(String date) {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            long time = sdf.parse(date).getTime();
            long now = System.currentTimeMillis();
            time = now - time;
            if (time < 60 * 1000) {
                str = "刚刚";
            } else if (time < 60 * 60 * 1000) {
                str = time / (60 * 1000) + "分钟前";
            } else if (time < 86400000) {
                str = time / (60 * 60 * 1000) + "小时前";
            } else if (time< 2592000000L){
                str = time / (86400000) + "天前";
            } else if (time< 31536000000L){
                str = time / (2592000000L) + "月前";
            } else {
                str = time / (31536000000L) + "年前";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return   str ;
    }

    public static SpannableString getSpannable(String str,int start,int end,EventFinish event) {
        SpannableString sbs = new SpannableString(str);
        sbs.setSpan(new MyUrlSpan(event),start,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sbs.setSpan(new ForegroundColorSpan(Color.BLUE),start ,end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        return sbs;
    }
}
