package com.travelsky.airportapp.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.travelsky.airportapp.R;
import com.travelsky.airportapp.utils.FileHelper;
import com.travelsky.airportapp.view.ElectronicSignView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 电子签名
 */
public class ElectronicSignActivity1 extends Activity {

    private ImageView ivShow;
    private Button btnOk;
    private Button btnNext;
    private Button btnClear;
    private ElectronicSignView signView;
    private boolean isShow = false;
    private Bitmap bit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electronic_sign);
        initBar();
        initView();
        addListener();

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
        actionBar.setTitle("电子签名");
        //设置Actionbar的背景
        try {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.bar11));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 初始化xml里面的控件
     */
    private void initView() {
        signView = (ElectronicSignView) findViewById(R.id.view_elect_sign);
        btnOk = (Button) findViewById(R.id.btn_ok);
        ivShow = (ImageView) findViewById(R.id.iv_show);
        btnClear = (Button) findViewById(R.id.btn_clear);
//        btnNext = (Button) findViewById(R.id.btn_next);
        ivShow.setVisibility(View.VISIBLE);
    }

    /**
     * 各个控件的点击事件
     */
    private void addListener() {
        btnOk.setOnClickListener(new OnClickListener() {
            //确认签名
            @Override
            public void onClick(View v) {
                bit = signView.getPic();
                if (bit != null) {
                    //...
                    ivShow.setVisibility(View.VISIBLE);
                    isShow = true;
                    ivShow.setImageBitmap(bit);
                } else {
                    Toast.makeText(ElectronicSignActivity1.this, "请输入您的签名", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnClear.setOnClickListener(new OnClickListener() {
            //重新签名
            @Override
            public void onClick(View arg0) {
                signView.clearCanvas();
                ivShow.setVisibility(View.GONE);
            }
        });
        btnNext.setOnClickListener(new OnClickListener() {
            //下一步
            @Override
            public void onClick(View arg0) {
                if (isShow) {
                    isShow = false;
//                    String filePath = savePic(bit);
                    String filePath = saveImg(bit);
                    Intent it = new Intent(ElectronicSignActivity1.this, PreviewDocActivity.class);
                    Bundle alBundle = new Bundle();
                    alBundle.putString("path1", filePath);
                    it.putExtras(alBundle);
                    startActivity(it);
                    finish();
                } else {
                    Toast.makeText(ElectronicSignActivity1.this, "请输入您的签名", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    String fileName = "";

    /**
     * 保存图片的方法
     * @param bit bitmap图片
     * @return
     */
    public String savePic(Bitmap bit) {
        if (bit != null) {
            fileName = "sign" + System.currentTimeMillis() + ".jpg";
            FileHelper helper = FileHelper.getInstance(ElectronicSignActivity1.this);
            File file = null;
            try {

                file = helper.saveBitmapToFile(bit, "/sdcard/ADMS/pic/sign/" + fileName);
                return file.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private Bitmap getDiskBitmap() {
        Bitmap bitmap = null;
        try {
            File file = new File("/sdcard/ADMS/pic/sign/" + fileName);
            if (file.exists()) {
                bitmap = BitmapFactory.decodeFile("/sdcard/ADMS/pic/sign/" + fileName);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        return bitmap;
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
    /**
     * 保存图片
     * @author YOLANDA
     * @param bitmap 图片
     * @return
     */
    private String saveImg(Bitmap bitmap){
        File myappDir = new File(Environment.getExternalStorageDirectory(), "airPortPhoto1");
        if(myappDir.exists() && myappDir.isFile()) {
            myappDir.delete();
        }
        if (!myappDir.exists()) {
            myappDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".png";
        File file = new File(myappDir, fileName);
        if(file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }
    /**
     * 保存图片到SD卡
     * @author YOLANDA
     * @param isInsertGallery 是否保存到图库
     * @return
     */
    private String saveImg1(Bitmap bitmap, boolean isInsertGallery){
        File myappDir = new File(Environment.getExternalStorageDirectory(), "yolanda");
        if(myappDir.exists() && myappDir.isFile()) {
            myappDir.delete();
        }
        if (!myappDir.exists()) {
            myappDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".png";
        File file = new File(myappDir, fileName);
        if(file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(isInsertGallery) {
            try {
                MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), fileName, null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return file.getAbsolutePath();
    }
}