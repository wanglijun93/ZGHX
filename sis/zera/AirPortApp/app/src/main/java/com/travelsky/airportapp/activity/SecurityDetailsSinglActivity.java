package com.travelsky.airportapp.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
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
import com.travelsky.airportapp.R;
import com.travelsky.airportapp.adapter.DetailsAdapter;
import com.travelsky.airportapp.adapter.OfflineSecurityDetailAdapter;
import com.travelsky.airportapp.domain.AirInfo;
import com.travelsky.airportapp.domain.FlightData;
import com.travelsky.airportapp.utils.AirportApplication;
import com.travelsky.airportapp.utils.AorD;
import com.travelsky.airportapp.utils.CacheUtils;
import com.travelsky.airportapp.utils.DateToString;
import com.travelsky.airportapp.utils.FileHelper;
import com.travelsky.airportapp.utils.LogUtil;
import com.travelsky.airportapp.utils.NetworkUtils;
import com.travelsky.airportapp.utils.ToastBig;
import com.travelsky.airportapp.utils.db.DataBaseDao;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;

/**
 * @author wlj
 *         保障详情
 */
public class SecurityDetailsSinglActivity extends Activity implements View.OnClickListener {

    @InjectView(R.id.icon)
    ImageView icon;
    @InjectView(R.id.aircorp)
    TextView aircorp;
    @InjectView(R.id.comefrom)
    TextView comefrom;
    @InjectView(R.id.incident)
    TextView incident;
    @InjectView(R.id.incident1)
    TextView incident1;
    @InjectView(R.id.qianxu)
    TextView qianxu;
    @InjectView(R.id.tv_in_out_port)
    TextView tvInOutPort;
    @InjectView(R.id.FLT_No)
    TextView FLTNo;
    @InjectView(R.id.airwei)
    TextView airwei;
    @InjectView(R.id.plan)
    TextView plan;
    @InjectView(R.id.iv_tianjia)
    ImageView ivTianjia;
    @InjectView(R.id.iv_tianjia_no)
    ImageView ivTianjiano;
    @InjectView(R.id.line)
    ImageView line;

    private ListView lvSds;
    private Intent intent;
    private String userSeq;
    private String userId;
    private String Ts;
    private String json_baozhang;
    private String request;
    private String customer_name;
    private String code_three;
    private String alter_arr_time;
    private String dep_arr_flag;
    private String flight_num_two;
    private String slot;
    private String register_num;
    private AirInfo airInfo;
    private AirInfo.DataBean data;
    private List<AirInfo.DataBean.AccaServiceBean> accaService;
    private List<AirInfo.DataBean.AccaServiceBean> accaService2;
    private String time_1;
    private DetailsAdapter detailsAdapter;
    private String flight_state;
    private List<AirInfo.DataBean.AccaServiceBean> list1;
    private List<FlightData.AccaServiceDateBean> list2;
    private ProgressBar pb_details;
    private LinearLayout ll_details;
    private String eye;
    private String eyerequest;
    private String substring;
    private Intent intent2;
    private String dep_airport_three;
    private DataBaseDao dao;
    private OfflineSecurityDetailAdapter offlineAdapter;
    private List<FlightData.AccaServiceDateBean> serviceDateBySeq;
    private String fight_seq;
    private List<FlightData.AccaFlightDateBean> accaFlightday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.security_details_singl_header);
        ButterKnife.inject(this);
        ll_details = (LinearLayout) findViewById(R.id.ll_details);
        lvSds = (ListView) findViewById(R.id.lv_sds);
        lvSds.setDividerHeight(0);
        dao = new DataBaseDao(SecurityDetailsSinglActivity.this);
        pb_details = (ProgressBar) findViewById(R.id.pb_details);

        userSeq = CacheUtils.getString(SecurityDetailsSinglActivity.this, CacheUtils.user_seq);
        userId = CacheUtils.getString(SecurityDetailsSinglActivity.this, CacheUtils.userId);
        fight_seq = CacheUtils.getString(SecurityDetailsSinglActivity.this, CacheUtils.Fight_Seq);
        //制作签名下·
        Ts = String.valueOf(System.currentTimeMillis()).substring(0, 10);
        //GetResponseMysign();
        json_baozhang = "{\"UserId\":\"" + userId + "\",\"User_Seq\":\"" + userSeq + "\",\"Fight_Seq\":\"" + fight_seq + "\",\"Ts\":\"" + Ts + "\",\"Sign\":\"" + GetResponseMysign() + "\"}";
        LogUtil.d("---baozhang", json_baozhang);
        initBar();
        initBtn();

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        accaFlightday = (List<FlightData.AccaFlightDateBean>) getIntent().getSerializableExtra("offline");

    }

    @Override
    protected void onResume() {
        super.onResume();
        onCreate(null);
        if (NetworkUtils.checkNetwork(SecurityDetailsSinglActivity.this)) {

            requestInternet();

        } else {
            try {
                initTouOff();
            } catch (Exception e) {
                e.printStackTrace();
            }
            serviceDateBySeq = dao.getServiceDateBySeq(fight_seq);

            offlineAdapter = new OfflineSecurityDetailAdapter(SecurityDetailsSinglActivity.this, serviceDateBySeq);
            lvSds.setAdapter(offlineAdapter);

            initTianjia();
            //去除ListView的分割线
            lvSds.setDividerHeight(0);
            line.setVisibility(View.VISIBLE);
            ll_details.setVisibility(View.VISIBLE);
            offlineAdapter.notifyDataSetChanged();
            pb_details.setVisibility(View.GONE);
        }
    }

    /**
     * 判断项目是否添加完成
     */
    private void initTianjia() {
        if (lvSds.getCount() < 29) {
            ivTianjiano.setVisibility(View.GONE);
            ivTianjia.setVisibility(View.VISIBLE);
        } else {
            ivTianjiano.setVisibility(View.VISIBLE);
            ivTianjia.setVisibility(View.GONE);
        }
    }

    public void onEventMainThread(List<AirInfo.DataBean.AccaServiceBean> list1) {
        this.list1 = list1;
    }


    public void onEventMainThread(String str) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("网络变化")
                .setMessage("需要刷新数据")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onResume();
                        AirportApplication.net = false;
                    }
                })
                .show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_SEARCH) {
                    return true;
                } else {
                    return false; //默认返回 false，这里false不能屏蔽返回键，改成true就可以了
                }
            }
        });

    }


    public void onEvent(List<FlightData.AccaServiceDateBean> list2) {
        this.list2 = list2;
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
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
     * 发起网络请求
     */
    private void requestInternet() {
        pb_details.setVisibility(View.VISIBLE);

        PostFlightDetails(Cantons.FlightDetailsByUser, json_baozhang);

    }

    /**
     * 设置保障详情头部的参数
     */
    private void initTouOff() {
        accaFlightday = (List<FlightData.AccaFlightDateBean>) getIntent().getSerializableExtra("offline");

        //航空公司名
        customer_name = accaFlightday.get(0).get_customer_code();
        //状态
        code_three = accaFlightday.get(0).get_code_three();
        //预计到达时间
        alter_arr_time = accaFlightday.get(0).get_alter_arr_time() + "";
        //进港或者出港
        dep_arr_flag = accaFlightday.get(0).get_dep_arr_flag();
        //中文名字
        dep_airport_three = accaFlightday.get(0).get_dep_airport_three();
        //航班号
        flight_num_two = accaFlightday.get(0).get_flight_num_two();
        //机位号
        slot = accaFlightday.get(0).get_slot();
        //飞机号
        register_num = accaFlightday.get(0).get_register_num();
        //状态
        flight_state = accaFlightday.get(0).get_flight_state();
        icon.setImageResource(getDrawResourceID(accaFlightday.get(0).get_customer_code()));

        incident.setText(dao.threeCodeToName(code_three).get(0));

        if ("延误".equals(flight_state)) {
            incident1.setTextColor(Color.RED);
            incident1.setText(flight_state);
        } else {
            incident1.setText("正常");
            incident1.setTextColor(Color.parseColor("#0a57b0"));
        }

        List<String> customerName = dao.codeToName(accaFlightday.get(0).get_customer_code());
        aircorp.setText(customerName.get(0));

        if ("".equals(accaFlightday.get(0).get_arr_airport_three())) {
            comefrom.setText("去往");
        } else {
            comefrom.setText("来自");
        }
        //进港或者出港判断
        String ad = AorD.ad(dep_arr_flag);
        if (ad == "进港") {
            if (accaFlightday.get(0).get_real_arr_time() != 0) {
                time_1 = accaFlightday.get(0).get_real_arr_time() + "";
            } else if (accaFlightday.get(0).get_alter_arr_time() != 0) {
                time_1 = accaFlightday.get(0).get_alter_arr_time() + "";
            } else {
                time_1 = accaFlightday.get(0).get_scheme_arr_time() + "";

            }
        }
        if (ad == "出港") {

            if (accaFlightday.get(0).get_real_dep_time() != 0) {
                time_1 = accaFlightday.get(0).get_real_dep_time() + "";
            } else if (accaFlightday.get(0).get_alter_dep_time() != 0) {
                time_1 = accaFlightday.get(0).get_alter_dep_time() + "";
            } else {
                time_1 = accaFlightday.get(0).get_scheme_dep_time() + "";

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
     * 设置保障详情头部的参数
     */
    private void initTou() {

        //航空公司名
        customer_name = data.getCustomer_Name();
        //状态
        code_three = data.getCode_Three();
        //预计到达时间
        alter_arr_time = data.getAlter_Arr_Time();
        //进港或者出港
        dep_arr_flag = data.getDep_arr_Flag();
        //中文名字
        dep_airport_three = data.getDep_Airport_Three();
        //航班号
        flight_num_two = data.getFlight_Num_Two();
        //机位号
        slot = data.getSlot();
        //飞机号
        register_num = data.getRegister_Num();
        //状态
        flight_state = data.getFlight_State();
        icon.setImageResource(getDrawResourceID(data.getCustomer_Code()));

        try {
            incident.setText(dao.threeCodeToName(code_three).get(0));

            if ("延误".equals(flight_state)) {
                incident1.setTextColor(Color.RED);
                incident1.setText(flight_state);
            } else {
                incident1.setText("正常");
                incident1.setTextColor(Color.parseColor("#0a57b0"));
            }

            List<String> customerName = dao.codeToName(data.getCustomer_Code());
            aircorp.setText(customerName.get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }


        if ("".equals(data.getArr_Airport_Three())) {
            comefrom.setText("去往");
        } else {
            comefrom.setText("来自");
        }
        //进港或者出港判断
        String ad = AorD.ad(dep_arr_flag);
        if (ad == "进港") {
            if (data.getReal_Arr_Time() != "") {
                time_1 = data.getReal_Arr_Time();
            } else if (data.getAlter_Arr_Time() != "") {
                time_1 = data.getAlter_Arr_Time();
            } else {
                time_1 = data.getScheme_Arr_Time();

            }
        }
        if (ad == "出港") {

            if (data.getReal_Dep_Time() != "") {
                time_1 = data.getReal_Dep_Time();
            } else if (data.getAlter_Dep_Time() != "") {
                time_1 = data.getAlter_Dep_Time();
            } else {
                time_1 = data.getScheme_Dep_Time();

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
     * 发起post网络请求
     *
     * @param url  这个网络请求的接口地址
     * @param json 这个请求携带的参数
     * @return
     */
    private void PostFlightDetails(String url, String json) {
        OkHttpUtils
                .postString()
                .url(url)
                .addHeader("User-Agent", "PDCS-APP")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(json)
                .build()
                .execute(new FlightDetailsCallback());
    }

    //保障详情回调
    public class FlightDetailsCallback extends StringCallback {
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
            Toast.makeText(SecurityDetailsSinglActivity.this, "onError:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response, int id) {
            LogUtil.d("TAG", "onResponse：complete");
            LogUtil.d("TAG", "onResponse:" + response);
            request = response;
            String resMsg = null;
            try {
                JSONObject jsonObject = new JSONObject(response);
                resMsg = jsonObject.optString("resMsg");
                String resCode = jsonObject.optString("resCode");
                if ("-4".equals(resCode)) {

                    Intent intent = new Intent(SecurityDetailsSinglActivity.this, LoginActivity.class);
                    intent.putExtra("request", resMsg + ",重新登陆");
                    startActivity(intent);
                    finish();
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            airInfo = new Gson().fromJson(response, AirInfo.class);

            data = airInfo.getData();

            accaService = data.getAccaService();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //显示头部数据
                    initTou();
                    if (accaService != null) {

                        detailsAdapter = new DetailsAdapter(SecurityDetailsSinglActivity.this, accaService);
                        lvSds.setAdapter(detailsAdapter);

                        initTianjia();
                        //去除ListView的分割线
                        lvSds.setDividerHeight(0);
                        line.setVisibility(View.VISIBLE);
                        pb_details.setVisibility(View.GONE);
                        ll_details.setVisibility(View.VISIBLE);
                        detailsAdapter.notifyDataSetChanged();
                    } else {
                        lvSds.setVisibility(View.GONE);
                        line.setVisibility(View.GONE);
                        pb_details.setVisibility(View.GONE);
                        ll_details.setVisibility(View.VISIBLE);
                    }

                }
            });

        }

        @Override
        public void inProgress(float progress, long total, int id) {
            LogUtil.d("TAG", "inProgress:" + progress);
        }
    }

    private void initBtn() {
        ivTianjia.setOnClickListener(this);
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
        actionBar.setTitle("保障详情");
        //设置Actionbar的背景
        try {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.bar11));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 添加bar 按钮
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.eye, menu);
        inflater.inflate(R.menu.print, menu);
        return true;
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
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.print_qianming:
                if (NetworkUtils.checkNetwork(SecurityDetailsSinglActivity.this)) {
                    if (accaService != null && list1 != null) {
                        Intent intent1 = new Intent(SecurityDetailsSinglActivity.this, PrintDocActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("list", (Serializable) accaService);
                        bundle.putSerializable("list1", (Serializable) list1);
                        intent1.putExtra("air", request);
                        intent1.putExtra("accaFlightday", (Serializable) accaFlightday);
                        intent1.putExtras(bundle);
                        startActivity(intent1);
                    } else {
                        ToastBig.toast(SecurityDetailsSinglActivity.this, "未添加服务项目");
                    }
                } else {
                    if (serviceDateBySeq.size() != 0) {
                        Intent intent1 = new Intent(SecurityDetailsSinglActivity.this, PrintDocActivity.class);
                        Bundle bundle = new Bundle();
                        intent1.putExtra("air", request);
                        bundle.putSerializable("list2", (Serializable) list2);
                        intent1.putExtra("accaFlightday", (Serializable) accaFlightday);
                        intent1.putExtras(bundle);
                        this.startActivity(intent1);
                    } else {
                        ToastBig.toast(SecurityDetailsSinglActivity.this, "未添加服务项目");
                    }
                }

                break;
            case R.id.print_eye:
                if (NetworkUtils.checkNetwork(SecurityDetailsSinglActivity.this)) {
                    if (accaService != null) {
                        intent2 = new Intent(SecurityDetailsSinglActivity.this, EyeActivity.class);
                        Bundle bundle2 = new Bundle();
                        bundle2.putSerializable("list", (Serializable) accaService);
                        bundle2.putSerializable("list1", (Serializable) list1);
                        intent2.putExtra("air", request);
                        intent2.putExtra("accaFlightday", (Serializable) accaFlightday);
                        intent2.putExtras(bundle2);
                        startActivity(intent2);
                    } else {
                        ToastBig.toast(SecurityDetailsSinglActivity.this, "未添加服务项目");
                    }
                } else {
                    if (serviceDateBySeq.size() != 0) {
                        intent2 = new Intent(SecurityDetailsSinglActivity.this, EyeActivity.class);
                        intent2.putExtra("accaFlightday", (Serializable) accaFlightday);
                        startActivity(intent2);
                    } else {
                        ToastBig.toast(SecurityDetailsSinglActivity.this, "未添加服务项目");
                    }
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_tianjia:
                if (NetworkUtils.checkNetwork(SecurityDetailsSinglActivity.this)) {
                    if (lvSds.getCount() < 29) {
                        //跳转到  添加已完成事项
                        Intent intent = new Intent(this, AddFinishedMatterActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("list", (Serializable) accaService);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    } else {
                        ivTianjia.setVisibility(View.GONE);
                        ivTianjiano.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (lvSds.getCount() < 29) {
                        //跳转到  添加已完成事项
                        Intent intent = new Intent(this, AddFinishedMatterActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("list", (Serializable) serviceDateBySeq);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    } else {
                        ivTianjia.setVisibility(View.GONE);
                        ivTianjiano.setVisibility(View.VISIBLE);
                    }
                }

                break;
        }
    }

    /**
     * 根据图片的名称获取对应的资源id
     *
     * @param resourceName
     * @return
     */
    public int getDrawResourceID(String resourceName) {
        Resources res = SecurityDetailsSinglActivity.this.getResources();
        int picid = res.getIdentifier("aircorp_" + resourceName.toLowerCase(), "drawable", SecurityDetailsSinglActivity.this.getPackageName());
        return picid;
    }
}
