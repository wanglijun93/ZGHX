package com.travelsky.airportapp.utils.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Base64;

import com.orhanobut.logger.Logger;
import com.travelsky.airportapp.domain.AccaServiceType;
import com.travelsky.airportapp.domain.AirInfo;
import com.travelsky.airportapp.domain.EyeInfo;
import com.travelsky.airportapp.domain.FlightData;
import com.travelsky.airportapp.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iwangtianyi on 2016/9/22.
 */

public class DataBaseDao extends DBHelper {

    private String s;

    public DataBaseDao(Context context) {
        super(context);
    }

    /**
     * 获取服务类型表数据
     * @return
     */
    public List<AccaServiceType> getAccaServiceType() {
        List<AccaServiceType> serviceList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("ACCA_SERVICE_TYPE", null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            AccaServiceType service = new AccaServiceType();

            service.setTYPE_SEQ(cursor.getInt(cursor.getColumnIndex("TYPE_SEQ")));
            service.setCOMPANY_CODE(cursor.getString(cursor.getColumnIndex("COMPANY_CODE")));
            service.setSERVICE_TYPE(cursor.getString(cursor.getColumnIndex("SERVICE_TYPE")));
            service.setSERVICE_NAME(cursor.getString(cursor.getColumnIndex("SERVICE_NAME")));
            service.setHOUR_FLAG(cursor.getString(cursor.getColumnIndex("HOUR_FLAG")));
            service.setCREATE_ID(cursor.getString(cursor.getColumnIndex("CREATE_ID")));
            service.setCREATE_TIME(cursor.getLong(cursor.getColumnIndex("CREATE_TIME")));
            service.setUPDATE_ID(cursor.getString(cursor.getColumnIndex("UPDATE_ID")));
            service.setUPDATE_TIME(cursor.getLong(cursor.getColumnIndex("UPDATE_TIME")));

            serviceList.add(service);
        }
        db.close();

        return serviceList;
    }

    /**
     * 根据航班seq查询航班信息
     * @param flight_seq
     * @return
     */
    public List<FlightData.AccaFlightDateBean> getAccaFlightBySeq(String flight_seq) {
        List<FlightData.AccaFlightDateBean> serviceList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(DBHelper.ACCA_FLIGHT, null, "_flight_seq=?", new String[]{flight_seq}, null, null, null);

        while (cursor.moveToNext()) {
            FlightData.AccaFlightDateBean flight = new FlightData.AccaFlightDateBean();

            flight.set_alter_arr_time(cursor.getLong(cursor.getColumnIndex("_alter_arr_time")));
            flight.set_alter_dep_time(cursor.getLong(cursor.getColumnIndex("_alter_dep_time")));
            flight.set_arr_airport_four(cursor.getString(cursor.getColumnIndex("_arr_airport_four")));
            flight.set_arr_airport_three(cursor.getString(cursor.getColumnIndex("_arr_airport_three")));
            flight.set_code_three(cursor.getString(cursor.getColumnIndex("_code_three")));
            flight.set_company_code(cursor.getString(cursor.getColumnIndex("_company_code")));
            flight.set_create_id(cursor.getString(cursor.getColumnIndex("_create_id")));
            flight.set_create_time(cursor.getInt(cursor.getColumnIndex("_create_time")));
            flight.set_customer_code(cursor.getString(cursor.getColumnIndex("_customer_code")));
            flight.set_dep_airport_four(cursor.getString(cursor.getColumnIndex("_dep_airport_four")));
            flight.set_dep_airport_three(cursor.getString(cursor.getColumnIndex("_dep_airport_three")));
            flight.set_dep_arr_flag(cursor.getString(cursor.getColumnIndex("_dep_arr_flag")));
            flight.set_flight_num_two(cursor.getString(cursor.getColumnIndex("_flight_num_two")));
            flight.set_flight_seq(cursor.getString(cursor.getColumnIndex("_flight_seq")));
            flight.set_flight_state(cursor.getString(cursor.getColumnIndex("_flight_state")));
            flight.set_flight_state_code(cursor.getString(cursor.getColumnIndex("_flight_state_code")));
            flight.set_import_id(cursor.getString(cursor.getColumnIndex("_import_id")));
            flight.set_normal_state(cursor.getString(cursor.getColumnIndex("_normal_state")));
            flight.set_normal_state_code(cursor.getString(cursor.getColumnIndex("_normal_state_code")));
            flight.set_plan_flight_date(cursor.getInt(cursor.getColumnIndex("_plan_flight_date")));
            flight.set_real_arr_time(cursor.getLong(cursor.getColumnIndex("_real_arr_time")));
            flight.set_real_arrdep_time(cursor.getLong(cursor.getColumnIndex("_real_arrdep_time")));
            flight.set_real_dep_time(cursor.getLong(cursor.getColumnIndex("_real_dep_time")));
            flight.set_real_flight_date(cursor.getInt(cursor.getColumnIndex("_real_flight_date")));
            flight.set_register_num(cursor.getString(cursor.getColumnIndex("_register_num")));
            flight.set_route(cursor.getString(cursor.getColumnIndex("_route")));
            flight.set_route_type(cursor.getString(cursor.getColumnIndex("_route_type")));
            flight.set_scheme_arr_time(cursor.getLong(cursor.getColumnIndex("_scheme_arr_time")));
            flight.set_scheme_dep_time(cursor.getLong(cursor.getColumnIndex("_scheme_dep_time")));
            flight.set_segment(cursor.getString(cursor.getColumnIndex("_segment")));
            flight.set_segment_type(cursor.getString(cursor.getColumnIndex("_segment_type")));
            flight.set_slot(cursor.getString(cursor.getColumnIndex("_slot")));
            flight.set_state_reason(cursor.getString(cursor.getColumnIndex("_state_reason")));
            flight.set_state_reason_code(cursor.getString(cursor.getColumnIndex("_state_reason_code")));
            flight.set_task_class(cursor.getString(cursor.getColumnIndex("_task_class")));
            flight.set_task_name(cursor.getString(cursor.getColumnIndex("_task_name")));
            flight.set_update_id(cursor.getString(cursor.getColumnIndex("_update_id")));
            flight.set_update_time(cursor.getInt(cursor.getColumnIndex("_update_time")));

            serviceList.add(flight);
        }
        db.close();

        return serviceList;
    }

    /**
     * 根据日期获取航班信息
     * @param today 日期格式(yyyyMMdd)
     * @return
     */
    public List<FlightData.AccaFlightDateBean> getAccaFlightToday(String today) {
        List<FlightData.AccaFlightDateBean> serviceList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from ACCA_FLIGHT inner join ACCA_USER_FLIGHT on ACCA_FLIGHT._flight_seq=ACCA_USER_FLIGHT._flight_id where _real_flight_date=? and ACCA_USER_FLIGHT._operationState is not ?", new String[]{today, "D"});

        while (cursor.moveToNext()) {
            FlightData.AccaFlightDateBean flight = new FlightData.AccaFlightDateBean();

            flight.set_alter_arr_time(cursor.getLong(cursor.getColumnIndex("_alter_arr_time")));
            flight.set_alter_dep_time(cursor.getLong(cursor.getColumnIndex("_alter_dep_time")));
            flight.set_arr_airport_four(cursor.getString(cursor.getColumnIndex("_arr_airport_four")));
            flight.set_arr_airport_three(cursor.getString(cursor.getColumnIndex("_arr_airport_three")));
            flight.set_code_three(cursor.getString(cursor.getColumnIndex("_code_three")));
            flight.set_company_code(cursor.getString(cursor.getColumnIndex("_company_code")));
            flight.set_create_id(cursor.getString(cursor.getColumnIndex("_create_id")));
            flight.set_create_time(cursor.getInt(cursor.getColumnIndex("_create_time")));
            flight.set_customer_code(cursor.getString(cursor.getColumnIndex("_customer_code")));
            flight.set_dep_airport_four(cursor.getString(cursor.getColumnIndex("_dep_airport_four")));
            flight.set_dep_airport_three(cursor.getString(cursor.getColumnIndex("_dep_airport_three")));
            flight.set_dep_arr_flag(cursor.getString(cursor.getColumnIndex("_dep_arr_flag")));
            flight.set_flight_num_two(cursor.getString(cursor.getColumnIndex("_flight_num_two")));
            flight.set_flight_seq(cursor.getString(cursor.getColumnIndex("_flight_seq")));
            flight.set_flight_state(cursor.getString(cursor.getColumnIndex("_flight_state")));
            flight.set_flight_state_code(cursor.getString(cursor.getColumnIndex("_flight_state_code")));
            flight.set_import_id(cursor.getString(cursor.getColumnIndex("_import_id")));
            flight.set_normal_state(cursor.getString(cursor.getColumnIndex("_normal_state")));
            flight.set_normal_state_code(cursor.getString(cursor.getColumnIndex("_normal_state_code")));
            flight.set_plan_flight_date(cursor.getInt(cursor.getColumnIndex("_plan_flight_date")));
            flight.set_real_arr_time(cursor.getLong(cursor.getColumnIndex("_real_arr_time")));
            flight.set_real_arrdep_time(cursor.getLong(cursor.getColumnIndex("_real_arrdep_time")));
            flight.set_real_dep_time(cursor.getLong(cursor.getColumnIndex("_real_dep_time")));
            flight.set_real_flight_date(cursor.getInt(cursor.getColumnIndex("_real_flight_date")));
            flight.set_register_num(cursor.getString(cursor.getColumnIndex("_register_num")));
            flight.set_route(cursor.getString(cursor.getColumnIndex("_route")));
            flight.set_route_type(cursor.getString(cursor.getColumnIndex("_route_type")));
            flight.set_scheme_arr_time(cursor.getLong(cursor.getColumnIndex("_scheme_arr_time")));
            flight.set_scheme_dep_time(cursor.getLong(cursor.getColumnIndex("_scheme_dep_time")));
            flight.set_segment(cursor.getString(cursor.getColumnIndex("_segment")));
            flight.set_segment_type(cursor.getString(cursor.getColumnIndex("_segment_type")));
            flight.set_slot(cursor.getString(cursor.getColumnIndex("_slot")));
            flight.set_state_reason(cursor.getString(cursor.getColumnIndex("_state_reason")));
            flight.set_state_reason_code(cursor.getString(cursor.getColumnIndex("_state_reason_code")));
            flight.set_task_class(cursor.getString(cursor.getColumnIndex("_task_class")));
            flight.set_task_name(cursor.getString(cursor.getColumnIndex("_task_name")));
            flight.set_update_id(cursor.getString(cursor.getColumnIndex("_update_id")));
            flight.set_update_time(cursor.getInt(cursor.getColumnIndex("_update_time")));

            serviceList.add(flight);
        }
        db.close();

        return serviceList;
    }

    /**
     * 获取服务信息
     * @return
     */
    public List<FlightData.AccaServiceDateBean1> getAccaServiceDate1() {
        List<FlightData.AccaServiceDateBean1> serviceList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(DBHelper.ACCA_SERVICE, null, "_operationState is not null and _operationState is not ?", new String[]{""}, null, null, null);

        while (cursor.moveToNext()) {
            FlightData.AccaServiceDateBean1 service = new FlightData.AccaServiceDateBean1();

            service.set_attach(cursor.getString(cursor.getColumnIndex("_attach")));
            service.set_client_sign(cursor.getString(cursor.getColumnIndex("_client_sign")));
            service.set_company_code(cursor.getString(cursor.getColumnIndex("_company_code")));
            service.set_create_id(cursor.getString(cursor.getColumnIndex("_create_id")));
            service.set_create_time(cursor.getLong(cursor.getColumnIndex("_create_time")));
            service.set_end_time(cursor.getLong(cursor.getColumnIndex("_end_time")));
            service.set_invoice_no(cursor.getString(cursor.getColumnIndex("_invoice_no")));
            service.set_item_name(cursor.getString(cursor.getColumnIndex("_item_name")));
            service.set_item_value(cursor.getString(cursor.getColumnIndex("_item_value")));
            service.set_link_flight_id(cursor.getString(cursor.getColumnIndex("_link_flight_id")));
            service.set_server_sign(cursor.getString(cursor.getColumnIndex("_server_sign")));
            service.set_service_seq(cursor.getString(cursor.getColumnIndex("_service_seq")));
            service.set_service_type(cursor.getString(cursor.getColumnIndex("_service_type")));
            service.set_signstate(cursor.getString(cursor.getColumnIndex("_signstate")));
            service.set_start_time(cursor.getLong(cursor.getColumnIndex("_start_time")));
            service.set_update_id(cursor.getString(cursor.getColumnIndex("_update_id")));
            service.set_update_time(cursor.getLong(cursor.getColumnIndex("_update_time")));
            service.set_used_time(cursor.getString(cursor.getColumnIndex("_used_time")));
            service.set_operationState(cursor.getString(cursor.getColumnIndex("_operationState")));

            serviceList.add(service);
        }
        db.close();

        return serviceList;
    }

    /**
     * 根据航班id获取服务信息
     * @param flightId
     * @return
     */
    public List<FlightData.AccaServiceDateBean> getServiceDateBySeq(String flightId) {
        List<FlightData.AccaServiceDateBean> serviceList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from ACCA_SERVICE where _link_flight_id=? and _operationState is not ? order by _update_time desc", new String[]{flightId, "D"});

        while (cursor.moveToNext()) {
            FlightData.AccaServiceDateBean service = new FlightData.AccaServiceDateBean();

            service.set_attach(cursor.getString(cursor.getColumnIndex("_attach")));
            service.set_client_sign(cursor.getString(cursor.getColumnIndex("_client_sign")));
            service.set_company_code(cursor.getString(cursor.getColumnIndex("_company_code")));
            service.set_create_id(cursor.getString(cursor.getColumnIndex("_create_id")));
            service.set_create_time(cursor.getLong(cursor.getColumnIndex("_create_time")));
            service.set_end_time(cursor.getLong(cursor.getColumnIndex("_end_time")));
            service.set_invoice_no(cursor.getString(cursor.getColumnIndex("_invoice_no")));
            service.set_item_name(cursor.getString(cursor.getColumnIndex("_item_name")));
            service.set_item_value(cursor.getString(cursor.getColumnIndex("_item_value")));
            service.set_link_flight_id(cursor.getString(cursor.getColumnIndex("_link_flight_id")));
            service.set_server_sign(cursor.getBlob(cursor.getColumnIndex("_server_sign")));
            service.set_service_seq(cursor.getString(cursor.getColumnIndex("_service_seq")));
            service.set_service_type(cursor.getString(cursor.getColumnIndex("_service_type")));
            service.set_signstate(cursor.getString(cursor.getColumnIndex("_signstate")));
            service.set_start_time(cursor.getLong(cursor.getColumnIndex("_start_time")));
            service.set_update_id(cursor.getString(cursor.getColumnIndex("_update_id")));
            service.set_update_time(cursor.getLong(cursor.getColumnIndex("_update_time")));
            service.set_used_time(cursor.getString(cursor.getColumnIndex("_used_time")));

            serviceList.add(service);
        }
        db.close();

        return serviceList;
    }

    /**
     * 有网络连接时，根据航班id获取服务信息
     * @param flight_id
     * @return
     */
    public List<AirInfo.DataBean.AccaServiceBean> getServiceDateInternet(String flight_id) {
        List<AirInfo.DataBean.AccaServiceBean> serviceList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from ACCA_SERVICE where _link_flight_id = ? and _operationState is not ? order by _update_time desc", new String[]{flight_id, "D"});

            while (cursor.moveToNext()) {
            AirInfo.DataBean.AccaServiceBean service = new AirInfo.DataBean.AccaServiceBean();

            service.setAttach(cursor.getString(cursor.getColumnIndex("_attach")));
            service.setCreate_time(cursor.getString(cursor.getColumnIndex("_create_time")));
            service.setEnd_time(cursor.getString(cursor.getColumnIndex("_end_time")));
            service.setItem_name(cursor.getString(cursor.getColumnIndex("_item_name")));
            service.setItem_value(cursor.getString(cursor.getColumnIndex("_item_value")));
            service.setService_seq(cursor.getString(cursor.getColumnIndex("_service_seq")));
            service.setSignstate(cursor.getString(cursor.getColumnIndex("_signstate")));
            service.setStart_time(cursor.getString(cursor.getColumnIndex("_start_time")));
            service.setUpdate_time(cursor.getString(cursor.getColumnIndex("_update_time")));

            serviceList.add(service);
        }
        db.close();

        return serviceList;
    }

    /**
     * 获取用户对应航班信息
     * @return
     */
    public List<FlightData.AccauserFlightDateBean> getAccauserFlightDateBean() {
        List<FlightData.AccauserFlightDateBean> serviceList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(DBHelper.ACCA_USER_FLIGHT, null, "_operationState is not null", null, null, null, null);

        while (cursor.moveToNext()) {
            FlightData.AccauserFlightDateBean service = new FlightData.AccauserFlightDateBean();

            service.set_company_code(cursor.getString(cursor.getColumnIndex("_company_code")));
            service.set_create_id(cursor.getString(cursor.getColumnIndex("_create_id")));
            service.set_create_time(cursor.getLong(cursor.getColumnIndex("_create_time")));
            service.set_flight_id(cursor.getString(cursor.getColumnIndex("_flight_id")));
            service.set_sequence(cursor.getString(cursor.getColumnIndex("_sequence")));
            service.set_update_id(cursor.getString(cursor.getColumnIndex("_update_id")));
            service.set_update_time(cursor.getLong(cursor.getColumnIndex("_update_time")));
            service.set_user_id(cursor.getString(cursor.getColumnIndex("_user_id")));
            service.set_operationState(cursor.getString(cursor.getColumnIndex("_operationState")));

            serviceList.add(service);
        }
        db.close();

        return serviceList;
    }

    /**
     * 添加航班信息
     * @param flight
     */
    public void addAccaFlightDateBean(List<FlightData.AccaFlightDateBean> flight) {
        long id = 0;
        SQLiteDatabase db = getReadableDatabase();
        for (int i = 0; i < flight.size(); i++) {
            ContentValues values = new ContentValues();
            values.put("_alter_arr_time", flight.get(i).get_alter_arr_time());
            values.put("_alter_dep_time", flight.get(i).get_alter_dep_time());
            values.put("_arr_airport_four", flight.get(i).get_arr_airport_four());
            values.put("_arr_airport_three", flight.get(i).get_arr_airport_three());
            values.put("_code_three", flight.get(i).get_code_three());
            values.put("_company_code", flight.get(i).get_company_code());
            values.put("_create_id", flight.get(i).get_create_id());
            values.put("_create_time", flight.get(i).get_create_time());
            values.put("_customer_code", flight.get(i).get_customer_code());
            values.put("_dep_airport_four", flight.get(i).get_dep_airport_four());
            values.put("_dep_airport_three", flight.get(i).get_dep_airport_three());
            values.put("_dep_arr_flag", flight.get(i).get_dep_arr_flag());
            values.put("_flight_num_two", flight.get(i).get_flight_num_two());
            values.put("_flight_seq", flight.get(i).get_flight_seq());
            values.put("_flight_state", flight.get(i).get_flight_state());
            values.put("_flight_state_code", flight.get(i).get_flight_state_code());
            values.put("_import_id", flight.get(i).get_import_id());
            values.put("_normal_state", flight.get(i).get_normal_state());
            values.put("_normal_state_code", flight.get(i).get_normal_state_code());
            values.put("_plan_flight_date", flight.get(i).get_plan_flight_date());
            values.put("_real_arr_time", flight.get(i).get_real_arr_time());
            values.put("_real_arrdep_time", flight.get(i).get_real_arrdep_time());
            values.put("_real_dep_time", flight.get(i).get_real_dep_time());
            values.put("_real_flight_date", flight.get(i).get_real_flight_date());
            values.put("_register_num", flight.get(i).get_register_num());
            values.put("_route", flight.get(i).get_route());
            values.put("_route_type", flight.get(i).get_route_type());
            values.put("_scheme_arr_time", flight.get(i).get_scheme_arr_time());
            values.put("_scheme_dep_time", flight.get(i).get_scheme_dep_time());
            values.put("_segment", flight.get(i).get_segment());
            values.put("_segment_type", flight.get(i).get_segment_type());
            values.put("_slot", flight.get(i).get_slot());
            values.put("_state_reason", flight.get(i).get_state_reason());
            values.put("_state_reason_code", flight.get(i).get_state_reason_code());
            values.put("_task_class", flight.get(i).get_task_class());
            values.put("_task_name", flight.get(i).get_task_name());
            values.put("_update_id", flight.get(i).get_update_id());
            values.put("_update_time", flight.get(i).get_update_time());
            id = db.insert(DBHelper.ACCA_FLIGHT, null, values);
        }
//        if (id > 0) {
//            Logger.d("添加成功");
//        } else {
//            Logger.d("添加失败");
//
//        }

//        db.close();
    }

    /**
     * 有网络连接时，根据航班seq删除航班信息
     * @param flight_seq
     */
    public void delAccaFlightDateBySeq(String flight_seq) {
        SQLiteDatabase db = getReadableDatabase();
        int id = db.delete(DBHelper.ACCA_FLIGHT, "_flight_seq=?", new String[]{flight_seq});
        if (id > 0) {
            Logger.d("删除成功");
        } else {
            Logger.d("删除失败");
        }
        db.close();
    }

    /**
     * 删除航班信息表
     */
    public void delAccaFlightDateBean() {
        SQLiteDatabase db = getReadableDatabase();
        int id = db.delete(DBHelper.ACCA_FLIGHT, null, null);
        if (id > 0) {
            //Logger.d("删除成功");
        } else {
            Logger.d("删除失败");
        }
        db.close();
    }

    /**
     * 保存服务信息
     * @param service
     */
    public void saveAccaServiceDate(List<FlightData.AccaServiceDateBean> service) {
        SQLiteDatabase db = getReadableDatabase();
        long id = 0;
        for (int i = 0; i < service.size(); i++) {
            ContentValues values = new ContentValues();
            values.put("_attach", service.get(i).get_attach());
            values.put("_client_sign", String.valueOf(service.get(i).get_client_sign()));
            values.put("_company_code", service.get(i).get_company_code());
            values.put("_create_id", service.get(i).get_create_id());
            values.put("_create_time", service.get(i).get_create_time());
            values.put("_end_time", service.get(i).get_end_time());
            values.put("_invoice_no", service.get(i).get_invoice_no());
            values.put("_item_name", service.get(i).get_item_name());
            values.put("_item_value", service.get(i).get_item_value());
            values.put("_link_flight_id", service.get(i).get_link_flight_id());
            byte[] server_sign = service.get(i).get_server_sign();
            if (server_sign != null) {
                s = Base64.encodeToString(server_sign, Base64.DEFAULT);
                values.put("_server_sign", s);
            } else {
                //LogUtil.d("---null", "签名空");
            }
            values.put("_service_seq", service.get(i).get_service_seq());
            values.put("_service_type", service.get(i).get_service_type());
            values.put("_signstate", service.get(i).get_signstate());
            values.put("_start_time", service.get(i).get_start_time());
            values.put("_update_id", service.get(i).get_update_id());
            values.put("_update_time", service.get(i).get_update_time());
            values.put("_used_time", service.get(i).get_used_time());

            id = db.insert(DBHelper.ACCA_SERVICE, null, values);
        }
//        if (id > 0) {
//            Logger.d("添加成功");
//        } else {
//            Logger.d("添加失败");
//
//        }

//        db.close();
    }

    /**
     * 本地添加服务信息
     * @param service
     */
    public void addAccaServiceDate(FlightData.AccaServiceDateBean service) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("_attach", service.get_attach());
        values.put("_client_sign", String.valueOf(service.get_client_sign()));
        values.put("_company_code", service.get_company_code());
        values.put("_create_id", service.get_create_id());
        values.put("_create_time", service.get_create_time());
        values.put("_end_time", service.get_end_time());
        values.put("_invoice_no", service.get_invoice_no());
        values.put("_item_name", service.get_item_name());
        values.put("_item_value", service.get_item_value());
        values.put("_link_flight_id", service.get_link_flight_id());
        byte[] server_sign = service.get_server_sign();
        if (server_sign != null) {
            s = Base64.encodeToString(server_sign, Base64.DEFAULT);
            values.put("_server_sign", s);
        } else {
            LogUtil.d("---null", "签名空");
        }
        values.put("_service_seq", service.get_service_seq());
        values.put("_service_type", service.get_service_type());
        values.put("_signstate", service.get_signstate());
        values.put("_start_time", service.get_start_time());
        values.put("_update_id", service.get_update_id());
        values.put("_update_time", service.get_update_time());
        values.put("_used_time", service.get_used_time());
        values.put("_operationState","I");
        long id = db.insert(DBHelper.ACCA_SERVICE, null, values);
        if (id > 0) {
            Logger.d("添加成功");
        } else {
            Logger.d("添加失败");

        }

        db.close();
    }

    /**
     * 有网络连接时，添加服务信息
     * @param service
     */
    public void addAccaServiceInternet(FlightData.AccaServiceDateBean service) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("_attach", service.get_attach());
        values.put("_client_sign", String.valueOf(service.get_client_sign()));
        values.put("_company_code", service.get_company_code());
        values.put("_create_id", service.get_create_id());
        values.put("_create_time", service.get_create_time());
        values.put("_end_time", service.get_end_time());
        values.put("_invoice_no", service.get_invoice_no());
        values.put("_item_name", service.get_item_name());
        values.put("_item_value", service.get_item_value());
        values.put("_link_flight_id", service.get_link_flight_id());
        byte[] server_sign = service.get_server_sign();
        if (server_sign != null) {
            s = Base64.encodeToString(server_sign, Base64.DEFAULT);
            values.put("_server_sign", s);
        } else {
            LogUtil.d("---null", "签名空");
        }
        values.put("_service_seq", service.get_service_seq());
        values.put("_service_type", service.get_service_type());
        values.put("_signstate", service.get_signstate());
        values.put("_start_time", service.get_start_time());
        values.put("_update_id", service.get_update_id());
        values.put("_update_time", service.get_update_time());
        values.put("_used_time", service.get_used_time());
        long id = db.insert(DBHelper.ACCA_SERVICE, null, values);
        if (id > 0) {
            Logger.d("添加成功");
        } else {
            Logger.d("添加失败");

        }

        db.close();
    }

    /**
     * 根据航班的seq，修改本地服务信息
     * @param service
     * @param service_seq
     */
    public void updateAccaServiceDate(FlightData.AccaServiceDateBean service, String service_seq) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("_attach", service.get_attach());
        values.put("_company_code", service.get_company_code());
        values.put("_create_id", service.get_create_id());
        values.put("_create_time", service.get_create_time());
        values.put("_end_time", service.get_end_time());
        values.put("_invoice_no", service.get_invoice_no());
        values.put("_item_name", service.get_item_name());
        values.put("_item_value", service.get_item_value());
        values.put("_link_flight_id", service.get_link_flight_id());
        values.put("_service_seq", service.get_service_seq());
        values.put("_service_type", service.get_service_type());
        values.put("_signstate", service.get_signstate());
        values.put("_start_time", service.get_start_time());
        values.put("_update_id", service.get_update_id());
        values.put("_update_time", service.get_update_time());
        values.put("_used_time", service.get_used_time());
        values.put("_operationState", "U");

        int id = db.update(DBHelper.ACCA_SERVICE, values, "_service_seq=?", new String[]{service_seq});

        if (id > 0) {
            Logger.d("修改成功");
        } else {
            Logger.d("修改失败");

        }

        db.close();
    }

    /**
     * 有网络连接时，根据航班的seq，修改本地服务信息
     * @param service
     * @param service_seq
     */
    public void updateServiceInternet(FlightData.AccaServiceDateBean service, String service_seq) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("_attach", service.get_attach());
        values.put("_company_code", service.get_company_code());
        values.put("_create_id", service.get_create_id());
        values.put("_create_time", service.get_create_time());
        values.put("_end_time", service.get_end_time());
        values.put("_invoice_no", service.get_invoice_no());
        values.put("_item_name", service.get_item_name());
        values.put("_item_value", service.get_item_value());
        values.put("_link_flight_id", service.get_link_flight_id());
        values.put("_service_seq", service.get_service_seq());
        values.put("_service_type", service.get_service_type());
        values.put("_signstate", service.get_signstate());
        values.put("_start_time", service.get_start_time());
        values.put("_update_id", service.get_update_id());
        values.put("_update_time", service.get_update_time());
        values.put("_used_time", service.get_used_time());

        int id = db.update(DBHelper.ACCA_SERVICE, values, "_service_seq=?", new String[]{service_seq});

        if (id > 0) {
            Logger.d("修改成功");
        } else {
            Logger.d("修改失败");

        }

        db.close();
    }

    /**
     * 根据服务的seq,删除本地服务信息
     * @param service_seq
     */
    public void delAccaServiceDateBySeq(String service_seq) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("_operationState", "D");

        int id = db.update(DBHelper.ACCA_SERVICE, values, "_service_seq=?", new String[]{service_seq});

        if (id > 0) {
            Logger.d("删除成功");
        } else {
            Logger.d("删除失败");

        }

        db.close();
    }

    /**
     * 有网络时，根据服务的seq,删除服务信息
     * @param service_seq
     */
    public void delAccaServiceInternet(String service_seq) {
        SQLiteDatabase db = getReadableDatabase();

        int id = db.delete(DBHelper.ACCA_SERVICE, "_service_seq=?", new String[]{service_seq});

        if (id > 0) {
            Logger.d("修改成功");
        } else {
            Logger.d("修改失败");

        }

        db.close();
    }

    /**
     * 删除服务信息表
     */
    public void delAccaServiceDate() {
        SQLiteDatabase db = getReadableDatabase();
        int id = db.delete(DBHelper.ACCA_SERVICE, null, null);
        if (id > 0) {
            //Logger.d("删除成功");
        } else {
            Logger.d("删除失败");
        }
        db.close();
    }

    /**
     * 上传成功，删除服务项目
     */
    public void upDelAccaServiceDate() {
        SQLiteDatabase db = getReadableDatabase();

        int id = db.delete(DBHelper.ACCA_SERVICE, "_operationState = ?", new String[]{"D"});

        if (id > 0) {
            //Logger.d("删除成功");
        } else {
            Logger.d("删除失败");
        }
        db.close();
    }

    /**
     * 上传成功，添加修改本地服务项目
     */
    public void upModifyAccaServiceDate() {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("_operationState", "");
        int id = db.update(DBHelper.ACCA_SERVICE, values, "_operationState = ? or _operationState = ?", new String[]{"I", "U"});

        if (id > 0) {
            //Logger.d("修改成功");
        } else {
            Logger.d("修改失败");
        }
        db.close();
    }

    /**
     * 保存用户对应航班信息
     * @param userFlight
     */
    public void addAccauserFlightDate(List<FlightData.AccauserFlightDateBean> userFlight) {
        SQLiteDatabase db = getReadableDatabase();
        long id = 0;
        for (int i = 0; i < userFlight.size(); i++) {
            ContentValues values = new ContentValues();
            values.put("_company_code", userFlight.get(i).get_company_code());
            values.put("_create_id", userFlight.get(i).get_create_id());
            values.put("_create_time", userFlight.get(i).get_create_time());
            values.put("_flight_id", userFlight.get(i).get_flight_id());
            values.put("_sequence", userFlight.get(i).get_sequence());
            values.put("_update_id", userFlight.get(i).get_update_id());
            values.put("_update_time", userFlight.get(i).get_update_time());
            values.put("_user_id", userFlight.get(i).get_user_id());

            id = db.insert(DBHelper.ACCA_USER_FLIGHT, null, values);
        }
//        if (id > 0) {
//            Logger.d("添加成功");
//        } else {
//            Logger.d("添加失败");
//
//        }

//        db.close();
    }

    /**
     * 离线模式，根据航班编号，删除航班信息
     * @param flight_id
     */
    public void updateAccaFlightDate(String flight_id) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("_operationState", "D");

        int id = db.update(DBHelper.ACCA_FLIGHT, values, "_flight_seq=?", new String[]{flight_id});

        if (id > 0) {
            Logger.d("删除成功");
        } else {
            Logger.d("删除失败");

        }

        db.close();
    }

    /**
     * 离线模式，根据航班编号，删除航班信息
     * @param flight_id
     */
    public void updateAccauserFlightDate(String flight_id) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("_operationState", "D");

        int id = db.update(DBHelper.ACCA_USER_FLIGHT, values, "_flight_id=?", new String[]{flight_id});

        if (id > 0) {
            Logger.d("删除成功");
        } else {
            Logger.d("删除失败");

        }

        db.close();
    }

    /**
     * 上传成功删除航班信息
     */
    public void upDelAcFlightDate() {
        SQLiteDatabase db = getReadableDatabase();

        int id = db.delete(DBHelper.ACCA_FLIGHT, "_operationState = ?", new String[]{"D"});

        if (id > 0) {
            Logger.d("上传删除成功");
        } else {
            Logger.d("上传删除失败");

        }

        db.close();
    }

    /**
     * 有网络连接时，根据航班id删除用户对应航班信息
     * @param flight_id
     */
    public void delAccauserFlightDate(String flight_id) {
        SQLiteDatabase db = getReadableDatabase();

        int id = db.delete(DBHelper.ACCA_USER_FLIGHT, "_flight_id=?", new String[]{flight_id});

        if (id > 0) {
            Logger.d("删除成功");
        } else {
            Logger.d("删除失败");

        }

        db.close();
    }

    /**
     * 删除用户对应航班信息表
     */
    public void delAccauserFlightDate() {
        SQLiteDatabase db = getReadableDatabase();
        int id = db.delete(DBHelper.ACCA_USER_FLIGHT, null, null);
        if (id > 0) {
            //Logger.d("删除成功");
        } else {
            Logger.d("删除失败");
        }
        db.close();
    }

    /**
     * 按航班号查找
     * @param flightDate
     * @param flightNo
     * @return
     */
    public List<FlightData.AccaFlightDateBean> getAccaFlightByFlightNo(String flightDate, String flightNo) {
        List<FlightData.AccaFlightDateBean> serviceList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(DBHelper.ACCA_FLIGHT, null, "_flight_num_two like ? and _plan_flight_date=?", new String[]{"%" + flightNo + "%", flightDate}, null, null, null);

        while (cursor.moveToNext()) {
            FlightData.AccaFlightDateBean flight = new FlightData.AccaFlightDateBean();

            flight.set_alter_arr_time(cursor.getLong(cursor.getColumnIndex("_alter_arr_time")));
            flight.set_alter_dep_time(cursor.getLong(cursor.getColumnIndex("_alter_dep_time")));
            flight.set_arr_airport_four(cursor.getString(cursor.getColumnIndex("_arr_airport_four")));
            flight.set_arr_airport_three(cursor.getString(cursor.getColumnIndex("_arr_airport_three")));
            flight.set_code_three(cursor.getString(cursor.getColumnIndex("_code_three")));
            flight.set_company_code(cursor.getString(cursor.getColumnIndex("_company_code")));
            flight.set_create_id(cursor.getString(cursor.getColumnIndex("_create_id")));
            flight.set_create_time(cursor.getInt(cursor.getColumnIndex("_create_time")));
            flight.set_customer_code(cursor.getString(cursor.getColumnIndex("_customer_code")));
            flight.set_dep_airport_four(cursor.getString(cursor.getColumnIndex("_dep_airport_four")));
            flight.set_dep_airport_three(cursor.getString(cursor.getColumnIndex("_dep_airport_three")));
            flight.set_dep_arr_flag(cursor.getString(cursor.getColumnIndex("_dep_arr_flag")));
            flight.set_flight_num_two(cursor.getString(cursor.getColumnIndex("_flight_num_two")));
            flight.set_flight_seq(cursor.getString(cursor.getColumnIndex("_flight_seq")));
            flight.set_flight_state(cursor.getString(cursor.getColumnIndex("_flight_state")));
            flight.set_flight_state_code(cursor.getString(cursor.getColumnIndex("_flight_state_code")));
            flight.set_import_id(cursor.getString(cursor.getColumnIndex("_import_id")));
            flight.set_normal_state(cursor.getString(cursor.getColumnIndex("_normal_state")));
            flight.set_normal_state_code(cursor.getString(cursor.getColumnIndex("_normal_state_code")));
            flight.set_plan_flight_date(cursor.getInt(cursor.getColumnIndex("_plan_flight_date")));
            flight.set_real_arr_time(cursor.getLong(cursor.getColumnIndex("_real_arr_time")));
            flight.set_real_arrdep_time(cursor.getLong(cursor.getColumnIndex("_real_arrdep_time")));
            flight.set_real_dep_time(cursor.getLong(cursor.getColumnIndex("_real_dep_time")));
            flight.set_real_flight_date(cursor.getInt(cursor.getColumnIndex("_real_flight_date")));
            flight.set_register_num(cursor.getString(cursor.getColumnIndex("_register_num")));
            flight.set_route(cursor.getString(cursor.getColumnIndex("_route")));
            flight.set_route_type(cursor.getString(cursor.getColumnIndex("_route_type")));
            flight.set_scheme_arr_time(cursor.getLong(cursor.getColumnIndex("_scheme_arr_time")));
            flight.set_scheme_dep_time(cursor.getLong(cursor.getColumnIndex("_scheme_dep_time")));
            flight.set_segment(cursor.getString(cursor.getColumnIndex("_segment")));
            flight.set_segment_type(cursor.getString(cursor.getColumnIndex("_segment_type")));
            flight.set_slot(cursor.getString(cursor.getColumnIndex("_slot")));
            flight.set_state_reason(cursor.getString(cursor.getColumnIndex("_state_reason")));
            flight.set_state_reason_code(cursor.getString(cursor.getColumnIndex("_state_reason_code")));
            flight.set_task_class(cursor.getString(cursor.getColumnIndex("_task_class")));
            flight.set_task_name(cursor.getString(cursor.getColumnIndex("_task_name")));
            flight.set_update_id(cursor.getString(cursor.getColumnIndex("_update_id")));
            flight.set_update_time(cursor.getInt(cursor.getColumnIndex("_update_time")));

            serviceList.add(flight);
        }
        db.close();

        return serviceList;
    }

    /**
     * 按机位号查找
     * @param flightDate
     * @param planeseats
     * @return
     */
    public List<FlightData.AccaFlightDateBean> getAccaFlightByPlaneseats(String flightDate, String planeseats) {
        List<FlightData.AccaFlightDateBean> serviceList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(DBHelper.ACCA_FLIGHT, null, "_slot = ? and _plan_flight_date=?", new String[]{planeseats, flightDate}, null, null, null);

        while (cursor.moveToNext()) {
            FlightData.AccaFlightDateBean flight = new FlightData.AccaFlightDateBean();

            flight.set_alter_arr_time(cursor.getLong(cursor.getColumnIndex("_alter_arr_time")));
            flight.set_alter_dep_time(cursor.getLong(cursor.getColumnIndex("_alter_dep_time")));
            flight.set_arr_airport_four(cursor.getString(cursor.getColumnIndex("_arr_airport_four")));
            flight.set_arr_airport_three(cursor.getString(cursor.getColumnIndex("_arr_airport_three")));
            flight.set_code_three(cursor.getString(cursor.getColumnIndex("_code_three")));
            flight.set_company_code(cursor.getString(cursor.getColumnIndex("_company_code")));
            flight.set_create_id(cursor.getString(cursor.getColumnIndex("_create_id")));
            flight.set_create_time(cursor.getInt(cursor.getColumnIndex("_create_time")));
            flight.set_customer_code(cursor.getString(cursor.getColumnIndex("_customer_code")));
            flight.set_dep_airport_four(cursor.getString(cursor.getColumnIndex("_dep_airport_four")));
            flight.set_dep_airport_three(cursor.getString(cursor.getColumnIndex("_dep_airport_three")));
            flight.set_dep_arr_flag(cursor.getString(cursor.getColumnIndex("_dep_arr_flag")));
            flight.set_flight_num_two(cursor.getString(cursor.getColumnIndex("_flight_num_two")));
            flight.set_flight_seq(cursor.getString(cursor.getColumnIndex("_flight_seq")));
            flight.set_flight_state(cursor.getString(cursor.getColumnIndex("_flight_state")));
            flight.set_flight_state_code(cursor.getString(cursor.getColumnIndex("_flight_state_code")));
            flight.set_import_id(cursor.getString(cursor.getColumnIndex("_import_id")));
            flight.set_normal_state(cursor.getString(cursor.getColumnIndex("_normal_state")));
            flight.set_normal_state_code(cursor.getString(cursor.getColumnIndex("_normal_state_code")));
            flight.set_plan_flight_date(cursor.getInt(cursor.getColumnIndex("_plan_flight_date")));
            flight.set_real_arr_time(cursor.getLong(cursor.getColumnIndex("_real_arr_time")));
            flight.set_real_arrdep_time(cursor.getLong(cursor.getColumnIndex("_real_arrdep_time")));
            flight.set_real_dep_time(cursor.getLong(cursor.getColumnIndex("_real_dep_time")));
            flight.set_real_flight_date(cursor.getInt(cursor.getColumnIndex("_real_flight_date")));
            flight.set_register_num(cursor.getString(cursor.getColumnIndex("_register_num")));
            flight.set_route(cursor.getString(cursor.getColumnIndex("_route")));
            flight.set_route_type(cursor.getString(cursor.getColumnIndex("_route_type")));
            flight.set_scheme_arr_time(cursor.getLong(cursor.getColumnIndex("_scheme_arr_time")));
            flight.set_scheme_dep_time(cursor.getLong(cursor.getColumnIndex("_scheme_dep_time")));
            flight.set_segment(cursor.getString(cursor.getColumnIndex("_segment")));
            flight.set_segment_type(cursor.getString(cursor.getColumnIndex("_segment_type")));
            flight.set_slot(cursor.getString(cursor.getColumnIndex("_slot")));
            flight.set_state_reason(cursor.getString(cursor.getColumnIndex("_state_reason")));
            flight.set_state_reason_code(cursor.getString(cursor.getColumnIndex("_state_reason_code")));
            flight.set_task_class(cursor.getString(cursor.getColumnIndex("_task_class")));
            flight.set_task_name(cursor.getString(cursor.getColumnIndex("_task_name")));
            flight.set_update_id(cursor.getString(cursor.getColumnIndex("_update_id")));
            flight.set_update_time(cursor.getInt(cursor.getColumnIndex("_update_time")));

            serviceList.add(flight);
        }
        db.close();

        return serviceList;
    }

    /**
     * 根据三字码查找航空公司名称
     * @param code
     * @return
     */
    public List<String> codeToName(String code) {
        List<String> strList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(DBHelper.ACCA_CUSTOMER_CONVERT, new String[]{"CUSTOMER_NAME"}, "CUSTOMER_CODE=?", new String[]{code}, null, null, null);

        while (cursor.moveToNext()) {
            String string = cursor.getString(0);

            strList.add(string);
        }
        db.close();
        return strList;
    }

    /**
     * 根据三字码查找航班名称
     * @param threeCode
     * @return
     */
    public List<String> threeCodeToName(String threeCode) {
        List<String> strList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(DBHelper.ACCA_AIRPORT, new String[]{"NAME_ABBR"}, "CODE_THREE=?", new String[]{threeCode}, null, null, null);

        while (cursor.moveToNext()) {
            String string = cursor.getString(0);

            strList.add(string);
        }
        db.close();
        return strList;
    }

    /**
     * 根据服务seq往Acca_service表中插入签名数据
     *
     * @param seq  服务的唯一标识 seq
     * @param sing 服务的签名
     */
    public void UpdateServiceSing(String seq, String sing) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("_server_sign", sing);
        values.put("_signstate", "Y");
        values.put("_operationState", "U");
        int update = db.update(DBHelper.ACCA_SERVICE, values, "_service_seq=?", new String[]{seq});
        if (update > 0) {
            LogUtil.d("---update---", "修改成功");
        } else {
            LogUtil.d("---update---", "修改失败");
        }
        LogUtil.d("---update---", "执行了保存本地操作");
        db.close();
    }

    /**
     * 按实际起飞时间查找
     * @param stime
     * @param etime
     * @return
     */
    public List<FlightData.AccaFlightDateBean> getRealDepTime(String stime, String etime) {
        List<FlightData.AccaFlightDateBean> serviceList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from ACCA_FLIGHT inner join ACCA_USER_FLIGHT on ACCA_FLIGHT._flight_seq=ACCA_USER_FLIGHT._flight_id where ACCA_USER_FLIGHT._operationState is not ? and _real_dep_time Between ? and ?", new String[]{"D", stime, etime});

        while (cursor.moveToNext()) {
            FlightData.AccaFlightDateBean flight = new FlightData.AccaFlightDateBean();

            flight.set_alter_arr_time(cursor.getLong(cursor.getColumnIndex("_alter_arr_time")));
            flight.set_alter_dep_time(cursor.getLong(cursor.getColumnIndex("_alter_dep_time")));
            flight.set_arr_airport_four(cursor.getString(cursor.getColumnIndex("_arr_airport_four")));
            flight.set_arr_airport_three(cursor.getString(cursor.getColumnIndex("_arr_airport_three")));
            flight.set_code_three(cursor.getString(cursor.getColumnIndex("_code_three")));
            flight.set_company_code(cursor.getString(cursor.getColumnIndex("_company_code")));
            flight.set_create_id(cursor.getString(cursor.getColumnIndex("_create_id")));
            flight.set_create_time(cursor.getInt(cursor.getColumnIndex("_create_time")));
            flight.set_customer_code(cursor.getString(cursor.getColumnIndex("_customer_code")));
            flight.set_dep_airport_four(cursor.getString(cursor.getColumnIndex("_dep_airport_four")));
            flight.set_dep_airport_three(cursor.getString(cursor.getColumnIndex("_dep_airport_three")));
            flight.set_dep_arr_flag(cursor.getString(cursor.getColumnIndex("_dep_arr_flag")));
            flight.set_flight_num_two(cursor.getString(cursor.getColumnIndex("_flight_num_two")));
            flight.set_flight_seq(cursor.getString(cursor.getColumnIndex("_flight_seq")));
            flight.set_flight_state(cursor.getString(cursor.getColumnIndex("_flight_state")));
            flight.set_flight_state_code(cursor.getString(cursor.getColumnIndex("_flight_state_code")));
            flight.set_import_id(cursor.getString(cursor.getColumnIndex("_import_id")));
            flight.set_normal_state(cursor.getString(cursor.getColumnIndex("_normal_state")));
            flight.set_normal_state_code(cursor.getString(cursor.getColumnIndex("_normal_state_code")));
            flight.set_plan_flight_date(cursor.getInt(cursor.getColumnIndex("_plan_flight_date")));
            flight.set_real_arr_time(cursor.getLong(cursor.getColumnIndex("_real_arr_time")));
            flight.set_real_arrdep_time(cursor.getLong(cursor.getColumnIndex("_real_arrdep_time")));
            flight.set_real_dep_time(cursor.getLong(cursor.getColumnIndex("_real_dep_time")));
            flight.set_real_flight_date(cursor.getInt(cursor.getColumnIndex("_real_flight_date")));
            flight.set_register_num(cursor.getString(cursor.getColumnIndex("_register_num")));
            flight.set_route(cursor.getString(cursor.getColumnIndex("_route")));
            flight.set_route_type(cursor.getString(cursor.getColumnIndex("_route_type")));
            flight.set_scheme_arr_time(cursor.getLong(cursor.getColumnIndex("_scheme_arr_time")));
            flight.set_scheme_dep_time(cursor.getLong(cursor.getColumnIndex("_scheme_dep_time")));
            flight.set_segment(cursor.getString(cursor.getColumnIndex("_segment")));
            flight.set_segment_type(cursor.getString(cursor.getColumnIndex("_segment_type")));
            flight.set_slot(cursor.getString(cursor.getColumnIndex("_slot")));
            flight.set_state_reason(cursor.getString(cursor.getColumnIndex("_state_reason")));
            flight.set_state_reason_code(cursor.getString(cursor.getColumnIndex("_state_reason_code")));
            flight.set_task_class(cursor.getString(cursor.getColumnIndex("_task_class")));
            flight.set_task_name(cursor.getString(cursor.getColumnIndex("_task_name")));
            flight.set_update_id(cursor.getString(cursor.getColumnIndex("_update_id")));
            flight.set_update_time(cursor.getInt(cursor.getColumnIndex("_update_time")));

            serviceList.add(flight);
        }
        db.close();

        return serviceList;
    }

    /**
     * 按预计起飞时间查找
     * @param stime
     * @param etime
     * @return
     */
    public List<FlightData.AccaFlightDateBean> getAlterDepTime(String stime, String etime) {
        List<FlightData.AccaFlightDateBean> serviceList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from ACCA_FLIGHT inner join ACCA_USER_FLIGHT on ACCA_FLIGHT._flight_seq=ACCA_USER_FLIGHT._flight_id where ACCA_USER_FLIGHT._operationState is not ? and _alter_dep_time Between ? and ?", new String[]{"D", stime, etime});

        while (cursor.moveToNext()) {
            FlightData.AccaFlightDateBean flight = new FlightData.AccaFlightDateBean();

            flight.set_alter_arr_time(cursor.getLong(cursor.getColumnIndex("_alter_arr_time")));
            flight.set_alter_dep_time(cursor.getLong(cursor.getColumnIndex("_alter_dep_time")));
            flight.set_arr_airport_four(cursor.getString(cursor.getColumnIndex("_arr_airport_four")));
            flight.set_arr_airport_three(cursor.getString(cursor.getColumnIndex("_arr_airport_three")));
            flight.set_code_three(cursor.getString(cursor.getColumnIndex("_code_three")));
            flight.set_company_code(cursor.getString(cursor.getColumnIndex("_company_code")));
            flight.set_create_id(cursor.getString(cursor.getColumnIndex("_create_id")));
            flight.set_create_time(cursor.getInt(cursor.getColumnIndex("_create_time")));
            flight.set_customer_code(cursor.getString(cursor.getColumnIndex("_customer_code")));
            flight.set_dep_airport_four(cursor.getString(cursor.getColumnIndex("_dep_airport_four")));
            flight.set_dep_airport_three(cursor.getString(cursor.getColumnIndex("_dep_airport_three")));
            flight.set_dep_arr_flag(cursor.getString(cursor.getColumnIndex("_dep_arr_flag")));
            flight.set_flight_num_two(cursor.getString(cursor.getColumnIndex("_flight_num_two")));
            flight.set_flight_seq(cursor.getString(cursor.getColumnIndex("_flight_seq")));
            flight.set_flight_state(cursor.getString(cursor.getColumnIndex("_flight_state")));
            flight.set_flight_state_code(cursor.getString(cursor.getColumnIndex("_flight_state_code")));
            flight.set_import_id(cursor.getString(cursor.getColumnIndex("_import_id")));
            flight.set_normal_state(cursor.getString(cursor.getColumnIndex("_normal_state")));
            flight.set_normal_state_code(cursor.getString(cursor.getColumnIndex("_normal_state_code")));
            flight.set_plan_flight_date(cursor.getInt(cursor.getColumnIndex("_plan_flight_date")));
            flight.set_real_arr_time(cursor.getLong(cursor.getColumnIndex("_real_arr_time")));
            flight.set_real_arrdep_time(cursor.getLong(cursor.getColumnIndex("_real_arrdep_time")));
            flight.set_real_dep_time(cursor.getLong(cursor.getColumnIndex("_real_dep_time")));
            flight.set_real_flight_date(cursor.getInt(cursor.getColumnIndex("_real_flight_date")));
            flight.set_register_num(cursor.getString(cursor.getColumnIndex("_register_num")));
            flight.set_route(cursor.getString(cursor.getColumnIndex("_route")));
            flight.set_route_type(cursor.getString(cursor.getColumnIndex("_route_type")));
            flight.set_scheme_arr_time(cursor.getLong(cursor.getColumnIndex("_scheme_arr_time")));
            flight.set_scheme_dep_time(cursor.getLong(cursor.getColumnIndex("_scheme_dep_time")));
            flight.set_segment(cursor.getString(cursor.getColumnIndex("_segment")));
            flight.set_segment_type(cursor.getString(cursor.getColumnIndex("_segment_type")));
            flight.set_slot(cursor.getString(cursor.getColumnIndex("_slot")));
            flight.set_state_reason(cursor.getString(cursor.getColumnIndex("_state_reason")));
            flight.set_state_reason_code(cursor.getString(cursor.getColumnIndex("_state_reason_code")));
            flight.set_task_class(cursor.getString(cursor.getColumnIndex("_task_class")));
            flight.set_task_name(cursor.getString(cursor.getColumnIndex("_task_name")));
            flight.set_update_id(cursor.getString(cursor.getColumnIndex("_update_id")));
            flight.set_update_time(cursor.getInt(cursor.getColumnIndex("_update_time")));

            serviceList.add(flight);
        }
        db.close();

        return serviceList;
    }

    /**
     * 按计划起飞时间查找
     * @param stime
     * @param etime
     * @return
     */
    public List<FlightData.AccaFlightDateBean> getSchemeDepTime(String stime, String etime) {
        List<FlightData.AccaFlightDateBean> serviceList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from ACCA_FLIGHT inner join ACCA_USER_FLIGHT on ACCA_FLIGHT._flight_seq=ACCA_USER_FLIGHT._flight_id where ACCA_USER_FLIGHT._operationState is not ? and _scheme_dep_time Between ? and ?", new String[]{"D", stime, etime});

        while (cursor.moveToNext()) {
            FlightData.AccaFlightDateBean flight = new FlightData.AccaFlightDateBean();

            flight.set_alter_arr_time(cursor.getLong(cursor.getColumnIndex("_alter_arr_time")));
            flight.set_alter_dep_time(cursor.getLong(cursor.getColumnIndex("_alter_dep_time")));
            flight.set_arr_airport_four(cursor.getString(cursor.getColumnIndex("_arr_airport_four")));
            flight.set_arr_airport_three(cursor.getString(cursor.getColumnIndex("_arr_airport_three")));
            flight.set_code_three(cursor.getString(cursor.getColumnIndex("_code_three")));
            flight.set_company_code(cursor.getString(cursor.getColumnIndex("_company_code")));
            flight.set_create_id(cursor.getString(cursor.getColumnIndex("_create_id")));
            flight.set_create_time(cursor.getInt(cursor.getColumnIndex("_create_time")));
            flight.set_customer_code(cursor.getString(cursor.getColumnIndex("_customer_code")));
            flight.set_dep_airport_four(cursor.getString(cursor.getColumnIndex("_dep_airport_four")));
            flight.set_dep_airport_three(cursor.getString(cursor.getColumnIndex("_dep_airport_three")));
            flight.set_dep_arr_flag(cursor.getString(cursor.getColumnIndex("_dep_arr_flag")));
            flight.set_flight_num_two(cursor.getString(cursor.getColumnIndex("_flight_num_two")));
            flight.set_flight_seq(cursor.getString(cursor.getColumnIndex("_flight_seq")));
            flight.set_flight_state(cursor.getString(cursor.getColumnIndex("_flight_state")));
            flight.set_flight_state_code(cursor.getString(cursor.getColumnIndex("_flight_state_code")));
            flight.set_import_id(cursor.getString(cursor.getColumnIndex("_import_id")));
            flight.set_normal_state(cursor.getString(cursor.getColumnIndex("_normal_state")));
            flight.set_normal_state_code(cursor.getString(cursor.getColumnIndex("_normal_state_code")));
            flight.set_plan_flight_date(cursor.getInt(cursor.getColumnIndex("_plan_flight_date")));
            flight.set_real_arr_time(cursor.getLong(cursor.getColumnIndex("_real_arr_time")));
            flight.set_real_arrdep_time(cursor.getLong(cursor.getColumnIndex("_real_arrdep_time")));
            flight.set_real_dep_time(cursor.getLong(cursor.getColumnIndex("_real_dep_time")));
            flight.set_real_flight_date(cursor.getInt(cursor.getColumnIndex("_real_flight_date")));
            flight.set_register_num(cursor.getString(cursor.getColumnIndex("_register_num")));
            flight.set_route(cursor.getString(cursor.getColumnIndex("_route")));
            flight.set_route_type(cursor.getString(cursor.getColumnIndex("_route_type")));
            flight.set_scheme_arr_time(cursor.getLong(cursor.getColumnIndex("_scheme_arr_time")));
            flight.set_scheme_dep_time(cursor.getLong(cursor.getColumnIndex("_scheme_dep_time")));
            flight.set_segment(cursor.getString(cursor.getColumnIndex("_segment")));
            flight.set_segment_type(cursor.getString(cursor.getColumnIndex("_segment_type")));
            flight.set_slot(cursor.getString(cursor.getColumnIndex("_slot")));
            flight.set_state_reason(cursor.getString(cursor.getColumnIndex("_state_reason")));
            flight.set_state_reason_code(cursor.getString(cursor.getColumnIndex("_state_reason_code")));
            flight.set_task_class(cursor.getString(cursor.getColumnIndex("_task_class")));
            flight.set_task_name(cursor.getString(cursor.getColumnIndex("_task_name")));
            flight.set_update_id(cursor.getString(cursor.getColumnIndex("_update_id")));
            flight.set_update_time(cursor.getInt(cursor.getColumnIndex("_update_time")));

            serviceList.add(flight);
        }
        db.close();

        return serviceList;
    }

    /**
     * 按实际降落时间查找
     * @param stime
     * @param etime
     * @return
     */
    public List<FlightData.AccaFlightDateBean> getRealArrTime(String stime, String etime) {
        List<FlightData.AccaFlightDateBean> serviceList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from ACCA_FLIGHT inner join ACCA_USER_FLIGHT on ACCA_FLIGHT._flight_seq=ACCA_USER_FLIGHT._flight_id where ACCA_USER_FLIGHT._operationState is not ? and _real_arr_time Between ? and ?", new String[]{"D", stime, etime});

        while (cursor.moveToNext()) {
            FlightData.AccaFlightDateBean flight = new FlightData.AccaFlightDateBean();

            flight.set_alter_arr_time(cursor.getLong(cursor.getColumnIndex("_alter_arr_time")));
            flight.set_alter_dep_time(cursor.getLong(cursor.getColumnIndex("_alter_dep_time")));
            flight.set_arr_airport_four(cursor.getString(cursor.getColumnIndex("_arr_airport_four")));
            flight.set_arr_airport_three(cursor.getString(cursor.getColumnIndex("_arr_airport_three")));
            flight.set_code_three(cursor.getString(cursor.getColumnIndex("_code_three")));
            flight.set_company_code(cursor.getString(cursor.getColumnIndex("_company_code")));
            flight.set_create_id(cursor.getString(cursor.getColumnIndex("_create_id")));
            flight.set_create_time(cursor.getInt(cursor.getColumnIndex("_create_time")));
            flight.set_customer_code(cursor.getString(cursor.getColumnIndex("_customer_code")));
            flight.set_dep_airport_four(cursor.getString(cursor.getColumnIndex("_dep_airport_four")));
            flight.set_dep_airport_three(cursor.getString(cursor.getColumnIndex("_dep_airport_three")));
            flight.set_dep_arr_flag(cursor.getString(cursor.getColumnIndex("_dep_arr_flag")));
            flight.set_flight_num_two(cursor.getString(cursor.getColumnIndex("_flight_num_two")));
            flight.set_flight_seq(cursor.getString(cursor.getColumnIndex("_flight_seq")));
            flight.set_flight_state(cursor.getString(cursor.getColumnIndex("_flight_state")));
            flight.set_flight_state_code(cursor.getString(cursor.getColumnIndex("_flight_state_code")));
            flight.set_import_id(cursor.getString(cursor.getColumnIndex("_import_id")));
            flight.set_normal_state(cursor.getString(cursor.getColumnIndex("_normal_state")));
            flight.set_normal_state_code(cursor.getString(cursor.getColumnIndex("_normal_state_code")));
            flight.set_plan_flight_date(cursor.getInt(cursor.getColumnIndex("_plan_flight_date")));
            flight.set_real_arr_time(cursor.getLong(cursor.getColumnIndex("_real_arr_time")));
            flight.set_real_arrdep_time(cursor.getLong(cursor.getColumnIndex("_real_arrdep_time")));
            flight.set_real_dep_time(cursor.getLong(cursor.getColumnIndex("_real_dep_time")));
            flight.set_real_flight_date(cursor.getInt(cursor.getColumnIndex("_real_flight_date")));
            flight.set_register_num(cursor.getString(cursor.getColumnIndex("_register_num")));
            flight.set_route(cursor.getString(cursor.getColumnIndex("_route")));
            flight.set_route_type(cursor.getString(cursor.getColumnIndex("_route_type")));
            flight.set_scheme_arr_time(cursor.getLong(cursor.getColumnIndex("_scheme_arr_time")));
            flight.set_scheme_dep_time(cursor.getLong(cursor.getColumnIndex("_scheme_dep_time")));
            flight.set_segment(cursor.getString(cursor.getColumnIndex("_segment")));
            flight.set_segment_type(cursor.getString(cursor.getColumnIndex("_segment_type")));
            flight.set_slot(cursor.getString(cursor.getColumnIndex("_slot")));
            flight.set_state_reason(cursor.getString(cursor.getColumnIndex("_state_reason")));
            flight.set_state_reason_code(cursor.getString(cursor.getColumnIndex("_state_reason_code")));
            flight.set_task_class(cursor.getString(cursor.getColumnIndex("_task_class")));
            flight.set_task_name(cursor.getString(cursor.getColumnIndex("_task_name")));
            flight.set_update_id(cursor.getString(cursor.getColumnIndex("_update_id")));
            flight.set_update_time(cursor.getInt(cursor.getColumnIndex("_update_time")));

            serviceList.add(flight);
        }
        db.close();

        return serviceList;
    }

    /**
     * 按预计降落时间查找
     * @param stime
     * @param etime
     * @return
     */
    public List<FlightData.AccaFlightDateBean> getAlterArrTime(String stime, String etime) {
        List<FlightData.AccaFlightDateBean> serviceList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from ACCA_FLIGHT inner join ACCA_USER_FLIGHT on ACCA_FLIGHT._flight_seq=ACCA_USER_FLIGHT._flight_id where ACCA_USER_FLIGHT._operationState is not ? and _alter_arr_time Between ? and ?", new String[]{"D", stime, etime});

        while (cursor.moveToNext()) {
            FlightData.AccaFlightDateBean flight = new FlightData.AccaFlightDateBean();

            flight.set_alter_arr_time(cursor.getLong(cursor.getColumnIndex("_alter_arr_time")));
            flight.set_alter_dep_time(cursor.getLong(cursor.getColumnIndex("_alter_dep_time")));
            flight.set_arr_airport_four(cursor.getString(cursor.getColumnIndex("_arr_airport_four")));
            flight.set_arr_airport_three(cursor.getString(cursor.getColumnIndex("_arr_airport_three")));
            flight.set_code_three(cursor.getString(cursor.getColumnIndex("_code_three")));
            flight.set_company_code(cursor.getString(cursor.getColumnIndex("_company_code")));
            flight.set_create_id(cursor.getString(cursor.getColumnIndex("_create_id")));
            flight.set_create_time(cursor.getInt(cursor.getColumnIndex("_create_time")));
            flight.set_customer_code(cursor.getString(cursor.getColumnIndex("_customer_code")));
            flight.set_dep_airport_four(cursor.getString(cursor.getColumnIndex("_dep_airport_four")));
            flight.set_dep_airport_three(cursor.getString(cursor.getColumnIndex("_dep_airport_three")));
            flight.set_dep_arr_flag(cursor.getString(cursor.getColumnIndex("_dep_arr_flag")));
            flight.set_flight_num_two(cursor.getString(cursor.getColumnIndex("_flight_num_two")));
            flight.set_flight_seq(cursor.getString(cursor.getColumnIndex("_flight_seq")));
            flight.set_flight_state(cursor.getString(cursor.getColumnIndex("_flight_state")));
            flight.set_flight_state_code(cursor.getString(cursor.getColumnIndex("_flight_state_code")));
            flight.set_import_id(cursor.getString(cursor.getColumnIndex("_import_id")));
            flight.set_normal_state(cursor.getString(cursor.getColumnIndex("_normal_state")));
            flight.set_normal_state_code(cursor.getString(cursor.getColumnIndex("_normal_state_code")));
            flight.set_plan_flight_date(cursor.getInt(cursor.getColumnIndex("_plan_flight_date")));
            flight.set_real_arr_time(cursor.getLong(cursor.getColumnIndex("_real_arr_time")));
            flight.set_real_arrdep_time(cursor.getLong(cursor.getColumnIndex("_real_arrdep_time")));
            flight.set_real_dep_time(cursor.getLong(cursor.getColumnIndex("_real_dep_time")));
            flight.set_real_flight_date(cursor.getInt(cursor.getColumnIndex("_real_flight_date")));
            flight.set_register_num(cursor.getString(cursor.getColumnIndex("_register_num")));
            flight.set_route(cursor.getString(cursor.getColumnIndex("_route")));
            flight.set_route_type(cursor.getString(cursor.getColumnIndex("_route_type")));
            flight.set_scheme_arr_time(cursor.getLong(cursor.getColumnIndex("_scheme_arr_time")));
            flight.set_scheme_dep_time(cursor.getLong(cursor.getColumnIndex("_scheme_dep_time")));
            flight.set_segment(cursor.getString(cursor.getColumnIndex("_segment")));
            flight.set_segment_type(cursor.getString(cursor.getColumnIndex("_segment_type")));
            flight.set_slot(cursor.getString(cursor.getColumnIndex("_slot")));
            flight.set_state_reason(cursor.getString(cursor.getColumnIndex("_state_reason")));
            flight.set_state_reason_code(cursor.getString(cursor.getColumnIndex("_state_reason_code")));
            flight.set_task_class(cursor.getString(cursor.getColumnIndex("_task_class")));
            flight.set_task_name(cursor.getString(cursor.getColumnIndex("_task_name")));
            flight.set_update_id(cursor.getString(cursor.getColumnIndex("_update_id")));
            flight.set_update_time(cursor.getInt(cursor.getColumnIndex("_update_time")));

            serviceList.add(flight);
        }
        db.close();

        return serviceList;
    }

    /**
     * 按计划降落时间查找
     * @param stime
     * @param etime
     * @return
     */
    public List<FlightData.AccaFlightDateBean> getSchemeArrTime(String stime, String etime) {
        List<FlightData.AccaFlightDateBean> serviceList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from ACCA_FLIGHT inner join ACCA_USER_FLIGHT on ACCA_FLIGHT._flight_seq=ACCA_USER_FLIGHT._flight_id where ACCA_USER_FLIGHT._operationState is not ? and _scheme_arr_time Between ? and ?", new String[]{"D", stime, etime});

        while (cursor.moveToNext()) {
            FlightData.AccaFlightDateBean flight = new FlightData.AccaFlightDateBean();

            flight.set_alter_arr_time(cursor.getLong(cursor.getColumnIndex("_alter_arr_time")));
            flight.set_alter_dep_time(cursor.getLong(cursor.getColumnIndex("_alter_dep_time")));
            flight.set_arr_airport_four(cursor.getString(cursor.getColumnIndex("_arr_airport_four")));
            flight.set_arr_airport_three(cursor.getString(cursor.getColumnIndex("_arr_airport_three")));
            flight.set_code_three(cursor.getString(cursor.getColumnIndex("_code_three")));
            flight.set_company_code(cursor.getString(cursor.getColumnIndex("_company_code")));
            flight.set_create_id(cursor.getString(cursor.getColumnIndex("_create_id")));
            flight.set_create_time(cursor.getInt(cursor.getColumnIndex("_create_time")));
            flight.set_customer_code(cursor.getString(cursor.getColumnIndex("_customer_code")));
            flight.set_dep_airport_four(cursor.getString(cursor.getColumnIndex("_dep_airport_four")));
            flight.set_dep_airport_three(cursor.getString(cursor.getColumnIndex("_dep_airport_three")));
            flight.set_dep_arr_flag(cursor.getString(cursor.getColumnIndex("_dep_arr_flag")));
            flight.set_flight_num_two(cursor.getString(cursor.getColumnIndex("_flight_num_two")));
            flight.set_flight_seq(cursor.getString(cursor.getColumnIndex("_flight_seq")));
            flight.set_flight_state(cursor.getString(cursor.getColumnIndex("_flight_state")));
            flight.set_flight_state_code(cursor.getString(cursor.getColumnIndex("_flight_state_code")));
            flight.set_import_id(cursor.getString(cursor.getColumnIndex("_import_id")));
            flight.set_normal_state(cursor.getString(cursor.getColumnIndex("_normal_state")));
            flight.set_normal_state_code(cursor.getString(cursor.getColumnIndex("_normal_state_code")));
            flight.set_plan_flight_date(cursor.getInt(cursor.getColumnIndex("_plan_flight_date")));
            flight.set_real_arr_time(cursor.getLong(cursor.getColumnIndex("_real_arr_time")));
            flight.set_real_arrdep_time(cursor.getLong(cursor.getColumnIndex("_real_arrdep_time")));
            flight.set_real_dep_time(cursor.getLong(cursor.getColumnIndex("_real_dep_time")));
            flight.set_real_flight_date(cursor.getInt(cursor.getColumnIndex("_real_flight_date")));
            flight.set_register_num(cursor.getString(cursor.getColumnIndex("_register_num")));
            flight.set_route(cursor.getString(cursor.getColumnIndex("_route")));
            flight.set_route_type(cursor.getString(cursor.getColumnIndex("_route_type")));
            flight.set_scheme_arr_time(cursor.getLong(cursor.getColumnIndex("_scheme_arr_time")));
            flight.set_scheme_dep_time(cursor.getLong(cursor.getColumnIndex("_scheme_dep_time")));
            flight.set_segment(cursor.getString(cursor.getColumnIndex("_segment")));
            flight.set_segment_type(cursor.getString(cursor.getColumnIndex("_segment_type")));
            flight.set_slot(cursor.getString(cursor.getColumnIndex("_slot")));
            flight.set_state_reason(cursor.getString(cursor.getColumnIndex("_state_reason")));
            flight.set_state_reason_code(cursor.getString(cursor.getColumnIndex("_state_reason_code")));
            flight.set_task_class(cursor.getString(cursor.getColumnIndex("_task_class")));
            flight.set_task_name(cursor.getString(cursor.getColumnIndex("_task_name")));
            flight.set_update_id(cursor.getString(cursor.getColumnIndex("_update_id")));
            flight.set_update_time(cursor.getInt(cursor.getColumnIndex("_update_time")));

            serviceList.add(flight);
        }
        db.close();

        return serviceList;
    }


    /**
     * 查询服务签名，根据航班fligth_seq查询，并显示签名预览
     *
     * @param flightSeq 航班的唯一标识
     * @return list集合 包涵所有服务信息
     */
    public List<EyeInfo.FlightDataBean.SignInfoBean> selectServiceSign(String flightSeq) {
        List<EyeInfo.FlightDataBean.SignInfoBean> signList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select  a._item_name ,a._item_value ,a._end_time,a._server_sign,a._service_seq,a._start_time  from ACCA_SERVICE as a \n" +
                "INNER JOIN ACCA_FLIGHT as b ON b._flight_seq = a._link_flight_id where a._link_flight_id = ? and a._operationState is not ? order by a._update_time desc ", new String[]{flightSeq,"D"});
        while (cursor.moveToNext()) {
            EyeInfo.FlightDataBean.SignInfoBean signInfoBean = new EyeInfo.FlightDataBean.SignInfoBean();
            //判断，如果签名字段为空的， 就不把这条服务信息添加的list集合中
            //2017/08/16,今天经理需求改动，if判断去掉
//            if (cursor.getString(cursor.getColumnIndex("_server_sign")) != null) {
                signInfoBean.setEnd_Time(cursor.getString(cursor.getColumnIndex("_end_time")));
                signInfoBean.setItem_Name(cursor.getString(cursor.getColumnIndex("_item_name")));
                signInfoBean.setItem_Value(cursor.getString(cursor.getColumnIndex("_item_value")));
                signInfoBean.setServer_Sign(cursor.getString(cursor.getColumnIndex("_server_sign")));
                signInfoBean.setService_Seq(cursor.getString(cursor.getColumnIndex("_service_seq")));
                signInfoBean.setStart_Time(cursor.getString(cursor.getColumnIndex("_start_time")));
                signList.add(signInfoBean);
//            }
        }
        db.close();
        return signList;
    }

    /**
     * 查询服务是否签名，根据航班fligth_seq查询，并显示没有签名的项目在页面
     *
     * @param flightSeq 航班的唯一标识
     * @return list集合 包涵所以服务信息
     */
    public List<AirInfo.DataBean.AccaServiceBean> selectServiceYorN(String flightSeq) {
        List<AirInfo.DataBean.AccaServiceBean> ynList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select  a._attach,a._create_time,a._end_time,a._item_name ,a._item_value ,a._service_seq,a._signstate,a._start_time,a._update_time  from ACCA_SERVICE as a \n" +
                "INNER JOIN ACCA_FLIGHT as b ON b._flight_seq = a._link_flight_id where a._link_flight_id = ?", new String[]{flightSeq});
        while (cursor.moveToNext()) {
            AirInfo.DataBean.AccaServiceBean accaServiceBean = new AirInfo.DataBean.AccaServiceBean();
            //判断，如果签名字段为空的， 就不把这条服务信息添加的list集合中
            accaServiceBean.setAttach(cursor.getString(cursor.getColumnIndex("_attach")));
            accaServiceBean.setCreate_time(cursor.getString(cursor.getColumnIndex("_create_time")));
            accaServiceBean.setEnd_time(cursor.getString(cursor.getColumnIndex("_end_time")));
            accaServiceBean.setItem_name(cursor.getString(cursor.getColumnIndex("_item_name")));
            accaServiceBean.setItem_value(cursor.getString(cursor.getColumnIndex("_item_value")));
            accaServiceBean.setService_seq(cursor.getString(cursor.getColumnIndex("_service_seq")));
            accaServiceBean.setSignstate(cursor.getString(cursor.getColumnIndex("_signstate")));
            accaServiceBean.setStart_time(cursor.getString(cursor.getColumnIndex("_start_time")));
            accaServiceBean.setUpdate_time(cursor.getString(cursor.getColumnIndex("_update_time")));
            ynList.add(accaServiceBean);

        }
        db.close();
        return ynList;
    }
    /**
     * 查询服务是否签名，根据航班fligth_seq查询，并显示没有签名的项目在页面
     *
     * @param flightSeq 航班的唯一标识
     * @return list集合 包涵所以服务信息
     */
    public List<FlightData.AccaServiceDateBean> selectServiceNorY(String flightSeq) {
        List<FlightData.AccaServiceDateBean> ynList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select  a._attach,a._create_time,a._end_time,a._item_name ,a._item_value ,a._service_seq,a._signstate,a._start_time,a._update_time  from ACCA_SERVICE as a \n" +
                "INNER JOIN ACCA_FLIGHT as b ON b._flight_seq = a._link_flight_id where a._link_flight_id = ?", new String[]{flightSeq});
        while (cursor.moveToNext()) {
            FlightData.AccaServiceDateBean accaServiceBean = new FlightData.AccaServiceDateBean();
            //判断，如果签名字段为空的， 就不把这条服务信息添加的list集合中
            accaServiceBean.set_attach(cursor.getString(cursor.getColumnIndex("_attach")));
            accaServiceBean.set_create_time(cursor.getLong(cursor.getColumnIndex("_create_time")));
            accaServiceBean.set_end_time(cursor.getLong(cursor.getColumnIndex("_end_time")));
            accaServiceBean.set_item_name(cursor.getString(cursor.getColumnIndex("_item_name")));
            accaServiceBean.set_item_value(cursor.getString(cursor.getColumnIndex("_item_value")));
            accaServiceBean.set_service_seq(cursor.getString(cursor.getColumnIndex("_service_seq")));
            accaServiceBean.set_signstate(cursor.getString(cursor.getColumnIndex("_signstate")));
            accaServiceBean.set_start_time(cursor.getLong(cursor.getColumnIndex("_start_time")));
            accaServiceBean.set_update_time(cursor.getLong(cursor.getColumnIndex("_update_time")));
            ynList.add(accaServiceBean);

        }
        db.close();
        return ynList;
    }

}