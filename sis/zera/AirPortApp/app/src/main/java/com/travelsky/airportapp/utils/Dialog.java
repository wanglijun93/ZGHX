package com.travelsky.airportapp.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.travelsky.airportapp.R;

/**
 * Created by iwanglijun on 2016/9/9.
 */
public class Dialog  {
    //加载动画
    public static AlertDialog planDialog(Context context) {
        View v = View.inflate(context, R.layout.dialog, null);
        ImageView image = (ImageView)v.findViewById(R.id.image);
        TranslateAnimation animation = new TranslateAnimation(0, 300, 0, 0);
        animation.setDuration(3000);
        animation.setRepeatCount(100);
        image.setAnimation(animation);
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setView(v)
                .show();

        WindowManager.LayoutParams params =
                alertDialog.getWindow().getAttributes();
        params.width = 500;
        params.height = 500 ;
        alertDialog.getWindow().setAttributes(params);
        return alertDialog;
    }
}
