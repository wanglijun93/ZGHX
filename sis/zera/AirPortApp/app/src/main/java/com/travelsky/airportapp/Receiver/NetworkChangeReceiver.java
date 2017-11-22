package com.travelsky.airportapp.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;

import com.travelsky.airportapp.service.UpDataService;
import com.travelsky.airportapp.utils.AirportApplication;
import com.travelsky.airportapp.utils.LogUtil;

import de.greenrobot.event.EventBus;

/**
 * Created by iwangtianyi on 2016/11/14.
 * 获取手机网络状态
 */
public class NetworkChangeReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        //LogUtil.e("TAG", "onReceive");
        Intent intent1 = new Intent(context, UpDataService.class);
        State wifiState = null;
        State mobileState = null;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        if (wifiState != null && mobileState != null
                && State.CONNECTED != wifiState
                && State.CONNECTED == mobileState) {
            // 手机网络连接成功
            LogUtil.e("TAG", "手机网络连接成功");
            if (!AirportApplication.net) {
                AirportApplication.net = true;
                context.startService(intent1);
            }
        } else if (wifiState != null && mobileState != null
                && State.CONNECTED != wifiState
                && State.CONNECTED != mobileState) {
            // 手机没有任何的网络
            LogUtil.e("TAG", "手机没有任何的网络");
            if (!AirportApplication.noNet) {
                AirportApplication.noNet = true;
                EventBus.getDefault().post("网络断开");
            }
            context.stopService(intent1);
        } else if (wifiState != null && State.CONNECTED == wifiState) {
            // 无线网络连接成功
            LogUtil.e("TAG", "无线网络连接成功");
            if (!AirportApplication.net) {
                AirportApplication.net = true;
                context.startService(intent1);
            }

        }
    }
}

