package com.travelsky.airportapp.base;

/**
 * Created by iwanglijun on 2016/9/7.
 * 搜索返回数据业务bean
 */
public class SearchBean {


    /**
     * Acheme_Dep_Time : 20160907003300
     * Dep_Arr_Flag : A
     * Flight_Num_Two : null
     * Register_Num : B1963
     * ResCode : 1
     * ResMsg : 正确
     * Sign : null
     * Slot : 231
     * Ts : null
     * flight_date : null
     * flight_num_two : BK3042
     */

    private String Acheme_Dep_Time;
    private String Dep_Arr_Flag;
    private Object Flight_Num_Two;
    private String Register_Num;
    private int ResCode;
    private String ResMsg;
    private Object Sign;
    private String Slot;
    private Object Ts;
    private Object flight_date;
    private String flight_num_two;



    public SearchBean() {
        super();
    }

    public String getAcheme_Dep_Time() {
        return Acheme_Dep_Time;
    }

    public void setAcheme_Dep_Time(String Acheme_Dep_Time) {
        this.Acheme_Dep_Time = Acheme_Dep_Time;
    }

    public String getDep_Arr_Flag() {
        return Dep_Arr_Flag;
    }

    public void setDep_Arr_Flag(String Dep_Arr_Flag) {
        this.Dep_Arr_Flag = Dep_Arr_Flag;
    }

    public Object getFlight_Num_Two() {
        return Flight_Num_Two;
    }

    public void setFlight_Num_Two(Object Flight_Num_Two) {
        this.Flight_Num_Two = Flight_Num_Two;
    }

    public String getRegister_Num() {
        return Register_Num;
    }

    public void setRegister_Num(String Register_Num) {
        this.Register_Num = Register_Num;
    }

    public int getResCode() {
        return ResCode;
    }

    public void setResCode(int ResCode) {
        this.ResCode = ResCode;
    }

    public String getResMsg() {
        return ResMsg;
    }

    public void setResMsg(String ResMsg) {
        this.ResMsg = ResMsg;
    }

    public Object getSign() {
        return Sign;
    }

    public void setSign(Object Sign) {
        this.Sign = Sign;
    }

    public String getSlot() {
        return Slot;
    }

    public void setSlot(String Slot) {
        this.Slot = Slot;
    }

    public Object getTs() {
        return Ts;
    }

    public void setTs(Object Ts) {
        this.Ts = Ts;
    }

    public Object getFlight_date() {
        return flight_date;
    }

    public void setFlight_date(Object flight_date) {
        this.flight_date = flight_date;
    }

    public String getFlight_num_two() {
        return flight_num_two;
    }

    public void setFlight_num_two(String flight_num_two) {
        this.flight_num_two = flight_num_two;
    }
}
