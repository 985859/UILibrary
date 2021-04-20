package com.mylibrary.api.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyDBOpenHelper extends SQLiteOpenHelper {
    public static final String SELECT = "selects";//搜索结果表名

    public MyDBOpenHelper(Context context) {
        super(context, context.getPackageName() + "1.db", null, 1);
    }

    public MyDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, context.getPackageName() + ".db", null, 1);
    }


    @Override
    //数据库第一次创建时被调用
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists " + SELECT + "(_id integer  not null primary key  autoincrement, title  varchar(50) , time varchar(50),type integer)");
        db.execSQL("create table if not exists bitmap(_id integer  not null primary key  autoincrement, base64  varchar(10000) , time varchar(50))");
    }

    //软件版本号发生改变时调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE person ADD phone VARCHAR(12) ");
    }
}