package com.travelsky.airportapp.pager;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 获取应用版本号的工具类
 * @author wlj
 * @on 2016/7/7
 * @email wljdev@l63.com
 * @csdn http://blog.csdn.net/mynamelijun
 */
public class PackageInfoUtils {
    public static String getPackageVersion(Context context) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            String versionName = packageInfo.versionName;
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "解析版本号失败";
        }
    }
}
