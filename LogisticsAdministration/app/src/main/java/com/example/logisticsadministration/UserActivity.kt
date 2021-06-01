package com.example.logisticsadministration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.logisticsadministration.util.ToastUtil
import com.example.logisticsadministration.util.UserInfoUnit
import kotlin.system.exitProcess

class UserActivity : AppCompatActivity() {

    // 用户菜单界面
    // 控件定义
    lateinit var button_enter : Button
    lateinit var button_search_local : Button
    lateinit var button_search_company_xml : Button
    lateinit var button_search_company_json : Button
    lateinit var button_logout : Button
    lateinit var button_exit : Button
    lateinit var username : EditText
    lateinit var password : EditText
    // 全局变量定义
    lateinit var user : String
    lateinit var pd : String
    lateinit var city : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        // 接受信息
        val intent = intent
        user = intent.getStringExtra("username")!!
        pd = intent.getStringExtra("password")!!
        city = intent.getStringExtra("city")!!

        //控件绑定
        button_enter = findViewById(R.id.button_enter_2)
        button_search_local = findViewById(R.id.button_search_local_2)
        button_search_company_xml = findViewById(R.id.button_search_company_xml_2)
        button_search_company_json = findViewById(R.id.button_search_company_json_2)
        button_logout = findViewById(R.id.button_logout_2)
        button_exit = findViewById(R.id.button_exit_2)
        username = findViewById(R.id.edit_username_2)
        password = findViewById(R.id.edit_password_2)

        // EditView显示信息
        username.hint = "我是$user"
        password.hint = "我的密码是$pd"

        // 实现界面跳转
        button_enter.setOnClickListener(this::enter)
        button_search_local.setOnClickListener(this::search_local)
        button_search_company_xml.setOnClickListener(this::search_company_xml)
        button_search_company_json.setOnClickListener(this::search_company_json)
        button_logout.setOnClickListener(this::logout)
        button_exit.setOnClickListener { exitProcess(0) }
    }

    private fun enter(v: View){
        // 跳转
        val intent: Intent = Intent(this@UserActivity, EnterActivity::class.java)
        //传递信息
        intent.putExtra("username", user)
        intent.putExtra("password", pd)
        intent.putExtra("city", city)
        startActivity(intent)
        exitProcess(0)
    }
    private fun search_local(v: View){
        // 跳转
        val intent: Intent = Intent(this@UserActivity, SearchLocalActivity::class.java)
        //传递信息
        intent.putExtra("username", user)
        intent.putExtra("password", pd)
        intent.putExtra("city", city)
        startActivity(intent)
        exitProcess(0)
    }
    private fun search_company_xml(v: View){
        // 跳转
        val intent: Intent = Intent(this@UserActivity, SearchCompanyXMLActivity::class.java)
        //传递信息
        intent.putExtra("username", user)
        intent.putExtra("password", pd)
        intent.putExtra("city", city)
        startActivity(intent)
        exitProcess(0)
    }
    private fun search_company_json(v: View){
        // 跳转
        val intent: Intent = Intent(this@UserActivity, SearchCompanyJSONActivity::class.java)
        //传递信息
        intent.putExtra("username", user)
        intent.putExtra("password", pd)
        intent.putExtra("city", city)
        startActivity(intent)
        exitProcess(0)
    }
    private fun logout(v: View){
        // 跳转
        ToastUtil.showMsg(this,"用户已登出")
        val intent: Intent = Intent(this@UserActivity, MainActivity::class.java)
        //传递信息
        startActivity(intent)
        exitProcess(0)
    }
}