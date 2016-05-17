package com.example.benben.firstline.ui.activity;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by benben on 2016/5/16.
 */
public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>();

    public static void addActvitiy(Activity activity) {
        if (!activities.contains(activity)) {
            activities.add(activity);
        }

    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

}
