package com.travelsky.airportapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.travelsky.airportapp.Cantons;
import com.travelsky.airportapp.domain.FlightData;
import com.travelsky.airportapp.domain.UserDataUp;
import com.travelsky.airportapp.utils.AirportApplication;
import com.travelsky.airportapp.utils.CacheUtils;
import com.travelsky.airportapp.utils.FileHelper;
import com.travelsky.airportapp.utils.LogUtil;
import com.travelsky.airportapp.utils.db.DataBaseDao;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;

/**
 * 上传用户信息
 */
public class UpDataService extends Service {
    private DataBaseDao dao;
    private String APIPrivateKey = "2D7E7C96-DAC5-4526-96C3-C60CDEC4B120";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        dao = new DataBaseDao(UpDataService.this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.e("TAG", "onStartCommand");

        UserDataUp userDataUp = new UserDataUp();

        //用户对应航班信息
        List<FlightData.AccauserFlightDateBean> accauserFlightDateBean = dao.getAccauserFlightDateBean();

        List<UserDataUp.AccauserFlightDateBean> flightDateBeanList = new ArrayList<>();
        for (int i = 0; i < accauserFlightDateBean.size(); i++) {

            UserDataUp.AccauserFlightDateBean flightDateBean = new UserDataUp.AccauserFlightDateBean();
            flightDateBean.setSequence(accauserFlightDateBean.get(i).get_sequence());

            flightDateBeanList.add(flightDateBean);
        }
        userDataUp.setAccauserFlightDate(flightDateBeanList);

        //航班服务信息
        List<FlightData.AccaServiceDateBean1> accaServiceDate = dao.getAccaServiceDate1();

        List<UserDataUp.AccaServiceDateBean> serviceDateBeanList = new ArrayList<>();
        for (int j = 0; j < accaServiceDate.size(); j++) {
            UserDataUp.AccaServiceDateBean serviceDateBean = new UserDataUp.AccaServiceDateBean();
            serviceDateBean.setAttach(accaServiceDate.get(j).get_attach());
            serviceDateBean.setClient_sign(accaServiceDate.get(j).get_client_sign());
            serviceDateBean.setCompany_code(accaServiceDate.get(j).get_company_code());
            serviceDateBean.setCreate_id(accaServiceDate.get(j).get_create_id());
            serviceDateBean.setCreate_time(accaServiceDate.get(j).get_create_time());
            serviceDateBean.setEnd_time(accaServiceDate.get(j).get_end_time());
            serviceDateBean.setInvoice_no(accaServiceDate.get(j).get_invoice_no());
            serviceDateBean.setItem_name(accaServiceDate.get(j).get_item_name());
            serviceDateBean.setItem_value(accaServiceDate.get(j).get_item_value());
            serviceDateBean.setLink_flight_id(accaServiceDate.get(j).get_link_flight_id());
            String server_sign = accaServiceDate.get(j).get_server_sign();
            serviceDateBean.setServer_sign(server_sign);
            serviceDateBean.setService_seq(accaServiceDate.get(j).get_service_seq());
            serviceDateBean.setService_type(accaServiceDate.get(j).get_service_type());
            serviceDateBean.setSignstate(accaServiceDate.get(j).get_signstate());
            serviceDateBean.setStart_time(accaServiceDate.get(j).get_start_time());
            serviceDateBean.setUpdate_id(accaServiceDate.get(j).get_update_id());
            serviceDateBean.setUpdate_time(accaServiceDate.get(j).get_update_time());
            serviceDateBean.setUsed_time(accaServiceDate.get(j).get_used_time());
            serviceDateBean.setOperationstate(accaServiceDate.get(j).get_operationState());

            serviceDateBeanList.add(serviceDateBean);
        }

        userDataUp.setAccaServiceDate(serviceDateBeanList);

        String userId = CacheUtils.getString(UpDataService.this, CacheUtils.userId);
        String user_seq = CacheUtils.getString(UpDataService.this, CacheUtils.user_seq);
        String Ts = String.valueOf(System.currentTimeMillis()).substring(0, 10);

        Map<String, String> map = new HashMap<>();
        map.put("userid", userId);
        map.put("user_seq", user_seq);
        map.put("ts", Ts);

        String sign = FileHelper.ToMD5(CacheUtils.GetPostStrings(map) + APIPrivateKey);

        userDataUp.setUserId(userId);
        userDataUp.setUser_Seq(user_seq);
        userDataUp.setTs(Ts);
        userDataUp.setSign(sign);

        int size = userDataUp.getAccaServiceDate().size();
        LogUtil.d("---上传服务的size", size + "");

        final String json = new Gson().toJson(userDataUp);

        LogUtil.d("---上传的json", json);
        LogUtil.d("---上传的json大小", json.length() + "");

        PostUpUserFlightData(json);
        EventBus.getDefault().post("网络连接");
        AirportApplication.noNet = false;

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //发送post请求
    private void PostUpUserFlightData(String json) {

        OkHttpUtils
                .postString()
                .url(Cantons.UpUserFlightData)
                .addHeader("User-Agent", "PDCS-APP")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(json)
                .build()
                .execute(new UpUserFlightDataCallback());
    }

    //上传用户信息回调
    public class UpUserFlightDataCallback extends StringCallback {
        @Override
        public void onBefore(Request request, int id) {
            //setTitle("loading...");
        }

        @Override
        public void onAfter(int id) {
            //setTitle("Sample-okHttp");
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            e.printStackTrace();
            Toast.makeText(UpDataService.this, "onError:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response, int id) {
            Log.e("TAG", "onResponse：complete");
            Log.e("TAG", "onResponse:" + response);

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(response);
                String resCode = jsonObject.optString("ResCode");
                if ("1".equals(resCode)) {
                    dao.upDelAcFlightDate();
                    dao.upDelAccaServiceDate();
                    dao.upModifyAccaServiceDate();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void inProgress(float progress, long total, int id) {
            Log.e("TAG", "inProgress:" + progress);
        }
    }
}
