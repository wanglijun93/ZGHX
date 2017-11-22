package com.travelsky.airportapp.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.travelsky.airportapp.Cantons;
import com.travelsky.airportapp.HttpHelper;
import com.travelsky.airportapp.R;
import com.travelsky.airportapp.adapter.EyeAdapter;
import com.travelsky.airportapp.domain.EyeInfo;
import com.travelsky.airportapp.domain.FlightData;
import com.travelsky.airportapp.utils.AorD;
import com.travelsky.airportapp.utils.CacheUtils;
import com.travelsky.airportapp.utils.DateToString;
import com.travelsky.airportapp.utils.FileHelper;
import com.travelsky.airportapp.utils.LogUtil;
import com.travelsky.airportapp.utils.NetworkUtils;
import com.travelsky.airportapp.utils.db.DataBaseDao;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * @author wanglijun
 *         签字预览界面
 */
public class EyeActivity extends Activity {

    @InjectView(R.id.icon)
    ImageView icon;   //图标
    @InjectView(R.id.aircorp)
    TextView aircorp;   //航班名字
    @InjectView(R.id.scicen)
    LinearLayout scicen;
    @InjectView(R.id.comefrom)
    TextView comefrom;   //去往 来自
    @InjectView(R.id.incident1)
    TextView incident1;    //正常 延误
    @InjectView(R.id.incident)
    TextView incident;    //长沙
    @InjectView(R.id.ll_fj_commefrom)
    LinearLayout llFjCommefrom;
    @InjectView(R.id.qianxu)
    TextView qianxu;   //实际降落
    @InjectView(R.id.arrivetm)
    TextView arrivetm;   //时间
    @InjectView(R.id.llqx)
    LinearLayout llqx;
    @InjectView(R.id.contentll)
    LinearLayout contentll;
    @InjectView(R.id.tv_in_out_port)
    TextView tvInOutPort;    //进港 出港
    @InjectView(R.id.FLT_No)
    TextView FLTNo;   //航班号
    @InjectView(R.id.airwei)
    TextView airwei;  //机位号
    @InjectView(R.id.plan)
    TextView plan;   //飞机号
    @InjectView(R.id.ll_contentll2)
    LinearLayout llContentll2;
    @InjectView(R.id.textview1)
    TextView textview1;
    @InjectView(R.id.textview2)
    TextView textview2;
    @InjectView(R.id.textview3)
    TextView textview3;
    @InjectView(R.id.textview4)
    TextView textview4;
    @InjectView(R.id.imageview5)
    TextView imageview5;
    @InjectView(R.id.ll_contentll3)
    LinearLayout llContentll3;
    @InjectView(R.id.listview)
    ListView listview;
    @InjectView(R.id.ll_contentll5)
    LinearLayout llContentll5;
    @InjectView(R.id.progressBar_eye)
    ProgressBar progressBareye;
    private String eyerequest;
    private EyeInfo eyeInfo;
    private EyeInfo.FlightDataBean flightData;
    private String acheme_dep_time;
    private List<EyeInfo.FlightDataBean.SignInfoBean> signInfo;
    private String userSeq;
    private String userId;
    private String fight_seq;
    private String Ts;
    private String eye;
    private String arr_airport_three;
    private String dep_airport_three;
    private int groupPosition;
    private int childPosition;
    private DataBaseDao dao;
    private String customer_name;
    private String time_1;
    private List<FlightData.AccaFlightDateBean> accaFlightday1;
    private FlightData.AccaFlightDateBean accaFlightDateBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eye);
        ButterKnife.inject(this);
        Ts = String.valueOf(System.currentTimeMillis()).substring(0, 10);
        userSeq = CacheUtils.getString(EyeActivity.this, CacheUtils.user_seq);
        userId = CacheUtils.getString(EyeActivity.this, CacheUtils.userId);
        GetResponseMysign();
        if (NetworkUtils.checkNetwork(EyeActivity.this)) {
            LogUtil.d("---是否连接网络", "已连接网络");
            eye = "{\"UserId\":\"" + userId + "\",\"User_Seq\":\"" + userSeq + "\",\"Fight_Seq\":\"" + fight_seq + "\",\"Ts\":\"" + Ts + "\",\"Sign\":\"" + GetResponseMysign() + "\"}";
            LogUtil.d("---eye提交的数据", eye);
            OkHttpUtils
                    .postString()
                    .url(Cantons.ServiceSignInfo)
                    .addHeader("User-Agent", "PDCS-APP")
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .content(eye)
                    .build()
                    .execute(new EyeCallback());

        } else {
            dao = new DataBaseDao(EyeActivity.this);
            LogUtil.d("---是否连接网络", "未连接网络");
            initTou1();
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    List<EyeInfo.FlightDataBean.SignInfoBean> signInfoBeen = dao.selectServiceSign(fight_seq);
                                    if (signInfoBeen != null) {
                                        //数据适配器
                                        listview.setAdapter(new EyeAdapter(EyeActivity.this, signInfoBeen));
                                        progressBareye.setVisibility(View.GONE);
                                    } else {
                                        progressBareye.setVisibility(View.GONE);
                                    }

                                }
                            });
                        }

                    }
            ).start();
        }

        initBar();

    }

    /**
     * 设置保障详情头部的参数
     */
    private void initTou1() {
        LogUtil.d("---inittou1", "执行了离线的头布局填充");
        accaFlightday1 = (List<FlightData.AccaFlightDateBean>) getIntent().getSerializableExtra("accaFlightday");
        accaFlightDateBean = accaFlightday1.get(0);
        //航空公司名
        customer_name = accaFlightDateBean.get_customer_code();
        //状态
        String code_three = accaFlightDateBean.get_code_three();
        //预计到达时间
        String alter_arr_time = accaFlightDateBean.get_alter_arr_time() + "";
        //进港或者出港
        String dep_arr_flag = accaFlightDateBean.get_dep_arr_flag();
        //中文名字
        dep_airport_three = accaFlightDateBean.get_dep_airport_three();
        //航班号
        String flight_num_two = accaFlightDateBean.get_flight_num_two();
        //机位号
        String slot = accaFlightDateBean.get_slot();
        //飞机号
        String register_num = accaFlightDateBean.get_register_num();
        //状态
        String flight_state = accaFlightDateBean.get_flight_state();
        icon.setImageResource(getDrawResourceID(accaFlightDateBean.get_customer_code()));

        incident.setText(dao.threeCodeToName(code_three).get(0));

        if ("延误".equals(flight_state)) {
            incident1.setTextColor(Color.RED);
            incident1.setText(flight_state);
        } else {
            incident1.setText("正常");
            incident1.setTextColor(Color.parseColor("#0a57b0"));
        }

        List<String> customerName = dao.codeToName(accaFlightDateBean.get_customer_code());
        aircorp.setText(customerName.get(0));

        if ("".equals(accaFlightDateBean.get_arr_airport_three())) {
            comefrom.setText("去往");
        } else {
            comefrom.setText("来自");
        }
        //进港或者出港判断
        String ad = AorD.ad(dep_arr_flag);
        if (ad == "进港") {
            if (accaFlightDateBean.get_real_arr_time() != 0) {
                time_1 = accaFlightDateBean.get_real_arr_time() + "";
            } else if (accaFlightDateBean.get_alter_arr_time() != 0) {
                time_1 = accaFlightDateBean.get_alter_arr_time() + "";
            } else {
                time_1 = accaFlightDateBean.get_scheme_arr_time() + "";

            }
        }
        if (ad == "出港") {

            if (accaFlightDateBean.get_real_dep_time() != 0) {
                time_1 = accaFlightDateBean.get_real_dep_time() + "";
            } else if (accaFlightDateBean.get_alter_dep_time() != 0) {
                time_1 = accaFlightDateBean.get_alter_dep_time() + "";
            } else {
                time_1 = accaFlightDateBean.get_scheme_dep_time() + "";

            }
        }
        String formatStr = "yyyyMMddHHmmss";
        String dateFromatStr = "yyyy-MM-dd HH:mm:ss";
        //转换为2016-05-03 02:33:00 的格式
        String qianxu1 = DateToString.StringToDate(time_1, formatStr, dateFromatStr);
        qianxu.setText("   " + qianxu1);
        tvInOutPort.setText(ad);
        FLTNo.setText(flight_num_two);
        airwei.setText(slot);
        plan.setText(register_num);
    }

    /**
     * 根据图片的名称获取对应的资源id
     *
     * @param resourceName
     * @return
     */
    public int getDrawResourceID(String resourceName) {
        Resources res = EyeActivity.this.getResources();
        int picid = res.getIdentifier("aircorp_" + resourceName.toLowerCase(), "drawable", EyeActivity.this.getPackageName());
        return picid;
    }

    /**
     * 发起post网络请求
     *
     * @param url  这个网络请求的接口地址
     * @param json 这个请求携带的参数
     * @return
     */
    private String PostRegistLogin2(String url, String json) {
        return HttpHelper.Post(url, json);
    }

    /**
     * 请求携带的签名
     *
     * @return
     */
    private String GetResponseMysign() {
        LogUtil.d("---getresponsemysign", "请求携带的签名");
        String APIPrivateKey = "2D7E7C96-DAC5-4526-96C3-C60CDEC4B120";
        Map<String, String> map = new HashMap<>();
        fight_seq = CacheUtils.getString(this, CacheUtils.Fight_Seq);
        map.put("fight_seq", fight_seq);
        map.put("ts", Ts);
        map.put("user_seq", userSeq);
        map.put("userid", userId);
        String key = CacheUtils.GetPostStrings(map) + APIPrivateKey;
        String keyMd5 = FileHelper.ToMD5(key);
        LogUtil.d("---md5", keyMd5);
        return keyMd5;

    }

    /**
     * 设置保障详情头部的参数
     */
    private void initTou() {
        //航空公司名
        customer_name = flightData.getCustomer_Name();
        aircorp.setText(customer_name);
        String customer_code = flightData.getCustomer_Code();
        icon.setImageResource(getDrawResourceID(customer_code));
        if ("".equals(flightData.getArr_Airport_Three())) {

            incident.setText(flightData.getDep_Airport_Three());
        } else {
            incident.setText(flightData.getArr_Airport_Three());
        }
        String flight_state = flightData.getFlight_State();
        if ("延误".equals(flight_state)) {
            incident1.setText(flight_state);
            incident1.setTextColor(Color.RED);
        } else {
            incident1.setText("正常");
            incident1.setTextColor(Color.parseColor("#0a57b0"));
        }
        //去往 来自
        arr_airport_three = flightData.getArr_Airport_Three();
        //
        dep_airport_three = flightData.getDep_Airport_Three();
        if ("".equals(arr_airport_three)) {
            comefrom.setText("去往");
        } else {
            comefrom.setText("来自");
        }
        String dep_arr_flag = flightData.getDep_arr_Flag();

        String in_out_port = "";
        if ("A".equals(dep_arr_flag)) {
            tvInOutPort.setText("进港");
            if (!flightData.getReal_Arr_Time().isEmpty()) {
                acheme_dep_time = flightData.getReal_Arr_Time();
                in_out_port = "实际降落";
            } else if (!flightData.getAlter_Arr_Time().isEmpty()) {
                acheme_dep_time = flightData.getAlter_Arr_Time();
                in_out_port = "预计降落";
            } else {
                acheme_dep_time = flightData.getScheme_Arr_Time();
                in_out_port = "计划降落";

            }
        }

        if ("D".equals(dep_arr_flag)) {
            tvInOutPort.setText("出港");
            if (!flightData.getReal_Dep_Time().isEmpty()) {
                acheme_dep_time = flightData.getReal_Dep_Time();
                in_out_port = "实际起飞";
            } else if (!flightData.getAlter_Dep_Time().isEmpty()) {
                acheme_dep_time = flightData.getAlter_Dep_Time();
                in_out_port = "预计起飞";
            } else {
                acheme_dep_time = flightData.getScheme_Dep_Time();
                in_out_port = "计划起飞";

            }
        }
        String formatStr = "yyyyMMddHHmmss";
        String dateFromatStr = "yyyy-MM-dd HH:mm:ss";
        //转换为2016-05-03 02:33:00 的格式
        String qianxu1 = DateToString.StringToDate(acheme_dep_time, formatStr, dateFromatStr);
        qianxu.setText("  " + in_out_port + "  " + qianxu1);

        FLTNo.setText(flightData.getFlight_Num_Two());
        airwei.setText(flightData.getSlot());
        plan.setText(flightData.getRegister_Num());
    }

    /**
     * 初始化action bar
     */
    private void initBar() {
        //得到Actionbar
        ActionBar actionBar = getActionBar();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayUseLogoEnabled(false);
        actionBar.setTitle("签名");
        //设置Actionbar的背景
        try {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.bar11));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 根据id 做出相应操作
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 联网请求回调
     */
    private class EyeCallback extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {
            e.printStackTrace();
            Toast.makeText(EyeActivity.this, "onError:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response, int id) {
            LogUtil.d("response", response);
            if (response != null) {
                eyeInfo = new Gson().fromJson(response, EyeInfo.class);
                flightData = eyeInfo.getFlightData();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initTou();
                        signInfo = flightData.getSignInfo();
                        if (signInfo != null) {
                            //数据适配器
                            listview.setAdapter(new EyeAdapter(EyeActivity.this, signInfo));
                            progressBareye.setVisibility(View.GONE);
                        } else {
                            progressBareye.setVisibility(View.GONE);
                        }

                    }
                });
            }
        }
    }
}
