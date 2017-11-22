package com.travelsky.airportapp.domain;

import java.util.List;

/**
 * Created by meisme on 2016/12/2.
 */
public class DataLoad {
    private String UserId;
    private String User_Seq;
    private List<Flight.DataBean.FlightIdBean> FlightId;
    private String Ts;
    private String Sign;

    public DataLoad() {
    }

    public DataLoad(String userId, String user_Seq, List<Flight.DataBean.FlightIdBean> flightId, String ts, String sign) {
        UserId = userId;
        User_Seq = user_Seq;
        FlightId = flightId;
        Ts = ts;
        Sign = sign;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUser_Seq() {
        return User_Seq;
    }

    public void setUser_Seq(String user_Seq) {
        User_Seq = user_Seq;
    }

    public List<Flight.DataBean.FlightIdBean> getFlightId() {
        return FlightId;
    }

    public void setFlightId(List<Flight.DataBean.FlightIdBean> flightId) {
        FlightId = flightId;
    }

    public String getTs() {
        return Ts;
    }

    public void setTs(String ts) {
        Ts = ts;
    }

    public String getSign() {
        return Sign;
    }

    public void setSign(String sign) {
        Sign = sign;
    }
}
