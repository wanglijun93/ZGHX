package com.travelsky.airportapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 读取缓存工具类
 */
public class CacheUtils {

    public static final String userId = "userId";
    public static final String Company_Code = "Company_Code";
    public static final String user_seq = "user_seq";
    public static final String Fight_Seq = "fight_seq";
    public static final String username = "username";
    public static final String userpwd = "userpwd";


    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences("example", Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();

    }

    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("example", Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }

    public static void putString(Context context, String key, String values) {
        SharedPreferences sp = context.getSharedPreferences("example", Context.MODE_PRIVATE);
        sp.edit().putString(key, values).commit();

    }

    public static String getString(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("example", Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }

    public static String GetPostStrings(Map<String, String> map) {

        List<String> list = new ArrayList<String>();

        for (String s : map.keySet()) {
            if (s == null || "".equals(s)) {
                return "参数为空";
            }
            list.add(s);
        }

        String[] str = new String[list.size()];

        for (int i = 0; i < list.size(); i++) {
            str[i] = list.get(i);
        }

        String sign = "";

        Arrays.sort(str);
        for (int j = 0; j < str.length; j++) {
            sign += str[j] + "=" + map.get(str[j]) + "&";
        }

        return sign.substring(0, sign.length() - 1);
    }
}