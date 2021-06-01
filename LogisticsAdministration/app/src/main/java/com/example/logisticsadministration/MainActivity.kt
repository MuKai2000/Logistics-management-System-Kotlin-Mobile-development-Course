package com.example.logisticsadministration

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.logisticsadministration.util.AppDatabaseHelper
import com.example.logisticsadministration.util.ToastUtil
import com.example.logisticsadministration.util.UserInfoUnit
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {
    // 主界面

    // 数据库声明
    private var UserDatabase : String = "Info.db"
    lateinit var UserDB : AppDatabaseHelper
    // 控件声明
    lateinit var button_login : Button
    lateinit var button_exit : Button
    lateinit var button_register : Button
    lateinit var username : EditText
    lateinit var password : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 控件绑定
        button_login = findViewById(R.id.button_login_1)
        // button_login.background.mutate().alpha = 170
        button_exit = findViewById(R.id.button_exit_1)
        button_register = findViewById(R.id.button_register_1)
        username = findViewById(R.id.edit_username_1)
        password = findViewById(R.id.edit_password_1)
        // 数据库绑定
        UserDB = AppDatabaseHelper(this, UserDatabase, null, 1)


        // 实现界面跳转
        button_login.setOnClickListener(this::login)
        button_register.setOnClickListener(this::register)
        button_exit.setOnClickListener { exitProcess(0) }

    }

    private fun login(v: View){
        // 获取用户名与密码信息
        val username : String = username.text.toString()
        val passward : String = password.text.toString()
        var city : String = ""
        // 读取数据库
        var db : SQLiteDatabase = UserDB.writableDatabase
        //Toast 内容
        val ok = "登录成功"
        val error = "账号或密码错误"
        val empty = "账号或密码不能为空"
        val none = "此账号未注册"
        // 首先判断用户名密码是否为空
        if(username == ""|| passward == ""){
            // 空
            ToastUtil.showMsg(this@MainActivity,empty)
        }
        else{
            // 检查注册信息
            var cursor : Cursor = db.query("User",null,null,null,null,null,null)
            var hasRegisted : Int = 0
            if(cursor.moveToFirst()){
                do{
                    // 逐条读取User表的数据进行比对
                    var login : String = cursor.getString(cursor.getColumnIndex("user_login"))
                    var pd : String = cursor.getString(cursor.getColumnIndex("user_password"))
                    // Log.d("MainActivity", "User password is $pd")
                    if(login==username){
                        hasRegisted = 1
                        if(pd==passward){
                            hasRegisted=2
                            city = cursor.getString(cursor.getColumnIndex("user_city"))
                            // Log.d("MainActivity", "User password is right $pd")
                            break
                        }
                    }
                }while(cursor.moveToNext())
            }
            cursor.close()
            // 判断
            if(hasRegisted==0){
                // 未注册
                ToastUtil.showMsg(this@MainActivity,none)
            }
            else if(hasRegisted==1){
                // 不匹配
                ToastUtil.showMsg(this@MainActivity,error)
            }
            else{
                // 匹配
                ToastUtil.showMsg(this@MainActivity,ok)
                // 跳转
                val intent: Intent = Intent(this@MainActivity, UserActivity::class.java)
                //传递信息
                intent.putExtra("username", username)
                intent.putExtra("password", passward)
                intent.putExtra("city", city)
                startActivity(intent)
                exitProcess(0)
            }
        }
    }

    private fun register(v: View){
        // 跳转
        val intent: Intent = Intent(this@MainActivity, RegisterActivity::class.java)
        //传递信息
        startActivity(intent)
        exitProcess(0)
    }
}