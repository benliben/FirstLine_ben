package com.example.benben.firstline.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by benben on 2016/5/14.
 */
public class HttpUtil {
    public static String sendHtpRequest(final String address) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(address);//传入目标的网络地址
            connection = (HttpURLConnection) url.openConnection();//得到实例化
            connection.setRequestMethod("GET");//设置HTTP请所使用的方法
            connection.setConnectTimeout(8000);//链接超时
            connection.setReadTimeout(8000);//读取超时
            connection.setDoInput(true);
            connection.setDoOutput(true);
            InputStream in = connection.getInputStream();//获取到服务器返回的输入流
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine() )!= null) {
                response.append(line);
            }
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}

