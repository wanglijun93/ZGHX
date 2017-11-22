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
import android.widget.Button;
import android.widget.GridView;

import com.travelsky.airportapp.R;
import com.travelsky.airportapp.adapter.CleanAdapter;
import com.travelsky.airportapp.adapter.CleanAdapter1;
import com.travelsky.airportapp.adapter.MaintenanceAdapter;
import com.travelsky.airportapp.adapter.MaintenanceAdapter1;
import com.travelsky.airportapp.adapter.SpecialCarAdapter;
import com.travelsky.airportapp.adapter.SpecialCarAdapter1;
import com.travelsky.airportapp.domain.AirInfo;
import com.travelsky.airportapp.domain.FlightData;
import com.travelsky.airportapp.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author iwangtianyi
 *         添加已完成事项
 */
public class AddFinishedMatterActivity extends Activity implements View.OnClickListener {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private LayoutInflater mInflater;
    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private View view1, view2, view3;//页卡视图
    private List<View> mViewList = new ArrayList<>();//页卡视图集合

    private GridView gv_matter;
    private Button btn_select_famf;
    private List<AirInfo.DataBean.AccaServiceBean> accaServiceBeen;
    private List<FlightData.AccaServiceDateBean> accaServiceBeen1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_finished_matter);
        //初始化actionBar
        initBar();

        btn_select_famf = (Button) findViewById(R.id.btn_select_famf);
        btn_select_famf.setOnClickListener(this);
        mViewPager = (ViewPager) findViewById(R.id.vp_finish_matter);
        mTabLayout = (TabLayout) findViewById(R.id.tl_finish_matter);

        mInflater = LayoutInflater.from(this);
        view1 = mInflater.inflate(R.layout.matter_gridview, null);
        view2 = mInflater.inflate(R.layout.matter_gridview, null);
        view3 = mInflater.inflate(R.layout.matter_gridview, null);
        if (NetworkUtils.checkNetwork(AddFinishedMatterActivity.this)) {
            accaServiceBeen = (List<AirInfo.DataBean.AccaServiceBean>) getIntent().getSerializableExtra("list");
            //加载gridview 特车
            gv_matter = (GridView) view1.findViewById(R.id.gv_matter);
            gv_matter.setAdapter(new SpecialCarAdapter(this, accaServiceBeen, mTabLayout, mTitleList));

            //加载gridview  机务
            gv_matter = (GridView) view2.findViewById(R.id.gv_matter);
            gv_matter.setAdapter(new MaintenanceAdapter(this, accaServiceBeen, mTabLayout, mTitleList));
            //加载gridview   清洁
            gv_matter = (GridView) view3.findViewById(R.id.gv_matter);
            gv_matter.setAdapter(new CleanAdapter(this, accaServiceBeen, mTabLayout, mTitleList));
        } else {
            accaServiceBeen1 = (List<FlightData.AccaServiceDateBean>) getIntent().getSerializableExtra("list");
            //加载gridview 特车
            gv_matter = (GridView) view1.findViewById(R.id.gv_matter);
            gv_matter.setAdapter(new SpecialCarAdapter1(this, accaServiceBeen1, mTabLayout, mTitleList));

            //加载gridview  机务
            gv_matter = (GridView) view2.findViewById(R.id.gv_matter);
            gv_matter.setAdapter(new MaintenanceAdapter1(this, accaServiceBeen1, mTabLayout, mTitleList));
            //加载gridview   清洁
            gv_matter = (GridView) view3.findViewById(R.id.gv_matter);
            gv_matter.setAdapter(new CleanAdapter1(this, accaServiceBeen1, mTabLayout, mTitleList));
        }

        //添加页卡视图
        mViewList.add(view1);
        mViewList.add(view2);
        mViewList.add(view3);

        //添加页卡标题
        mTitleList.add("特车");
        mTitleList.add("机务");
        mTitleList.add("清洁");


        mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));//添加tab选项卡
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(2)));


        MyPagerAdapter mAdapter = new MyPagerAdapter(mViewList);
        mViewPager.setAdapter(mAdapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        mTabLayout.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器

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
        actionBar.setTitle("添加完成的事项");

        //设置Actionbar不显示图标
        actionBar.setDisplayShowHomeEnabled(false);
        //设置Actionbar的背景
        try {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.bar11));
        } catch (Exception ex) {
            ex.printStackTrace();
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

    /**
     * 确认选择的点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_select_famf:

                String service_type = "";
                if (0 == mTabLayout.getSelectedTabPosition()) {
                    service_type = mTitleList.get(0);
                } else if (1 == mTabLayout.getSelectedTabPosition()) {
                    service_type = mTitleList.get(1);
                } else {
                    service_type = mTitleList.get(2);
                }
                Intent intent = new Intent(AddFinishedMatterActivity.this, ModifySecurityDetailsActivity.class);
                if (!SpecialCarAdapter.isChecked) {
                    intent.putExtra("name", SpecialCarAdapter.name);
                    intent.putExtra("Service_Seq", "");
                    intent.putExtra("service_type", service_type);
                    startActivity(intent);
                    finish();
                }
                break;
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