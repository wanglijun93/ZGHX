package com.travelsky.airportapp.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.travelsky.airportapp.Cantons;
import com.travelsky.airportapp.R;
import com.travelsky.airportapp.domain.AccaServiceType;
import com.travelsky.airportapp.domain.FlightData;
import com.travelsky.airportapp.utils.CacheUtils;
import com.travelsky.airportapp.utils.DateToString;
import com.travelsky.airportapp.utils.FileHelper;
import com.travelsky.airportapp.utils.LogUtil;
import com.travelsky.airportapp.utils.NetworkUtils;
import com.travelsky.airportapp.utils.TimeString2Data2Long;
import com.travelsky.airportapp.utils.ToastBig;
import com.travelsky.airportapp.utils.ToastUtil;
import com.travelsky.airportapp.utils.db.DataBaseDao;
import com.travelsky.airportapp.view.CalendarView;
import com.travelsky.airportapp.view.timeview.ArrayWheelAdapter;
import com.travelsky.airportapp.view.timeview.NumericWheelAdapter;
import com.travelsky.airportapp.view.timeview.WheelView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.greenrobot.event.EventBus;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;

/**
 * 修改事项
 */
public class ModifySecurityDetailsActivity extends Activity implements View.OnClickListener {
    private LinearLayout startLayout;
    private LinearLayout endLayout;
    public static WheelView startHour;
    public static WheelView startDays;
    public static WheelView startMins;

    public static WheelView endHour;
    public static WheelView endDays;
    public static WheelView endMins;
    private SimpleDateFormat sfDate = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat formatType = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    static String[] minutes = new String[]{"00", "01", "02", "03", "04", "05", "06",
            "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17",
            "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28",
            "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39",
            "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50",
            "51", "52", "53", "54", "55", "56", "57", "58", "59"};
    static String[] daysDesc = new String[]{"昨天", "今天", "明天"};
    private String APIPrivateKey = "2D7E7C96-DAC5-4526-96C3-C60CDEC4B120";

    private TextView tv_matter;
    private Button btnfinish;
    private Button btndelete;
    private Date date;
    private EditText et_mounts;
    private EditText et_remarks;
    private String json;
    private String service_seq;
    private String service_type;
    private String name;
    private String num;
    private String start_Time;
    private String end_Time;
    private String attach;
    private String ts;
    private String json1;
    private String flag = "N";
    private String start_time;
    private String end_time;
    private String shour;
    private String smin;
    private String ehour;
    private String emin;
    private int sday = 1;
    private int eday = 1;
    private ProgressBar pb_matter;
    private DataBaseDao dao;
    private String delserviceSeq;
    private String serviceSeqIn;
    private String signstate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_matter);

        dao = new DataBaseDao(ModifySecurityDetailsActivity.this);
        EventBus.getDefault().register(this);

        initBar();
        initPanel();

        getData();

        createButtonItem();

    }

    private void getData() {

        //从前一个页面传递过来的名称
        name = getIntent().getStringExtra("name");
        tv_matter = (TextView) findViewById(R.id.tv_matter);
        tv_matter.setText(name);

        //从前一个页面传递过来的数量
        num = getIntent().getStringExtra("num");
        et_mounts = (EditText) findViewById(R.id.et_mounts);
        et_mounts.setText(num);
        //光标显示在数字后面
        et_mounts.setSelection(et_mounts.getText().toString().length());

        //使发送按键执行一次
        final boolean[] flagClick1 = {false};
        et_mounts.setOnEditorActionListener(new Button.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    if (isEmpty()) {
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                        if (!flagClick1[0]) {
                            flagClick1[0] = true;
                            LogUtil.e("TAG", "clickEditorAction1");
                            clickEditorAction();
                        }

                    }
                    return true;
                }
                return false;
            }
        });
        attach = getIntent().getStringExtra("Attach");
        et_remarks = (EditText) findViewById(R.id.et_remarks);
        et_remarks.setSingleLine(false);
        et_remarks.setText(attach);


        //使发送按键执行一次
        final boolean[] flagClick2 = {false};
        et_remarks.setOnEditorActionListener(new Button.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    if (!flagClick2[0]) {
                        flagClick2[0] = true;
                        LogUtil.e("TAG", "clickEditorAction2");
                        clickEditorAction();
                    }
                    return true;
                }
                return false;
            }
        });

        service_seq = getIntent().getStringExtra("Service_Seq");
        LogUtil.e("TAG", "server+seq==" + service_seq);

        //接收上个页面传递的数据
        start_time = getIntent().getStringExtra("start_time");
        end_time = getIntent().getStringExtra("end_time");

        String formatStr = "yyyyMMddHHmm";
        String dateFromatStr = "yyyy-MM-dd HH:mm";
        //转换为2016-05-03 02:33:00 的格式

        if (!"".equals(start_time) && !"".equals(end_time)) {
            String stime = DateToString.StringToDate(start_time, formatStr, dateFromatStr);
            String etime = DateToString.StringToDate(end_time, formatStr, dateFromatStr);

            long s_time = 0;
            try {
                s_time = TimeString2Data2Long.stringToLong(stime, dateFromatStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long e_time = 0;
            try {
                e_time = TimeString2Data2Long.stringToLong(etime, dateFromatStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            sday = compareDay(s_time);
            eday = compareDay(e_time);
        }

        if ("".equals(service_seq)) {
            btndelete.setEnabled(false);
            btndelete.setBackgroundResource(R.drawable.button_shape);
        } else {
            btndelete.setEnabled(true);
            btndelete.setBackgroundResource(R.drawable.shanchubg);
        }

        long timeMillis = System.currentTimeMillis();
        date = new Date(timeMillis);

        if (!"".equals(service_seq)) {
            shour = start_time.substring(8, 10);
            smin = start_time.substring(10, 12);
            ehour = end_time.substring(8, 10);
            emin = end_time.substring(10, 12);
        } else {
            shour = formatType.format(date).substring(11, 13);
            smin = formatType.format(date).substring(14, 16);
            ehour = formatType.format(date).substring(11, 13);
            emin = formatType.format(date).substring(14, 16);
        }
        service_type = getIntent().getStringExtra("service_type");

        signstate = getIntent().getStringExtra("signstate");

        //是否与时间有关
        List<AccaServiceType> accaServiceDate = dao.getAccaServiceType();
        for (int i = 0; i < accaServiceDate.size(); i++) {
            if (name.equals(accaServiceDate.get(i).getSERVICE_NAME())) {
                flag = accaServiceDate.get(i).getHOUR_FLAG();
                break;
            }
        }

    }

    //判断数量是否为空
    public boolean isEmpty() {
        String numStr = et_mounts.getText().toString();
        if (!"".equals(numStr)) {
            double num = Double.parseDouble(numStr);
            if (num > 0) {
                return true;
            } else {
                ToastBig.toast(ModifySecurityDetailsActivity.this, "数量不能为零");
                return false;
            }
        } else {
            ToastBig.toast(ModifySecurityDetailsActivity.this, "数量不能为空");
            return false;
        }

    }

    /**
     * 比较显示天与当前天
     *
     * @param time 显示日期毫秒数
     * @return 2 明天  1 今天  0 昨天
     */
    public int compareDay(long time) {
        Calendar curCalendar = Calendar.getInstance();
        int curDay = curCalendar.get(Calendar.DAY_OF_MONTH);
        Calendar showCalendar = Calendar.getInstance();
        showCalendar.setTimeInMillis(time);
        int showDay = showCalendar.get(Calendar.DAY_OF_MONTH);
        return showDay - curDay + 1;
    }

    private void initBar() {
        //得到Actionbar
        ActionBar actionBar = getActionBar();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayUseLogoEnabled(false);
        actionBar.setTitle("修改事项");
        //设置Actionbar的背景
        try {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.bar11));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 初始化按钮
     */
    private void initPanel() {
        pb_matter = (ProgressBar) findViewById(R.id.pb_matter);
        //完成修改按钮
        btnfinish = (Button) findViewById(R.id.btn_finish);
        //删除事项按钮
        btndelete = (Button) findViewById(R.id.btn_delete);
        btnfinish.setOnClickListener(this);
        btndelete.setOnClickListener(this);
        startLayout = (LinearLayout) findViewById(R.id.my_trave_filter_aitline_ll);
        endLayout = (LinearLayout) findViewById(R.id.my_trave_filter_aitline_ll2);

    }

    //时间控件
    private void createButtonItem() {
        long timeMillis = System.currentTimeMillis();
        date = new Date(timeMillis);

        //开始时间
        LinearLayout startTime = (LinearLayout) LinearLayout.inflate(this,
                R.layout.start_time_filter, null);

        startDays = (WheelView) startTime.findViewById(R.id.start_days);
        startHour = (WheelView) startTime.findViewById(R.id.start_hour);
        startMins = (WheelView) startTime.findViewById(R.id.start_mins);


        startDays.setAdapter(new ArrayWheelAdapter<>(daysDesc));
        startDays.setCurrentItem(sday);
        startDays.addScrollingListener(listener);

        startHour.setAdapter(new NumericWheelAdapter(0, 23));
        startHour.setCurrentItem(Integer.parseInt(shour));

        startHour.setLabel("时");
        startHour.addScrollingListener(listener);

        startMins.setAdapter(new ArrayWheelAdapter<String>(minutes));
        startMins.setCurrentItem(Integer.parseInt(smin));
        startMins.setLabel("分");
        startMins.addScrollingListener(listener);

        //结束时间
        LinearLayout endTime = (LinearLayout) LinearLayout.inflate(this,
                R.layout.end_time_filter, null);

        endDays = (WheelView) endTime.findViewById(R.id.end_days);
        endHour = (WheelView) endTime.findViewById(R.id.end_hour);
        endMins = (WheelView) endTime.findViewById(R.id.end_mins);

        endDays.setAdapter(new ArrayWheelAdapter<String>(daysDesc));
        endDays.setCurrentItem(eday);
        endDays.addScrollingListener(listener);

        endHour.setAdapter(new NumericWheelAdapter(0, 23));
        endHour.setCurrentItem(Integer.parseInt(ehour));
        endHour.setLabel("时");
        endHour.addScrollingListener(listener);

        endMins.setAdapter(new ArrayWheelAdapter<String>(minutes));
        endMins.setCurrentItem(Integer.parseInt(emin));
        endMins.setLabel("分");
        endMins.addScrollingListener(listener);

        startLayout.addView(startTime);
        endLayout.addView(endTime);

        List<String> list = CalendarView.startEndTime(startDays, startHour, startMins, endDays, endHour, endMins);
        start_Time = list.get(0);
        end_Time = list.get(1);

        setTimeTouchable();
    }

    /**
     * 设置时间控件是否可以滑动
     */
    private void setTimeTouchable() {
        if ("N".equals(flag)) {
            startDays.setAlpha(0.3f);
            startDays.setTouchable(false);
            startHour.setAlpha(0.3f);
            startHour.setTouchable(false);
            startMins.setAlpha(0.3f);
            startMins.setTouchable(false);

            endDays.setAlpha(0.3f);
            endDays.setTouchable(false);
            endHour.setAlpha(0.3f);
            endHour.setTouchable(false);
            endMins.setAlpha(0.3f);
            endMins.setTouchable(false);

        }
    }

    WheelView.OnWheelScrollListener listener = new WheelView.OnWheelScrollListener() {
        @Override
        public void onScrollingStarted(WheelView wheel) {

        }

        @Override
        public void onScrollingFinished(WheelView wheel) {
            List<String> list = CalendarView.startEndTime(startDays, startHour, startMins, endDays, endHour, endMins);

            //滑动控件后，得到的时间
            start_Time = list.get(0);
            end_Time = list.get(1);

            countAmounts();

        }
    };

    /**
     * 计算数量
     */
    protected void countAmounts() {
        double stTime = getDeptTime(startDays, startHour, startMins);
        double etTime = getDeptTime(endDays, endHour, endMins);
        double amounts = (etTime - stTime) / 3600000.0;
        if (amounts > 0) {
            DecimalFormat decimalFormat = new DecimalFormat("0.00"); //构造方法的字符格式这里如果小数不足2位,会以0补足.
            String amountsStr = decimalFormat.format(amounts);
            if (amountsStr.endsWith(".00")) {   //若是整数，则去除.00
                amountsStr = amountsStr.substring(0, amountsStr.length() - 3);
            }
            et_mounts.setText(amountsStr);
        } else {
            et_mounts.setText("");
        }

    }

    private long getDeptTime(WheelView days, WheelView hour, WheelView mins) {
        String date = "";
        String time = "";
        Calendar cal = Calendar.getInstance();
        if ("昨天".endsWith(days.getAdapter().getItem(days.getCurrentItem()))) {
            try {
                cal.add(Calendar.DATE, -1);
                date = sfDate.format(cal.getTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("今天".endsWith(days.getAdapter().getItem(
                days.getCurrentItem()))) {
            try {
                date = sfDate.format(cal.getTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                cal.add(Calendar.DATE, 1);
                date = sfDate.format(cal.getTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int hours = 0, minu = 0;
        try {
            hours = Integer.parseInt(hour.getAdapter().getItem(
                    hour.getCurrentItem()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (hours < 10) {
            time += "0" + hours;
        } else {
            time += hours + "";
        }
        try {
            minu = Integer.parseInt(mins.getAdapter().getItem(
                    mins.getCurrentItem()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (minu < 10) {
            time += ":0" + minu;
        } else {
            time += ":" + minu;
        }
        String[] ss = new String[]{date, time};
        String strTime = ss[0] + " " + ss[1];
        long curTime = 0;
        try {
            curTime = TimeString2Data2Long.stringToLong(strTime, "yyyy-MM-dd HH:mm");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return curTime;
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

            //添加修改
            case R.id.btn_finish:

                clickEditorAction();
                break;
            case R.id.btn_delete:
                new AlertDialog.Builder(ModifySecurityDetailsActivity.this)
                        .setMessage("确定要删除这个事项么?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (NetworkUtils.checkNetwork(ModifySecurityDetailsActivity.this)) {
                                    pb_matter.setVisibility(View.VISIBLE);
                                    btnfinish.setEnabled(false);
                                    btndelete.setEnabled(false);
                                    InternetRequestDelete();

                                } else {
                                    dao.delAccaServiceDateBySeq(service_seq);
                                    Intent intent = new Intent(ModifySecurityDetailsActivity.this, SecurityDetailsSinglActivity.class);
                                    startActivity(intent);
                                }

                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;

        }
    }

    //点击回车
    public void clickEditorAction() {
        if (isEmpty()) {

            try {
                Date date = TimeString2Data2Long.stringToDate(start_Time, "yyyy-MM-dd HH:mm");
                Date date2 = TimeString2Data2Long.stringToDate(end_Time, "yyyy-MM-dd HH:mm");

                long lstart = TimeString2Data2Long.dateToLong(date);
                long lend = TimeString2Data2Long.dateToLong(date2);
                if (lstart > lend) {

                    ToastBig.toast(ModifySecurityDetailsActivity.this, "开始时间不能大于结束时间");
                    return;

                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
            //如果联网，请求网络
            if (NetworkUtils.checkNetwork(ModifySecurityDetailsActivity.this)) {
                pb_matter.setVisibility(View.VISIBLE);
                InternetRequest();
                btnfinish.setEnabled(false);
                btndelete.setEnabled(false);

            } else {
                //如果没有联网，在本地添加服务
                String company_code = CacheUtils.getString(ModifySecurityDetailsActivity.this, CacheUtils.Company_Code);
                String userId = CacheUtils.getString(ModifySecurityDetailsActivity.this, CacheUtils.userId);
                String flight_id = CacheUtils.getString(ModifySecurityDetailsActivity.this, CacheUtils.Fight_Seq);
                UUID uuid = UUID.randomUUID();
                if ("".equals(service_seq)) {
                    SimpleDateFormat formatTypeOff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String create_time_off = formatTypeOff.format(date);
                    String update_time_off = formatTypeOff.format(date);

                    FlightData.AccaServiceDateBean serviceDateBean = new FlightData.AccaServiceDateBean();
                    serviceDateBean.set_attach(et_remarks.getText().toString());
                    try {
                        Date date_Create = TimeString2Data2Long.stringToDate(create_time_off, "yyyy-MM-dd HH:mm:ss");
                        String dateCreate = TimeString2Data2Long.dateToString(date_Create, "yyyyMMddHHmmss");
                        Date date_End = TimeString2Data2Long.stringToDate(end_Time, "yyyy-MM-dd HH:mm");
                        String dateEnd = TimeString2Data2Long.dateToString(date_End, "yyyyMMddHHmm");
                        serviceDateBean.set_create_time(Long.parseLong(dateCreate));
                        serviceDateBean.set_end_time(Long.parseLong(dateEnd));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    serviceDateBean.set_company_code(company_code);
                    serviceDateBean.set_create_id(userId);
                    serviceDateBean.set_item_name(name);
                    serviceDateBean.set_signstate("N");
                    serviceDateBean.set_service_type(service_type);
                    serviceDateBean.set_item_value(et_mounts.getText().toString().trim());
                    serviceDateBean.set_update_id(userId);
                    serviceDateBean.set_link_flight_id(flight_id);

                    serviceDateBean.set_service_seq(uuid.toString());

                    serviceDateBean.set_invoice_no("");
                    serviceDateBean.set_used_time("00:00:00");
                    LogUtil.e("TAG", "start_time" + start_Time);
                    try {
                        Date date_Start = TimeString2Data2Long.stringToDate(start_Time, "yyyy-MM-dd HH:mm");
                        String dateStart = TimeString2Data2Long.dateToString(date_Start, "yyyyMMddHHmm");
                        Date date_Update = TimeString2Data2Long.stringToDate(update_time_off, "yyyy-MM-dd HH:mm:ss");
                        String dateUpdate = TimeString2Data2Long.dateToString(date_Update, "yyyyMMddHHmmss");
                        serviceDateBean.set_start_time(Long.parseLong(dateStart));
                        serviceDateBean.set_update_time(Long.parseLong(dateUpdate));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    dao.addAccaServiceDate(serviceDateBean);

                    Intent intent = new Intent(ModifySecurityDetailsActivity.this, SecurityDetailsSinglActivity.class);
                    startActivity(intent);
                } else {
                    //如果没有联网，在本地修改服务
                    SimpleDateFormat formatTypeOff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String create_time_off = formatTypeOff.format(date);
                    String update_time_off = formatTypeOff.format(date);
                    FlightData.AccaServiceDateBean serviceDateBean = new FlightData.AccaServiceDateBean();
                    serviceDateBean.set_attach(et_remarks.getText().toString());
                    try {
                        Date date_Create = TimeString2Data2Long.stringToDate(create_time_off, "yyyy-MM-dd HH:mm:ss");
                        String dateCreate = TimeString2Data2Long.dateToString(date_Create, "yyyyMMddHHmmss");
                        Date date_Update = TimeString2Data2Long.stringToDate(update_time_off, "yyyy-MM-dd HH:mm:ss");
                        String dateUpdate = TimeString2Data2Long.dateToString(date_Update, "yyyyMMddHHmmss");
                        serviceDateBean.set_create_time(Long.parseLong(dateCreate));
                        serviceDateBean.set_update_time(Long.parseLong(dateUpdate));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    serviceDateBean.set_company_code(company_code);
                    serviceDateBean.set_create_id(userId);
                    serviceDateBean.set_service_seq(service_seq);
                    serviceDateBean.set_link_flight_id(flight_id);
                    serviceDateBean.set_service_type(service_type);
                    serviceDateBean.set_update_id(userId);
                    serviceDateBean.set_item_name(name);
                    serviceDateBean.set_signstate(signstate);
                    serviceDateBean.set_item_value(et_mounts.getText().toString().trim());
                    serviceDateBean.set_start_time(Long.parseLong(start_time));
                    serviceDateBean.set_end_time(Long.parseLong(end_time));
                    serviceDateBean.set_invoice_no("");
                    serviceDateBean.set_used_time("00:00:00");

                    dao.updateAccaServiceDate(serviceDateBean, service_seq);

                    Intent intent = new Intent(ModifySecurityDetailsActivity.this, SecurityDetailsSinglActivity.class);
                    startActivity(intent);
                }
            }

        }
    }

    //联网添加或修改数据
    public void addOrUpdateDate(String serviceSeqIn, String start_Time, String end_Time) {
        String company_code = CacheUtils.getString(ModifySecurityDetailsActivity.this, CacheUtils.Company_Code);
        String userId = CacheUtils.getString(ModifySecurityDetailsActivity.this, CacheUtils.userId);
        String flight_id = CacheUtils.getString(ModifySecurityDetailsActivity.this, CacheUtils.Fight_Seq);
        if ("".equals(service_seq)) {
            SimpleDateFormat formatTypeOff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String create_time_off = formatTypeOff.format(date);
            String update_time_off = formatTypeOff.format(date);

            FlightData.AccaServiceDateBean serviceDateBean = new FlightData.AccaServiceDateBean();
            serviceDateBean.set_attach(et_remarks.getText().toString());
            try {
                Date date_Create = TimeString2Data2Long.stringToDate(create_time_off, "yyyy-MM-dd HH:mm:ss");
                String dateCreate = TimeString2Data2Long.dateToString(date_Create, "yyyyMMddHHmmss");
                Date date_End = TimeString2Data2Long.stringToDate(end_Time, "yyyy-MM-dd HH:mm");
                String dateEnd = TimeString2Data2Long.dateToString(date_End, "yyyyMMddHHmm");
                serviceDateBean.set_create_time(Long.parseLong(dateCreate));
                serviceDateBean.set_end_time(Long.parseLong(dateEnd));

            } catch (ParseException e) {
                e.printStackTrace();
            }

            serviceDateBean.set_company_code(company_code);
            serviceDateBean.set_create_id(userId);
            serviceDateBean.set_item_name(name);
            serviceDateBean.set_signstate("N");
            serviceDateBean.set_service_type(service_type);
            serviceDateBean.set_item_value(et_mounts.getText().toString().trim());
            serviceDateBean.set_update_id(userId);
            serviceDateBean.set_link_flight_id(flight_id);
            serviceDateBean.set_service_seq(serviceSeqIn);

            serviceDateBean.set_invoice_no("");
            serviceDateBean.set_used_time("00:00:00");
            LogUtil.e("TAG", "start_time" + start_Time);
            try {
                Date date_Start = TimeString2Data2Long.stringToDate(start_Time, "yyyy-MM-dd HH:mm");
                String dateStart = TimeString2Data2Long.dateToString(date_Start, "yyyyMMddHHmm");
                Date date_Update = TimeString2Data2Long.stringToDate(update_time_off, "yyyy-MM-dd HH:mm:ss");
                String dateUpdate = TimeString2Data2Long.dateToString(date_Update, "yyyyMMddHHmmss");
                serviceDateBean.set_start_time(Long.parseLong(dateStart));
                serviceDateBean.set_update_time(Long.parseLong(dateUpdate));

            } catch (ParseException e) {
                e.printStackTrace();
            }

            dao.addAccaServiceInternet(serviceDateBean);

            Intent intent = new Intent(ModifySecurityDetailsActivity.this, SecurityDetailsSinglActivity.class);
            startActivity(intent);
        } else {
            SimpleDateFormat formatTypeOff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String create_time_off = formatTypeOff.format(date);
            String update_time_off = formatTypeOff.format(date);
            FlightData.AccaServiceDateBean serviceDateBean = new FlightData.AccaServiceDateBean();
            serviceDateBean.set_attach(et_remarks.getText().toString());
            try {
                Date date_Create = TimeString2Data2Long.stringToDate(create_time_off, "yyyy-MM-dd HH:mm:ss");
                String dateCreate = TimeString2Data2Long.dateToString(date_Create, "yyyyMMddHHmmss");
                Date date_Update = TimeString2Data2Long.stringToDate(update_time_off, "yyyy-MM-dd HH:mm:ss");
                String dateUpdate = TimeString2Data2Long.dateToString(date_Update, "yyyyMMddHHmmss");
                serviceDateBean.set_create_time(Long.parseLong(dateCreate));
                serviceDateBean.set_update_time(Long.parseLong(dateUpdate));

            } catch (ParseException e) {
                e.printStackTrace();
            }
            serviceDateBean.set_company_code(company_code);
            serviceDateBean.set_create_id(userId);
            serviceDateBean.set_service_seq(serviceSeqIn);
            serviceDateBean.set_link_flight_id(flight_id);
            serviceDateBean.set_service_type(service_type);
            serviceDateBean.set_update_id(userId);
            serviceDateBean.set_item_name(name);
            serviceDateBean.set_signstate(signstate);
            serviceDateBean.set_item_value(et_mounts.getText().toString().trim());
            LogUtil.e("TAG", "startaaa=" + start_time);
            try {

                Date date_Start = TimeString2Data2Long.stringToDate(start_Time, "yyyy-MM-dd HH:mm");
                String dateStart = TimeString2Data2Long.dateToString(date_Start, "yyyyMMddHHmm");
                Date date_End = TimeString2Data2Long.stringToDate(end_Time, "yyyy-MM-dd HH:mm");
                String dateEnd = TimeString2Data2Long.dateToString(date_End, "yyyyMMddHHmm");
                serviceDateBean.set_start_time(Long.parseLong(dateStart));
                serviceDateBean.set_end_time(Long.parseLong(dateEnd));
            } catch (Exception e) {
                e.printStackTrace();
            }
            serviceDateBean.set_invoice_no("");
            serviceDateBean.set_used_time("00:00:00");

            dao.updateServiceInternet(serviceDateBean, serviceSeqIn);

            Intent intent = new Intent(ModifySecurityDetailsActivity.this, SecurityDetailsSinglActivity.class);
            startActivity(intent);
        }
    }

    //有网络删除数据
    public void InternetRequestDelete() {
        String user_seq = CacheUtils.getString(ModifySecurityDetailsActivity.this, CacheUtils.user_seq);
        String fight_seq = CacheUtils.getString(ModifySecurityDetailsActivity.this, CacheUtils.Fight_Seq);
        ts = String.valueOf(System.currentTimeMillis()).substring(0, 10);

        Map<String, String> map = new HashMap<>();
        map.put("user_seq", user_seq);
        map.put("fight_seq", fight_seq);
        map.put("ts", ts);

        json1 = "{\"User_Seq\":\"" + user_seq + "\",\"Fight_Seq\":\"" + fight_seq + "\",\"Service_Seq\":\"" + service_seq + "\",\"Ts\":\"" + ts + "\",\"Sign\":\"" + FileHelper.ToMD5(CacheUtils.GetPostStrings(map) + APIPrivateKey) + "\"}";

        OkHttpUtils
                .postString()
                .url(Cantons.FlightServiceDel)
                .addHeader("User-Agent", "PDCS-APP")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(json1)
                .build()
                .execute(new FlightServiceDelCallback());

    }

    //删除事项回调
    public class FlightServiceDelCallback extends StringCallback {
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
            Toast.makeText(ModifySecurityDetailsActivity.this, "onError:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response, int id) {
            LogUtil.d("TAG", "onResponse：complete");
            LogUtil.d("TAG", "onResponse:" + response);

            try {
                JSONObject jsonObject = new JSONObject(response);
                String resCode = jsonObject.optString("resCode");
                final String resMsg = jsonObject.optString("resMsg");
                delserviceSeq = jsonObject.optString("serviceSeq");
                if ("1".equals(resCode)) {
                    //删除服务信息
                    dao.delAccaServiceInternet(delserviceSeq);
                    Intent intent = new Intent(ModifySecurityDetailsActivity.this, SecurityDetailsSinglActivity.class);
                    startActivity(intent);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pb_matter.setVisibility(View.GONE);
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastBig.toast(ModifySecurityDetailsActivity.this, resMsg);
                            pb_matter.setVisibility(View.GONE);
                        }
                    });
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

    public void onEventMainThread(String str) {
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

    /**
     * 发起网络请求
     */
    public void InternetRequest() {
        String userId = CacheUtils.getString(ModifySecurityDetailsActivity.this, CacheUtils.userId);
        String user_seq = CacheUtils.getString(ModifySecurityDetailsActivity.this, CacheUtils.user_seq);
        String fight_seq = CacheUtils.getString(ModifySecurityDetailsActivity.this, CacheUtils.Fight_Seq);
        String company_code = CacheUtils.getString(ModifySecurityDetailsActivity.this, CacheUtils.Company_Code);
        //获取10位的时间戳
        ts = String.valueOf(System.currentTimeMillis()).substring(0, 10);

        Map<String, String> map = new HashMap<>();
        map.put("userid", userId);
        map.put("user_seq", user_seq);
        map.put("fight_seq", fight_seq);
        map.put("ts", ts);

        json = "{\"UserId\":\"" + userId + "\",\"User_Seq\":\"" + user_seq + "\",\"Fight_Seq\":\"" + fight_seq + "\",\"Service_Seq\":\"" + service_seq + "\"," +
                "\"company_code\":\"" + company_code + "\"," + "\"service_type\":\"" + service_type + "\"," +
                "\"item_name\":\"" + tv_matter.getText().toString() + "\",\"Item_Value\": \"" + et_mounts.getText().toString() + "\"," +
                "\"Start_Time\":\"" + start_Time + "\",\"End_Time\":\"" + end_Time + "\",\"Attach\":\"" + et_remarks.getText().toString() + "\"," +
                "\"Ts\":\"" + ts + "\",\"Sign\":\"" + FileHelper.ToMD5(CacheUtils.GetPostStrings(map) + APIPrivateKey) + "\"}";
        LogUtil.d("---modifysecuritydetailsactivity", json);
        OkHttpUtils
                .postString()
                .url(Cantons.FlightServiceUp)
                .addHeader("User-Agent", "PDCS-APP")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(json)
                .build()
                .execute(new FlightServiceUpCallback());

    }

    //修改事项回调
    public class FlightServiceUpCallback extends StringCallback {
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
            Toast.makeText(ModifySecurityDetailsActivity.this, "onError:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response, int id) {
            LogUtil.d("TAG", "onResponse：complete");
            LogUtil.d("TAG", "onResponse:" + response);

            try {
                JSONObject jsonObject = new JSONObject(response);
                String resCode = jsonObject.optString("resCode");
                serviceSeqIn = jsonObject.optString("serviceSeq");
                final String resMsg = jsonObject.optString("resMsg");
                if ("1".equals(resCode)) {
                    addOrUpdateDate(serviceSeqIn, start_Time, end_Time);
                    Intent intent = new Intent(ModifySecurityDetailsActivity.this, SecurityDetailsSinglActivity.class);
                    startActivity(intent);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            pb_matter.setVisibility(View.VISIBLE);
                        }
                    });
                    finish();
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showToast(resMsg);
                            pb_matter.setVisibility(View.GONE);
                            btnfinish.setEnabled(true);
                            btndelete.setEnabled(true);
                        }
                    });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(ModifySecurityDetailsActivity.this);
    }
}
