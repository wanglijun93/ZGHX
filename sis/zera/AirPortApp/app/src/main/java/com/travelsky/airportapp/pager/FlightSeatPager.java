package com.travelsky.airportapp.pager;

import android.app.Activity;
import android.view.View;

import com.travelsky.airportapp.R;
import com.travelsky.airportapp.base.BasePager;

/**
 * 机位显示
 */
public class FlightSeatPager extends BasePager {

    public FlightSeatPager(Activity activity) {
        super(activity);
    }


    @Override
    public void initData() {
        super.initData();

        //加载视图
        View view = View.inflate(mActivity, R.layout.airport_flight_seat_list, null);

        //把当前View添加到FrameLayout中
        fl_base_content.addView(view);
    }
}
