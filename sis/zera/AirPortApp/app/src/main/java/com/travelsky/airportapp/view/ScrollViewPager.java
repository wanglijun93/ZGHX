package com.travelsky.airportapp.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * 可以滑动的ViewPager
 */
public class ScrollViewPager extends ViewPager {
    public ScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollViewPager(Context context) {
        super(context);
    }

}
