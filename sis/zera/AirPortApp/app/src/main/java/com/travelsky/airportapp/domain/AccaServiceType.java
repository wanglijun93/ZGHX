package com.travelsky.airportapp.domain;

/**
 * Created by iwangtianyi on 2016/9/22.
 */

public class AccaServiceType {
    private int TYPE_SEQ;
    private String COMPANY_CODE;
    private String SERVICE_TYPE;
    private String SERVICE_NAME;
    private String HOUR_FLAG;
    private String CREATE_ID;
    private long CREATE_TIME;
    private String UPDATE_ID;
    private long UPDATE_TIME;

    public AccaServiceType() {
    }

    public AccaServiceType(int TYPE_SEQ, String COMPANY_CODE, String SERVICE_TYPE, String SERVICE_NAME, String HOUR_FLAG, long CREATE_TIME, String CREATE_ID, String UPDATE_ID, long UPDATE_TIME) {
        this.TYPE_SEQ = TYPE_SEQ;
        this.COMPANY_CODE = COMPANY_CODE;
        this.SERVICE_TYPE = SERVICE_TYPE;
        this.SERVICE_NAME = SERVICE_NAME;
        this.HOUR_FLAG = HOUR_FLAG;
        this.CREATE_TIME = CREATE_TIME;
        this.CREATE_ID = CREATE_ID;
        this.UPDATE_ID = UPDATE_ID;
        this.UPDATE_TIME = UPDATE_TIME;
    }

    public int getTYPE_SEQ() {
        return TYPE_SEQ;
    }

    public void setTYPE_SEQ(int TYPE_SEQ) {
        this.TYPE_SEQ = TYPE_SEQ;
    }

    public long getUPDATE_TIME() {
        return UPDATE_TIME;
    }

    public void setUPDATE_TIME(long UPDATE_TIME) {
        this.UPDATE_TIME = UPDATE_TIME;
    }

    public long getCREATE_TIME() {
        return CREATE_TIME;
    }

    public void setCREATE_TIME(long CREATE_TIME) {
        this.CREATE_TIME = CREATE_TIME;
    }

    public String getCREATE_ID() {
        return CREATE_ID;
    }

    public void setCREATE_ID(String CREATE_ID) {
        this.CREATE_ID = CREATE_ID;
    }

    public String getHOUR_FLAG() {
        return HOUR_FLAG;
    }

    public void setHOUR_FLAG(String HOUR_FLAG) {
        this.HOUR_FLAG = HOUR_FLAG;
    }

    public String getSERVICE_TYPE() {
        return SERVICE_TYPE;
    }

    public void setSERVICE_TYPE(String SERVICE_TYPE) {
        this.SERVICE_TYPE = SERVICE_TYPE;
    }

    public String getCOMPANY_CODE() {
        return COMPANY_CODE;
    }

    public void setCOMPANY_CODE(String COMPANY_CODE) {
        this.COMPANY_CODE = COMPANY_CODE;
    }

    public String getSERVICE_NAME() {
        return SERVICE_NAME;
    }

    public void setSERVICE_NAME(String SERVICE_NAME) {
        this.SERVICE_NAME = SERVICE_NAME;
    }

    public String getUPDATE_ID() {
        return UPDATE_ID;
    }

    public void setUPDATE_ID(String UPDATE_ID) {
        this.UPDATE_ID = UPDATE_ID;
    }
}
