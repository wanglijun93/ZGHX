package com.travelsky.airportapp.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by iwangtianyi on 2016/11/1.
 */
public class FlightData implements Serializable{

    /**
     * ResCode : 1
     * ResMsg : 正确
     */

    private int ResCode;
    private String ResMsg;
    /**
     * _alter_arr_time : null
     * _alter_dep_time : 20161101011100
     * _arr_airport_four : VTBD
     * _arr_airport_three : DMK
     * _code_three : CSX
     * _company_code : CSX
     * _create_id : AppServer
     * _create_time : 20161101
     * _customer_code : OZ
     * _dep_airport_four : ZGHA
     * _dep_airport_three : CSX
     * _dep_arr_flag : D
     * _flight_num_two : OZ621
     * _flight_seq : e0e01b13-f985-4b6d-aa7d-24bd680eeb97
     * _flight_state : 延误
     * _flight_state_code : DLY
     * _import_id : 3507001
     * _normal_state : 起飞
     * _normal_state_code : DEP
     * _plan_flight_date : 20161101
     * _real_arr_time : null
     * _real_arrdep_time : 20161101011100
     * _real_dep_time : 20161101011000
     * _real_flight_date : 20161101
     * _register_num : HSBKD
     * _route : CSX-DMK
     * _route_type : I
     * _scheme_arr_time : 20161101011100
     * _scheme_dep_time : 20161101011100
     * _segment : CSX-DMK
     * _segment_type : I
     * _slot : 233
     * _state_reason : 飞机调配
     * _state_reason_code : 10108
     * _task_class : ZB
     * _task_name : WZ
     * _update_id : LG
     * _update_time : 20161101
     */

    private List<AccaFlightDateBean> accaFlightDate;
    /**
     * _attach :
     * _client_sign : null
     * _company_code : CSX
     * _create_id : CSX0002
     * _create_time : 20161102103451
     * _end_time : 201611021034
     * _invoice_no :
     * _item_name : 皮带传送车
     * _item_value : 2
     * _link_flight_id : a211a65d-a5e1-48bc-b37e-8c8ae438a17f
     * _server_sign : null
     * _service_seq : b5741905-7713-4abe-9aae-369aa8f72aca
     * _service_type : 特车
     * _signstate : N
     * _start_time : 201611021034
     * _update_id : CSX0002
     * _update_time : 20161102103451
     * _used_time : 00:00:00
     */

    private List<AccaServiceDateBean> accaServiceDate;
    /**
     * _company_code : CSX
     * _create_id : CSX0001
     * _create_time : 20161101144913
     * _flight_id : e0e01b13-f985-4b6d-aa7d-24bd680eeb97
     * _sequence : 8316ec1d-e8dc-4317-b79f-52e6daa0e922
     * _update_id : CSX0001
     * _update_time : 20161101144913
     * _user_id : CSX0001
     */

    private List<AccauserFlightDateBean> accauserFlightDate;

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

    public List<AccaFlightDateBean> getAccaFlightDate() {
        return accaFlightDate;
    }

    public void setAccaFlightDate(List<AccaFlightDateBean> accaFlightDate) {
        this.accaFlightDate = accaFlightDate;
    }

    public List<AccaServiceDateBean> getAccaServiceDate() {
        return accaServiceDate;
    }

    public void setAccaServiceDate(List<AccaServiceDateBean> accaServiceDate) {
        this.accaServiceDate = accaServiceDate;
    }

    public List<AccauserFlightDateBean> getAccauserFlightDate() {
        return accauserFlightDate;
    }

    public void setAccauserFlightDate(List<AccauserFlightDateBean> accauserFlightDate) {
        this.accauserFlightDate = accauserFlightDate;
    }

    public static class AccaFlightDateBean implements Serializable{
        private long _alter_arr_time;
        private long _alter_dep_time;
        private String _arr_airport_four;
        private String _arr_airport_three;
        private String _code_three;
        private String _company_code;
        private String _create_id;
        private int _create_time;
        private String _customer_code;
        private String _dep_airport_four;
        private String _dep_airport_three;
        private String _dep_arr_flag;
        private String _flight_num_two;
        private String _flight_seq;
        private String _flight_state;
        private String _flight_state_code;
        private String _import_id;
        private String _normal_state;
        private String _normal_state_code;
        private int _plan_flight_date;
        private long _real_arr_time;
        private long _real_arrdep_time;
        private long _real_dep_time;
        private int _real_flight_date;
        private String _register_num;
        private String _route;
        private String _route_type;
        private long _scheme_arr_time;
        private long _scheme_dep_time;
        private String _segment;
        private String _segment_type;
        private String _slot;
        private String _state_reason;
        private String _state_reason_code;
        private String _task_class;
        private String _task_name;
        private String _update_id;
        private int _update_time;

        public long get_alter_arr_time() {
            return _alter_arr_time;
        }

        public void set_alter_arr_time(long _alter_arr_time) {
            this._alter_arr_time = _alter_arr_time;
        }

        public long get_alter_dep_time() {
            return _alter_dep_time;
        }

        public void set_alter_dep_time(long _alter_dep_time) {
            this._alter_dep_time = _alter_dep_time;
        }

        public String get_arr_airport_four() {
            return _arr_airport_four;
        }

        public void set_arr_airport_four(String _arr_airport_four) {
            this._arr_airport_four = _arr_airport_four;
        }

        public String get_arr_airport_three() {
            return _arr_airport_three;
        }

        public void set_arr_airport_three(String _arr_airport_three) {
            this._arr_airport_three = _arr_airport_three;
        }

        public String get_code_three() {
            return _code_three;
        }

        public void set_code_three(String _code_three) {
            this._code_three = _code_three;
        }

        public String get_company_code() {
            return _company_code;
        }

        public void set_company_code(String _company_code) {
            this._company_code = _company_code;
        }

        public String get_create_id() {
            return _create_id;
        }

        public void set_create_id(String _create_id) {
            this._create_id = _create_id;
        }

        public int get_create_time() {
            return _create_time;
        }

        public void set_create_time(int _create_time) {
            this._create_time = _create_time;
        }

        public String get_customer_code() {
            return _customer_code;
        }

        public void set_customer_code(String _customer_code) {
            this._customer_code = _customer_code;
        }

        public String get_dep_airport_four() {
            return _dep_airport_four;
        }

        public void set_dep_airport_four(String _dep_airport_four) {
            this._dep_airport_four = _dep_airport_four;
        }

        public String get_dep_airport_three() {
            return _dep_airport_three;
        }

        public void set_dep_airport_three(String _dep_airport_three) {
            this._dep_airport_three = _dep_airport_three;
        }

        public String get_dep_arr_flag() {
            return _dep_arr_flag;
        }

        public void set_dep_arr_flag(String _dep_arr_flag) {
            this._dep_arr_flag = _dep_arr_flag;
        }

        public String get_flight_num_two() {
            return _flight_num_two;
        }

        public void set_flight_num_two(String _flight_num_two) {
            this._flight_num_two = _flight_num_two;
        }

        public String get_flight_seq() {
            return _flight_seq;
        }

        public void set_flight_seq(String _flight_seq) {
            this._flight_seq = _flight_seq;
        }

        public String get_flight_state() {
            return _flight_state;
        }

        public void set_flight_state(String _flight_state) {
            this._flight_state = _flight_state;
        }

        public String get_flight_state_code() {
            return _flight_state_code;
        }

        public void set_flight_state_code(String _flight_state_code) {
            this._flight_state_code = _flight_state_code;
        }

        public String get_import_id() {
            return _import_id;
        }

        public void set_import_id(String _import_id) {
            this._import_id = _import_id;
        }

        public String get_normal_state() {
            return _normal_state;
        }

        public void set_normal_state(String _normal_state) {
            this._normal_state = _normal_state;
        }

        public String get_normal_state_code() {
            return _normal_state_code;
        }

        public void set_normal_state_code(String _normal_state_code) {
            this._normal_state_code = _normal_state_code;
        }

        public int get_plan_flight_date() {
            return _plan_flight_date;
        }

        public void set_plan_flight_date(int _plan_flight_date) {
            this._plan_flight_date = _plan_flight_date;
        }

        public long get_real_arr_time() {
            return _real_arr_time;
        }

        public void set_real_arr_time(long _real_arr_time) {
            this._real_arr_time = _real_arr_time;
        }

        public long get_real_arrdep_time() {
            return _real_arrdep_time;
        }

        public void set_real_arrdep_time(long _real_arrdep_time) {
            this._real_arrdep_time = _real_arrdep_time;
        }

        public long get_real_dep_time() {
            return _real_dep_time;
        }

        public void set_real_dep_time(long _real_dep_time) {
            this._real_dep_time = _real_dep_time;
        }

        public int get_real_flight_date() {
            return _real_flight_date;
        }

        public void set_real_flight_date(int _real_flight_date) {
            this._real_flight_date = _real_flight_date;
        }

        public String get_register_num() {
            return _register_num;
        }

        public void set_register_num(String _register_num) {
            this._register_num = _register_num;
        }

        public String get_route() {
            return _route;
        }

        public void set_route(String _route) {
            this._route = _route;
        }

        public String get_route_type() {
            return _route_type;
        }

        public void set_route_type(String _route_type) {
            this._route_type = _route_type;
        }

        public long get_scheme_arr_time() {
            return _scheme_arr_time;
        }

        public void set_scheme_arr_time(long _scheme_arr_time) {
            this._scheme_arr_time = _scheme_arr_time;
        }

        public long get_scheme_dep_time() {
            return _scheme_dep_time;
        }

        public void set_scheme_dep_time(long _scheme_dep_time) {
            this._scheme_dep_time = _scheme_dep_time;
        }

        public String get_segment() {
            return _segment;
        }

        public void set_segment(String _segment) {
            this._segment = _segment;
        }

        public String get_segment_type() {
            return _segment_type;
        }

        public void set_segment_type(String _segment_type) {
            this._segment_type = _segment_type;
        }

        public String get_slot() {
            return _slot;
        }

        public void set_slot(String _slot) {
            this._slot = _slot;
        }

        public String get_state_reason() {
            return _state_reason;
        }

        public void set_state_reason(String _state_reason) {
            this._state_reason = _state_reason;
        }

        public String get_state_reason_code() {
            return _state_reason_code;
        }

        public void set_state_reason_code(String _state_reason_code) {
            this._state_reason_code = _state_reason_code;
        }

        public String get_task_class() {
            return _task_class;
        }

        public void set_task_class(String _task_class) {
            this._task_class = _task_class;
        }

        public String get_task_name() {
            return _task_name;
        }

        public void set_task_name(String _task_name) {
            this._task_name = _task_name;
        }

        public String get_update_id() {
            return _update_id;
        }

        public void set_update_id(String _update_id) {
            this._update_id = _update_id;
        }

        public int get_update_time() {
            return _update_time;
        }

        public void set_update_time(int _update_time) {
            this._update_time = _update_time;
        }
    }

    public static class AccaServiceDateBean  implements Serializable{
        private String _attach;
        private String _client_sign;
        private String _company_code;
        private String _create_id;
        private long _create_time;
        private long _end_time;
        private String _invoice_no;
        private String _item_name;
        private String _item_value;
        private String _link_flight_id;
        private byte[] _server_sign;
        private String _service_seq;
        private String _service_type;
        private String _signstate;
        private long _start_time;
        private String _update_id;
        private long _update_time;
        private String _used_time;
        private String _operationState;

        public String get_operationState() {
            return _operationState;
        }

        public void set_operationState(String _operationState) {
            this._operationState = _operationState;
        }

        public String get_attach() {
            return _attach;
        }

        public void set_attach(String _attach) {
            this._attach = _attach;
        }

        public String get_client_sign() {
            return _client_sign;
        }

        public void set_client_sign(String _client_sign) {
            this._client_sign = _client_sign;
        }

        public String get_company_code() {
            return _company_code;
        }

        public void set_company_code(String _company_code) {
            this._company_code = _company_code;
        }

        public String get_create_id() {
            return _create_id;
        }

        public void set_create_id(String _create_id) {
            this._create_id = _create_id;
        }

        public long get_create_time() {
            return _create_time;
        }

        public void set_create_time(long _create_time) {
            this._create_time = _create_time;
        }

        public long get_end_time() {
            return _end_time;
        }

        public void set_end_time(long _end_time) {
            this._end_time = _end_time;
        }

        public String get_invoice_no() {
            return _invoice_no;
        }

        public void set_invoice_no(String _invoice_no) {
            this._invoice_no = _invoice_no;
        }

        public String get_item_name() {
            return _item_name;
        }

        public void set_item_name(String _item_name) {
            this._item_name = _item_name;
        }

        public String get_item_value() {
            return _item_value;
        }

        public void set_item_value(String _item_value) {
            this._item_value = _item_value;
        }

        public String get_link_flight_id() {
            return _link_flight_id;
        }

        public void set_link_flight_id(String _link_flight_id) {
            this._link_flight_id = _link_flight_id;
        }

        public byte[] get_server_sign() {
            return _server_sign;
        }

        public void set_server_sign(byte[] _server_sign) {
            this._server_sign = _server_sign;
        }

        public String get_service_seq() {
            return _service_seq;
        }

        public void set_service_seq(String _service_seq) {
            this._service_seq = _service_seq;
        }

        public String get_service_type() {
            return _service_type;
        }

        public void set_service_type(String _service_type) {
            this._service_type = _service_type;
        }

        public String get_signstate() {
            return _signstate;
        }

        public void set_signstate(String _signstate) {
            this._signstate = _signstate;
        }

        public long get_start_time() {
            return _start_time;
        }

        public void set_start_time(long _start_time) {
            this._start_time = _start_time;
        }

        public String get_update_id() {
            return _update_id;
        }

        public void set_update_id(String _update_id) {
            this._update_id = _update_id;
        }

        public long get_update_time() {
            return _update_time;
        }

        public void set_update_time(long _update_time) {
            this._update_time = _update_time;
        }

        public String get_used_time() {
            return _used_time;
        }

        public void set_used_time(String _used_time) {
            this._used_time = _used_time;
        }
    }
    public static class AccaServiceDateBean1  implements Serializable{
        private String _attach;
        private String _client_sign;
        private String _company_code;
        private String _create_id;
        private long _create_time;
        private long _end_time;
        private String _invoice_no;
        private String _item_name;
        private String _item_value;
        private String _link_flight_id;
        private String _server_sign;
        private String _service_seq;
        private String _service_type;
        private String _signstate;
        private long _start_time;
        private String _update_id;
        private long _update_time;
        private String _used_time;
        private String _operationState;

        public String get_operationState() {
            return _operationState;
        }

        public void set_operationState(String _operationState) {
            this._operationState = _operationState;
        }

        public String get_attach() {
            return _attach;
        }

        public void set_attach(String _attach) {
            this._attach = _attach;
        }

        public String get_client_sign() {
            return _client_sign;
        }

        public void set_client_sign(String _client_sign) {
            this._client_sign = _client_sign;
        }

        public String get_company_code() {
            return _company_code;
        }

        public void set_company_code(String _company_code) {
            this._company_code = _company_code;
        }

        public String get_create_id() {
            return _create_id;
        }

        public void set_create_id(String _create_id) {
            this._create_id = _create_id;
        }

        public long get_create_time() {
            return _create_time;
        }

        public void set_create_time(long _create_time) {
            this._create_time = _create_time;
        }

        public long get_end_time() {
            return _end_time;
        }

        public void set_end_time(long _end_time) {
            this._end_time = _end_time;
        }

        public String get_invoice_no() {
            return _invoice_no;
        }

        public void set_invoice_no(String _invoice_no) {
            this._invoice_no = _invoice_no;
        }

        public String get_item_name() {
            return _item_name;
        }

        public void set_item_name(String _item_name) {
            this._item_name = _item_name;
        }

        public String get_item_value() {
            return _item_value;
        }

        public void set_item_value(String _item_value) {
            this._item_value = _item_value;
        }

        public String get_link_flight_id() {
            return _link_flight_id;
        }

        public void set_link_flight_id(String _link_flight_id) {
            this._link_flight_id = _link_flight_id;
        }

        public String get_server_sign() {
            return _server_sign;
        }

        public void set_server_sign(String _server_sign) {
            this._server_sign = _server_sign;
        }

        public String get_service_seq() {
            return _service_seq;
        }

        public void set_service_seq(String _service_seq) {
            this._service_seq = _service_seq;
        }

        public String get_service_type() {
            return _service_type;
        }

        public void set_service_type(String _service_type) {
            this._service_type = _service_type;
        }

        public String get_signstate() {
            return _signstate;
        }

        public void set_signstate(String _signstate) {
            this._signstate = _signstate;
        }

        public long get_start_time() {
            return _start_time;
        }

        public void set_start_time(long _start_time) {
            this._start_time = _start_time;
        }

        public String get_update_id() {
            return _update_id;
        }

        public void set_update_id(String _update_id) {
            this._update_id = _update_id;
        }

        public long get_update_time() {
            return _update_time;
        }

        public void set_update_time(long _update_time) {
            this._update_time = _update_time;
        }

        public String get_used_time() {
            return _used_time;
        }

        public void set_used_time(String _used_time) {
            this._used_time = _used_time;
        }
    }
    public static class AccauserFlightDateBean {
        private String _company_code;
        private String _create_id;
        private long _create_time;
        private String _flight_id;
        private String _sequence;
        private String _update_id;
        private long _update_time;
        private String _user_id;
        private String _operationState;

        public String get_operationState() {
            return _operationState;
        }

        public void set_operationState(String _operationState) {
            this._operationState = _operationState;
        }

        public String get_company_code() {
            return _company_code;
        }

        public void set_company_code(String _company_code) {
            this._company_code = _company_code;
        }

        public String get_create_id() {
            return _create_id;
        }

        public void set_create_id(String _create_id) {
            this._create_id = _create_id;
        }

        public long get_create_time() {
            return _create_time;
        }

        public void set_create_time(long _create_time) {
            this._create_time = _create_time;
        }

        public String get_flight_id() {
            return _flight_id;
        }

        public void set_flight_id(String _flight_id) {
            this._flight_id = _flight_id;
        }

        public String get_sequence() {
            return _sequence;
        }

        public void set_sequence(String _sequence) {
            this._sequence = _sequence;
        }

        public String get_update_id() {
            return _update_id;
        }

        public void set_update_id(String _update_id) {
            this._update_id = _update_id;
        }

        public long get_update_time() {
            return _update_time;
        }

        public void set_update_time(long _update_time) {
            this._update_time = _update_time;
        }

        public String get_user_id() {
            return _user_id;
        }

        public void set_user_id(String _user_id) {
            this._user_id = _user_id;
        }
    }
}
