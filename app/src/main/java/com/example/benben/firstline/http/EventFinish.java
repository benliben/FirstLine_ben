package com.example.benben.firstline.http;

/**
 * Created by beneben on 2016/5/27.
 * 网络通信成功或失败后的回调接口
 */
public interface EventFinish {
	abstract void onFinish(Object obj);

//	abstract void onCancel(Object obj);
}
