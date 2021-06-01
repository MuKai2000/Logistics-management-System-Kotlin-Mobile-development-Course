package com.example.logisticsadministration

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.logisticsadministration.util.AppDatabaseHelper
import com.example.logisticsadministration.util.LogisticsInfo
import com.example.logisticsadministration.util.LogisticsInfoRvAdapter
import kotlin.system.exitProcess

class SearchLocalActivity : AppCompatActivity() {
    // 本地搜索
    // 数据库声明
    private var UserDatabase : String = "Info.db"
    lateinit var UserDB : AppDatabaseHelper
    // 控件声明
    lateinit var button_back : Button
    lateinit var RCycleView : RecyclerView
    // 全局变量定义
    lateinit var user : String
    lateinit var pd : String
    lateinit var city : String
    private var mList  : List<LogisticsInfo> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_local)

        // 接受信息
        val intent = intent
        user = intent.getStringExtra("username")!!
        pd = intent.getStringExtra("password")!!
        city = intent.getStringExtra("city")!!
        // 数据库绑定
        UserDB = AppDatabaseHelper(this, UserDatabase, null, 1)
        // 控件绑定
        button_back = findViewById(R.id.button_back_4)
        RCycleView = findViewById(R.id.recycle_view_4)

        // 数据读入
        val num = initInfoData()
        var layout : LinearLayoutManager = LinearLayoutManager(this)
        RCycleView.layoutManager = layout
        var adapter : LogisticsInfoRvAdapter = LogisticsInfoRvAdapter(mList)
        RCycleView.adapter = adapter


        // 实现界面跳转
        button_back.setOnClickListener(this::back)
    }

    private fun back(v: View){
        // 跳转
        val intent: Intent = Intent(this, UserActivity::class.java)
        //传递信息
        intent.putExtra("username", user)
        intent.putExtra("password", pd)
        intent.putExtra("city", city)
        startActivity(intent)
        exitProcess(0)
    }

    private fun initInfoData():Int{
        // 读取数据库
        var db : SQLiteDatabase = UserDB.writableDatabase
        // 统计个数
        var cursor : Cursor = db.query("Logistic",null,null,null,null,null,null)
        var num : Int = 0
        if(cursor.moveToFirst()){
            do{
                var no : String = cursor.getString(cursor.getColumnIndex("bill_id"))
                var start : String = cursor.getString(cursor.getColumnIndex("bill_start"))
                var end : String = cursor.getString(cursor.getColumnIndex("bill_end"))
                var goods : String = cursor.getString(cursor.getColumnIndex("bill_goods"))
                var num : String = cursor.getString(cursor.getColumnIndex("bill_num"))
                var sender : String = cursor.getString(cursor.getColumnIndex("bill_sender"))
                var sender_tel : String = cursor.getString(cursor.getColumnIndex("bill_sender_tel"))
                var name : String = cursor.getString(cursor.getColumnIndex("bill_receiver"))
                var tel : String = cursor.getString(cursor.getColumnIndex("bill_receiver_tel"))
                var cost_paid : String = cursor.getString(cursor.getColumnIndex("bill_cost_paid"))
                var cost : String = cursor.getString(cursor.getColumnIndex("bill_cost_unpaid"))

                var info : LogisticsInfo = LogisticsInfo(no ,start,end,goods,num,
                    sender,sender_tel,name,tel,"",cost,cost_paid)
                mList += info

            }while(cursor.moveToNext())
        }
        cursor.close()
        Log.d("num",num.toString())
        return num
    }
}