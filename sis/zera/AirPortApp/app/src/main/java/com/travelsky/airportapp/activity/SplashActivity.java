package com.travelsky.airportapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Window;

import com.travelsky.airportapp.R;

/**
 * splash界面
 */
public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        init();

    }

    /**
     * 开启子线程等待2秒
     */
    private void init() {
        new Thread() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                loadMainUI();
            }

        }.start();
    }

    /**
     * 加载主界面
     */
    private void loadMainUI() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
