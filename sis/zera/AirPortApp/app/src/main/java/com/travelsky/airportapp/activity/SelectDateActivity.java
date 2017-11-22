package com.travelsky.airportapp.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.travelsky.airportapp.R;
import com.travelsky.airportapp.utils.CalendarUtil;
import com.travelsky.airportapp.view.CalendarView;
import com.travelsky.airportapp.view.XListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author wty
 * 显示日期页面
 */
public class SelectDateActivity extends Activity implements XListView.IXListViewListener, OnClickListener{
    
    private XListView listView;
    private Calendar calendar = Calendar.getInstance();
    private List<List<String>> arrayLists = new ArrayList<List<String>>();
        
    private CalendarListAdapter mAdapter;    
    private int curYear;
    private Handler myHandler = new Handler();
    private boolean unusePreviousDate;
    
    public static int selectyear;
    public static int selectmonth;
    public static int selectday;

    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_select_layout);
        initBar();
        initData();
        initPanel();
        gotoToday();
    }

    /**
     * 初始化actionbar
     */
    private void initBar() {
        //得到Actionbar
        ActionBar actionBar = getActionBar();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayUseLogoEnabled(false);
        //设置Actionbar的背景
        try{
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.bar11));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

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

    private void initData() {
        date = getIntent().getStringExtra("date");
        if(date!=null) {
            String[] split = date.split("-");
            if(split.length==3) {
                selectyear = Integer.parseInt(split[0]);
                selectmonth = Integer.parseInt(split[1]);
                selectday = Integer.parseInt(split[2]);
            }
        }
    }

    private void initPanel() {
        listView = (XListView) findViewById(android.R.id.list);
        listView.setXListViewListener(this);
        listView.setClickable(false);
        listView.setPullLoadEnable(true);
        listView.setPullRefreshEnable(true);
        
        listView.setHeaderColor(0xffffffff);
        listView.setFootColor(0xffffffff);
        listView.setHeaderTextColor(0xffbebebe);
        
        
        curYear = calendar.get(Calendar.YEAR);
        mAdapter = new CalendarListAdapter(this);
        unusePreviousDate = getIntent().getBooleanExtra("UnusePreviousDate", false);
        if(unusePreviousDate) {
            mAdapter.UnusePreviousDate();
            listView.setPullRefreshEnable(false);
        }
        if(selectyear!=0) {
        	mAdapter.year = selectyear;
        }else {
        	mAdapter.year = calendar.get(Calendar.YEAR);
        }
        listView.setHeaderShowTxt("下拉，查看"+(mAdapter.year-1), "松开，加载"+(mAdapter.year-1));
        listView.setFootShowTxt("上拉，查看"+(mAdapter.year+1), "松开，加载"+(mAdapter.year+1));
        initDate();
        mAdapter.setList(arrayLists);
        listView.setAdapter(mAdapter);
        if(selectmonth!=0) {
            listView.setSelection(selectmonth);
        }else {
            listView.setSelection(calendar.get(Calendar.MONTH));
        }
    }
    
    private void initDate() {
        arrayLists.clear();
        for(int i=0; i<12; i++) {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.MONTH, i);
            calendar.set(Calendar.YEAR, mAdapter.year);
            List<String> lunar = CalendarUtil.getLunar(calendar, CalendarUtil.getCountDay(i + 1, mAdapter.year));
            arrayLists.add(lunar);
        }
    }
    
    
    
    @Override
    public void onRefresh() {
    	mAdapter.year--;
        myHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                initDate();
                listView.stopLoadMore();
                listView.stopRefresh();
                listView.setHeaderShowTxt("下拉，查看" + (mAdapter.year - 1), "松开，加载" + (mAdapter.year - 1));
                listView.setFootShowTxt("上拉，查看" + (mAdapter.year + 1), "松开，加载" + (mAdapter.year + 1));
                if (curYear == mAdapter.year && unusePreviousDate) {
                    listView.setPullRefreshEnable(false);
                }
                mAdapter.notifyDataSetChanged();
                listView.setSelection(11);
            }
        }, 1000);
    }

    @Override
    public void onLoadMore() {
    	mAdapter.year++;
        myHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                initDate();
                listView.stopLoadMore();
                listView.stopRefresh();
                listView.setHeaderShowTxt("下拉，查看" + (mAdapter.year - 1), "松开，加载" + (mAdapter.year - 1));
                listView.setFootShowTxt("上拉，查看" + (mAdapter.year + 1), "松开，加载" + (mAdapter.year + 1));
                listView.setPullRefreshEnable(true);
                mAdapter.notifyDataSetChanged();
                listView.setSelection(0);
            }
        }, 1000);
    }

    @Override
    public void onClick(View view) {
    }

    private void gotoToday() {
        Calendar instance = Calendar.getInstance();
        mAdapter.year = instance.get(Calendar.YEAR);
        mAdapter.notifyDataSetChanged();
        int mon = instance.get(Calendar.MONTH);
        listView.setSelection(mon+1);
        CalendarView.drawToday =  true;
    }
    
    private class CalendarListAdapter extends BaseAdapter implements CalendarView.onItemClickListener {
        private Context ctx;
        private List<List<String>> list;
        public int year;
        private boolean unusePreviousDateFlag;
        
        public void setList(List<List<String>> list) {
            this.list = list;
        }
        
        public CalendarListAdapter(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int pos) {
            return list.get(pos);
        }

        @Override
        public long getItemId(int pos) {
            return pos;
        }

        @Override
        public View getView(int pos, View convertView, ViewGroup arg2) {
            CalendarView calendarItem = null;
            if(convertView!=null) {
                int line = (convertView.getHeight()-CalendarView.WEEKTITLEH)/CalendarView.CELLH-1;
                calendarItem = (CalendarView) convertView;
                calendarItem.initDate(year, pos+1);
                int rows = calendarItem.getRows();
                if(line!=rows) {
                    convertView = null;
                }
            }
            if(convertView == null) {
                calendarItem = new CalendarView(ctx);
                calendarItem.initDate(year, pos+1);
                calendarItem.setLunar(list.get(pos));
                if(unusePreviousDateFlag) {
                    calendarItem.UnusePreviousDate();
                }
                calendarItem.setOnItemClickListener(this);
            }else {
                calendarItem = (CalendarView) convertView;
                calendarItem.setLunar(list.get(pos));
                if(unusePreviousDateFlag) {
                    calendarItem.UnusePreviousDate();
                }
            }
            return calendarItem;
        }


        public void UnusePreviousDate() {
            unusePreviousDateFlag = true;
        }

		@Override
		public void onClick(String date) {
			Intent intent = new Intent();
			intent.putExtra("date", date);
			setResult(RESULT_OK, intent);
            finish();
		}
    }
}
