package com.walker.httputils;

import android.content.Entity;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 2016/7/25.
 */
public class HttpUtils {
    /**
     * 定义网络post请求
     * @param url
     * @param map
     * */
    public static String post(String url, Map<String,String> map){
        //封装请求参数：
        List<NameValuePair> pairs = new ArrayList<>();
        if (map!=null&&!map.isEmpty()){
            for (Map.Entry<String,String> entry:map.entrySet()){
                pairs.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
            }

        }
        //把请求参数变成请求体部分
        try{
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs,"utf-8");
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(entity);
            HttpParams params = httpClient.getParams();//计算网络超时用
            HttpConnectionParams.setConnectionTimeout(params,3*1000);//链接超时
            HttpConnectionParams.setSoTimeout(params,5*1000);//设置请求超时
            httpClient.setParams(params);
            HttpResponse response = httpClient.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode()>=200&&statusLine.getStatusCode()<300){
                String resultTemp = EntityUtils.toString(response.getEntity());
                return resultTemp;
            }else {
                return null;
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return  null;
        }
    }

    /**
     * get请求
     * @param url
     *
     * */

    public static String get(String url){
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("charest", HTTP.UTF_8);
        httpGet.addHeader("Accept","application/json");
        httpGet.addHeader("Content-Type","application/json;charset=UTF-8");
        HttpParams params = httpClient.getParams();
        HttpConnectionParams.setConnectionTimeout(params,3*1000);
        HttpConnectionParams.setSoTimeout(params,5*1000);
        httpGet.setParams(params);
        try {

            HttpResponse response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode()>=200&&response.getStatusLine().getStatusCode()<300){
                return EntityUtils.toString(response.getEntity(),HTTP.UTF_8);
            }else {
                return "";
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
