package com.example.logisticsadministration

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.logisticsadministration.util.AppDatabaseHelper
import com.example.logisticsadministration.util.ToastUtil
import com.example.logisticsadministration.util.UserInfoUnit
import kotlin.system.exitProcess

class EnterActivity : AppCompatActivity() {
    // 运单输入界面
    // 数据库声明
    private var UserDatabase : String = "Info.db"
    lateinit var UserDB : AppDatabaseHelper
    // 控件声明
    lateinit var button_back : Button
    lateinit var button_save : Button
    lateinit var text_departure : TextView
    lateinit var edit_terminus : EditText
    lateinit var edit_consignor : EditText
    lateinit var edit_tel_consignor : EditText
    lateinit var edit_consignee : EditText
    lateinit var edit_tel_consignee : EditText
    lateinit var edit_name_goods : EditText
    lateinit var edit_num_goods : EditText
    lateinit var edit_cost_paid : EditText
    lateinit var edit_cost_unpaid : EditText
    // 全局变量定义
    lateinit var user : String
    lateinit var pd : String
    lateinit var city : String


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter)

        // 接受信息
        val intent = intent
        user = intent.getStringExtra("username")!!
        pd = intent.getStringExtra("password")!!
        city = intent.getStringExtra("city")!!
        // 数据库绑定
        UserDB = AppDatabaseHelper(this, UserDatabase, null, 1)
        // 控件绑定
        button_back = findViewById(R.id.button_back_3)
        button_save = findViewById(R.id.button_save_3)
        text_departure = findViewById(R.id.text_departure_3)
        edit_terminus = findViewById(R.id.edit_terminus_3)
        edit_consignor = findViewById(R.id.edit_consignor_3)
        edit_tel_consignor = findViewById(R.id.edit_tel_consignor_3)
        edit_consignee = findViewById(R.id.edit_consignee_3)
        edit_tel_consignee = findViewById(R.id.edit_tel_consignee_3)
        edit_name_goods = findViewById(R.id.edit_name_goods_3)
        edit_num_goods = findViewById(R.id.edit_num_goods_3)
        edit_cost_paid = findViewById(R.id.edit_cost_paid_3)
        edit_cost_unpaid = findViewById(R.id.edit_cost_unpaid_3)
        // 显示始发站
        text_departure.text = "发站:$city"
        // 实现界面跳转
        button_back.setOnClickListener(this::back)
        button_save.setOnClickListener(this::save)
    }

    private fun back(v: View){
        // 跳转
        val intent: Intent = Intent(this, UserActivity::class.java)
        //传递信息
        //传递信息
        intent.putExtra("username", user)
        intent.putExtra("password", pd)
        intent.putExtra("city", city)
        startActivity(intent)
        exitProcess(0)
    }

    private fun save(v: View){
        // 获取到站 货物名称 件数
        var id : Int = -1
        val terminus : String = edit_terminus.text.toString()
        val name_goods : String = edit_name_goods.text.toString()
        val num_goods : String = edit_num_goods.text.toString()

        // 读取数据库
        var db : SQLiteDatabase = UserDB.writableDatabase
        // 统计个数
        var cursor : Cursor = db.query("Logistic",null,null,null,null,null,null)
        var num : Int = 0
        if(cursor.moveToFirst()){
            do{
                num++
            }while(cursor.moveToNext())
        }
        cursor.close()
        Log.d("num",num.toString())
        id = num + 1

        // 判断并写入数据库
        if(terminus==""||name_goods==""||num_goods==""){
            ToastUtil.showMsg(this,"红框选项不能为空")
        }
        else {
            // 读取EditText
            var values = ContentValues()
            values.put("bill_id",id)
            values.put("bill_start",city)
            values.put("bill_end",terminus)
            values.put("bill_sender",edit_consignor.text.toString())
            values.put("bill_sender_tel",edit_tel_consignor.text.toString())
            values.put("bill_receiver",edit_consignee.text.toString())
            values.put("bill_receiver_tel",edit_tel_consignee.text.toString())
            values.put("bill_goods",name_goods)
            values.put("bill_num",num_goods)
            values.put("bill_cost_paid" ,edit_cost_paid.text.toString())
            values.put("bill_cost_unpaid",edit_cost_unpaid.text.toString())
            // 写入
            db.insert("Logistic",null,values)
            values.clear()
            ToastUtil.showMsg(this,"录入成功")

            // 跳转
            val intent: Intent = Intent(this, UserActivity::class.java)
            //传递信息
            //传递信息
            intent.putExtra("username", user)
            intent.putExtra("password", pd)
            intent.putExtra("city", city)
            startActivity(intent)
            exitProcess(0)
        }
    }
}