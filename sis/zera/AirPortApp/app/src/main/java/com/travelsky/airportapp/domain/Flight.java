package com.travelsky.airportapp.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by iwangtianyi on 2016/9/13.
 */
public class Flight implements Serializable{


    /**
     * Ts : 123456745
     * Sign : shjxcvbnmouytreazxjgfdsas
     * UserId : CSX0001
     * Company_Code : CSX
     * data : {"FlightId":[{"Id":"d6fa795f-789d-4e40-b243-ae63f8dba254"}]}
     */

    private String Ts;
    private String Sign;
    private String UserId;
    private String Company_Code;
    private DataBean data;

    public String getTs() {
        return Ts;
    }

    public void setTs(String Ts) {
        this.Ts = Ts;
    }

    public String getSign() {
        return Sign;
    }

    public void setSign(String Sign) {
        this.Sign = Sign;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getCompany_Code() {
        return Company_Code;
    }

    public void setCompany_Code(String Company_Code) {
        this.Company_Code = Company_Code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * Id : d6fa795f-789d-4e40-b243-ae63f8dba254
         */

        private List<FlightIdBean> FlightId;

        public List<FlightIdBean> getFlightId() {
            return FlightId;
        }

        public void setFlightId(List<FlightIdBean> FlightId) {
            this.FlightId = FlightId;
        }

        public static class FlightIdBean implements Serializable{
            private String Id;

            public String getId() {
                return Id;
            }

            public void setId(String Id) {
                this.Id = Id;
            }
        }
    }
}
