package com.travelsky.airportapp.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;

import com.travelsky.airportapp.R;
import com.travelsky.airportapp.utils.FileHelper;

/**
 * @author imgpng@163.com
 * @date 2015-10-19
 * @filename PreviewDocActivity.java
 * @description： 详细信息
 * （原来的网页预览）
 */
public class PreviewDocActivity extends Activity implements View.OnClickListener {
    private WebView mWebView;
    private String mJsonData;//
    private String path;
    private String html;
    private String data = "";
    private String data1 = "";
    private WebSettings wSet;
    private String path1;
    private Button btn_print2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_document_activity);
        btn_print2 = (Button) findViewById(R.id.btn_print2);
        btn_print2.setOnClickListener(this);
        path = getIntent().getStringExtra("path");
        path1 = getIntent().getStringExtra("path1");
        data = "<img height=\\\"50\\\" width=\\\"108\\\" src=\\\"" + path + "\\\"/>";
        data1 = "<img height=\\\"50\\\" width=\\\"108\\\" src=\\\"" + path1 + "\\\"/>";
        initBar();
        init();

        html = FileHelper.getInstance(getApplicationContext()).buildHtmlString(mJsonData);
        html = html.replace("@image_assets", "file://" + path);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    /**
     * 初始化action bar
     */
    private void initBar() {
        //得到Actionbar
        ActionBar actionBar = getActionBar();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayUseLogoEnabled(false);
        actionBar.setTitle("详细信息");
        //设置Actionbar的背景
        try {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.bar11));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void init() {
        mJsonData = initDataJson().toString();
        this.mWebView = (WebView) findViewById(R.id.wv_print_manifest);
        initWebView();
    }


    /**
     * 初始化webView
     */
    @SuppressLint("JavascriptInterface")
    private void initWebView() {
        wSet = mWebView.getSettings();
        wSet.setJavaScriptEnabled(true);
        wSet.setSupportZoom(true);
        wSet.setBuiltInZoomControls(true);
        wSet.setDisplayZoomControls(false);
        mWebView.setInitialScale(70);

        wSet.setLoadWithOverviewMode(true);
        wSet.setJavaScriptCanOpenWindowsAutomatically(true);

        wSet.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        wSet.setUseWideViewPort(true);
        wSet.setSavePassword(false);
        wSet.setSaveFormData(false);
        wSet.setBlockNetworkLoads(true);
        //第一个参数代表在js中可以调用的方法名字，第二个参数是这个方法在js中的名
        mWebView.addJavascriptInterface(new JavaData(), "js2java");
        mWebView.loadUrl("file:///android_asset/html/table.html");

        mWebView.setWebChromeClient(new WebChromeClient() {// 对话框
            @Override
            public boolean onJsAlert(WebView view, String url, String message,
                                     final JsResult result) {
                // 构架一个builder来显示网页中的对话框
                Builder builder = new Builder(PreviewDocActivity.this);
                builder.setTitle("提示对话框");
                builder.setMessage(message);
                builder.setPositiveButton(android.R.string.ok,
                        new AlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // 点击确定按钮之后，继续执行网页中的操作
                                result.confirm();
                            }
                        });
                builder.setCancelable(false);
                builder.create();
                builder.show();
                return true;
            }
        });
    }

    /**
     * yqp add 添加预览功能
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.clear();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_print2:
                Intent intent = new Intent(PreviewDocActivity.this,SecurityDetailsSinglActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }


    /**
     * @author imgpng js调用的对象
     */
    private class JavaData {
        /**
         * yqp add js调用方法
         *
         * @return
         */
        @JavascriptInterface
        public String js2java() {
            return mJsonData;
        }

        /**
         * 与js交互时用到的方法，在js里直接调用的
         */
        @JavascriptInterface
        public void showToast() {
            Intent intent = new Intent(PreviewDocActivity.this, ElectronicSignActivity.class);
            startActivity(intent);
        }
        /**
         * 与js交互时用到的方法，在js里直接调用的
         */
        @JavascriptInterface
        public void showToast1() {
            Handler handler = new Handler();

            handler.post(new Runnable() {
                @Override
                public void run() {
                    Intent intent1 = new Intent(PreviewDocActivity.this, ElectronicSignActivity1.class);
                    startActivity(intent1);
                }
            });

        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        ((FrameLayout) mWebView.getParent()).removeView(mWebView);
        mWebView.setFocusable(true); //
        mWebView.removeAllViews();
        mWebView.removeJavascriptInterface("js2java");
        mWebView.clearHistory();
        mWebView.destroy();
        mWebView = null;
    }

    /**
     * add yqp 创建json数据
     */
    private StringBuffer initDataJson() {
        StringBuffer builderjson = new StringBuffer();
        builderjson.delete(0, builderjson.length());
        builderjson.append("{");
        for (int i = 0; i < 40; i++) {
            if (i < 8) {// 表格头部数据填充
                builderjson.append("\"" + i + "\" : \"" + ""
                        + "\",");
            } else if (i < 29) {// 特车
                String data = "";
                builderjson.append("\"" + i + "\" : \"" + data + "\",");
            } else if (i < 34) {// 机务
                String data = "";
                builderjson.append("\"" + i + "\" : \"" + data + "\",");

            } else if (i < 37) {// 清洁
                String data = "";
                builderjson.append("\"" + i + "\" : \"" + data + "\",");
            } else if (i < 38) {// 其他需要说明
                String shuoming = "\"" + i + "\" : \"" + "  \",";
                builderjson.append(shuoming);
            } else if (i == 38) {// 服务人员签字
                String qianzi = "\"" + i + "\" : \"" + data + "\",";
                builderjson.append(qianzi);
            }else if (i == 39) {// 航空公司代表签字
                String qianzi = "\"" + i + "\" : \"" + data1 + "\",";
                builderjson.append(qianzi);
            }
        }
        builderjson.deleteCharAt(builderjson.lastIndexOf(","));
        builderjson.append("}");

        return builderjson;
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
