package com.travelsky.airportapp.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by iwanglijun on 2016/9/12.
 */
public class Key {
    //获取签名
    public static String GetResponseMysign(LinkedHashMap<String, String> map, String privateKey) {
        String fullstring = GetPostStrings(map, "_sign") + privateKey;
        return FileHelper.ToMD5(fullstring);
    }

    private static String GetPostStrings(LinkedHashMap<String, String> map, String excepted) {
        LinkedHashMap<String, String> sPara = new LinkedHashMap<>();

        //过滤空值、sign与sign_type参数
        List<String> keyList = new ArrayList<String>();
        Iterator<String> it = map.keySet().iterator();
        int i = 0;
        while (it.hasNext()) {
            keyList.add(it.next());
            if (keyList.get(i) != excepted && map.get(keyList.get(i)) != "" && map.get(keyList.get(i)) != null) {
                sPara.put(keyList.get(i), map.get(keyList.get(i)));
                i++;
            }
        }

        //获得签名结果
        StringBuilder prestr = new StringBuilder();
        for (String temp : sPara.keySet()) {
            prestr.append(temp.toLowerCase() + "=" + sPara.get(temp) + "&");
        }

        //去掉最後一個&字符
        return prestr.substring(0, prestr.length() - 1);
    }

}
