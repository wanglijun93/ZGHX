package com.travelsky.airportapp.utils;

/**
 * Created by iwanglijun on 2016/9/19.
 */
public class AorD {
    public static  String ad(String name) {
        if ("A".equals(name)) {
           return "进港";
        }
        if ("D".equals(name)) {
            return  "出港";
        }
        return  name;
    }

}
