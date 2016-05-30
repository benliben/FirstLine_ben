package com.example.benben.firstline;

import android.util.Log;

/**
 * Created by benben on 2016/5/14.
 * 日志类
 *
 * 然后我们只需要修改 LEVEL 常量的值，就可以自由地控制日志的打印行为了。
 *          比如让LEVEL 等于 VERBOSE 就可以把所有的日志都打印出来，
             让 LEVEL 等于 WARN 就可以只打印警告以上级别的日志
             ，让 LEVEL 等于 NOTHING 就可以把所有日志都屏蔽掉。
 */
public class LogUtil {
    public static final  int  VERBOSE=1;
    public static final  int  DEBUG=2;
    public static final  int  INFO=3;
    public static final  int  WARN=4;
    public static final  int  ERROR=5;
    public static final  int  NOTHING=6;
    public static final  int  LEVEL=VERBOSE;

    public static void v(String tag, String msg) {
        if (LEVEL <= VERBOSE) {
            Log.v(tag, msg);
        }
    } public static void d(String tag, String msg) {
        if (LEVEL <= DEBUG) {
            Log.d(tag, msg);
        }
    } public static void i(String tag, String msg) {
        if (LEVEL <= INFO) {
            Log.i(tag, msg);
        }
    } public static void w(String tag, String msg) {
        if (LEVEL <= WARN) {
            Log.w(tag, msg);
        }
    } public static void e(String tag, String msg) {
        if (LEVEL <= ERROR) {
            Log.e(tag, msg);
        }
    }
}
