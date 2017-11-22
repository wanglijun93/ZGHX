package com.travelsky.airportapp.domain;

import java.util.List;

/**
 * Created by iwanglijun on 2016/10/13.
 */
public class EyeInfo {

    /**
     * Alter_Arr_Time :
     * Alter_Dep_Time : 20161013040000
     * Arr_Airport_Three : 长沙
     * Code_Three : CSX
     * Company_Code : CSX
     * Customer_Name : 奥凯航空
     * Dep_Airport_Three : 西宁
     * Dep_arr_Flag : A
     * Flight_Num_Two : BK3042
     * Flight_Seq : 9b294c53-f91a-445d-9ee2-6e1ead7bfd63
     * Flight_State :
     * Flight_State_Code :
     * Normal_State : 到达
     * Normal_State_Code : ARR
     * Real_Arr_Time : 20161013080000
     * Real_Dep_Time :
     * Register_Num : B1963
     * Scheme_Arr_Time : 20161013080000
     * Scheme_Dep_Time : 20161013040000
     * SignInfo : [{"End_Time":"201610111511","Item_Name":"气源车","Item_Value":"15.15","Server_Sign":"System.Byte[]","Service_Seq":"a0d77a2a-471a-4e57-bbca-3e3c618850e4","Start_Time":"201610110002"},{"End_Time":"201610111445","Item_Name":"扫雪车","Item_Value":"6","Server_Sign":"System.Byte[]","Service_Seq":"fcdff68a-109a-4fb8-a414-a9db2b423686","Start_Time":"201610111445"}]
     * Slot : 231
     * State_Reason :
     * State_Reason_Code :
     * Task_Class : ZB
     */

    private FlightDataBean FlightData;
    /**
     * FlightData : {"Alter_Arr_Time":"","Alter_Dep_Time":"20161013040000","Arr_Airport_Three":"长沙","Code_Three":"CSX","Company_Code":"CSX","Customer_Name":"奥凯航空","Dep_Airport_Three":"西宁","Dep_arr_Flag":"A","Flight_Num_Two":"BK3042","Flight_Seq":"9b294c53-f91a-445d-9ee2-6e1ead7bfd63","Flight_State":"","Flight_State_Code":"","Normal_State":"到达","Normal_State_Code":"ARR","Real_Arr_Time":"20161013080000","Real_Dep_Time":"","Register_Num":"B1963","Scheme_Arr_Time":"20161013080000","Scheme_Dep_Time":"20161013040000","SignInfo":[{"End_Time":"201610111511","Item_Name":"气源车","Item_Value":"15.15","Server_Sign":"System.Byte[]","Service_Seq":"a0d77a2a-471a-4e57-bbca-3e3c618850e4","Start_Time":"201610110002"},{"End_Time":"201610111445","Item_Name":"扫雪车","Item_Value":"6","Server_Sign":"System.Byte[]","Service_Seq":"fcdff68a-109a-4fb8-a414-a9db2b423686","Start_Time":"201610111445"}],"Slot":"231","State_Reason":"","State_Reason_Code":"","Task_Class":"ZB"}
     * ResCode : 1
     * ResMsg : 正确
     */

    private int ResCode;
    private String ResMsg;

    public FlightDataBean getFlightData() {
        return FlightData;
    }

    public void setFlightData(FlightDataBean FlightData) {
        this.FlightData = FlightData;
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

    public static class FlightDataBean {
        private String Alter_Arr_Time;
        private String Alter_Dep_Time;
        private String Arr_Airport_Three;
        private String Code_Three;
        private String Company_Code;
        private String Customer_Name;
        private String Customer_Code;

        public String getCustomer_Code() {
            return Customer_Code;
        }

        public void setCustomer_Code(String customer_Code) {
            Customer_Code = customer_Code;
        }

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
         * End_Time : 201610111511
         * Item_Name : 气源车
         * Item_Value : 15.15
         * Server_Sign : System.Byte[]
         * Service_Seq : a0d77a2a-471a-4e57-bbca-3e3c618850e4
         * Start_Time : 201610110002
         */

        private List<SignInfoBean> SignInfo;

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

        public List<SignInfoBean> getSignInfo() {
            return SignInfo;
        }

        public void setSignInfo(List<SignInfoBean> SignInfo) {
            this.SignInfo = SignInfo;
        }

        public static class SignInfoBean {
            private String End_Time;
            private String Item_Name;
            private String Item_Value;
            private String Server_Sign;
            private String Service_Seq;
            private String Start_Time;

            public String getEnd_Time() {
                return End_Time;
            }

            public void setEnd_Time(String End_Time) {
                this.End_Time = End_Time;
            }

            public String getItem_Name() {
                return Item_Name;
            }

            public void setItem_Name(String Item_Name) {
                this.Item_Name = Item_Name;
            }

            public String getItem_Value() {
                return Item_Value;
            }

            public void setItem_Value(String Item_Value) {
                this.Item_Value = Item_Value;
            }

            public String getServer_Sign() {
                return Server_Sign;
            }

            public void setServer_Sign(String Server_Sign) {
                this.Server_Sign = Server_Sign;
            }

            public String getService_Seq() {
                return Service_Seq;
            }

            public void setService_Seq(String Service_Seq) {
                this.Service_Seq = Service_Seq;
            }

            public String getStart_Time() {
                return Start_Time;
            }

            public void setStart_Time(String Start_Time) {
                this.Start_Time = Start_Time;
            }
        }
    }
}
