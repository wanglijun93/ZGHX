package com.travelsky.airportapp.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.travelsky.airportapp.R;
import com.travelsky.airportapp.adapter.PrintAdapter;
import com.travelsky.airportapp.adapter.PrintAdapter2;
import com.travelsky.airportapp.domain.AirInfo;
import com.travelsky.airportapp.domain.FlightData;
import com.travelsky.airportapp.utils.LogUtil;
import com.travelsky.airportapp.utils.NetworkUtils;
import com.travelsky.airportapp.utils.ToastBig;
import com.travelsky.airportapp.utils.db.DataBaseDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 *
 * @author wty
 *         电子签名（打印单据页面）
 */
public class PrintDocActivity extends Activity implements View.OnClickListener {

    private GridView gv_print;
    private Button btn_print;
    private String air1;
    private Map<Integer, String> map;
    private List<AirInfo.DataBean.AccaServiceBean> list1;
    private List<FlightData.AccaServiceDateBean> list2;
    private List<FlightData.AccaFlightDateBean> accaFlightday1;
    private FlightData.AccaFlightDateBean accaFlightDateBean;
    private String flight_seq;
    private DataBaseDao dao;
    private String string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_doc);
        initTitle();
        air1 = getIntent().getStringExtra("air");
        accaFlightday1 = (List<FlightData.AccaFlightDateBean>) getIntent().getSerializableExtra("accaFlightday");
        accaFlightDateBean = accaFlightday1.get(0);
        flight_seq = accaFlightDateBean.get_flight_seq();
        LogUtil.d("---flight_seq", flight_seq);
        if (NetworkUtils.checkNetwork(PrintDocActivity.this)) {
            LogUtil.d("---", "连接网络");
            list1 = (List<AirInfo.DataBean.AccaServiceBean>) getIntent().getSerializableExtra("list1");
            btn_print = (Button) findViewById(R.id.btn_print);
            btn_print.setOnClickListener(this);
            gv_print = (GridView) findViewById(R.id.gv_print);
            gv_print.setAdapter(new PrintAdapter(this, list1));
            EventBus.getDefault().register(this);
        } else {
            LogUtil.d("---", "未连接网络");
            dao = new DataBaseDao(PrintDocActivity.this);
            list2 = dao.selectServiceNorY(flight_seq);
            list2 = (List<FlightData.AccaServiceDateBean>) getIntent().getSerializableExtra("list2");
            if (list2 != null) {
                btn_print = (Button) findViewById(R.id.btn_print);
                btn_print.setOnClickListener(this);
                gv_print = (GridView) findViewById(R.id.gv_print);
                gv_print.setAdapter(new PrintAdapter2(this, list2));
                EventBus.getDefault().register(this);
            } else {
                LogUtil.d("---list", "list集合为空");
            }

        }


    }


    public void onEventMainThread(Map<Integer, String> map) {
        this.map = map;
    }

    private  boolean flag = false;
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
                    if (keyCode == KeyEvent.KEYCODE_SEARCH)
                    {
                        return true;
                    }
                    else
                    {
                        return false; //默认返回 false，这里false不能屏蔽返回键，改成true就可以了
                    }
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void initTitle() {
        //得到Actionbar
        ActionBar actionBar = getActionBar();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayUseLogoEnabled(false);
        actionBar.setTitle("电子签名");
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
            case R.id.btn_print:
                if (NetworkUtils.checkNetwork(PrintDocActivity.this)) {
                    if (map == null) {
                        ToastBig.toast(PrintDocActivity.this, "未选择服务项目");
                    } else if ("{}" == map.toString()) {
                        ToastBig.toast(PrintDocActivity.this, "未选择服务项目");
                    } else {
                        Intent intent = new Intent(this, PreviewDocActivity1.class);
                        intent.putExtra("air1", air1);
                        Bundle bundle = new Bundle();
                        bundle.putString("path", "");
                        bundle.putSerializable("map", (Serializable) map);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    if (map == null) {
                        ToastBig.toast(PrintDocActivity.this, "未选择服务项目");
                    } else if ("{}" == map.toString()) {
                        ToastBig.toast(PrintDocActivity.this, "未选择服务项目");
                    } else {
                        Intent intent = new Intent(this, PreviewDocActivity1.class);
                        intent.putExtra("accaFlightday", (Serializable) accaFlightday1);
                        Bundle bundle = new Bundle();
                        bundle.putString("path", "");
                        bundle.putSerializable("map", (Serializable) map);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }
                }


                break;
        }
    }

}