package com.travelsky.airportapp.domain;

import java.util.List;

/**
 * Created by iwangtianyi on 2016/11/10.
 */
public class UserDataUp {


    /**
     * UserId : CXS001
     * User_Seq : 0895133f-bc37-4e72-96fe-0d15cbf34766
     * Ts : 1234567890
     * Sign : wrewqrer2343werwqerqwerqwr
     * accauserFlightDate : [{"sequence":"0895133f-bc37-4e72-96fe-0d15cbf34766","OperationState":"D"}]
     * accaServiceDate : [{"attach":"","client_sign":null,"company_code":"CSX","create_id":"CSX0001","create_time":20161101164949,"end_time":201611011649,"invoice_no":"","item_name":"牵引车","item_value":"8","link_flight_id":"f05db8af-fb92-4351-a5f0-d79bcde9234b","server_sign":[137,80],"service_seq":"9e4f65d9-911f-42f4-84c5-356211a0105f","service_type":"特车","signstate":"Y","start_time":201611011649,"update_id":"CSX0001","update_time":20161101165142,"used_time":"00:00:00","OperationState":"D"}]
     */

    private String UserId;
    private String User_Seq;
    private String Ts;
    private String Sign;
    /**
     * sequence : 0895133f-bc37-4e72-96fe-0d15cbf34766
     * OperationState : D
     */

    private List<AccauserFlightDateBean> accauserFlightDate;
    /**
     * attach :
     * client_sign : null
     * company_code : CSX
     * create_id : CSX0001
     * create_time : 20161101164949
     * end_time : 201611011649
     * invoice_no :
     * item_name : 牵引车
     * item_value : 8
     * link_flight_id : f05db8af-fb92-4351-a5f0-d79bcde9234b
     * server_sign : [137,80]
     * service_seq : 9e4f65d9-911f-42f4-84c5-356211a0105f
     * service_type : 特车
     * signstate : Y
     * start_time : 201611011649
     * update_id : CSX0001
     * update_time : 20161101165142
     * used_time : 00:00:00
     * OperationState : D
     */

    private List<AccaServiceDateBean> accaServiceDate;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getUser_Seq() {
        return User_Seq;
    }

    public void setUser_Seq(String User_Seq) {
        this.User_Seq = User_Seq;
    }

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

    public List<AccauserFlightDateBean> getAccauserFlightDate() {
        return accauserFlightDate;
    }

    public void setAccauserFlightDate(List<AccauserFlightDateBean> accauserFlightDate) {
        this.accauserFlightDate = accauserFlightDate;
    }

    public List<AccaServiceDateBean> getAccaServiceDate() {
        return accaServiceDate;
    }

    public void setAccaServiceDate(List<AccaServiceDateBean> accaServiceDate) {
        this.accaServiceDate = accaServiceDate;
    }

    public static class AccauserFlightDateBean {
        private String sequence;
        private String OperationState;

        public String getSequence() {
            return sequence;
        }

        public void setSequence(String sequence) {
            this.sequence = sequence;
        }

        public String getOperationState() {
            return OperationState;
        }

        public void setOperationState(String OperationState) {
            this.OperationState = OperationState;
        }
    }

    public static class AccaServiceDateBean {
        private String attach;
        private String client_sign;
        private String company_code;
        private String create_id;
        private long create_time;
        private long end_time;
        private String invoice_no;
        private String item_name;
        private String item_value;
        private String link_flight_id;
        private String service_seq;
        private String service_type;
        private String signstate;
        private long start_time;
        private String update_id;
        private long update_time;
        private String used_time;
        private String operationstate;
        private String server_sign;

        public String getAttach() {
            return attach;
        }

        public void setAttach(String attach) {
            this.attach = attach;
        }

        public String getClient_sign() {
            return client_sign;
        }

        public void setClient_sign(String client_sign) {
            this.client_sign = client_sign;
        }

        public String getCompany_code() {
            return company_code;
        }

        public void setCompany_code(String company_code) {
            this.company_code = company_code;
        }

        public String getCreate_id() {
            return create_id;
        }

        public void setCreate_id(String create_id) {
            this.create_id = create_id;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public long getEnd_time() {
            return end_time;
        }

        public void setEnd_time(long end_time) {
            this.end_time = end_time;
        }

        public String getInvoice_no() {
            return invoice_no;
        }

        public void setInvoice_no(String invoice_no) {
            this.invoice_no = invoice_no;
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

        public String getLink_flight_id() {
            return link_flight_id;
        }

        public void setLink_flight_id(String link_flight_id) {
            this.link_flight_id = link_flight_id;
        }

        public String getService_seq() {
            return service_seq;
        }

        public void setService_seq(String service_seq) {
            this.service_seq = service_seq;
        }

        public String getService_type() {
            return service_type;
        }

        public void setService_type(String service_type) {
            this.service_type = service_type;
        }

        public String getSignstate() {
            return signstate;
        }

        public void setSignstate(String signstate) {
            this.signstate = signstate;
        }

        public long getStart_time() {
            return start_time;
        }

        public void setStart_time(long start_time) {
            this.start_time = start_time;
        }

        public String getUpdate_id() {
            return update_id;
        }

        public void setUpdate_id(String update_id) {
            this.update_id = update_id;
        }

        public long getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(long update_time) {
            this.update_time = update_time;
        }

        public String getUsed_time() {
            return used_time;
        }

        public void setUsed_time(String used_time) {
            this.used_time = used_time;
        }

        public String getOperationstate() {
            return operationstate;
        }

        public void setOperationstate(String operationState) {
            this.operationstate = operationState;
        }

        public String getServer_sign() {
            return server_sign;
        }

        public void setServer_sign(String server_sign) {
            this.server_sign = server_sign;
        }
    }
}
