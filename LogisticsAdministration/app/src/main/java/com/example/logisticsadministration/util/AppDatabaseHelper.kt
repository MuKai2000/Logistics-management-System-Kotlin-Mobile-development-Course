package com.example.logisticsadministration.util

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper


class AppDatabaseHelper(private val mContext: Context, name: String?, factory: CursorFactory?, version: Int) : SQLiteOpenHelper(mContext, name, factory, version) {

    // User表 保存id 用户名 密码 城市等7条信息
    private val CREATE_USER : String = ("create table User ("
            + "user_id integer primary key autoincrement,"
            + "user_department text,"
            + "user_name text,"
            + "user_login integer,"
            + "user_password text,"
            + "user_tel integer,"
            + "user_city text)")

    // Logistic表 保存id 发站、到站、发货人、收货人等11条数据信息
    private val CREATE_LOGISTIC : String = ("create table Logistic ("
            + "bill_id integer primary key autoincrement,"
            + "bill_start text,"
            + "bill_end text,"
            + "bill_sender text,"
            + "bill_sender_tel integer,"
            + "bill_receiver text,"
            + "bill_receiver_tel integer,"
            + "bill_goods text,"
            + "bill_num integer,"
            + "bill_cost_paid integer,"
            + "bill_cost_unpaid integer)")

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_USER)
        db.execSQL(CREATE_LOGISTIC)
        // Toast
        // ToastUtil.showMsg(mContext,"Create Table Successful")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("drop table if exists User")
        db.execSQL("drop table if exists Logistic")
        onCreate(db)
    }

}