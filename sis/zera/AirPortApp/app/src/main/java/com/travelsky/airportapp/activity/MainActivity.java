package com.travelsky.airportapp.activity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.travelsky.airportapp.Cantons;
import com.travelsky.airportapp.R;
import com.travelsky.airportapp.adapter.FlightListAdapter;
import com.travelsky.airportapp.adapter.FlightShowAdapter;
import com.travelsky.airportapp.domain.DataLoad;
import com.travelsky.airportapp.domain.FlightData;
import com.travelsky.airportapp.domain.FlightShow;
import com.travelsky.airportapp.utils.AirportApplication;
import com.travelsky.airportapp.utils.CacheUtils;
import com.travelsky.airportapp.utils.FileHelper;
import com.travelsky.airportapp.utils.Key;
import com.travelsky.airportapp.utils.LogUtil;
import com.travelsky.airportapp.utils.NetworkUtils;
import com.travelsky.airportapp.utils.TimeString2Data2Long;
import com.travelsky.airportapp.utils.ToastBig;
import com.travelsky.airportapp.utils.db.DataBaseDao;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import de.greenrobot.event.EventBus;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;

/**
 * @author iwangtianyi
 *         首页
 */
public class MainActivity extends FragmentActivity {

    private Intent intent;
    private ExpandableListView lv;
    private String Ts;
    private String APIPrivateKey = "2D7E7C96-DAC5-4526-96C3-C60CDEC4B120";

    private String json;
    private LinearLayout lladd;
    private FlightListAdapter adapter;
    private FlightData flightData;
    private String sign;
    private String user_seq;
    private String userId;
    private String jsonDel;
    private ProgressBar pb_main;
    private DataBaseDao dao;
    private String flight_seq = "";
    private List<FlightData.AccaFlightDateBean> accaFlightToday;
    private List<FlightData.AccaFlightDateBean> accaFlightYesterday;
    private FlightShow flightShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ExpandableListView) findViewById(R.id.lv);
        lladd = (LinearLayout) findViewById(R.id.lladd);
        dao = new DataBaseDao(MainActivity.this);
        pb_main = (ProgressBar) findViewById(R.id.pb_main);

        //得到Actionbar
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayUseLogoEnabled(false);
        //设置Actionbar的背景
        try {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.bar11));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        userId = CacheUtils.getString(MainActivity.this, "userId");
        user_seq = CacheUtils.getString(MainActivity.this, "user_seq");
        //获取10位的时间戳
        Ts = String.valueOf(System.currentTimeMillis()).substring(0, 10);

        final LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("Ts", Ts);
        map.put("user_seq", user_seq);
        map.put("userId", userId);

        sign = Key.GetResponseMysign(map, APIPrivateKey);

        dowUserFlightData(userId, user_seq, Ts, sign);

        long l = System.currentTimeMillis();
        Date date = new Date(l);
        String today = TimeString2Data2Long.dateToString(date, "yyyyMMdd");

        accaFlightToday = dao.getAccaFlightToday(today);
        //获取昨天的时间
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        final String yesterday = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
        accaFlightYesterday = dao.getAccaFlightToday(yesterday);

        //点击进入保障详情
        lv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //groupPosition 0是当前航班 1是历史航班
                //childPosition 表示所点击的航班值
                LogUtil.d("TAG", "groupPosition" + groupPosition);
                LogUtil.d("TAG", "childPosition" + childPosition);
                Intent intent = new Intent(MainActivity.this, SecurityDetailsSinglActivity.class);

                List<FlightData.AccaFlightDateBean> accaFlight = null;
                //当前航班0
                if (groupPosition == 0) {
                    if (NetworkUtils.checkNetwork(MainActivity.this)) {

                        CacheUtils.putString(MainActivity.this, CacheUtils.Fight_Seq, flightShow.getData().get(childPosition).getFlight_Seq());
                        accaFlight = dao.getAccaFlightBySeq(flightShow.getData().get(childPosition).getFlight_Seq());
                    } else {

                        CacheUtils.putString(MainActivity.this, CacheUtils.Fight_Seq, accaFlightToday.get(childPosition).get_flight_seq());
                        accaFlight = dao.getAccaFlightBySeq(accaFlightToday.get(childPosition).get_flight_seq());
                    }
                    //历史航班1
                } else if (groupPosition == 1) {

                    if (NetworkUtils.checkNetwork(MainActivity.this)) {

                        CacheUtils.putString(MainActivity.this, CacheUtils.Fight_Seq, flightShow.getTheDayData().get(childPosition).getFlight_Seq());
                        accaFlight = dao.getAccaFlightBySeq(flightShow.getTheDayData().get(childPosition).getFlight_Seq());
                    } else {
                        CacheUtils.putString(MainActivity.this, CacheUtils.Fight_Seq, accaFlightYesterday.get(childPosition).get_flight_seq());
                        accaFlight = dao.getAccaFlightBySeq(accaFlightYesterday.get(childPosition).get_flight_seq());
                    }
                }
                intent.putExtra("offline", (Serializable) accaFlight);
                startActivity(intent);

                return true;
            }
        });
        //长按删除
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View childView, int flatPos, long id) {
                if (ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                    long packedPos = ((ExpandableListView) parent).getExpandableListPosition(flatPos);
                    int groupPosition = ExpandableListView.getPackedPositionGroup(packedPos);
                    int childPosition = ExpandableListView.getPackedPositionChild(packedPos);
                    //删除当前航班信息
                    if (groupPosition == 0) {
                        if (NetworkUtils.checkNetwork(MainActivity.this)) {

                            flight_seq = flightShow.getData().get(childPosition).getFlight_Seq();
                        } else {
                            flight_seq = accaFlightToday.get(childPosition).get_flight_seq();
                        }

                        new AlertDialog.Builder(MainActivity.this)
                                .setMessage("是否在航班列表中删除此航班？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        pb_main.setVisibility(View.VISIBLE);
                                        if (NetworkUtils.checkNetwork(MainActivity.this)) {
                                            delFlight(flight_seq);
                                        } else {
                                            dao.updateAccauserFlightDate(flight_seq);
                                            dao.updateAccaFlightDate(flight_seq);
                                            InternetRequest(userId, user_seq, Ts, sign);

                                        }
                                    }
                                })
                                .setNegativeButton("取消", null)
                                .show();
                    } else {
                        //删除历史航班信息
                        if (NetworkUtils.checkNetwork(MainActivity.this)) {

                            flight_seq = flightShow.getTheDayData().get(childPosition).getFlight_Seq();
                        } else {
                            flight_seq = accaFlightYesterday.get(childPosition).get_flight_seq();
                        }
                        new AlertDialog.Builder(MainActivity.this)
                                .setMessage("是否在航班列表中删除此航班？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        pb_main.setVisibility(View.VISIBLE);
                                        if (NetworkUtils.checkNetwork(MainActivity.this)) {
                                            delFlight(flight_seq);

                                        } else {
                                            dao.updateAccauserFlightDate(flight_seq);
                                            dao.updateAccaFlightDate(flight_seq);
                                            InternetRequest(userId, user_seq, Ts, sign);
                                        }
                                    }
                                })
                                .setNegativeButton("取消", null)
                                .show();
                    }

                    return true;
                }

                return false;
            }

        });

    }

    //保存数据
    public void dowUserFlightData(String userId, String user_seq, String Ts, String sign) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lladd.setVisibility(View.VISIBLE);
                pb_main.setVisibility(View.VISIBLE);
            }
        });

        DataLoad load = new DataLoad(userId, user_seq, null, Ts, sign);
        final String jsonAdd = new Gson().toJson(load);
        LogUtil.d("---首页提交的数据", jsonAdd);
        new Thread() {
            public void run() {

                if (NetworkUtils.checkNetwork(MainActivity.this)) {

                    PostDowUserFlightData(jsonAdd);

                }

            }
        }.start();
    }


    //长按删除航班
    public void delFlight(final String flight_seq) {
        pb_main.setVisibility(View.VISIBLE);
        String user_Id = CacheUtils.getString(MainActivity.this, "userId");
        String userSeq = CacheUtils.getString(MainActivity.this, "user_seq");
        //获取10位的时间戳
        String ts = String.valueOf(System.currentTimeMillis()).substring(0, 10);

        Map<String, String> mapDel = new HashMap<>();
        mapDel.put("user_seq", userSeq);
        mapDel.put("ts", ts);
        mapDel.put("userid", user_Id);

        String signDelete = FileHelper.ToMD5(CacheUtils.GetPostStrings(mapDel) + APIPrivateKey);

        jsonDel = "{\"UserId\":\"" + user_Id + "\",\"User_Seq\":\"" + userSeq + "\",\"Flight_Seq\":\"" + flight_seq + "\",\"Ts\":\"" + ts + "\",\"Sign\":\"" + signDelete + "\"}";

        PostDelFlight(jsonDel);

    }


    @Override
    protected void onResume() {
        super.onResume();

        //获取10位的时间戳
        Ts = String.valueOf(System.currentTimeMillis()).substring(0, 10);

        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("Ts", Ts);
        map.put("user_seq", user_seq);
        map.put("userId", userId);

        sign = Key.GetResponseMysign(map, APIPrivateKey);

        if (NetworkUtils.checkNetwork(MainActivity.this)) {

            InternetRequest(userId, user_seq, Ts, sign);
        } else {

            showData();
        }
    }

    /**
     * 联网请求
     *
     * @param userId   用户ID
     * @param user_seq 用户唯一标识
     * @param Ts       时间戳
     * @param sign     签名
     */
    public void InternetRequest(String userId, String user_seq, String Ts, String sign) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lladd.setVisibility(View.VISIBLE);
                pb_main.setVisibility(View.VISIBLE);
            }
        });

        json = "{\"UserId\":\"" + userId + "\",\"User_Seq\":\"" + user_seq + "\",\"Ts\":\"" + Ts + "\",\"Sign\":\"" + sign + "\"}";

        LogUtil.d("---首页提交的数据", json);
        new Thread() {
            public void run() {

                if (NetworkUtils.checkNetwork(MainActivity.this)) {

                    PostShowFlightList(json);

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showData();
                        }
                    });

                }

            }
        }.start();
    }

    //有网络时，显示数据
    public void showData2() {
        if (flightShow != null) {
            FlightShowAdapter flightShowAdapter = new FlightShowAdapter(MainActivity.this, flightShow.getData(), flightShow.getTheDayData());
            //填充数据到ListView
            lv.setAdapter(flightShowAdapter);
            lv.expandGroup(0);

            lladd.setVisibility(View.GONE);
            pb_main.setVisibility(View.GONE);
            flightShowAdapter.notifyDataSetChanged();
        } else {
            lladd.setVisibility(View.VISIBLE);
            pb_main.setVisibility(View.GONE);
        }

    }

    //无网络时，显示数据
    public void showData() {
        long l = System.currentTimeMillis();
        Date date = new Date(l);
        String today = TimeString2Data2Long.dateToString(date, "yyyyMMdd");

        accaFlightToday = dao.getAccaFlightToday(today);
        //获取昨天的时间
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String yesterday = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
        accaFlightYesterday = dao.getAccaFlightToday(yesterday);

        if (accaFlightToday != null || accaFlightYesterday != null) {
            adapter = new FlightListAdapter(MainActivity.this, accaFlightToday, accaFlightYesterday);
            //填充数据到ListView
            lv.setAdapter(adapter);
            lv.expandGroup(0);
            lladd.setVisibility(View.GONE);
            pb_main.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        } else {
            lladd.setVisibility(View.VISIBLE);
            pb_main.setVisibility(View.GONE);
        }

    }


    public void onEventMainThread(String str) {
        LogUtil.d("TAG", "MainActivity+=+=" + str);

        onResume();
        AirportApplication.net = false;

    }

    //发送post请求 显示航班列表
    private void PostShowFlightList(String json) {
        OkHttpUtils
                .postString()
                .url(Cantons.ShowFlightList)
                .addHeader("User-Agent", "PDCS-APP")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(json)
                .build()
                .execute(new ShowFlightListCallback());
    }

    //显示航班列表回调
    public class ShowFlightListCallback extends StringCallback {
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
            Toast.makeText(MainActivity.this, "onError:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response, int id) {
            LogUtil.d("TAG", "onResponse：complete");
            LogUtil.d("TAG", "onResponse:" + response);

            try {
                JSONObject jsonObject = new JSONObject(response);
                String resMsg = jsonObject.optString("ResMsg");
                String resCode = jsonObject.optString("ResCode");
                if ("-4".equals(resCode)) {

                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.putExtra("request", resMsg + ",重新登陆");
                    startActivity(intent);
                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            flightShow = new Gson().fromJson(response, FlightShow.class);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    showData2();

                }
            });

        }

        @Override
        public void inProgress(float progress, long total, int id) {
            LogUtil.d("TAG", "inProgress:" + progress);
        }
    }

    //发送post请求 下载用户信息
    private void PostDowUserFlightData(String json) {

        OkHttpUtils
                .postString()
                .url(Cantons.DowUserFlightData)
                .addHeader("User-Agent", "PDCS-APP")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(json)
                .build()
                .execute(new DowUserFlightDataCallback());
    }

    //下载用户信息回调
    public class DowUserFlightDataCallback extends StringCallback {
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
            Toast.makeText(MainActivity.this, "onError:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response, int id) {
            LogUtil.d("TAG", "onResponse：complete");
            LogUtil.d("TAG", "onResponse:" + response);

            try {
                JSONObject jsonObject = new JSONObject(response);
                String resMsg = jsonObject.optString("ResMsg");
                String resCode = jsonObject.optString("ResCode");
                if ("-4".equals(resCode)) {

                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.putExtra("request", resMsg + ",重新登陆");
                    startActivity(intent);
                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            flightData = new Gson().fromJson(response, FlightData.class);
            LogUtil.d("---首页数据", response);
            int resCode = flightData.getResCode();
            if (resCode != 0) {
                try {
                    if (flightData.getAccaFlightDate() != null) {
                        dao.delAccaFlightDateBean();
                        dao.addAccaFlightDateBean(flightData.getAccaFlightDate());
                    } else {
                        LogUtil.d("---没有数据111", "没有数据返回");
                    }

                    if (flightData.getAccauserFlightDate() != null) {
                        dao.delAccauserFlightDate();
                        dao.addAccauserFlightDate(flightData.getAccauserFlightDate());
                    } else {
                        LogUtil.d("---没有数据222", "没有数据返回");
                    }

                    if (flightData.getAccaServiceDate() != null) {
                        dao.delAccaServiceDate();

                        dao.saveAccaServiceDate(flightData.getAccaServiceDate());

                    } else {
                        LogUtil.d("---没有数据333", "没有数据返回");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                LogUtil.d("---没有数据", "没有数据返回");
                dao.delAccaFlightDateBean();
                dao.delAccauserFlightDate();
                dao.delAccaServiceDate();
            }

        }

        @Override
        public void inProgress(float progress, long total, int id) {
            LogUtil.d("TAG", "inProgress:" + progress);
        }
    }

    //发送post请求 删除航班信息
    private void PostDelFlight(String json) {

        OkHttpUtils
                .postString()
                .url(Cantons.FlightDelByUser)
                .addHeader("User-Agent", "PDCS-APP")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(json)
                .build()
                .execute(new DelFlightCallback());
    }

    //删除航班回调
    public class DelFlightCallback extends StringCallback {
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
            Toast.makeText(MainActivity.this, "onError:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response, int id) {
            LogUtil.d("TAG", "onResponse：complete");
            LogUtil.d("TAG", "onResponse:" + response);

            try {
                JSONObject jsonObject = new JSONObject(response);
                String resCode = jsonObject.optString("ResCode");
                if ("1".equals(resCode)) {
                    InternetRequest(userId, user_seq, Ts, sign);
                    dao.delAccauserFlightDate(flight_seq);
                    dao.delAccaFlightDateBySeq(flight_seq);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void inProgress(float progress, long total, int id) {
            LogUtil.d("TAG", "inProgress:" + progress);
        }
    }

    /**
     * 加载菜单
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.flightcontrol_menu, menu);
        return true;
    }

    /**
     * action bar 点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //联网搜索航班
            case R.id.search_data:
                intent = new Intent(MainActivity.this, SearchInfoActivity.class);
                intent.putExtra("line", "online");
                startActivity(intent);

                break;
            //本地搜索航班
            case R.id.search_show:
                intent = new Intent(MainActivity.this, SearchInfoActivity.class);
                intent.putExtra("line", "offline");
                startActivity(intent);
                break;
        }
        return true;
    }

    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click(); //调用双击退出函数
        }
        return false;
    }

    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            ToastBig.toast(MainActivity.this, "再按一次退出程序");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        dao.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(MainActivity.this);
    }
}
