/*
package com.travelsky.airportapp.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.travelsky.airportapp.R;
import com.travelsky.airportapp.base.BaseFragment;
import com.travelsky.airportapp.base.BasePager;
import com.travelsky.airportapp.pager.DataStaticsPager;
import com.travelsky.airportapp.pager.FlightControlPager;
import com.travelsky.airportapp.pager.FlightSeatPager;
import com.travelsky.airportapp.pager.InfoPlatPager;
import com.travelsky.airportapp.view.ScrollViewPager;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

*/
/**
 * 作用：正文的Fragment
 *//*


public class ContentFragment extends BaseFragment {

    private RadioGroup rg_content_fragment;

    private ScrollViewPager vp_content_fragment;
    */
/**
     * 页面的集合
     *//*

    private ArrayList<BasePager> basePagers;

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.content_fragment, null);
        rg_content_fragment = (RadioGroup) view.findViewById(R.id.rg_content_fragment);
        vp_content_fragment = (ScrollViewPager) view.findViewById(R.id.vp_content_fragment);

        return view;
    }


    @Override
    public void initData() {
        super.initData();
        //默认选中首页
        rg_content_fragment.check(R.id.rb_flightcontrol);

        //把五个页面加载到集合中--准备数据
        basePagers = new ArrayList<>();
        basePagers.add(new FlightControlPager(mActivity));//添加航班管控页面
        basePagers.add(new FlightSeatPager(mActivity));//添加机位显示页面
        basePagers.add(new DataStaticsPager(mActivity));//添加数据统计页面
        basePagers.add(new InfoPlatPager(mActivity));//添加信息平台页面
        //设置ViewPager的适配器
        vp_content_fragment.setAdapter(new MyAdapter());


        //设置监听ScrollViewPager滑动状态的改变
        vp_content_fragment.addOnPageChangeListener(new ScrollViewPagerChangeListener());

        //设置监听RadioGroup选中状态的改变
        rg_content_fragment.setOnCheckedChangeListener(new MyOnCheckedChangeListener());

    }

    class ScrollViewPagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    rg_content_fragment.check(R.id.rb_flightcontrol);
                    //发送数据改变MainActivity中Actionbar的菜单
                    EventBus.getDefault().post(new Integer(0));
                    //重新创建OptionsMenu
                    mActivity.invalidateOptionsMenu();
                    break;
                case 1:
                    rg_content_fragment.check(R.id.rb_flightseat);
                    EventBus.getDefault().post(new Integer(1));

                    mActivity.invalidateOptionsMenu();
                    break;
                case 2:
                    rg_content_fragment.check(R.id.rb_datastatics);
                    EventBus.getDefault().post(new Integer(2));

                    mActivity.invalidateOptionsMenu();
                    break;
                case 3:
                    rg_content_fragment.check(R.id.rb_infoplat);
                    EventBus.getDefault().post(new Integer(3));

                    mActivity.invalidateOptionsMenu();
                    break;

            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            switch (checkedId) {
                case R.id.rb_flightcontrol://切换到Viewpager第0个页面
                    vp_content_fragment.setCurrentItem(0, false);

                    break;
                case R.id.rb_flightseat://切换到Viewpager第1个页面
                    vp_content_fragment.setCurrentItem(1, false);

                    break;
                case R.id.rb_datastatics://切换到Viewpager第2个页面
                    vp_content_fragment.setCurrentItem(2, false);

                    break;
                case R.id.rb_infoplat://切换到Viewpager第3个页面
                    vp_content_fragment.setCurrentItem(3, false);

                    break;

            }
        }
    }

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return basePagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            BasePager basePager = basePagers.get(position);//position=0本质是HomePager
            View rootView = basePager.rootView;

            //调用各个页面的initData()方法
            basePager.initData();
            container.addView(rootView);
            return rootView;


        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }
}
*/
