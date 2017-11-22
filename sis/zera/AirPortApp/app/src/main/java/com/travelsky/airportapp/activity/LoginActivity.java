package com.travelsky.airportapp.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.travelsky.airportapp.AESOperator;
import com.travelsky.airportapp.Base64Utils;
import com.travelsky.airportapp.Cantons;
import com.travelsky.airportapp.R;
import com.travelsky.airportapp.RSAUtils;
import com.travelsky.airportapp.domain.User;
import com.travelsky.airportapp.utils.CacheUtils;
import com.travelsky.airportapp.utils.FileHelper;
import com.travelsky.airportapp.utils.LogUtil;
import com.travelsky.airportapp.utils.NetworkUtils;
import com.travelsky.airportapp.utils.ToastBig;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;

/**
 * 登陆页面
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    @InjectView(R.id.relativeLayout1)
    RelativeLayout relativeLayout1;
    @InjectView(R.id.regist_name_edit)
    EditText registNameEdit;
    @InjectView(R.id.relativeLayout2)
    RelativeLayout relativeLayout2;
    @InjectView(R.id.regist_pass_edit)
    EditText registPassEdit;
    @InjectView(R.id.login_button)
    Button loginButton;
    @InjectView(R.id.login_top)
    LinearLayout loginTop;

    @InjectView(R.id.cb_duigou)
    CheckBox cb_duigou;
    @InjectView(R.id.iv_duigou)
    ImageView iv_duigou;

    //是否记住密码
    private boolean sPwd = false;

    private String Ts;
    private Map<String, String> map;
    private ProgressBar pb_progressbar;

    private String APIPrivateKey = "2D7E7C96-DAC5-4526-96C3-C60CDEC4B120";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        //登录按钮
        loginButton.setOnClickListener(this);
        //是都选择保存账号密码
        cb_duigou.setOnClickListener(this);
        initBar();

        getData();

        pb_progressbar = (ProgressBar) findViewById(R.id.pb_progressbar);

        registPassEdit.setOnEditorActionListener(new Button.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    pb_progressbar.setVisibility(View.VISIBLE);
                    //是否联网
                    if (NetworkUtils.checkNetwork(LoginActivity.this)) {
                        if (checkEmpty()) {
                            requestInternet();
                        }
                    } else {
                        ToastBig.toast(LoginActivity.this, "网络连接不可用");
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.setClassName("com.android.settings", "com.android.settings.Settings");
                        startActivity(intent);
                        pb_progressbar.setVisibility(View.GONE);
                    }
                    registPassEdit.clearFocus();
                    return true;
                }
                return false;
            }
        });

        String request = getIntent().getStringExtra("request");
        LogUtil.d("TAG", "reust==" + request);
        if (request != null) {
            ToastBig.toast(LoginActivity.this, request);
        }
    }

    public void getData() {
        rawCopyData();

        String username = CacheUtils.getString(LoginActivity.this, CacheUtils.username);
        String userpwd = CacheUtils.getString(LoginActivity.this, CacheUtils.userpwd);

        if (!"".equals(username) && !"".equals(userpwd)) {
            registNameEdit.setText(username);
            registPassEdit.setText(userpwd);
        }

    }

    /**
     * 根据id 做出相应操作
     *
     * @param item
     * @return
     */
/*    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            //返回事件
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }*/

    /**
     * 初始化action bar
     */
    private void initBar() {
        //得到Actionbar
        ActionBar actionBar = getActionBar();
        actionBar.hide();
    }

    //判断用户名密码是否为空
    private boolean checkEmpty() {
        String name = registNameEdit.getText().toString().trim();
        String password = registPassEdit.getText().toString().trim();
        if (!"".equals(name) && name != null) {
            if (!"".equals(password) && password != null) {

                return true;
            } else {
                ToastBig.toast(LoginActivity.this, "请输入密码");
                pb_progressbar.setVisibility(View.GONE);
            }
        } else {

//            Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
            ToastBig.toast(LoginActivity.this, "请输入用户名");
            pb_progressbar.setVisibility(View.GONE);
        }

        return false;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.login_button:
                pb_progressbar.setVisibility(View.VISIBLE);

                //是否联网
                if (NetworkUtils.checkNetwork(this)) {
                    if (checkEmpty()) {
                        requestInternet();
                    }
                } else {
                    ToastBig.toast(LoginActivity.this, "网络连接不可用");
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setClassName("com.android.settings", "com.android.settings.Settings");
                    startActivity(intent);
                    pb_progressbar.setVisibility(View.GONE);
                }

                break;
            case R.id.cb_duigou:
                if (!sPwd) {
                    sPwd = true;    //没选中
                    iv_duigou.setVisibility(View.GONE);
                } else {
                    sPwd = false;   //选中
                    iv_duigou.setVisibility(View.VISIBLE);
                }
                break;
        }

    }

    //联网请求
    public void requestInternet() {

                //获取10位的时间戳
                Ts = String.valueOf(System.currentTimeMillis()).substring(0, 10);

                map = new HashMap<>();
                map.put("loginname", registNameEdit.getText().toString().trim());
                map.put("password", FileHelper.ToMD5(registPassEdit.getText().toString().trim()));
                map.put("ts", Ts);


                String sign = GetResponseMysign(map, APIPrivateKey);
                String str = "{\"jtext\":\"" + publicKeyRSA() + "\",\"Ts\":\"" + Ts + "\",\"Sign\":\"" + sign + "\"}";
                LogUtil.d("---登录提交的数据", str);
                //发送post请求
                OkHttpUtils
                        .postString()
                        .url(Cantons.loginUrl)
                        .addHeader("User-Agent","PDCS-APP")
                        .mediaType(MediaType.parse("application/json; charset=utf-8"))
                        .content(str)
                        .build()
                        .execute(new LoginCallback());

    }

    //航班信息回调
    public class LoginCallback extends StringCallback {
        @Override
        public void onBefore(Request request, int id) {
            //setTitle("loading...");
        }

        @Override
        public void onAfter(int id) {
            //setTitle("Sample-okHttp");
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            e.printStackTrace();
            Toast.makeText(LoginActivity.this, "onError:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response, int id) {
            LogUtil.d("TAG", "onResponse：complete");
            LogUtil.d("TAG", "onResponse:" + response);

            final User user = new Gson().fromJson(response, User.class);
            if (1 == user.getResCode()) {
                if (sPwd) {
                    CacheUtils.putString(LoginActivity.this, CacheUtils.username, "");
                    CacheUtils.putString(LoginActivity.this, CacheUtils.userpwd, "");
                } else {

                    CacheUtils.putString(LoginActivity.this, CacheUtils.username, registNameEdit.getText().toString().trim());
                    CacheUtils.putString(LoginActivity.this, CacheUtils.userpwd, registPassEdit.getText().toString().trim());
                }
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                CacheUtils.putString(LoginActivity.this, CacheUtils.userId, user.getUSER_ID());
                CacheUtils.putString(LoginActivity.this, CacheUtils.Company_Code, user.getCOMPANY_CODE());
                CacheUtils.putString(LoginActivity.this, CacheUtils.user_seq, user.getUSER_SEQ());
                startActivity(intent);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pb_progressbar.setVisibility(View.GONE);
                    }
                });
                finish();
            } else if ("请求已过期".equals(user.getResMsg())) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastBig.toast(LoginActivity.this, "请求过期,可能时间不准");
                        pb_progressbar.setVisibility(View.GONE);
                    }
                });

            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastBig.toast(LoginActivity.this, user.getResMsg());
                        LogUtil.d("---msg", user.getResMsg());
                        pb_progressbar.setVisibility(View.GONE);
                    }
                });

            }

        }
    }


    //RSA加密
    public String getRSA(String res) {
        String enString = null;
        try {
            enString = AESOperator.getInstance().encrypt(res);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return enString;
    }

    public String publicKeyRSA() {

        String json = "{\"loginName\":\"" + map.get("loginname") + "\",\"password\":\"" + map.get("password") + "\"}";
        //对用户名和密码加密
        String source = getRSA(json);
        try {

            InputStream inPublic = getResources().getAssets().open("rsa_public_key.pem");
            PublicKey publicKey = RSAUtils.loadPublicKey(inPublic);

            byte[] encryptByte = RSAUtils.encryptData(source.getBytes(), publicKey);

            String afterencrypt = Base64Utils.encode(encryptByte);

            return afterencrypt;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取签名
    public static String GetResponseMysign(Map<String, String> map, String privateKey) {
        String fullstring = CacheUtils.GetPostStrings(map) + privateKey;
        return FileHelper.ToMD5(fullstring);
    }

    //拷贝数据库
    public void rawCopyData() {
        final String DATABASE_FILENAME = "pdcs.db";
        String DATABASE_PATH = "/data/data/" + getApplicationContext().getPackageName() + "/databases";
        try {

            String databaseFilename = DATABASE_PATH + "/" + DATABASE_FILENAME;
            File dir = new File(DATABASE_PATH);

            if (!dir.exists())
                dir.mkdir();

            if (!(new File(databaseFilename)).exists()) {

                InputStream is = getResources().openRawResource(R.raw.pdcs);
                FileOutputStream fos = new FileOutputStream(databaseFilename);
                byte[] buffer = new byte[8192];
                int count = 0;

                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
