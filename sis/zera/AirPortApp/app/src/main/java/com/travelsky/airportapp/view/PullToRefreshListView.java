package com.travelsky.airportapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.travelsky.airportapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by iwanglijun on 2016/11/1.
 * 下拉刷新 上拉加载更多
 */
public class PullToRefreshListView extends ListView implements AbsListView.OnScrollListener {
    private static final int STATE_PULL_TO_REFRESH = 1;
    private static final int STATE_RELEASE_TO_REFRESH = 2;
    private static final int STATE_REFRESHING = 3;

    private int mCurrentState = STATE_PULL_TO_REFRESH;// 当前刷新状态

    private View mHeaderView;
    private int mHeaderViewHeight;
    private int startY = -1;

    private TextView tvTitle;
    private TextView tvTime;
    private ImageView ivArrow;

    private RotateAnimation animUp;
    private RotateAnimation animDown;
    private ProgressBar pbProgress;

    public PullToRefreshListView(Context context, AttributeSet attrs,
                                 int defStyle) {
        super(context, attrs, defStyle);
//        initHeaderView();
        initFooterView();
    }

    public PullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        initHeaderView();
        initFooterView();
    }

    public PullToRefreshListView(Context context) {
        super(context);
//        initHeaderView();
        initFooterView();
    }

    /**
     * 初始化头布局
     */
    private void initHeaderView() {
        mHeaderView = View.inflate(getContext(),
                R.layout.pull_to_refresh_header, null);
        this.addHeaderView(mHeaderView);

        tvTitle = (TextView) mHeaderView.findViewById(R.id.tv_title);
        tvTime = (TextView) mHeaderView.findViewById(R.id.tv_time);
        ivArrow = (ImageView) mHeaderView.findViewById(R.id.iv_arrow);
        pbProgress = (ProgressBar) mHeaderView.findViewById(R.id.pb_loading);

        // 隐藏头布局
        mHeaderView.measure(0, 0);
        mHeaderViewHeight = mHeaderView.getMeasuredHeight();
        mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);

        initAnim();
        setCurrentTime();
    }

    /**
     * 初始化脚布局
     */
    private void initFooterView() {
        mFooterView = View.inflate(getContext(),
                R.layout.pull_to_refresh_footer, null);
        mFooterView.measure(0, 0);
        mFooterViewHeight = mFooterView.getMeasuredHeight();
        mFooterView.setPadding(0, -mFooterViewHeight, 0, 0);
        this.addFooterView(mFooterView);
        this.setOnScrollListener(this);// 滑动监听
    }



    /**
     * 头布局的子类
     * 设置刷新时间
     */
    private void setCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(new Date());

        tvTime.setText(time);
    }

    /**
     * 头布局的子类
     * 初始化箭头动画
     */
    private void initAnim() {
        animUp = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animUp.setDuration(200);
        animUp.setFillAfter(true);

        animDown = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animDown.setDuration(200);
        animDown.setFillAfter(true);
    }

    /**
     * 刷新结束,收起控件
     */
    public void onRefreshComplete(boolean success) {
        if (!isLoadMore) {
//            mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);
//
//            mCurrentState = STATE_PULL_TO_REFRESH;
//            tvTitle.setText("下拉刷新");
//            pbProgress.setVisibility(View.INVISIBLE);
//            ivArrow.setVisibility(View.VISIBLE);
//
//            if (success) {// 只有刷新成功之后才更新时间
//                setCurrentTime();
//            }
        } else {
            //加载更多
            mFooterView.setPadding(0, -mFooterViewHeight, 0, 0);//隐藏布局
            isLoadMore = false;
        }
    }

    // 3. 定义成员变量,接收监听对象
    private OnRefreshListener mListener;
    private View mFooterView;
    private int mFooterViewHeight;

    /**
     * 2. 暴露接口,设置监听
     */
    public void setOnRefreshListener(OnRefreshListener listener) {
        mListener = listener;
    }

    /**
     * 1. 下拉刷新的回调接口
     */
    public interface OnRefreshListener {
        public void onRefresh();

        //下拉加载更多
        public void onLoadMore();
    }

    private boolean isLoadMore;// 标记是否正在加载更多

    // 滑动状态发生变化
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE) {// 空闲状态
            int lastVisiblePosition = getLastVisiblePosition();

            if (lastVisiblePosition == getCount() - 1 && !isLoadMore) {// 当前显示的是最后一个item并且没有正在加载更多
                // 到底了
                System.out.println("加载更多...");
                isLoadMore = true;
                mFooterView.setPadding(0, 0, 0, 0);// 显示加载更多的布局
                //将listview显示在最后一个item上,从而加载更多会直接展示出来, 无需手动滑动
                setSelection(getCount() - 1);

                //通知主界面加载下一页数据
                if (mListener != null) {
                    mListener.onLoadMore();
                }
            }
        }
    }

    // 滑动过程回调
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {

    }
}