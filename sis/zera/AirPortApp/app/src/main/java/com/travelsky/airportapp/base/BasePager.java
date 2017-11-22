package com.travelsky.airportapp.base;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;

import com.travelsky.airportapp.R;


public class BasePager {

    public FrameLayout fl_base_content;

    /**
     * 上下文
     */
    public final Activity mActivity;

    /**
     * 根View
     */
    public View rootView;

    public BasePager(Activity activity) {
        this.mActivity = activity;
        rootView = initView();
    }

    /**
     * 初始化视图
     *
     * @return
     */
    private View initView() {
        View view = View.inflate(mActivity, R.layout.base_pager, null);
        fl_base_content = (FrameLayout) view.findViewById(R.id.fl_base_content);
        return view;
    }

    /**
     * 当孩子需要请求网络数据，或者数据初始化的时候，重新该方法
     */
    public void initData() {

    }
}
