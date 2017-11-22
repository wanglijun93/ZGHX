package com.travelsky.airportapp.domain;


import java.io.Serializable;
import java.util.List;

public class AirInfo implements Serializable {


    /**
     * Alter_Arr_Time :
     * Alter_Dep_Time : 20161213010100
     * Arr_Airport_Three : 长沙
     * Code_Three : CSX
     * Company_Code : CSX
     * Customer_Code : HO
     * Customer_Name : 吉祥航空
     * Dep_Airport_Three : 上海虹桥
     * Dep_arr_Flag : A
     * Flight_Num_Two : HO1125
     * Flight_Seq : KJ345960-3848-4885-a9ea-872b62bcd6b8
     * Flight_State : 延误
     * Flight_State_Code : DLY
     * Normal_State : 到达
     * Normal_State_Code : ARR
     * Real_Arr_Time : 20161213010100
     * Real_Dep_Time : 20161213010000
     * Register_Num : B1645
     * Scheme_Arr_Time : 20161213010100
     * Scheme_Dep_Time : 20161213010100
     * Slot : 234
     * State_Reason : 前站流控
     * State_Reason_Code : 10131
     * Task_Class : ZB
     * accaService : [{"attach":"","create_time":"20161213095736","end_time":"201612130957","item_name":"垃圾车","item_value":"1","service_seq":"adcf6202-be7a-485a-bf34-05eaeb69f0ff","signstate":"N","start_time":"201612130957","update_time":"20161213095736"}]
     */

    private DataBean data;
    /**
     * data : {"Alter_Arr_Time":"","Alter_Dep_Time":"20161213010100","Arr_Airport_Three":"长沙","Code_Three":"CSX","Company_Code":"CSX","Customer_Code":"HO","Customer_Name":"吉祥航空","Dep_Airport_Three":"上海虹桥","Dep_arr_Flag":"A","Flight_Num_Two":"HO1125","Flight_Seq":"KJ345960-3848-4885-a9ea-872b62bcd6b8","Flight_State":"延误","Flight_State_Code":"DLY","Normal_State":"到达","Normal_State_Code":"ARR","Real_Arr_Time":"20161213010100","Real_Dep_Time":"20161213010000","Register_Num":"B1645","Scheme_Arr_Time":"20161213010100","Scheme_Dep_Time":"20161213010100","Slot":"234","State_Reason":"前站流控","State_Reason_Code":"10131","Task_Class":"ZB","accaService":[{"attach":"","create_time":"20161213095736","end_time":"201612130957","item_name":"垃圾车","item_value":"1","service_seq":"adcf6202-be7a-485a-bf34-05eaeb69f0ff","signstate":"N","start_time":"201612130957","update_time":"20161213095736"}]}
     * resCode : 1
     * resMsg : 正确
     */

    private int resCode;
    private String resMsg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getResCode() {
        return resCode;
    }

    public void setResCode(int resCode) {
        this.resCode = resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public static class DataBean implements Serializable{
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
        /**
         * attach :
         * create_time : 20161213095736
         * end_time : 201612130957
         * item_name : 垃圾车
         * item_value : 1
         * service_seq : adcf6202-be7a-485a-bf34-05eaeb69f0ff
         * signstate : N
         * start_time : 201612130957
         * update_time : 20161213095736
         */

        private List<AccaServiceBean> accaService;

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

        public String getCustomer_Code() {
            return Customer_Code;
        }

        public void setCustomer_Code(String Customer_Code) {
            this.Customer_Code = Customer_Code;
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

        public List<AccaServiceBean> getAccaService() {
            return accaService;
        }

        public void setAccaService(List<AccaServiceBean> accaService) {
            this.accaService = accaService;
        }

        public static class AccaServiceBean implements Serializable{
            private String attach;
            private String create_time;
            private String end_time;
            private String item_name;
            private String item_value;
            private String service_seq;
            private String signstate;
            private String start_time;
            private String update_time;

            public String getAttach() {
                return attach;
            }

            public void setAttach(String attach) {
                this.attach = attach;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public String getItem_name() {
                return item_name;
            }

            public void setItem_name(String item_name) {
                this.item_name = item_name;
            }

            public String getItem_value() {
                return item_value;
            }

            public void setItem_value(String item_value) {
                this.item_value = item_value;
            }

            public String getService_seq() {
                return service_seq;
            }

            public void setService_seq(String service_seq) {
                this.service_seq = service_seq;
            }

            public String getSignstate() {
                return signstate;
            }

            public void setSignstate(String signstate) {
                this.signstate = signstate;
            }

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }
        }
    }
}