package com.travelsky.airportapp.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.travelsky.airportapp.R;
import com.travelsky.airportapp.adapter.OfflineSearchAdapter;
import com.travelsky.airportapp.domain.FlightData;
import com.travelsky.airportapp.utils.CacheUtils;
import com.travelsky.airportapp.utils.db.DataBaseDao;

import java.io.Serializable;
import java.util.List;

/**
 * 离线航班信息
 */
public class OfflineSearchActivity extends Activity {

    private ListView lv_offline;
    private List<FlightData.AccaFlightDateBean> flightList;
    private OfflineSearchAdapter adapter;
    private DataBaseDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_flight);
        initBar();
        flightList = (List<FlightData.AccaFlightDateBean>) getIntent().getSerializableExtra("offlineSearch");
        lv_offline = (ListView) findViewById(R.id.lv_offline);
        dao = new DataBaseDao(OfflineSearchActivity.this);
        if (flightList != null) {
            adapter = new OfflineSearchAdapter(OfflineSearchActivity.this, flightList);
            lv_offline.setAdapter(adapter);
        }


        //航班列表的点击事件
        lv_offline.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String flight_seq = flightList.get(position).get_flight_seq();

                CacheUtils.putString(OfflineSearchActivity.this, CacheUtils.Fight_Seq, flight_seq);
                List<FlightData.AccaFlightDateBean> accaFlightBySeq = dao.getAccaFlightBySeq(flight_seq);
                Intent intent = new Intent(OfflineSearchActivity.this, SecurityDetailsSinglActivity.class);
                intent.putExtra("offline", (Serializable) accaFlightBySeq);
                startActivity(intent);
            }
        });
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
        actionBar.setTitle("查看航班信息");
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
}
