package com.travelsky.airportapp.domain;

import java.util.List;

/**
 * Created by meisme on 2016/12/1.
 */
public class FlightShow {


    /**
     * ResCode : 1
     * ResMsg : 正确
     * TheDayData : [{"Alter_Arr_Time":"","Alter_Dep_Time":"20161130004800","Arr_Airport_Three":"长沙","Code_Three":"CSX","Company_Code":"CSX","Customer_Name":"南方航空","Dep_Airport_Three":"秦皇岛","Dep_arr_Flag":"A","Flight_Num_Two":"CZ6700","Flight_Seq":"yya58984-ab2c-4f5f-8d3a-4876c0f9d152","Flight_State":"","Flight_State_Code":"","Normal_State":"到达","Normal_State_Code":"ARR","Real_Arr_Time":"20161130004800","Real_Dep_Time":"","Register_Num":"B6946","Scheme_Arr_Time":"20161130004800","Scheme_Dep_Time":"20161130004800","Slot":"216","State_Reason":"","State_Reason_Code":"","Task_Class":"ZB"},{"Alter_Arr_Time":"","Alter_Dep_Time":"20161130002700","Arr_Airport_Three":"长沙","Code_Three":"CSX","Company_Code":"CSX","Customer_Name":"南方航空","Dep_Airport_Three":"三亚","Dep_arr_Flag":"A","Flight_Num_Two":"CZ3944","Flight_Seq":"yya58984-ab2c-4f5f-8d3a-4876c0f9d154","Flight_State":"","Flight_State_Code":"","Normal_State":"到达","Normal_State_Code":"ARR","Real_Arr_Time":"20161130002700","Real_Dep_Time":"","Register_Num":"B1843","Scheme_Arr_Time":"20161130002700","Scheme_Dep_Time":"20161130002700","Slot":"223","State_Reason":"","State_Reason_Code":"","Task_Class":"ZB"},{"Alter_Arr_Time":"","Alter_Dep_Time":"20161130000200","Arr_Airport_Three":"长沙","Code_Three":"CSX","Company_Code":"CSX","Customer_Name":"东方航空","Dep_Airport_Three":"昆明","Dep_arr_Flag":"A","Flight_Num_Two":"MU9732","Flight_Seq":"yya58984-ab2c-4f5f-8d3a-4876c0f9d156","Flight_State":"","Flight_State_Code":"","Normal_State":"到达","Normal_State_Code":"ARR","Real_Arr_Time":"20161130000200","Real_Dep_Time":"","Register_Num":"B5816","Scheme_Arr_Time":"20161130000200","Scheme_Dep_Time":"20161130000200","Slot":"211","State_Reason":"","State_Reason_Code":"","Task_Class":"ZB"},{"Alter_Arr_Time":"","Alter_Dep_Time":"20161130001600","Arr_Airport_Three":"长沙","Code_Three":"CSX","Company_Code":"CSX","Customer_Name":"海南航空","Dep_Airport_Three":"重庆江北","Dep_arr_Flag":"A","Flight_Num_Two":"HU7634","Flight_Seq":"yya58984-ab2c-4f5f-8d3a-4876c0f9d153","Flight_State":"","Flight_State_Code":"","Normal_State":"到达","Normal_State_Code":"ARR","Real_Arr_Time":"20161130001600","Real_Dep_Time":"","Register_Num":"B5855","Scheme_Arr_Time":"20161130001600","Scheme_Dep_Time":"20161130001600","Slot":"6","State_Reason":"","State_Reason_Code":"","Task_Class":"ZB"},{"Alter_Arr_Time":"","Alter_Dep_Time":"20161130010100","Arr_Airport_Three":"长沙","Code_Three":"CSX","Company_Code":"CSX","Customer_Name":"吉祥航空","Dep_Airport_Three":"上海虹桥","Dep_arr_Flag":"A","Flight_Num_Two":"HO1125","Flight_Seq":"yya58984-ab2c-4f5f-8d3a-4876c0f9d158","Flight_State":"延误","Flight_State_Code":"DLY","Normal_State":"到达","Normal_State_Code":"ARR","Real_Arr_Time":"20161130010100","Real_Dep_Time":"20161130010000","Register_Num":"B1645","Scheme_Arr_Time":"20161130010100","Scheme_Dep_Time":"20161130010100","Slot":"234","State_Reason":"前站流控","State_Reason_Code":"10131","Task_Class":"ZB"},{"Alter_Arr_Time":"","Alter_Dep_Time":"20161130040000","Arr_Airport_Three":"长沙","Code_Three":"CSX","Company_Code":"CSX","Customer_Name":"奥凯航空","Dep_Airport_Three":"西宁","Dep_arr_Flag":"A","Flight_Num_Two":"BK3042","Flight_Seq":"yya58984-ab2c-4f5f-8d3a-4876c0f9d151","Flight_State":"","Flight_State_Code":"","Normal_State":"到达","Normal_State_Code":"ARR","Real_Arr_Time":"20161130080000","Real_Dep_Time":"","Register_Num":"B1963","Scheme_Arr_Time":"20161130080000","Scheme_Dep_Time":"20161130040000","Slot":"231","State_Reason":"","State_Reason_Code":"","Task_Class":"ZB"},{"Alter_Arr_Time":"","Alter_Dep_Time":"20161130010100","Arr_Airport_Three":"成都","Code_Three":"CSX","Company_Code":"CSX","Customer_Name":"成都航空","Dep_Airport_Three":"长沙","Dep_arr_Flag":"D","Flight_Num_Two":"EU2206","Flight_Seq":"yya58984-ab2c-4f5f-8d3a-4876c0f9d157","Flight_State":"延误","Flight_State_Code":"DLY","Normal_State":"起飞","Normal_State_Code":"DEP","Real_Arr_Time":"","Real_Dep_Time":"20161130010000","Register_Num":"B1632","Scheme_Arr_Time":"20161130010100","Scheme_Dep_Time":"20161130010100","Slot":"215","State_Reason":"前站流控","State_Reason_Code":"10131","Task_Class":"ZB"}]
     * UserId : null
     * User_Seq : null
     * data : [{"Alter_Arr_Time":"","Alter_Dep_Time":"20161201002700","Arr_Airport_Three":"长沙","Code_Three":"CSX","Company_Code":"CSX","Customer_Name":"南方航空","Dep_Airport_Three":"三亚","Dep_arr_Flag":"A","Flight_Num_Two":"CZ3944","Flight_Seq":"PKa58984-ab2c-4f5f-8d3a-4876c0f9d154","Flight_State":"","Flight_State_Code":"","Normal_State":"到达","Normal_State_Code":"ARR","Real_Arr_Time":"20161201002700","Real_Dep_Time":"","Register_Num":"B1843","Scheme_Arr_Time":"20161201002700","Scheme_Dep_Time":"20161201002700","Slot":"223","State_Reason":"","State_Reason_Code":"","Task_Class":"ZB"},{"Alter_Arr_Time":"","Alter_Dep_Time":"20161201010100","Arr_Airport_Three":"长沙","Code_Three":"CSX","Company_Code":"CSX","Customer_Name":"吉祥航空","Dep_Airport_Three":"上海虹桥","Dep_arr_Flag":"A","Flight_Num_Two":"HO1125","Flight_Seq":"PKa58984-ab2c-4f5f-8d3a-4876c0f9d158","Flight_State":"延误","Flight_State_Code":"DLY","Normal_State":"到达","Normal_State_Code":"ARR","Real_Arr_Time":"20161201010100","Real_Dep_Time":"20161201010000","Register_Num":"B1645","Scheme_Arr_Time":"20161201010100","Scheme_Dep_Time":"20161201010100","Slot":"234","State_Reason":"前站流控","State_Reason_Code":"10131","Task_Class":"ZB"},{"Alter_Arr_Time":"","Alter_Dep_Time":"20161201040000","Arr_Airport_Three":"长沙","Code_Three":"CSX","Company_Code":"CSX","Customer_Name":"奥凯航空","Dep_Airport_Three":"西宁","Dep_arr_Flag":"A","Flight_Num_Two":"BK3042","Flight_Seq":"PKa58984-ab2c-4f5f-8d3a-4876c0f9d151","Flight_State":"","Flight_State_Code":"","Normal_State":"到达","Normal_State_Code":"ARR","Real_Arr_Time":"20161201080000","Real_Dep_Time":"","Register_Num":"B1963","Scheme_Arr_Time":"20161201080000","Scheme_Dep_Time":"20161201040000","Slot":"231","State_Reason":"","State_Reason_Code":"","Task_Class":"ZB"}]
     */

    private int ResCode;
    private String ResMsg;
    private Object UserId;
    private Object User_Seq;
    /**
     * Alter_Arr_Time :
     * Alter_Dep_Time : 20161130004800
     * Arr_Airport_Three : 长沙
     * Code_Three : CSX
     * Company_Code : CSX
     * Customer_Name : 南方航空
     * Dep_Airport_Three : 秦皇岛
     * Dep_arr_Flag : A
     * Flight_Num_Two : CZ6700
     * Flight_Seq : yya58984-ab2c-4f5f-8d3a-4876c0f9d152
     * Flight_State :
     * Flight_State_Code :
     * Normal_State : 到达
     * Normal_State_Code : ARR
     * Real_Arr_Time : 20161130004800
     * Real_Dep_Time :
     * Register_Num : B6946
     * Scheme_Arr_Time : 20161130004800
     * Scheme_Dep_Time : 20161130004800
     * Slot : 216
     * State_Reason :
     * State_Reason_Code :
     * Task_Class : ZB
     */

    private List<TheDayDataBean> TheDayData;
    /**
     * Alter_Arr_Time :
     * Alter_Dep_Time : 20161201002700
     * Arr_Airport_Three : 长沙
     * Code_Three : CSX
     * Company_Code : CSX
     * Customer_Name : 南方航空
     * Dep_Airport_Three : 三亚
     * Dep_arr_Flag : A
     * Flight_Num_Two : CZ3944
     * Flight_Seq : PKa58984-ab2c-4f5f-8d3a-4876c0f9d154
     * Flight_State :
     * Flight_State_Code :
     * Normal_State : 到达
     * Normal_State_Code : ARR
     * Real_Arr_Time : 20161201002700
     * Real_Dep_Time :
     * Register_Num : B1843
     * Scheme_Arr_Time : 20161201002700
     * Scheme_Dep_Time : 20161201002700
     * Slot : 223
     * State_Reason :
     * State_Reason_Code :
     * Task_Class : ZB
     */

    private List<DataBean> data;

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

    public Object getUserId() {
        return UserId;
    }

    public void setUserId(Object UserId) {
        this.UserId = UserId;
    }

    public Object getUser_Seq() {
        return User_Seq;
    }

    public void setUser_Seq(Object User_Seq) {
        this.User_Seq = User_Seq;
    }

    public List<TheDayDataBean> getTheDayData() {
        return TheDayData;
    }

    public void setTheDayData(List<TheDayDataBean> TheDayData) {
        this.TheDayData = TheDayData;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class TheDayDataBean {
        private String Alter_Arr_Time;
        private String Alter_Dep_Time;
        private String Arr_Airport_Three;
        private String Code_Three;
        private String Company_Code;
        private String Customer_Code;
        private String Customer_Name;
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
        private String Scheme_Arr_Time;
        private String Scheme_Dep_Time;
        private String Slot;
        private String State_Reason;
        private String State_Reason_Code;
        private String Task_Class;

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
    }

    public static class DataBean {
        private String Alter_Arr_Time;
        private String Alter_Dep_Time;
        private String Arr_Airport_Three;
        private String Code_Three;
        private String Company_Code;
        private String Customer_Code;
        private String Customer_Name;
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
        private String Scheme_Arr_Time;
        private String Scheme_Dep_Time;
        private String Slot;
        private String State_Reason;
        private String State_Reason_Code;
        private String Task_Class;

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
    }
}
