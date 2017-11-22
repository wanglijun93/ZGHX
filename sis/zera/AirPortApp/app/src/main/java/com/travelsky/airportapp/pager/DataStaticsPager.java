package com.travelsky.airportapp.pager;

import android.app.Activity;
import android.view.View;

import com.travelsky.airportapp.R;
import com.travelsky.airportapp.base.BasePager;

/**
 * 数据统计
 */
public class DataStaticsPager extends BasePager {

    public DataStaticsPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        super.initData();

        //加载视图
        View view = View.inflate(mActivity, R.layout.delaystatisticslist_layout, null);

        //把当前View添加到FrameLayout中
        fl_base_content.addView(view);

    }
}