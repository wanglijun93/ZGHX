package com.travelsky.airportapp.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.travelsky.airportapp.R;
import com.travelsky.airportapp.utils.ToastUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 修改密码页面
 */
public class ModifyPasswdActivity extends Activity{

    @InjectView(R.id.cell_title)
    TextView cellTitle;
    @InjectView(R.id.changePwd_oldPwd_edit)
    EditText changePwdOldPwdEdit;
    @InjectView(R.id.cell_title1)
    TextView cellTitle1;
    @InjectView(R.id.changePwd_newPwd_edit)
    EditText changePwdNewPwdEdit;
    @InjectView(R.id.change_pwd_button)
    Button changePwdButton;
    private String oldPwdText;
    private String newPwdText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_passwd);

        ButterKnife.inject(this);
        init();
        initBar();
    }
    /**
     * 初始化action bar
     */
    private void initBar() {
        //得到Actionbar
        ActionBar actionBar = getActionBar();

        //设置Actionbar不显示图标
        actionBar.setDisplayShowHomeEnabled(false);
        //设置Actionbar的背景
        try {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.bar11));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private void init() {
        changePwdButton.setOnClickListener(changePwdClick);
    }

    private View.OnClickListener changePwdClick = new View.OnClickListener() {
        public void onClick(View v) {

            oldPwdText = changePwdOldPwdEdit.getText().toString();
            newPwdText = changePwdNewPwdEdit.getText().toString();
            if (oldPwdText == null || oldPwdText.trim().length() == 0 ||
                    newPwdText == null || newPwdText.trim().length() == 0) {
                ToastUtil.showToast("密码不能为空");
                return;

            } else {
                //不为空  要判断原密码是否正确
                Intent intent = new Intent(ModifyPasswdActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };
}
