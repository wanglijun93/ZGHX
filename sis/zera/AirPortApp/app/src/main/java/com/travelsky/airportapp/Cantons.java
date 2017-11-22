package com.travelsky.airportapp;

/**
 * Created by iwanglijun on 2016/9/6.
 */
public class Cantons {
    //网络服务器接口
    //public static final String url = "http://10.1.5.117/";
    //wifi MDZ服务器
//    public static final String url = "http://10.8.7.11/";
    //本地映射
    //public static final String url = "http://10.1.46.242:8007/";
    //网络服务器接口
    //public static final String url = "http://10.1.5.117:8008/";
    //服务器端接口
//    public static final String url = "http://10.1.15.169:8008/";
    //本地接口
    //public static final String url = "http://10.1.46.242:8008/";
    //本地映射 https
//    public static final String url = "https://10.1.46.242/";
    //https接口
    public static final String url = "https://pdcs.acca.com.cn/";
    //登陆接口
    public static final String loginUrl = url + "PdcsLogin.svc/LoginPost";
    //航班号搜索接口
    public static final String FlightList = url + "AddFlight.svc/FlightList";
    //机位号搜索接口
    public static final String FlightListBySlot = url + "AddFlight.svc/FlightListBySlot";
    //时间段号搜索接口
    public static final String FlightListByTime = url + "AddFlight.svc/FlightListByTime";
    //添加航班
    public static final String AddFlightbyUser = url + "UserAddFlight.svc/AddFlightbyUser";
    //显示航班列表
    public static final String ShowFlightList = url + "FightList.svc/ShowFlightList";
    //保障详情
    public static final String FlightDetailsByUser = url + "FlightDetails.svc/FlightDetailsByUser";
    //修改事项
    public static final String FlightServiceUp = url + "FlightDetails.svc/FlightServiceUp";
    //删除事项
    public static final String FlightServiceDel = url + "FlightDetails.svc/FlightServiceDel";
    //签名提交接口
    public static final String AddServiceSign = url + "ServiceSign.svc/AddServiceSign";
    //删除航班
    public static final String FlightDelByUser = url + "UserAddFlight.svc/FlightDelByUser";
    //签字列表
    public static final String ServiceSignInfo = url + "ShowSignInfo.svc/ServiceSignInfo";
    //下载用户信息
    public static final String DowUserFlightData = url + "UserDataLoad.svc/DowUserFlightData";
    //上传用户信息
    public static final String UpUserFlightData = url + "UserDataLoad.svc/UpUserFlightData";
}
