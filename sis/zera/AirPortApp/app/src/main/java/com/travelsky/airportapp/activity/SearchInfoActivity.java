package com.travelsky.airportapp.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.travelsky.airportapp.Cantons;
import com.travelsky.airportapp.HttpHelper;
import com.travelsky.airportapp.R;
import com.travelsky.airportapp.SlidingLayout;
import com.travelsky.airportapp.domain.FlightData;
import com.travelsky.airportapp.domain.SearchBean;
import com.travelsky.airportapp.rangebar.RangeBar;
import com.travelsky.airportapp.utils.A2bigA;
import com.travelsky.airportapp.utils.CacheUtils;
import com.travelsky.airportapp.utils.DateToString;
import com.travelsky.airportapp.utils.FileHelper;
import com.travelsky.airportapp.utils.LogUtil;
import com.travelsky.airportapp.utils.NetworkUtils;
import com.travelsky.airportapp.utils.ToastBig;
import com.travelsky.airportapp.utils.db.DataBaseDao;
import com.travelsky.airportapp.view.CalendarView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * @author iwangtianyi
 *         按条件查找航班
 */
public class SearchInfoActivity extends Activity implements View.OnClickListener {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private LayoutInflater mInflater;
    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private View view1, view2, view3;//页卡视图
    private List<View> mViewList = new ArrayList<>();//页卡视图集合
    private int startTime = 0;
    private int endTime = 24;
    private String request_date = null; // 出发日期
    private TextView tv_start_time;
    private TextView tv_end_time;
    private RangeBar rangeBar;
    private RelativeLayout ll_add_no;
    private RelativeLayout ll_add_seat;
    private RelativeLayout ll_date_time;

    private static final int DATA_NO = 1;
    private static final int DATA_SEAT = 2;
    private static final int DATA_TIME = 3;

    private TextView tv_add_no;
    private TextView tv_add_seat;
    private TextView tv_add_time;
    private Button search1;
    private String dateShow;
    private TextView search_hao;
    private RequestQueue queue;
    private Button search2;
    private Button search3;
    private Intent intent;
    private String json11;
    private String Ts;
    private String s;
    private String s1;
    private String resultJson;
    private String times;
    private String dateFromatStr;
    private EditText planeseat;
    private String jiwei_time;
    private String planeseats;
    private ArrayList<SearchBean> list;
    private TextView startTime1;
    private TextView endtime1;
    private String start_time;
    private String endtime;
    private String starttimes;
    private String endtimes;
    private String userSeq;
    private String userId;
    private Map<String, String> map;
    private CharSequence dateShow1;
    private ProgressBar pb_planno, pb_seat, pb_time;
    private String line;
    private DataBaseDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_info);
        mViewPager = (ViewPager) findViewById(R.id.vp_search);
        mTabLayout = (TabLayout) findViewById(R.id.tab_FindFragment_title);
        userSeq = CacheUtils.getString(SearchInfoActivity.this, CacheUtils.user_seq);
        userId = CacheUtils.getString(SearchInfoActivity.this, CacheUtils.userId);
        initBar();
        dao = new DataBaseDao(SearchInfoActivity.this);
        mInflater = LayoutInflater.from(this);
        //航班号搜索页面
        view1 = mInflater.inflate(R.layout.airportaddplanebynofragment_layout, null);
        //机位号搜索页面
        view2 = mInflater.inflate(R.layout.airportaddplanebyseatfragment_layout, null);
        //起飞时间搜索页面
        view3 = mInflater.inflate(R.layout.airportaddplanebytimefragment_layout, null);

        //航班号---------------------------------------
        ll_add_no = (RelativeLayout) view1.findViewById(R.id.ll_add_no);
        ll_add_no.setOnClickListener(this);
        tv_add_no = (TextView) view1.findViewById(R.id.tv_add_no);
        //航班号
        search_hao = (TextView) view1.findViewById(R.id.ed_planecode);
        pb_planno = (ProgressBar) view1.findViewById(R.id.pb_planno);
        search_hao.setTransformationMethod(new A2bigA());
        //获取当天的日期
        dateShow = CalendarView.getDate(0);
        tv_add_no.setText(dateShow);
        //搜索按钮
        search1 = (Button) view1.findViewById(R.id.search_button);
        search1.setOnClickListener(new MyClick());

        //机位号-------------------------------------
        ll_add_seat = (RelativeLayout) view2.findViewById(R.id.ll_add_seat);
        //日期选择的点击事件
        ll_add_seat.setOnClickListener(this);
        //日期
        tv_add_seat = (TextView) view2.findViewById(R.id.tv_add_seat);
        tv_add_seat.setText(dateShow);
        //机位号
        planeseat = (EditText) view2.findViewById(R.id.ed_planeseat);
        //搜索按钮
        search2 = (Button) view2.findViewById(R.id.search_button1);

        pb_seat = (ProgressBar) view2.findViewById(R.id.pb_seat);
        search2.setOnClickListener(new MyClick());

        //起飞时间段---------------------------------------
        ll_date_time = (RelativeLayout) view3.findViewById(R.id.ll_date_time);
        ll_date_time.setOnClickListener(this);
        tv_add_time = (TextView) view3.findViewById(R.id.tv_add_time);
        dateShow1 = tv_add_no.getText();
        LogUtil.d("---时间", dateShow1.toString());
        tv_add_time.setText(dateShow);
        //获取选择的开始时间和结束时间
        startTime1 = (TextView) view3.findViewById(R.id.tv_start_time);
        endtime1 = (TextView) view3.findViewById(R.id.tv_end_time);
        search3 = (Button) view3.findViewById(R.id.search_button3);
        search3.setOnClickListener(new MyClick());
        pb_time = (ProgressBar) view3.findViewById(R.id.pb_time);

        //--------------------------------------------
        rangeBar = (RangeBar) view3.findViewById(R.id.rangebar);
        tv_start_time = (TextView) view3.findViewById(R.id.tv_start_time);
        tv_end_time = (TextView) view3.findViewById(R.id.tv_end_time);

        rangeBar.setOnRangeBarChangeListener(new MyRangBar());
        //添加页卡视图
        mViewList.add(view1);
        mViewList.add(view2);
        mViewList.add(view3);

        //添加页卡标题
        mTitleList.add("按航班号");
        mTitleList.add("按机位号");
        mTitleList.add("按起飞时段");

        mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));//添加tab选项卡
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(2)));

        MyPagerAdapter mAdapter = new MyPagerAdapter(mViewList);
        mViewPager.setAdapter(mAdapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        mTabLayout.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器

        //设置ViewPager的滑动监听
        mViewPager.addOnPageChangeListener(new MyPagerChangListener());

        //向左滑动的透明页
        SlidingLayout layout = new SlidingLayout(this);
        layout.bindActivity(this);

        //判断是否搜索本地数据
        line = getIntent().getStringExtra("line");
    }

    /**
     * 设置RangeBar的监听事件
     */
    class MyRangBar implements RangeBar.OnRangeBarChangeListener {

        @Override
        public void onIndexChangeListener(RangeBar rangeBar, int leftThumbIndex, int rightThumbIndex) {
            startTime = leftThumbIndex * 4;
            if (startTime < 10) {
                tv_start_time.setText("0" + startTime + ":00");
            } else {
                tv_start_time.setText(startTime + ":00");
            }

            endTime = rightThumbIndex * 4;
            if (endTime < 10) {
                tv_end_time.setText("0" + endTime + ":00");
            } else {
                tv_end_time.setText(endTime + ":00");
            }
        }
    }

    /**
     * 搜索信息界面
     * 航班号
     * 机位号
     * 时间段
     */
    public class MyClick implements View.OnClickListener {

        private String formatStr;

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                //按航班号搜索
                case R.id.search_button:

                    pb_planno.setVisibility(View.VISIBLE);
                    times = tv_add_no.getText().toString();
                    Ts = String.valueOf(System.currentTimeMillis()).substring(0, 10);
                    formatStr = "yyyyMMdd";
                    dateFromatStr = "yyyy-MM-dd";
                    //转换为20160503 的格式
                    s = DateToString.StringToDate(times, dateFromatStr, formatStr);
                    LogUtil.d("------------time", s);
                    //得到大写的字母
                    s1 = search_hao.getText().toString().toUpperCase();
                    LogUtil.d("------------航班号", s1);
                    //如果为空
                    if (s1.isEmpty()) {
                        ToastBig.toast(SearchInfoActivity.this, "请输入航班号");
                        pb_planno.setVisibility(View.GONE);
                        return;
                    } else {
                        json11 = "{\"UserId\":\"" + userId + "\",\"User_Seq\":\"" + userSeq + "\",\"flight_date\":\"" + s + "\",\"flight_num_two\":\"" + s1 + "\",\"Ts\":\"" + Ts + "\",\"Sign\":\"" + GetResponseMysign() + "\"}";
                        LogUtil.d("-----------------json11", json11);
                        LogUtil.d("-----------------接口", Cantons.FlightList);

                        if ("online".equals(line)) {

                            if (NetworkUtils.checkNetwork(SearchInfoActivity.this)) {
                                //请求网络
//                                requestInternet();
                                //发送posts请求
                                OkHttpUtils
                                        .postString()
                                        .url(Cantons.FlightList)
                                        .addHeader("User-Agent", "PDCS-APP")
                                        .mediaType(MediaType.parse("application/json; charset=utf-8"))
                                        .content(json11)
                                        .build()
                                        .execute(new SearchInfoCallback1());
                            } else {
                                pb_planno.setVisibility(View.GONE);
                                ToastBig.toast(SearchInfoActivity.this, "网络连接不可用");
                                Intent intent = new Intent(SearchInfoActivity.this, MainActivity.class);
                                intent.setClassName("com.android.settings", "com.android.settings.Settings");
                                startActivity(intent);
                            }

                        } else {
                            search_hao_off();
                        }
                    }

                    break;
                //按机位号搜索
                case R.id.search_button1:
                    pb_seat.setVisibility(View.VISIBLE);
                    LogUtil.d("---------------机位号入口", "机位号");
                    times = tv_add_seat.getText().toString();
                    Ts = String.valueOf(System.currentTimeMillis()).substring(0, 10);
                    formatStr = "yyyyMMdd";
                    dateFromatStr = "yyyy-MM-dd";
                    //转换为20160503 的格式
                    jiwei_time = DateToString.StringToDate(times, dateFromatStr, formatStr);
                    //选中的日期
                    LogUtil.d("------------time", SearchInfoActivity.this.jiwei_time);
                    planeseats = SearchInfoActivity.this.planeseat.getText().toString();
                    //输入的机位号
                    LogUtil.d("------------机位号", planeseats);
                    if (planeseats.isEmpty()) {
                        ToastBig.toast(SearchInfoActivity.this, "请输入机位号");
                        pb_seat.setVisibility(View.GONE);
                        return;
                    } else {

                        json11 = "{\"UserId\":\"" + userId + "\",\"User_Seq\":\"" + userSeq + "\",\"flight_date\":\"" + SearchInfoActivity.this.jiwei_time + "\",\"slot\":\"" + planeseats + "\",\"Ts\":\"" + Ts + "\",\"Sign\":\"" + GetResponseMysign1() + "\"}";
                        LogUtil.d("-----------------json11", json11);
                        LogUtil.d("-----------------接口", Cantons.FlightListBySlot);
                        if ("online".equals(line)) {
                            if (NetworkUtils.checkNetwork(SearchInfoActivity.this)) {
                                //请求网络
//                                requestInternet1();
                                OkHttpUtils
                                        .postString()
                                        .url(Cantons.FlightListBySlot)
                                        .addHeader("User-Agent", "PDCS-APP")
                                        .mediaType(MediaType.parse("application/json; charset=utf-8"))
                                        .content(json11)
                                        .build()
                                        .execute(new SearchInfoCallback2());
                            } else {
                                pb_seat.setVisibility(View.GONE);
                                ToastBig.toast(SearchInfoActivity.this, "网络连接不可用");
                                Intent intent = new Intent(SearchInfoActivity.this, MainActivity.class);
                                intent.setClassName("com.android.settings", "com.android.settings.Settings");
                                startActivity(intent);
                            }
                        } else {
                            search_planeseats_off();
                        }
                    }

                    break;
                //按起飞时间段搜索
                case R.id.search_button3:
                    pb_time.setVisibility(View.VISIBLE);

                    LogUtil.d("---------------时间段入口", "时间段");
                    start_time = startTime1.getText().toString() + ":00";
                    endtime = endtime1.getText().toString() + ":00";
                    if ("24:00:00".equals(endtime)) {
                        endtime = "23:59:59";
                    }

                    LogUtil.d("----", start_time);
                    LogUtil.d("----", endtime);
                    //时间戳
                    Ts = String.valueOf(System.currentTimeMillis()).substring(0, 10);
                    //最后选择的航班时间
                    CharSequence text = tv_add_time.getText();
                    String string = text.toString();
                    String formatStr = "yyyy-MM-dd";
                    String dateFromatStr = "yyyyMMdd";
                    //转换为2016-05-03 02:33:00 的格式
                    String qianxu1 = DateToString.StringToDate(string, formatStr, dateFromatStr);
                    LogUtil.d("---最后时间", qianxu1);
                    LogUtil.d("---最后时间", string);
                    starttimes = string + " " + start_time;
                    endtimes = string + " " + endtime;
                    LogUtil.e("TAG", "starttimes" + starttimes);
                    LogUtil.e("TAG", "endtimes" + endtimes);
                    Logger.d("时间", starttimes);
                    Logger.d("时间", endtimes);
                    json11 = "{\"UserId\":\"" + userId + "\",\"User_Seq\":\"" + userSeq + "\",\"flight_dateStart\":\"" + starttimes + "\",\"flight_dateEnd\":\"" + endtimes + "\",\"ToDateTime\":\"" + qianxu1 + "\",\"Ts\":\"" + Ts + "\",\"Sign\":\"" + GetResponseMysign2() + "\"}";
                    LogUtil.d("-----------------json11", json11);
                    LogUtil.d("-----------------接口", Cantons.FlightListByTime);

                    if ("online".equals(line)) {
                        if (NetworkUtils.checkNetwork(SearchInfoActivity.this)) {
                            //请求网络
//                            requestInternet2();
                            OkHttpUtils
                                    .postString()
                                    .url(Cantons.FlightListByTime)
                                    .addHeader("User-Agent", "PDCS-APP")
                                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                                    .content(json11)
                                    .build()
                                    .execute(new SearchInfoCallback3());
                        } else {
                            pb_time.setVisibility(View.GONE);
                            ToastBig.toast(SearchInfoActivity.this, "网络连接不可用");
                            Intent intent = new Intent(SearchInfoActivity.this, MainActivity.class);
                            intent.setClassName("com.android.settings", "com.android.settings.Settings");
                            startActivity(intent);
                        }
                    } else {
                        //如果没有联网就从本地搜索
                        search_time_off();
                    }
                    break;
            }
        }

        //按起飞时间找本地航班
        private void search_time_off() {
            LogUtil.e("TAG", "search_time_off");

            //最后选择的航班时间
            CharSequence text = tv_add_time.getText();
            String string = text.toString();
            String formatStr = "yyyy-MM-dd";
            String dateFromatStr = "yyyyMMdd";
            //转换为2016-05-03 02:33:00 的格式
            String qianxu1 = DateToString.StringToDate(string, formatStr, dateFromatStr);
            final List<FlightData.AccaFlightDateBean> accaFlightToday = dao.getAccaFlightToday(qianxu1);
            if (accaFlightToday == null) {
                ToastBig.toast(SearchInfoActivity.this, "本地没有数据");
                return;
            }
            LogUtil.d("---本地搜索传入日期得到", accaFlightToday.size() + "");
            start_time = startTime1.getText().toString() + ":00";
            endtime = endtime1.getText().toString() + ":00";
            if ("24:00:00".equals(endtime)) {
                endtime = "23:59:59";
            }

            final String stime = DateToString.StringToDate(qianxu1 + start_time, "yyyyMMddHH:mm:ss", "yyyyMMddHHmmss");
            final String etime = DateToString.StringToDate(qianxu1 + endtime, "yyyyMMddHH:mm:ss", "yyyyMMddHHmmss");
            LogUtil.d("---本地搜索开始时间", stime);
            LogUtil.d("---本地搜索结束时间", etime);
            long startl = System.currentTimeMillis();
            Date date1 = new Date(startl);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            LogUtil.e("TAG", "formatoff1==" + format.format(date1));
            search3.setClickable(false);
            new Thread() {
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pb_time.setVisibility(View.VISIBLE);
                        }
                    });

                    List<FlightData.AccaFlightDateBean> accaFlightByTime = null;
                    for (int i = 0; i < accaFlightToday.size(); i++) {
                        if (accaFlightToday.get(i).get_real_dep_time() != 0) {
                            accaFlightByTime = dao.getRealDepTime(stime, etime);

                        }
                        if (accaFlightToday.get(i).get_real_arr_time() != 0) {
                            accaFlightByTime = dao.getRealArrTime(stime, etime);
                        }
                        if (accaFlightToday.get(i).get_alter_dep_time() != 0) {
                            accaFlightByTime = dao.getAlterDepTime(stime, etime);
                        }
                        if (accaFlightToday.get(i).get_alter_arr_time() != 0) {
                            accaFlightByTime = dao.getAlterArrTime(stime, etime);
                        }
                        if (accaFlightToday.get(i).get_scheme_dep_time() != 0) {
                            accaFlightByTime = dao.getSchemeDepTime(stime, etime);
                        }
                        if (accaFlightToday.get(i).get_scheme_arr_time() != 0) {
                            accaFlightByTime = dao.getSchemeArrTime(stime, etime);

                        }

                    }

                    if (accaFlightByTime == null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastBig.toast(SearchInfoActivity.this, "没有航班信息");
                                pb_time.setVisibility(View.GONE);
                            }
                        });
                        return;
                    }

                    Intent intent = new Intent(SearchInfoActivity.this, OfflineSearchActivity.class);
                    intent.putExtra("offlineSearch", (Serializable) accaFlightByTime);
                    startActivity(intent);
                    long endl = System.currentTimeMillis();

                    Date date1 = new Date(endl);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    LogUtil.e("TAG", "formatoff2==" + format.format(date1));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pb_time.setVisibility(View.GONE);
                        }
                    });

                    finish();
                }
            }.start();
        }

        //按机位号查找本地航班
        private void search_planeseats_off() {
            LogUtil.d("TAG", "search_planeseats_off");
            pb_seat.setVisibility(View.GONE);
            List<FlightData.AccaFlightDateBean> accaFlightByPlaneseats = dao.getAccaFlightByPlaneseats(SearchInfoActivity.this.jiwei_time, planeseats);

            if (0 == accaFlightByPlaneseats.size()) {
                ToastBig.toast(SearchInfoActivity.this, "没有航班信息");
                return;
            }

            Intent intent = new Intent(SearchInfoActivity.this, OfflineSearchActivity.class);
            intent.putExtra("offlineSearch", (Serializable) accaFlightByPlaneseats);
            startActivity(intent);

            finish();

        }

        //按航班号查找本地航班
        private void search_hao_off() {
            LogUtil.d("TAG", "search_hao_off");
            pb_planno.setVisibility(View.GONE);
            List<FlightData.AccaFlightDateBean> accaFlightByFlightNo = dao.getAccaFlightByFlightNo(s, s1);

            if (0 == accaFlightByFlightNo.size()) {
                ToastBig.toast(SearchInfoActivity.this, "没有航班信息");
                return;
            }

            Intent intent = new Intent(SearchInfoActivity.this, OfflineSearchActivity.class);
            intent.putExtra("offlineSearch", (Serializable) accaFlightByFlightNo);
            startActivity(intent);

            finish();
        }
    }

    /**
     * 一
     * 传递给后台的数据 包括时间戳 签名
     *
     * @return
     */
    public String GetResponseMysign() {
        String APIPrivateKey = "2D7E7C96-DAC5-4526-96C3-C60CDEC4B120";
        map = new HashMap<>();
        map.put("flight_date", s);
        map.put("flight_num_two", s1);
        map.put("ts", Ts);
        map.put("user_seq", userSeq);
        String key = CacheUtils.GetPostStrings(map) + APIPrivateKey;
        return FileHelper.ToMD5(key);
    }

    /**
     * 一
     * 传递给后台的数据 包括时间戳 签名
     *
     * @return
     */
    public String GetResponseMysign1() {
        String APIPrivateKey = "2D7E7C96-DAC5-4526-96C3-C60CDEC4B120";
//        String fullstring = "flight_date=" + jiwei_time + "&slot=" + planeseats + "&ts=" + Ts + "&user_seq=" + userSeq + APIPrivateKey;
        Map<String, String> map1 = new HashMap<>();
        map1.put("flight_date", jiwei_time);
        map1.put("slot", planeseats);
        map1.put("ts", Ts);
        map1.put("user_seq", userSeq);
        String key = CacheUtils.GetPostStrings(map1) + APIPrivateKey;
        return FileHelper.ToMD5(key);
    }

    /**
     * 一
     * 传递给后台的数据 包括时间戳 签名
     *
     * @return
     */
    public String GetResponseMysign2() {
        String APIPrivateKey = "2D7E7C96-DAC5-4526-96C3-C60CDEC4B120";
//        String fullstring = "flight_dateend=" + endtimes + "&flight_datestart=" + starttimes + "&ts=" + Ts + "&user_seq=" + userSeq + APIPrivateKey;
//        Logger.d("签名", fullstring);
        Map<String, String> map2 = new HashMap<>();
        map2.put("flight_dateend", endtimes);
        map2.put("flight_datestart", starttimes);
        map2.put("ts", Ts);
        map2.put("user_seq", userSeq);
        String key = CacheUtils.GetPostStrings(map2) + APIPrivateKey;
        return FileHelper.ToMD5(key);
    }

    //联网请求  航班号
    public void requestInternet() {
        new Thread(new Runnable() {
            public void run() {
                resultJson = PostRegistLogin2(Cantons.FlightList, json11);
                list = new ArrayList<>();
                LogUtil.d("---resultjosn", resultJson);
                try {
                    JSONArray jsonArray = new JSONArray(resultJson);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String string = jsonArray.getString(i);
                        SearchBean searchBean = new Gson().fromJson(string, SearchBean.class);
                        list.add(searchBean);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (1 == list.get(0).getResCode()) {
                    intent = new Intent(SearchInfoActivity.this, AirportSearchResultListActivity.class);
                    intent.putExtra("json", resultJson);
                    startActivity(intent);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pb_planno.setVisibility(View.GONE);
                        }
                    });
                    finish();
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if ("数据为空".equals(list.get(0).getResMsg())) {
                                ToastBig.toast(SearchInfoActivity.this, "未找到航班信息");
                            } else {
                                ToastBig.toast(SearchInfoActivity.this, list.get(0).getResMsg());
                            }
                            pb_planno.setVisibility(View.GONE);
                        }
                    });
                }
            }
        }).start();
    }

    //联网请求  机位号
    public void requestInternet1() {
        new Thread(new Runnable() {
            public void run() {
                resultJson = PostRegistLogin2(Cantons.FlightListBySlot, json11);
                list = new ArrayList<>();

                try {
                    JSONArray jsonArray = new JSONArray(resultJson);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String string = jsonArray.getString(i);
                        SearchBean searchBean = new Gson().fromJson(string, SearchBean.class);
                        list.add(searchBean);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (1 == list.get(0).getResCode()) {

                    intent = new Intent(SearchInfoActivity.this, AirportSearchResultListActivity.class);
                    intent.putExtra("json", resultJson);
                    startActivity(intent);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pb_seat.setVisibility(View.GONE);
                        }
                    });
                    finish();
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if ("数据为空".equals(list.get(0).getResMsg())) {

                                ToastBig.toast(SearchInfoActivity.this, "未找到航班信息");
                            } else {
                                ToastBig.toast(SearchInfoActivity.this, list.get(0).getResMsg());
                            }
                            pb_seat.setVisibility(View.GONE);
                        }
                    });
                }

            }
        }).start();
    }

    //联网请求  时间段
    public void requestInternet2() {
        new Thread(new Runnable() {
            public void run() {
                resultJson = PostRegistLogin2(Cantons.FlightListByTime, json11);
                LogUtil.d("---时间段请求数据", resultJson);
                list = new ArrayList<>();

                try {
                    JSONArray jsonArray = new JSONArray(resultJson);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String string = jsonArray.getString(i);
                        SearchBean searchBean = new Gson().fromJson(string, SearchBean.class);
                        list.add(searchBean);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (1 == list.get(0).getResCode()) {
                    intent = new Intent(SearchInfoActivity.this, AirportSearchResultListActivity.class);
                    intent.putExtra("json", resultJson);
                    startActivity(intent);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pb_time.setVisibility(View.GONE);
                        }
                    });
                    finish();
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pb_time.setVisibility(View.GONE);
                            if ("数据为空".equals(list.get(0).getResMsg())) {

                                ToastBig.toast(SearchInfoActivity.this, "未找到航班信息");
                            } else {
                                ToastBig.toast(SearchInfoActivity.this, list.get(0).getResMsg());
                            }
                        }
                    });
                }

            }
        }).start();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED && data != null) {
            String date = data.getStringExtra("date");
            if (requestCode == DATA_NO && resultCode == RESULT_OK) {
                tv_add_no.setText(date);
            } else if (requestCode == DATA_SEAT && resultCode == RESULT_OK) {
                tv_add_seat.setText(date);
            } else if (requestCode == DATA_TIME && resultCode == RESULT_OK) {
                tv_add_time.setText(date);
            }
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
     * 初始化action bar
     */
    private void initBar() {
        //得到Actionbar
        ActionBar actionBar = getActionBar();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayUseLogoEnabled(false);
        actionBar.setTitle("搜索信息");
        //设置Actionbar的背景
        try {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.bar11));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        int i = 0;
        switch (v.getId()) {
            //航班号页面
            case R.id.ll_add_no:
                i = DATA_NO;
                break;
            //机位号页面
            case R.id.ll_add_seat:
                i = DATA_SEAT;
                break;
            //起飞时间段页面
            case R.id.ll_date_time:
                i = DATA_TIME;
                break;
        }
        Intent intent = new Intent(SearchInfoActivity.this, SelectDateActivity.class);
        intent.putExtra("UnusePreviousDate", true);
        intent.putExtra("date", request_date);
        startActivityForResult(intent, i);

    }

    class MyPagerChangListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //发送ViewPager滑动的位置
            //搜索信息页面可以向左滑动
            //EventBus.getDefault().post(new String("" + position));
        }

        @Override
        public void onPageSelected(int position) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    //ViewPager适配器
    class MyPagerAdapter extends PagerAdapter {
        private List<View> mViewList;

        public MyPagerAdapter(List<View> mViewList) {
            this.mViewList = mViewList;
        }

        @Override
        public int getCount() {
            return mViewList.size();//页卡数
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;//官方推荐写法
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            mViewList.get(position);
            container.addView(mViewList.get(position));//添加页卡
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));//删除页卡
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);//页卡标题
        }

    }

    public void toSearchResult(View v) {
        Intent intent = new Intent(SearchInfoActivity.this, AirportSearchResultListActivity.class);
        startActivity(intent);
    }

    public void back(View v) {
        AnimationSet animation = new AnimationSet(true);

        AlphaAnimation alpha = new AlphaAnimation(1, 0);
        alpha.setDuration(1000);

        ScaleAnimation scale = new ScaleAnimation(1, 1.5f, 1, 1.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scale.setDuration(1000);

        animation.addAnimation(alpha);
        animation.addAnimation(scale);

        this.findViewById(R.id.ll_search_animation).startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                SearchInfoActivity.this.finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * https的请求，
     * 按航班号的请求回调
     */
    private class SearchInfoCallback1 extends StringCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            e.printStackTrace();
            Toast.makeText(SearchInfoActivity.this, "onError:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response, int id) {
            LogUtil.d("onResponse", response);
            list = new ArrayList<>();
            LogUtil.d("---resultjosn", response);
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    String string = jsonArray.getString(i);
                    SearchBean searchBean = new Gson().fromJson(string, SearchBean.class);
                    list.add(searchBean);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (1 == list.get(0).getResCode()) {
                intent = new Intent(SearchInfoActivity.this, AirportSearchResultListActivity.class);
                intent.putExtra("json", response);
                startActivity(intent);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pb_planno.setVisibility(View.GONE);
                    }
                });
                finish();
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if ("数据为空".equals(list.get(0).getResMsg())) {
                            ToastBig.toast(SearchInfoActivity.this, "未找到航班信息");
                        } else {
                            ToastBig.toast(SearchInfoActivity.this, list.get(0).getResMsg());
                        }
                        pb_planno.setVisibility(View.GONE);
                    }
                });
            }
        }
    }

    /**
     * https的请求，
     * 按机位号的请求回调
     */
    private class SearchInfoCallback2 extends StringCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            e.printStackTrace();
            Toast.makeText(SearchInfoActivity.this, "onError:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response, int id) {
            LogUtil.d("onResponse", response);
            list = new ArrayList<>();

            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    String string = jsonArray.getString(i);
                    SearchBean searchBean = new Gson().fromJson(string, SearchBean.class);
                    list.add(searchBean);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (1 == list.get(0).getResCode()) {

                intent = new Intent(SearchInfoActivity.this, AirportSearchResultListActivity.class);
                intent.putExtra("json", response);
                startActivity(intent);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pb_seat.setVisibility(View.GONE);
                    }
                });
                finish();
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if ("数据为空".equals(list.get(0).getResMsg())) {

                            ToastBig.toast(SearchInfoActivity.this, "未找到航班信息");
                        } else {
                            ToastBig.toast(SearchInfoActivity.this, list.get(0).getResMsg());
                        }
                        pb_seat.setVisibility(View.GONE);
                    }
                });
            }
        }
    }

    /**
     * https的请求，
     * 按起飞时间段的请求回调
     */
    private class SearchInfoCallback3 extends StringCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            e.printStackTrace();
            Toast.makeText(SearchInfoActivity.this, "onError:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response, int id) {
            LogUtil.d("onResponse", response);
            list = new ArrayList<>();

            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    String string = jsonArray.getString(i);
                    SearchBean searchBean = new Gson().fromJson(string, SearchBean.class);
                    list.add(searchBean);

                }
            } catch (JSONException e) {
                e.printStackTrace();
                LogUtil.e("TAG", "1230");
            }
            if (1 == list.get(0).getResCode()) {
                intent = new Intent(SearchInfoActivity.this, AirportSearchResultListActivity.class);
                intent.putExtra("json", response);
                startActivity(intent);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pb_time.setVisibility(View.GONE);
                    }
                });
                finish();
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pb_time.setVisibility(View.GONE);
                        if ("数据为空".equals(list.get(0).getResMsg())) {

                            ToastBig.toast(SearchInfoActivity.this, "未找到航班信息");
                        } else {
                            ToastBig.toast(SearchInfoActivity.this, list.get(0).getResMsg());
                        }
                    }
                });
            }
        }
    }
}