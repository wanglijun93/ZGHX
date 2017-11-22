package com.travelsky.airportapp.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.travelsky.airportapp.R;
import com.travelsky.airportapp.pager.PackageInfoUtils;
import com.travelsky.airportapp.utils.AirportApplication;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author wlj
 * 信息平台的帮助页面
 */
public class AboutActivity extends Activity implements View.OnClickListener {

    @InjectView(R.id.about_version)
    TextView aboutVersion;

    private ActionBar actionBar;
    private Context con;
    private Intent intent;
    private TextView tv11;
    private TextView tv21;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.inject(this);
        init();
        initBar();

    }

    /**
     * 添加bar 按钮
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.about_menu, menu);
        return true;
    }

    /**
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                //标题左上角的返回按钮事件
                onBackPressed();
                break;
            case R.id.show_data1:
                //自定义popupwidow
                PopuWindow();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 初始化action bar
     */
    private void initBar() {
        //得到Actionbar
        actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        //设置Actionbar的背景
        try {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.bar11));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * 获取应用版本号
     */
    private void init() {
        String version = PackageInfoUtils.getPackageVersion(this);
        aboutVersion.setText("V" + version);

    }

    /**
     * 自定义popuwind
     */
    public void PopuWindow() {
        con = AirportApplication.context;
        LayoutInflater inflater = (LayoutInflater) con.getSystemService(AirportApplication.LAYOUT_INFLATER_SERVICE);
        View inflate = inflater.inflate(R.layout.add_popupwin, null);
        //初始化自定义布局中的两个条目
        tv11 = (TextView) inflate.findViewById(R.id.tv_1);
        tv21 = (TextView) inflate.findViewById(R.id.tv_2);
        tv11.setOnClickListener(this);
        tv21.setOnClickListener(this);
        PopupWindow popupWindow = new PopupWindow(this);
        int height = AboutActivity.this.getWindowManager().getDefaultDisplay().getHeight();
        int width = AboutActivity.this.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        popupWindow.setContentView(inflate);
        // 设置SelectPicPopupWindow的View的宽
        popupWindow.setWidth(width / 3 + 50);
        // 设置SelectPicPopupWindow弹出窗体的高
        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        // 刷新状态
        popupWindow.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        popupWindow.setBackgroundDrawable(dw);
        View viewById = findViewById(R.id.show_data1);
        popupWindow.showAsDropDown(viewById, viewById.getLayoutParams().width / 2, 0);

    }

    /**
     * 修改密码  退出登录的点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_1:
                intent = new Intent(AboutActivity.this, ModifyPasswdActivity.class);
                startActivity(intent);

                break;
            case R.id.tv_2:
                intent = new Intent(AboutActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
