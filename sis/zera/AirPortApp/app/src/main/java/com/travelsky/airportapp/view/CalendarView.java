
package com.travelsky.airportapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.travelsky.airportapp.view.timeview.WheelView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CalendarView extends View {

    public static final int CELLH = 80;
    public static final int WEEKTITLEH = 40;
    private TextPaint mPaint;
    private Context ctx;
    private int count;
    private int startWeek;
    private float cellW;
    private RectF rect;
    private Paint rectPaint;

    private int year;
    private int month;
    private List<String> lunar;

    public int startDrawDayBg;

    private onItemClickListener listener;

    private static final String[] WEEKS = new String[] {
            "周日", "周一", "周二", "周三", "周四", "周五", "周六"
    };
    private TextPaint lunPaint;
    private float lunFontH;
    private GestureDetector detector;

    private int clickYear, clickMonth, clickDay;

    public static boolean drawToday = false;

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public CalendarView(Context context) {
        super(context);
        init(context);
    }

    public void initDate(int year, int month) {
        if (month <= 0 || month > 12) {
            throw new IllegalArgumentException("THE count 不能是: " + month);
        }
        this.year = year;
        this.month = month;

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        this.startWeek = calendar.get(Calendar.DAY_OF_WEEK);
        count = getCountDay(month, year);
        // lunar = CalendarUtil.getLunar(calendar, count);
    }

    public void setLunar(List<String> lunar) {
        this.lunar = lunar;
    }

    private int getCountDay(int month2, int year2) {
        switch (month2) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if ((year2 % 4 == 0 && year2 % 100 != 0) || year2 % 400 == 0)
                    return 29;
                return 28;
        }
        return 0;
    }

    private void init(Context ctx) {
        this.ctx = ctx;
        mPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(0xff333333);

        lunPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        lunPaint.setTextSize(15);
        lunPaint.setAntiAlias(true);
        FontMetrics fontMetrics = lunPaint.getFontMetrics();
        lunFontH = Math.abs(fontMetrics.bottom + fontMetrics.top);

        rectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        detector = new GestureDetector(ctx, new GestureDetector.OnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                if (listener != null) {
                    try {
                        float x = e.getX();
                        float y = e.getY();
                        if (y > CELLH + WEEKTITLEH) {
                            y = y - CELLH - WEEKTITLEH;
                            int w = (int) (x / cellW);
                            int h = (int) (y / CELLH);
                            int position = h * 7 + w;
                            position = position - (startWeek - 1);
                            if (position >= 0 && position < count) {
                                if (position >= startDrawDayBg) {
                                    String dayStr = null;
                                    position++;
                                    if (position < 10) {
                                        dayStr = "0" + (position);
                                    } else {
                                        dayStr = String.valueOf(position);
                                    }
                                    String monthStr = null;
                                    if (month < 10) {
                                        monthStr = "0" + month;
                                    } else {
                                        monthStr = String.valueOf(month);
                                    }
                                    listener.onClick(String.valueOf(year).concat("-")
                                            .concat(monthStr).concat("-").concat(dayStr));
                                    return true;
                                }
                            }
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }

                }
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                if (listener != null) {
                    try {
                        float x = e.getX();
                        float y = e.getY();
                        if (y > CELLH + WEEKTITLEH) {
                            y = y - CELLH - WEEKTITLEH;
                            int w = (int) (x / cellW);
                            int h = (int) (y / CELLH);
                            int position = h * 7 + w;
                            position = position - (startWeek - 1);
                            if (position >= 0 && position < count) {
                                if (position >= startDrawDayBg) {
                                    clickYear = year;
                                    clickMonth = month;
                                    clickDay = position + 1;
                                    postInvalidate();
                                    return true;
                                }
                            }
                        }
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }

                }
                return false;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return detector.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wSize = getMeasure(widthMeasureSpec, Integer.MAX_VALUE);
        int hSize = getMeasure(heightMeasureSpec, getRows() * CELLH + WEEKTITLEH + CELLH);
        setMeasuredDimension(wSize, hSize);
        cellW = getMeasuredWidth() / 7.0f;
        rect = new RectF(0, 0, cellW - 6, CELLH - 6);
    }

    public int getRows() {
        return (count + startWeek - 1 + 6) / 7;
    }

    private int getMeasure(int measureSpec, int supposeSize) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            return size;
        } else if (mode == MeasureSpec.AT_MOST) {
            if (supposeSize < size) {
                return supposeSize;
            } else {
                return size;
            }
        } else {
            return supposeSize;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        try {
            drawBg(canvas);
            drawTitle(canvas);
            drawWEEKTitle(canvas);
            drawDate(canvas);
            drawToday(canvas);
//            drawClickDay(canvas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void drawClickDay(Canvas canvas) {
        if (clickYear == this.year) {
            if (clickMonth == this.month && clickDay != 0) {

                int position = clickDay + startWeek - 2;

                float top = position / 7 * CELLH + CELLH + WEEKTITLEH;
                float left = position % 7 * cellW;
                rect.offsetTo(left + 3, top + 3);
                rectPaint.setColor(0xff67a82a);
                canvas.drawRect(rect, rectPaint);

                mPaint.setTextSize(28);
                mPaint.setFakeBoldText(true);
                mPaint.setColor(0xffffffff);
                FontMetrics fontMetrics = mPaint.getFontMetrics();
                float frontH = Math.abs(fontMetrics.bottom + fontMetrics.top);

                String dateStr = String.valueOf(clickDay);
                float frontW = mPaint.measureText(dateStr);
                canvas.drawText(dateStr, left + (cellW - frontW) / 2, top
                        + (CELLH / 3 * 2 - frontH) / 2 + frontH, mPaint);
                String lun = lunar.get(clickDay - 1);
                if (lun.contains("h")) {
                    lun = lun.replace("h", "");
                }
                frontW = lunPaint.measureText(lun);
                lunPaint.setColor(0xffffffff);
                canvas.drawText(lun, left + (cellW - frontW) / 2, top + CELLH
                        / 3 * 2 + lunFontH, lunPaint);
                clickYear = 0;
                clickMonth = 0;
                clickDay = 0;
            }
        }
    }

    private void drawToday(Canvas canvas) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        if (year == this.year && drawToday) {
            int month = calendar.get(Calendar.MONTH);
            {
                if (month == this.month - 1) {

                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    int position = day + startWeek - 2;

                    float top = position / 7 * CELLH + CELLH + WEEKTITLEH;
                    float left = position % 7 * cellW;
                    rect.offsetTo(left + 3, top + 3);
                    rectPaint.setColor(0xff30b5b9);
                    canvas.drawRect(rect, rectPaint);

                    mPaint.setTextSize(28);
                    mPaint.setFakeBoldText(true);
                    mPaint.setColor(0xffffffff);
                    FontMetrics fontMetrics = mPaint.getFontMetrics();
                    float frontH = Math.abs(fontMetrics.bottom + fontMetrics.top);

                    String dateStr = String.valueOf(day);
                    float frontW = mPaint.measureText(dateStr);
                    canvas.drawText(dateStr, left + (cellW - frontW) / 2, top
                            + (CELLH / 3 * 2 - frontH) / 2 + frontH, mPaint);
                    String lun = "今天";
                    lunPaint.setFakeBoldText(true);
                    frontW = lunPaint.measureText(lun);
                    lunPaint.setColor(0xffffffff);
                    canvas.drawText(lun, left + (cellW - frontW) / 2, top + CELLH / 3 * 2
                            + lunFontH, lunPaint);
                    lunPaint.setFakeBoldText(false);
                }
            }
        }
        drawToday = false;
    }

    private void drawTitle(Canvas canvas) {
        String yearStr = year + "年";
        String monthStr = month + "月";

        mPaint.setColor(0xffffffff);
        canvas.drawRect(0, 0, getWidth(), CELLH, mPaint);

        mPaint.setTextSize(35);
        mPaint.setFakeBoldText(true);
        mPaint.setColor(0xff585a55);
        FontMetrics fontMetrics = mPaint.getFontMetrics();
        float frontH = Math.abs(fontMetrics.bottom + fontMetrics.top);

        float yesrFrontW = mPaint.measureText(yearStr);
        float monthFrontW = mPaint.measureText(monthStr);

        canvas.drawText(yearStr, (getWidth() - (yesrFrontW + monthFrontW)) / 2, (CELLH - frontH)
                / 2 + frontH, mPaint);
        mPaint.setColor(0xff67a82a);
        canvas.drawText(monthStr, (getWidth() - (yesrFrontW + monthFrontW)) / 2 + yesrFrontW,
                (CELLH - frontH) / 2 + frontH, mPaint);
    }

    private void drawBg(Canvas canvas) {
        mPaint.setColor(0xfffafafa);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
    }

    public void drawDate(Canvas canvas) {
        mPaint.setTextSize(28);
        mPaint.setFakeBoldText(true);
        FontMetrics fontMetrics = mPaint.getFontMetrics();
        float frontH = Math.abs(fontMetrics.bottom + fontMetrics.top);
        mPaint.setColor(0xff333333);
        rectPaint.setColor(0xffecf0e8);
        lunPaint.setColor(0xff61645f);
        for (int i = 0; i < count; i++) {
            float left = ((i + (startWeek - 1)) % 7) * cellW;
            float top = ((i + (startWeek - 1)) / 7) * CELLH + WEEKTITLEH + CELLH;

            rect.offsetTo(left + 3, top + 3);
            if (i >= startDrawDayBg) {
                canvas.drawRect(rect, rectPaint);
            }

            String dateStr = String.valueOf(i + 1);
            float frontW = mPaint.measureText(dateStr);
            canvas.drawText(dateStr, left + (cellW - frontW) / 2, top + (CELLH / 3 * 2 - frontH)
                    / 2 + frontH, mPaint);
            String lun = lunar.get(i);
            if (lun.startsWith("h")) {
                lun = lun.substring(2, lun.length());
                lunPaint.setColor(0xffe22417);
                frontW = lunPaint.measureText(lun);
                canvas.drawText(lun, left + (cellW - frontW) / 2, top + CELLH / 3 * 2 + lunFontH,
                        lunPaint);
                lunPaint.setColor(0xff61645f);
            } else {
                frontW = lunPaint.measureText(lun);
                canvas.drawText(lun, left + (cellW - frontW) / 2, top + CELLH / 3 * 2 + lunFontH,
                        lunPaint);
            }
        }
    }

    public void UnusePreviousDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        if (year > this.year) {
            startDrawDayBg = count;
            return;
        }
        if (year < this.year) {
            startDrawDayBg = 0;
            return;
        }
        int month = calendar.get(Calendar.MONTH);
        if (this.month < month + 1) {
            startDrawDayBg = count;
        } else if (month + 1 == this.month) {
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            startDrawDayBg = day - 1;
        } else {
            startDrawDayBg = 0;
        }
    }

    private void drawWEEKTitle(Canvas canvas) {
        mPaint.setTextSize(16);
        FontMetrics fontMetrics = mPaint.getFontMetrics();
        mPaint.setAntiAlias(true);
        float frontH = Math.abs(fontMetrics.bottom + fontMetrics.top);
        mPaint.setColor(0xff8d8d8d);
        for (int i = 0; i < 7; i++) {
            float left = i * cellW;
            float top = CELLH;
            String weekStr = WEEKS[i];
            float frontW = mPaint.measureText(weekStr);
            canvas.drawText(weekStr, left + (cellW - frontW) / 2, top + (WEEKTITLEH - frontH) / 2
                    + frontH, mPaint);

        }
    }

    public interface onItemClickListener {
        void onClick(String date);
    }

    public static String getDate(int value) {
        final Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, value);
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        StringBuffer sb = new StringBuffer();
        sb.append(mYear).append("-");
        if (String.valueOf(mMonth).length() == 1 && mMonth != 9)
            sb.append("0");
        sb.append(mMonth + 1).append("-");
        if (String.valueOf(mDay).length() == 1)
            sb.append("0");
        sb.append(mDay);
        return sb.toString();
    }

    public static List<String> startEndTime(WheelView startDays,WheelView startHour,WheelView startMins,WheelView endDays,WheelView endHour,WheelView endMins) {

        List<String> list = new ArrayList<>();

        String startday = "";

        switch (startDays.getCurrentItem()) {
            case 0:
                startday = getDate(-1);
                break;
            case 1:
                startday = getDate(0);
                break;
            case 2:
                startday = getDate(1);
                break;
        }

        startday = startday+" "+startHour.getCurrentItem() +":"+startMins.getCurrentItem();
        String endday = "";
        switch (endDays.getCurrentItem()) {
            case 0:
                endday = getDate(-1);
                break;
            case 1:
                endday = getDate(0);
                break;
            case 2:
                endday = getDate(1);
                break;
        }

        endday = endday+" "+endHour.getCurrentItem()+":"+endMins.getCurrentItem();
        list.add(startday);
        list.add(endday);
        return list;
    }
}
