package com.travelsky.airportapp.domain;

/**
 * Created by iwangtianyi on 2016/9/6.
 */
public class User {


    /**
     * COMPANY_CODE : CSX
     * ResCode : 1
     * ResMsg : 正确
     * Sign : null
     * TELEPHONE : Y
     * Ts : null
     * USER_ID : CSX0002
     * USER_NAME : 李四
     * USER_SEQ : 6b0726fa-d175-4e60-9a16-cfae91bc59f3
     * USER_STATUS : Y
     * loginName : null
     * password : null
     */

    private String COMPANY_CODE;
    private int ResCode;
    private String ResMsg;
    private Object Sign;
    private String TELEPHONE;
    private Object Ts;
    private String USER_ID;
    private String USER_NAME;
    private String USER_SEQ;
    private String USER_STATUS;
    private Object loginName;
    private Object password;

    public String getCOMPANY_CODE() {
        return COMPANY_CODE;
    }

    public void setCOMPANY_CODE(String COMPANY_CODE) {
        this.COMPANY_CODE = COMPANY_CODE;
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

    public String getTELEPHONE() {
        return TELEPHONE;
    }

    public void setTELEPHONE(String TELEPHONE) {
        this.TELEPHONE = TELEPHONE;
    }

    public Object getTs() {
        return Ts;
    }

    public void setTs(Object Ts) {
        this.Ts = Ts;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getUSER_NAME() {
        return USER_NAME;
    }

    public void setUSER_NAME(String USER_NAME) {
        this.USER_NAME = USER_NAME;
    }

    public String getUSER_SEQ() {
        return USER_SEQ;
    }

    public void setUSER_SEQ(String USER_SEQ) {
        this.USER_SEQ = USER_SEQ;
    }

    public String getUSER_STATUS() {
        return USER_STATUS;
    }

    public void setUSER_STATUS(String USER_STATUS) {
        this.USER_STATUS = USER_STATUS;
    }

    public Object getLoginName() {
        return loginName;
    }

    public void setLoginName(Object loginName) {
        this.loginName = loginName;
    }

    public Object getPassword() {
        return password;
    }

    public void setPassword(Object password) {
        this.password = password;
    }
}
