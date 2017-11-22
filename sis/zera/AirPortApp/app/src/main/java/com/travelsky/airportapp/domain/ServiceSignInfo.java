package com.travelsky.airportapp.domain;

import java.util.List;

/**
 * Created by iwanglijun on 2016/9/28.
 */
public class ServiceSignInfo {


    /**
     * UserId : 用户id
     * User_Seq : 用户seq
     * Server_Sign : {"Serversign":"ewqrrrrrrrrrrrr32141555"}
     * Ts : 时间戳
     * Sign : 签名
     * ServiceData : [{"Service_seq":"服务seq"},{"Service_seq":"服务seq"}]
     */

    private String UserId;
    private String User_Seq;
    /**
     * Serversign : ewqrrrrrrrrrrrr32141555
     */

    private ServerSignBean Server_Sign;
    private String Ts;
    private String Sign;
    /**
     * Service_seq : 服务seq
     */

    private List<ServiceDataBean> ServiceData;

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

    public ServerSignBean getServer_Sign() {
        return Server_Sign;
    }

    public void setServer_Sign(ServerSignBean Server_Sign) {
        this.Server_Sign = Server_Sign;
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

    public List<ServiceDataBean> getServiceData() {
        return ServiceData;
    }

    public void setServiceData(List<ServiceDataBean> ServiceData) {
        this.ServiceData = ServiceData;
    }

    public static class ServerSignBean {
        private String Serversign;

        public String getServersign() {
            return Serversign;
        }

        public void setServersign(String Serversign) {
            this.Serversign = Serversign;
        }
    }

    public static class ServiceDataBean {
        private String Service_seq;

        public String getService_seq() {
            return Service_seq;
        }

        public void setService_seq(String Service_seq) {
            this.Service_seq = Service_seq;
        }
    }
}
