package com.example.benben.firstline;

import android.test.AndroidTestCase;

import com.example.benben.firstline.ui.activity.ActivityCollector;
import com.example.benben.firstline.ui.activity.login.LoginActivity;

/**
 * Created by benben on 2016/5/30.
 */
public class ActivityCollectorTest extends AndroidTestCase {
    @Override
    protected void setUp() throws Exception {
        /**在所有的测试用例执行之前调用，可以在这里进行一些初始化的操作*/
        super.setUp();
    }

    public void testAddActivity() {
        assertEquals(1, ActivityCollector.activities.size());//进行断言，认为activityCollector中的活动个数为0
        LoginActivity loginActivity = new LoginActivity();//实例一个loginActivity
        ActivityCollector.addActvitiy(loginActivity);//调用addActivity方法将这个活动添加到ActivityCollector中
        assertEquals(1, ActivityCollector.activities.size());//进行断言，认为目前activityCollector中的活动个数为1
    }

    @Override
    protected void tearDown() throws Exception {
        /**在所有测试用例执行之后调用，可以在这里进行一些资源释放的操作*/
        super.tearDown();
    }
}
