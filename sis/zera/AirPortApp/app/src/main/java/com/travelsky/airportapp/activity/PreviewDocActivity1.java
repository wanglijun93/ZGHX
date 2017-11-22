package com.travelsky.airportapp.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.travelsky.airportapp.Cantons;
import com.travelsky.airportapp.HttpHelper;
import com.travelsky.airportapp.R;
import com.travelsky.airportapp.adapter.PreviewDocAdapter;
import com.travelsky.airportapp.adapter.PreviewDocAdapter2;
import com.travelsky.airportapp.domain.AirInfo;
import com.travelsky.airportapp.domain.FlightData;
import com.travelsky.airportapp.domain.ServiceSignInfo;
import com.travelsky.airportapp.utils.AorD;
import com.travelsky.airportapp.utils.BitmapAndStringUtils;
import com.travelsky.airportapp.utils.CacheUtils;
import com.travelsky.airportapp.utils.DateToString;
import com.travelsky.airportapp.utils.FileHelper;
import com.travelsky.airportapp.utils.LogUtil;
import com.travelsky.airportapp.utils.NetworkUtils;
import com.travelsky.airportapp.utils.ToastBig;
import com.travelsky.airportapp.utils.db.DataBaseDao;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * 详细信息
 * 更改后的页面，（xml布局）
 */
public class PreviewDocActivity1 extends Activity implements View.OnClickListener {
    private String customer_name;
    private String code_three;
    private String alter_arr_time;
    private String dep_arr_flag;
    private String flight_num_two;
    private String slot;
    private String register_num;
    private AirInfo.DataBean data;
    private String time_1;
    private AirInfo airInfo;
    private String flight_state;
    private ListView listView;
    private Intent intent;
    private String air1;
    private String APIPrivateKey = "2D7E7C96-DAC5-4526-96C3-C60CDEC4B120";
    private List<String> isCheck = new ArrayList<String>();
    private List<AirInfo.DataBean.AccaServiceBean> accaService;
    private Button qianming;
    private Button baochun;
    private ImageView icon;
    private TextView aircorp;
    private TextView comefrom;
    private TextView incident;
    private TextView qianxu;
    private TextView tvInOutPort;
    private TextView fltNo;
    private TextView airwei;
    private TextView plan;
    private Map<Integer, AirInfo.DataBean.AccaServiceBean> map;
    private Map<Integer, FlightData.AccaServiceDateBean> map1;
    private String Ts;
    private ServiceSignInfo previewDoc;
    private ServiceSignInfo previewDoc1;
    private String json1;
    private AirInfo.DataBean.AccaServiceBean value;
    private FlightData.AccaServiceDateBean value1;
    private List<AirInfo.DataBean.AccaServiceBean> list1;
    private List<FlightData.AccaServiceDateBean> list2;
    private String filename = "";
    private Bitmap bitmap;
    private String bit;
    private String path;
    private ProgressBar progressBar;
    private ImageView imageView_qian;
    private TextView incident1;
    private String dep_airport_three;
    private DataBaseDao dao;
    private List<FlightData.AccaFlightDateBean> accaFlightday1;
    private FlightData.AccaFlightDateBean accaFlightDateBean;
    private String string;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_doc1);
        initTitle();
        dao = new DataBaseDao(PreviewDocActivity1.this);
        EventBus.getDefault().register(this);
        //fbi操作
        initId();
        qianming.setOnClickListener(this);
        baochun.setOnClickListener(this);
        intent = getIntent();
        //如果联网
        if (NetworkUtils.checkNetwork(PreviewDocActivity1.this)) {
            LogUtil.d("---", "连接网络");
            air1 = intent.getStringExtra("air1");
            initBitmap();
            Logger.d("--air1", air1);
            airInfo = new Gson().fromJson(air1, AirInfo.class);
            data = airInfo.getData();
            accaService = data.getAccaService();

            Bundle extras = intent.getExtras();
            map = (Map<Integer, AirInfo.DataBean.AccaServiceBean>) extras.get("map");
            list1 = new ArrayList<>();

            if (map != null) {
                LogUtil.d("---map", map.size() + "");
                for (Map.Entry<Integer, AirInfo.DataBean.AccaServiceBean> entry : map.entrySet()) {
                    value = entry.getValue();
                    list1.add(value);
                }
                initListView();
            }
            initTou();
        } else {
            LogUtil.d("---", "未连接网络");
            initTou1();
            initBitmap();

            Bundle extras = intent.getExtras();
            map1 = (Map<Integer, FlightData.AccaServiceDateBean>) extras.get("map");
            list2 = new ArrayList<>();

            if (map1 != null) {
                LogUtil.d("---map", map1.size() + "");
                for (Map.Entry<Integer, FlightData.AccaServiceDateBean> entry : map1.entrySet()) {
                    value1 = entry.getValue();
                    list2.add(value1);
                }
                initListView2();
            }

        }


    }

    private boolean flag = false;

    public void onEventMainThread(String str) {

        if (!flag) {
            flag = true;
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("网络变化")
                    .setMessage("需要刷新数据")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
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
    }

    /**
     * 未连接网络时候的头布局显示
     */
    private void initTou1() {
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
        fltNo.setText(flight_num_two);
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
        Resources res = PreviewDocActivity1.this.getResources();
        int picid = res.getIdentifier("aircorp_" + resourceName.toLowerCase(), "drawable", PreviewDocActivity1.this.getPackageName());
        return picid;
    }

    private void initBitmap() {
        path = intent.getStringExtra("path");
        LogUtil.d("---path", path);
        if (!"".equals(path)) {
            filename = path;
            File file = new File(filename);
            if (file.exists()) {
                bitmap = BitmapFactory.decodeFile(filename);
            }
            bit = BitmapAndStringUtils.convertIconToString(ImageCompressL(bitmap));
            LogUtil.d("TAG", "bitmap222222" + BitmapAndStringUtils.convertIconToString(ImageCompressL(bitmap)));
            LogUtil.d("---bit", bit);
            imageView_qian.setImageBitmap(bitmap);
        }

    }

    /* 图片压缩方法
    * 计算 bitmap大小，如果超过64kb，则进行压缩
    * @param bitmap
    * @return
    */
    private Bitmap ImageCompressL(Bitmap bitmap) {
        double targetwidth = Math.sqrt(6.00 * 1000);
        if (bitmap.getWidth() > targetwidth || bitmap.getHeight() > targetwidth) {
            // 创建操作图片用的matrix对象
            Matrix matrix = new Matrix();
            // 计算宽高缩放率
            double x = Math.max(targetwidth / bitmap.getWidth(), targetwidth
                    / bitmap.getHeight());
            // 缩放图片动作
            matrix.postScale((float) x, (float) x);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true);
        }
        return bitmap;
    }

    private void initId() {
        //listview显示服务项目 次数
        icon = (ImageView) findViewById(R.id.icon);
        aircorp = (TextView) findViewById(R.id.aircorp);
        comefrom = (TextView) findViewById(R.id.comefrom);
        incident = (TextView) findViewById(R.id.incident);
        incident1 = (TextView) findViewById(R.id.incident1);
        qianxu = (TextView) findViewById(R.id.qianxu);
        tvInOutPort = (TextView) findViewById(R.id.tv_in_out_port);
        fltNo = (TextView) findViewById(R.id.FLT_No);
        airwei = (TextView) findViewById(R.id.airwei);
        plan = (TextView) findViewById(R.id.plan);
        listView = (ListView) findViewById(R.id.listview);
        qianming = (Button) findViewById(R.id.qianming);
        baochun = (Button) findViewById(R.id.baochun);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        imageView_qian = (ImageView) findViewById(R.id.imageView_qing);
    }

    /**
     * listview数据适配器
     */
    private void initListView() {
        listView.setAdapter(new PreviewDocAdapter(this, accaService, list1));
        listView.setDividerHeight(0);
    }

    /**
     * listview数据适配器
     */
    private void initListView2() {
        listView.setAdapter(new PreviewDocAdapter2(this, list2));
        listView.setDividerHeight(0);
    }

    /**
     * 设置头部的参数
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
        flight_state = data.getFlight_State();
        String customer_code = data.getCustomer_Code();
        icon.setImageResource(getDrawResourceID(customer_code));
        if ("".equals(data.getArr_Airport_Three())) {

            incident.setText(data.getDep_Airport_Three());
        } else {
            incident.setText(data.getArr_Airport_Three());
        }
        if ("延误".equals(flight_state)) {
            incident1.setTextColor(Color.RED);
            incident1.setText(flight_state);
        } else {
            incident1.setText("正常");
            incident1.setTextColor(Color.parseColor("#0a57b0"));
        }
        aircorp.setText(customer_name);
        if ("".equals(data.getArr_Airport_Three())) {
            comefrom.setText("去往");
        } else {
            comefrom.setText("来自");
        }

        //进港或者出港判断
        String ad = AorD.ad(dep_arr_flag);
        if (ad == "进港") {
            if (!data.getReal_Arr_Time().isEmpty()) {
                time_1 = data.getReal_Arr_Time();
            } else if (!data.getAlter_Arr_Time().isEmpty()) {
                time_1 = data.getAlter_Arr_Time();
            } else {
                time_1 = data.getScheme_Arr_Time();
            }
        }
        if (ad == "出港") {
            if (!data.getReal_Dep_Time().isEmpty()) {
                time_1 = data.getReal_Dep_Time();
            } else if (!data.getAlter_Dep_Time().isEmpty()) {
                time_1 = data.getAlter_Dep_Time();
            } else {
                time_1 = data.getScheme_Dep_Time();
            }
        }
        String formatStr = "yyyyMMddHHmmss";
        String dateFromatStr = "yyyy-MM-dd HH:mm:ss";
        //转换为2016-05-03 02:33:00 的格式
        String qianxu1 = DateToString.StringToDate(time_1, formatStr, dateFromatStr);

        qianxu.setText(qianxu1);
        tvInOutPort.setText(ad);
        fltNo.setText(flight_num_two);
        airwei.setText(slot);
        plan.setText(register_num);
    }

    /**
     * 设置activitybar
     */
    public void initTitle() {
        //得到Actionbar
        ActionBar actionBar = getActionBar();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayUseLogoEnabled(false);
        actionBar.setTitle("详细信息");
        //设置Actionbar的背景
        try {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.bar11));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.qianming:
                if (NetworkUtils.checkNetwork(PreviewDocActivity1.this)) {
                    if (listView.getCount() > 0) {
                        Intent intent1 = new Intent(PreviewDocActivity1.this, ElectronicSignActivity.class);
                        intent1.putExtra("air", air1);
                        intent1.putExtra("accaFlightday", (Serializable) accaFlightday1);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("map", (Serializable) map);
                        intent1.putExtras(bundle);
                        startActivity(intent1);
                        finish();
                    } else {
                        ToastBig.toast(PreviewDocActivity1.this, "未添加服务项目");
                    }
                } else {
                    if (listView.getCount() > 0) {
                        Intent intent1 = new Intent(PreviewDocActivity1.this, ElectronicSignActivity.class);
                        Bundle bundle = new Bundle();
                        intent1.putExtra("accaFlightday", (Serializable) accaFlightday1);
                        bundle.putSerializable("map", (Serializable) map1);
                        intent1.putExtras(bundle);
                        startActivity(intent1);
                        finish();
                    } else {
                        ToastBig.toast(PreviewDocActivity1.this, "未添加服务项目");
                    }
                }

                break;
            case R.id.baochun:
                if (listView.getCount() > 0) {
                    if (NetworkUtils.checkNetwork(PreviewDocActivity1.this)) {
                        previewDoc1 = CreateJsonObject();
                        //服务签名
                        String server_sign = previewDoc1.getServer_Sign().getServersign();
                        List<ServiceSignInfo.ServiceDataBean> serviceData = previewDoc1.getServiceData();
                        for (int i = 0; i < list1.size(); i++) {
                            String service_seq = serviceData.get(i).getService_seq();
                            String serversign = server_sign;

                            dao.UpdateServiceSing(service_seq, serversign);
                        }
                        json1 = new Gson().toJson(previewDoc1);
                        LogUtil.d("---json1", json1);
                        progressBar.setVisibility(View.VISIBLE);
                        OkHttpUtils
                                .postString()
                                .url(Cantons.AddServiceSign)
                                .addHeader("User-Agent", "PDCS-APP")
                                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                                .content(json1)
                                .build()
                                .execute(new PreviewDocCallback());

                    } else {
                        if (imageView_qian.getDrawable() == null){
                            //判断断网情况下显示图片的位置是否为空
                            ToastBig.toast(PreviewDocActivity1.this, "保存失败，可能未签名");
                        }else {
                            //如果没有网络
                            ServiceSignInfo serviceSignInfo = CreateJsonObject();
                            for (int i = 0; i < list2.size(); i++) {
                                String service_seq = serviceSignInfo.getServiceData().get(i).getService_seq();
                                String serversign = serviceSignInfo.getServer_Sign().getServersign();

                                dao.UpdateServiceSing(service_seq, serversign);
//                            LogUtil.d("---serversign---", serversign);
//                            LogUtil.d("---service_seq---", service_seq);
                            }
                            ToastBig.toast(PreviewDocActivity1.this, "本地保存成功");
                            Intent intent = new Intent(PreviewDocActivity1.this, SecurityDetailsSinglActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    }
                } else {
                    ToastBig.toast(PreviewDocActivity1.this, "未添加服务项目");
                }

                break;
        }

    }

    //发送post请求
    private String PostRegistLogin(String json) {
        String url = Cantons.AddServiceSign;
        return HttpHelper.Post(url, json);
    }

    public ServiceSignInfo CreateJsonObject() {
        //获取10位的时间戳
        Ts = String.valueOf(System.currentTimeMillis()).substring(0, 10);
        String userId = CacheUtils.getString(PreviewDocActivity1.this, CacheUtils.userId);
        String userSeq = CacheUtils.getString(PreviewDocActivity1.this, CacheUtils.user_seq);

        previewDoc = new ServiceSignInfo();
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("user_seq", userSeq);
        map.put("ts", Ts);
        map.put("userid", userId);

        LogUtil.d("---user_seq", userSeq);
        LogUtil.d("---suerid", userId);

        previewDoc.setUserId(userId);
        previewDoc.setUser_Seq(userSeq);
        previewDoc.setTs(Ts);
        previewDoc.setSign(GetResponseMysign(map, APIPrivateKey));

        ServiceSignInfo.ServerSignBean serverSignBean = new ServiceSignInfo.ServerSignBean();
        serverSignBean.setServersign(bit);
        previewDoc.setServer_Sign(serverSignBean);

        ArrayList<ServiceSignInfo.ServiceDataBean> serviceDataBeen = new ArrayList<>();
        if (NetworkUtils.checkNetwork(PreviewDocActivity1.this)) {
            for (int i = 0; i < list1.size(); i++) {
                ServiceSignInfo.ServiceDataBean bean = new ServiceSignInfo.ServiceDataBean();
                bean.setService_seq(list1.get(i).getService_seq());
                serviceDataBeen.add(bean);
            }
            previewDoc.setServiceData(serviceDataBeen);

        } else {
            for (int i = 0; i < list2.size(); i++) {
                ServiceSignInfo.ServiceDataBean bean = new ServiceSignInfo.ServiceDataBean();
                bean.setService_seq(list2.get(i).get_service_seq());
                serviceDataBeen.add(bean);
            }
            previewDoc.setServiceData(serviceDataBeen);
        }
        return previewDoc;
    }

    //获取签名
    public static String GetResponseMysign(LinkedHashMap<String, String> map, String privateKey) {
        String fullstring = CacheUtils.GetPostStrings(map) + privateKey;
        return FileHelper.ToMD5(fullstring);
    }

    private class PreviewDocCallback extends StringCallback {
        @Override
        public void onError(Call call, Exception e, int id) {

        }

        @Override
        public void onResponse(String response, int id) {
            LogUtil.d("111111", response);
            String resCode = "";
            String resMsg = "";
            try {
                JSONObject object = new JSONObject(response);
                resCode = object.optString("ResCode");
                resMsg = object.optString("ResMsg");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if ("1".equals(resCode)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastBig.toast(PreviewDocActivity1.this, "保存成功");
                        progressBar.setVisibility(View.GONE);
                    }
                });
                Intent intent = new Intent(PreviewDocActivity1.this, SecurityDetailsSinglActivity.class);
                startActivity(intent);
                finish();
            } else {
                final String finalResMsg = resMsg;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        ToastBig.toast(PreviewDocActivity1.this, "保存失败，可能未签名");
                    }
                });
            }
        }
    }
}
