
package com.travelsky.airportapp.utils.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by isme on 2016/8/4.browser
 */

public class DBHelper extends SQLiteOpenHelper {
    private final static String DB_NAME = "pdcs.db";//数据库名
    private final static int VERSION = 1;//版本号
    // 数据表名，首页
    public static final String ACCA_SERVICE = "ACCA_SERVICE";
    public static final String ACCA_FLIGHT = "ACCA_FLIGHT";
    public static final String ACCA_USER_FLIGHT = "ACCA_USER_FLIGHT";
    public static final String ACCA_AIRPORT = "ACCA_AIRPORT";
    public static final String ACCA_CUSTOMER_CONVERT = "ACCA_CUSTOMER_CONVERT";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

