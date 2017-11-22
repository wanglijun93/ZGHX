package com.travelsky.airportapp.domain;

/**
 * Created by iwanglijun on 2016/9/7.
 * 搜索返回数据业务bean
 */
public class SearchBean {


    /**
     * Alter_Arr_Time :
     * Alter_Dep_Time : 2016-10-27 01:01:00
     * Arr_Airport_Three : 成都
     * Code_Three : CSX
     * Company_Code : CSX
     * Customer_Name : 成都航空
     * Dep_Airport_Three : 长沙
     * Dep_arr_Flag : D
     * Flight_Num_Two : EU2206
     * Flight_Seq : 8357b76f-160b-40fe-9b28-613bdd406e07
     * Flight_State : 延误
     * Flight_State_Code : DLY
     * Normal_State : 起飞
     * Normal_State_Code : DEP
     * Real_Arr_Time :
     * Real_Dep_Time : 2016-10-27 01:00:00
     * Register_Num : B1632
     * ResCode : 1
     * ResMsg : 正确
     * Scheme_Arr_Time : 2016-10-27 01:01:00
     * Scheme_Dep_Time : 2016-10-27 01:01:00
     * Sign : null
     * Slot : 215
     * State_Reason : 前站流控
     * State_Reason_Code : 10131
     * Task_Class : ZB
     * Ts : null
     */

    private String Alter_Arr_Time;
    private String Alter_Dep_Time;
    private String Arr_Airport_Three;
    private String Code_Three;
    private String Company_Code;
    private String Customer_Name;
    private String Customer_Code;
    private String Dep_Airport_Three;
    private String Dep_arr_Flag;
    private String Flight_Num_Two;
    private String Flight_Seq;
    private String Flight_State;
    private String Flight_State_Code;
    private String Normal_State;
    private String Normal_State_Code;
    private String Real_Arr_Time;
    private String Real_Dep_Time;
    private String Register_Num;


    private int ResCode;
    private String ResMsg;
    private String Scheme_Arr_Time;
    private String Scheme_Dep_Time;
    private Object Sign;
    private String Slot;
    private String State_Reason;
    private String State_Reason_Code;
    private String Task_Class;
    private Object Ts;

    public String getCustomer_Code() {
        return Customer_Code;
    }

    public void setCustomer_Code(String customer_Code) {
        Customer_Code = customer_Code;
    }

    public String getAlter_Arr_Time() {
        return Alter_Arr_Time;
    }

    public void setAlter_Arr_Time(String Alter_Arr_Time) {
        this.Alter_Arr_Time = Alter_Arr_Time;
    }

    public String getAlter_Dep_Time() {
        return Alter_Dep_Time;
    }

    public void setAlter_Dep_Time(String Alter_Dep_Time) {
        this.Alter_Dep_Time = Alter_Dep_Time;
    }

    public String getArr_Airport_Three() {
        return Arr_Airport_Three;
    }

    public void setArr_Airport_Three(String Arr_Airport_Three) {
        this.Arr_Airport_Three = Arr_Airport_Three;
    }

    public String getCode_Three() {
        return Code_Three;
    }

    public void setCode_Three(String Code_Three) {
        this.Code_Three = Code_Three;
    }

    public String getCompany_Code() {
        return Company_Code;
    }

    public void setCompany_Code(String Company_Code) {
        this.Company_Code = Company_Code;
    }

    public String getCustomer_Name() {
        return Customer_Name;
    }

    public void setCustomer_Name(String Customer_Name) {
        this.Customer_Name = Customer_Name;
    }

    public String getDep_Airport_Three() {
        return Dep_Airport_Three;
    }

    public void setDep_Airport_Three(String Dep_Airport_Three) {
        this.Dep_Airport_Three = Dep_Airport_Three;
    }

    public String getDep_arr_Flag() {
        return Dep_arr_Flag;
    }

    public void setDep_arr_Flag(String Dep_arr_Flag) {
        this.Dep_arr_Flag = Dep_arr_Flag;
    }

    public String getFlight_Num_Two() {
        return Flight_Num_Two;
    }

    public void setFlight_Num_Two(String Flight_Num_Two) {
        this.Flight_Num_Two = Flight_Num_Two;
    }

    public String getFlight_Seq() {
        return Flight_Seq;
    }

    public void setFlight_Seq(String Flight_Seq) {
        this.Flight_Seq = Flight_Seq;
    }

    public String getFlight_State() {
        return Flight_State;
    }

    public void setFlight_State(String Flight_State) {
        this.Flight_State = Flight_State;
    }

    public String getFlight_State_Code() {
        return Flight_State_Code;
    }

    public void setFlight_State_Code(String Flight_State_Code) {
        this.Flight_State_Code = Flight_State_Code;
    }

    public String getNormal_State() {
        return Normal_State;
    }

    public void setNormal_State(String Normal_State) {
        this.Normal_State = Normal_State;
    }

    public String getNormal_State_Code() {
        return Normal_State_Code;
    }

    public void setNormal_State_Code(String Normal_State_Code) {
        this.Normal_State_Code = Normal_State_Code;
    }

    public String getReal_Arr_Time() {
        return Real_Arr_Time;
    }

    public void setReal_Arr_Time(String Real_Arr_Time) {
        this.Real_Arr_Time = Real_Arr_Time;
    }

    public String getReal_Dep_Time() {
        return Real_Dep_Time;
    }

    public void setReal_Dep_Time(String Real_Dep_Time) {
        this.Real_Dep_Time = Real_Dep_Time;
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

    public String getScheme_Arr_Time() {
        return Scheme_Arr_Time;
    }

    public void setScheme_Arr_Time(String Scheme_Arr_Time) {
        this.Scheme_Arr_Time = Scheme_Arr_Time;
    }

    public String getScheme_Dep_Time() {
        return Scheme_Dep_Time;
    }

    public void setScheme_Dep_Time(String Scheme_Dep_Time) {
        this.Scheme_Dep_Time = Scheme_Dep_Time;
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

    public String getState_Reason() {
        return State_Reason;
    }

    public void setState_Reason(String State_Reason) {
        this.State_Reason = State_Reason;
    }

    public String getState_Reason_Code() {
        return State_Reason_Code;
    }

    public void setState_Reason_Code(String State_Reason_Code) {
        this.State_Reason_Code = State_Reason_Code;
    }

    public String getTask_Class() {
        return Task_Class;
    }

    public void setTask_Class(String Task_Class) {
        this.Task_Class = Task_Class;
    }

    public Object getTs() {
        return Ts;
    }

    public void setTs(Object Ts) {
        this.Ts = Ts;
    }
}
