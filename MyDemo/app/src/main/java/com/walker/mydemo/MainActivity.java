package com.walker.mydemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.walker.httputils.HttpUtils;

public class MainActivity extends Activity {

    HttpUtils httpUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        httpUtils = new HttpUtils();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String url = "https://www.baidu.com/";
              final String ss=  httpUtils.get(url);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,ss,Toast.LENGTH_LONG).show();
                    }
                });

            }
        }).start();
    }

}
