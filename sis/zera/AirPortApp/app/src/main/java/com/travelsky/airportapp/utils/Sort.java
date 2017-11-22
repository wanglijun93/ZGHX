package com.travelsky.airportapp.utils;

/**
 * Created by iwanglijun on 2016/9/8.
 */
public class Sort {
    public static void main(String[] args) {

        String[] list = {"apple", "add", "address", "ban", "ben", "even", "day"};
        sort(list);
        print(list);
    }

    public static void sort(String[] list) {
        int len = list.length;
        String temp = null;
        for (int i = 0; i < len - 1; i++) {
            for (int j = 0; j < len - 1 - i; j++) {
                if ((list[j].compareTo(list[j + 1])) > 0) {
                    temp = list[j];
                    list[j] = list[j + 1];
                    list[j + 1] = temp;
                }
            }
        }

    }

    public static void print(String[] list) {

        for (String str : list) {
            System.out.println(str);
        }
    }


}
