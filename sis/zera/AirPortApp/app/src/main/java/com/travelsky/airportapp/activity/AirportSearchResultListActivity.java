package com.travelsky.airportapp.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.travelsky.airportapp.Cantons;
import com.travelsky.airportapp.R;
import com.travelsky.airportapp.adapter.SearchResultAdapter;
import com.travelsky.airportapp.domain.DataLoad;
import com.travelsky.airportapp.domain.Flight;
import com.travelsky.airportapp.domain.FlightData;
import com.travelsky.airportapp.domain.SearchBean;
import com.travelsky.airportapp.utils.AirportApplication;
import com.travelsky.airportapp.utils.CacheUtils;
import com.travelsky.airportapp.utils.FileHelper;
import com.travelsky.airportapp.utils.LogUtil;
import com.travelsky.airportapp.utils.ToastBig;
import com.travelsky.airportapp.utils.db.DataBaseDao;
import com.travelsky.airportapp.view.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * @author wlj
 *         添加航班搜索列表
 */
public class AirportSearchResultListActivity extends Activity implements View.OnClickListener {
    public String tag = "AirportSearchResultListActivity";
    @InjectView(R.id.add_button)
    Button addButton;
    @InjectView(R.id.airport_add_plane_list)
    PullToRefreshListView airportAddPlaneList;
    private ActionBar actionBar;
    private SearchResultAdapter searchResultAdapter;
    private ArrayList<SearchBean> list;
    private TextView search4;

    private TextView search1;
    private TextView search2;
    private TextView search3;
    private TextView search5;
    private int count;
    private SearchBean searchb;
    private JSONArray array;
    private String json;
    private List<String> isCheck = new ArrayList<String>();
    private String Ts;
    private String APIPrivateKey = "2D7E7C96-DAC5-4526-96C3-C60CDEC4B120";
    private String json1;
    private Boolean aBoolean = false;
    private PopupWindow popupWindow;
    private ProgressBar pb_result;
    private DataBaseDao dao;
    private FlightData flightData;
    private String resCode;
    private String resMsg;
    private List<Flight.DataBean.FlightIdBean> flightId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airport_search_result_list);
        ButterKnife.inject(this);
        dao = new DataBaseDao(AirportSearchResultListActivity.this);
        //初始化前一个页面传来的数据
        initIntent();

        //初始化actionbar
        initBar();
        //初始化添加按钮
        initBtn();
        //listview 业务逻辑
        initList();
    }

    /**
     * 初始化前一个页面intent传递来的数据
     */
    private void initIntent() {

        Intent intent = getIntent();
        json = intent.getStringExtra("json");
        Logger.d("-----json", json);
        String string = "";
        pb_result = (ProgressBar) findViewById(R.id.pb_result);

        list = new ArrayList<>();
        try {
            array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                string = array.getString(i);
                searchb = new Gson().fromJson(string, SearchBean.class);
                list.add(searchb);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 搜索listview数据显示
     */
    private void initList() {
        //设置listview 多选模式
        airportAddPlaneList.setItemsCanFocus(false);
        airportAddPlaneList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        //listview数据适配器
        searchResultAdapter = new SearchResultAdapter(list, this);
        //自定义listview的监听回调
        airportAddPlaneList.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                LogUtil.d("---onloadmore", "执行了自定义方法");
                // 没有下一页
                Toast.makeText(AirportSearchResultListActivity.this, "没有更多数据了", Toast.LENGTH_SHORT)
                        .show();
                // 没有数据时也要收起控件
                airportAddPlaneList.onRefreshComplete(true);
            }
        });

        //数组数量
        count = searchResultAdapter.getCount();
        airportAddPlaneList.setAdapter(searchResultAdapter);
        airportAddPlaneList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            /**
             *
             * @param parent 当前ListView
             * @param view 代表当前被点击的条目
             * @param position 当前条目的位置
             * @param id 当前被点击的条目的id
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {
                LogUtil.d(tag, "点击了" + position);
                SearchResultAdapter.ViewHolder vHolder = (SearchResultAdapter.ViewHolder) view.getTag();
                vHolder.cBox.toggle();

                searchResultAdapter.isSelected.put(position, vHolder.cBox.isChecked());
                aBoolean = searchResultAdapter.isSelected.get(position);

                if (aBoolean) {
                    isCheck.add(list.get(position).getFlight_Seq());
                } else {
                    isCheck.remove(list.get(position).getFlight_Seq());
                }

                //获取10位的时间戳
                Ts = String.valueOf(System.currentTimeMillis()).substring(0, 10);

            }
        });
    }

    //获取签名
    public static String GetResponseMysign(LinkedHashMap<String, String> map, String privateKey) {
        String fullstring = CacheUtils.GetPostStrings(map) + privateKey;
        return FileHelper.ToMD5(fullstring);
    }

    public Flight CreateJsonObject() {

        String userId = CacheUtils.getString(AirportSearchResultListActivity.this, CacheUtils.userId);
        String Company_Code = CacheUtils.getString(AirportSearchResultListActivity.this, CacheUtils.Company_Code);

        Flight flight = new Flight();

        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("company_code", Company_Code);
        map.put("ts", Ts);
        map.put("userid", userId);

        flight.setCompany_Code(Company_Code);
        flight.setUserId(userId);
        flight.setTs(Ts);
        flight.setSign(GetResponseMysign(map, APIPrivateKey));

        List<Flight.DataBean.FlightIdBean> flightlist = new ArrayList<Flight.DataBean.FlightIdBean>();

        for (int i = 0; i < isCheck.size(); i++) {
            Flight.DataBean.FlightIdBean bean = new Flight.DataBean.FlightIdBean();
            bean.setId(isCheck.get(i));
            flightlist.add(bean);
        }

        Flight.DataBean dataBean = new Flight.DataBean();
        dataBean.setFlightId(flightlist);

        flight.setData(dataBean);

        return flight;
    }


    /**
     * 初始化页面按钮
     */
    private void initBtn() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //获取10位的时间戳
                Ts = String.valueOf(System.currentTimeMillis()).substring(0, 10);

                pb_result.setVisibility(View.VISIBLE);
                final Flight flight = CreateJsonObject();
                flightId = flight.getData().getFlightId();
                json1 = new Gson().toJson(flight);
                LogUtil.d("---jsonairport", json1);

                OkHttpUtils
                        .postString()
                        .url(Cantons.AddFlightbyUser)
                        .addHeader("User-Agent", "PDCS-APP")
                        .mediaType(MediaType.parse("application/json; charset=utf-8"))
                        .content(json1)
                        .build()
                        .execute(new AirportAddCallback());
            }
        });
    }

    /**
     * 初始化action bar
     */
    private void initBar() {
        //得到Actionbar
        actionBar = getActionBar();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayUseLogoEnabled(false);
        actionBar.setTitle("添加航班");
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
        inflater.inflate(R.menu.search_menu, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.show_data1:
                //自定义popupwidow
                PopuWindow();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 自定义popuwind
     */
    public void PopuWindow() {

        LayoutInflater inflater = (LayoutInflater) AirportApplication.context.getSystemService(AirportApplication.LAYOUT_INFLATER_SERVICE);
        View inflate = inflater.inflate(R.layout.search_popu, null);

        search1 = (TextView) inflate.findViewById(R.id.search_tv1);
        search2 = (TextView) inflate.findViewById(R.id.search_tv2);
        search3 = (TextView) inflate.findViewById(R.id.search_tv3);
        search4 = (TextView) inflate.findViewById(R.id.search_tv4);
        search5 = (TextView) inflate.findViewById(R.id.search_tv5);
        search1.setOnClickListener(this);
        search2.setOnClickListener(this);
        search3.setOnClickListener(this);
        search4.setOnClickListener(this);
        search5.setOnClickListener(this);

        popupWindow = new PopupWindow(this);
        int height = AirportSearchResultListActivity.this.getWindowManager().getDefaultDisplay().getHeight();
        int width = AirportSearchResultListActivity.this.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        popupWindow.setContentView(inflate);
        // 设置SelectPicPopupWindow的View
        popupWindow.setWidth(width / 3 + 50);
        // 设置SelectPicPopupWindow弹出窗体的高
        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        // 刷新状态
        popupWindow.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        popupWindow.setBackgroundDrawable(dw);
        View viewById = findViewById(R.id.show_data1);
        popupWindow.showAsDropDown(viewById, viewById.getLayoutParams().width / 2, 0);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_tv1:
                LogUtil.d("airportsearchresultlistactivity------", "按航班号显示");

                Collections.sort(list, new Comparator<SearchBean>() {
                    @Override
                    public int compare(SearchBean lhs, SearchBean rhs) {
                        return lhs.getFlight_Num_Two().compareTo(rhs.getFlight_Num_Two());
                    }
                });
                searchResultAdapter.notifyDataSetChanged();
                popupWindow.dismiss();
                break;
            case R.id.search_tv2:
                LogUtil.d("airportsearchresultlistactivity------", "按机位号显示");
                Collections.sort(list, new Comparator<SearchBean>() {
                    @Override
                    public int compare(SearchBean lhs, SearchBean rhs) {
                        return lhs.getSlot().compareTo(rhs.getSlot());
                    }
                });
                searchResultAdapter.notifyDataSetChanged();
                popupWindow.dismiss();
                break;
            case R.id.search_tv3:
                LogUtil.d("airportsearchresultlistactivity------", "按起飞时间显示");
                Collections.sort(list, new Comparator<SearchBean>() {
                    @Override
                    public int compare(SearchBean lhs, SearchBean rhs) {
                        return lhs.getReal_Dep_Time().compareTo(rhs.getReal_Dep_Time());
                    }
                });
                searchResultAdapter.notifyDataSetChanged();
                popupWindow.dismiss();
                break;
            case R.id.search_tv4:
                LogUtil.d("airportsearchresultlistactivity------", "全选1");
                isCheck.clear();
                for (int i = 0; i < count; i++) {
                    searchResultAdapter.isSelected.put(i, true);
                    LogUtil.d("airportsearchresultlistactivity------", "全选3");
                    isCheck.add(list.get(i).getFlight_Seq());
                    //获取10位的时间戳
                    Ts = String.valueOf(System.currentTimeMillis()).substring(0, 10);
                    searchResultAdapter.notifyDataSetChanged();
                    LogUtil.d("airportsearchresultlistactivity------", "全选4");


                }
                popupWindow.dismiss();
                break;
            case R.id.search_tv5:
                LogUtil.d("airportsearchresultlistactivity------", "取消");
                for (int i = 0; i < count; i++) {
                    searchResultAdapter.isSelected.put(i, false);
                    isCheck.remove(list.get(i).getFlight_Seq());
                    searchResultAdapter.notifyDataSetChanged();
                }
                popupWindow.dismiss();
                break;
        }
    }

    /**
     * 添加按钮成功的回调方法中的回调
     */
    private class AirportSearchResultListCallback extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {
            e.printStackTrace();
            Toast.makeText(AirportSearchResultListActivity.this, "onError:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response, int id) {
            LogUtil.e("TAG", "response==" + response);
            LogUtil.e("下一步是否执行", "4444444");
            try {
                JSONObject jsonObject = new JSONObject(response);
                String resMsgAdd = jsonObject.optString("ResMsg");
                if ("-4".equals(resCode)) {

                    Intent intent = new Intent(AirportSearchResultListActivity.this, LoginActivity.class);
                    intent.putExtra("request", resMsgAdd + ",重新登陆");
                    startActivity(intent);
                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            flightData = new Gson().fromJson(response, FlightData.class);
            LogUtil.d("---首页数据", response);
            int resCodeAdd = flightData.getResCode();
            if (resCodeAdd != 0) {
                try {
                    if (flightData.getAccaFlightDate() != null) {
                        dao.addAccaFlightDateBean(flightData.getAccaFlightDate());
                    } else {
                        LogUtil.d("---没有数据111", "没有数据返回");
                    }

                    if (flightData.getAccauserFlightDate() != null) {
                        dao.addAccauserFlightDate(flightData.getAccauserFlightDate());
                    } else {
                        LogUtil.d("---没有数据222", "没有数据返回");
                    }

                    if (flightData.getAccaServiceDate() != null) {
                        dao.saveAccaServiceDate(flightData.getAccaServiceDate());

                    } else {
                        LogUtil.d("---没有数据333", "没有数据返回");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            Intent intent = new Intent(AirportSearchResultListActivity.this, MainActivity.class);
            startActivity(intent);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    pb_result.setVisibility(View.GONE);
                    addButton.setEnabled(false);
                }
            });
            finish();
        }
    }

    /**
     * 添加按钮的回调方法
     */
    private class AirportAddCallback extends StringCallback {
        @Override
        public void onError(Call call, Exception e, int id) {

        }

        @Override
        public void onResponse(String response, int id) {
            LogUtil.d("111111", response);
            resCode = "";
            resMsg = "";
            try {
                JSONObject object = new JSONObject(response);
                resCode = object.optString("ResCode");
                resMsg = object.optString("ResMsg");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if ("1".equals(resCode)) {

                String user_Id = CacheUtils.getString(AirportSearchResultListActivity.this, "userId");
                String userSeq = CacheUtils.getString(AirportSearchResultListActivity.this, "user_seq");
                //获取10位的时间戳
                String ts = String.valueOf(System.currentTimeMillis()).substring(0, 10);

                Map<String, String> mapDel = new HashMap<>();
                mapDel.put("user_seq", userSeq);
                mapDel.put("ts", ts);
                mapDel.put("userid", user_Id);

                String sign = FileHelper.ToMD5(CacheUtils.GetPostStrings(mapDel) + APIPrivateKey);

                DataLoad dataLoad = new DataLoad(user_Id, userSeq, flightId, ts, sign);

                String jsonAdd = new Gson().toJson(dataLoad);

                OkHttpUtils
                        .postString()
                        .url(Cantons.DowUserFlightData)
                        .addHeader("User-Agent", "PDCS-APP")
                        .mediaType(MediaType.parse("application/json; charset=utf-8"))
                        .content(jsonAdd)
                        .build()
                        .execute(new AirportSearchResultListCallback());

            } else {
                final String finalResMsg = resMsg;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if ("航班编号为空".equals(finalResMsg)) {
                            ToastBig.toast(AirportSearchResultListActivity.this, "请选择航班");
                        } else {
                            ToastBig.toast(AirportSearchResultListActivity.this, finalResMsg);
                        }
                        pb_result.setVisibility(View.GONE);
                    }
                });
            }
        }
    }
}
