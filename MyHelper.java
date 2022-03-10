package com.john.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2020/12/29.
 */

public class MyHelper extends SQLiteOpenHelper {
    public MyHelper(Context context) {
        super(context, "SQL_data", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table student(id integer primary key autoincrement,name varchar(20),sex varchar(10),birth integer,department varchar(20),address varchar(20))");
        db.execSQL("insert into student values(901,'张老大','男',1985,'计算机系','北京市海淀区')");
        db.execSQL("insert intostudent values(902,'张老二','男',1986,'中文系','北京市昌平区')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
