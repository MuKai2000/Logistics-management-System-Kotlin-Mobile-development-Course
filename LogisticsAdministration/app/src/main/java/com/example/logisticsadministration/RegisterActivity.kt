package com.example.logisticsadministration

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CursorAdapter
import android.widget.EditText
import com.example.logisticsadministration.util.AppDatabaseHelper
import com.example.logisticsadministration.util.ToastUtil
import com.example.logisticsadministration.util.UserInfoUnit
import kotlin.system.exitProcess

class RegisterActivity : AppCompatActivity() {
    // 注册界面
    private var UserDatabase : String = "Info.db"
    lateinit var UserDB : AppDatabaseHelper
    // 控件声明
    lateinit var edit_department : EditText
    lateinit var edit_name : EditText
    lateinit var edit_username : EditText
    lateinit var edit_password : EditText
    lateinit var edit_tel : EditText
    lateinit var edit_city : EditText

    lateinit var button_back : Button
    lateinit var button_register : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        // 控件绑定
        edit_department = findViewById(R.id.edit_user_department_7)
        edit_name = findViewById(R.id.edit_user_name_7)
        edit_username = findViewById(R.id.edit_user_username_7)
        edit_password = findViewById(R.id.edit_user_password_7)
        edit_tel = findViewById(R.id.edit_user_tel_7)
        edit_city = findViewById(R.id.edit_user_city_7)
        button_back = findViewById(R.id.button_back_7)
        button_register = findViewById(R.id.button_register_7)

        // 数据库绑定
        UserDB  = AppDatabaseHelper(this, UserDatabase, null, 1)

        // 实现界面跳转
        button_back.setOnClickListener(this::back)
        button_register.setOnClickListener(this::register)
    }

    private fun back(v: View){
        // 跳转
        val intent: Intent = Intent(this@RegisterActivity, MainActivity::class.java)
        //传递信息
        startActivity(intent)
        exitProcess(0)
    }

    private fun register(v: View){

        // 获取用户名与密码
        var id : Int = -1
        val username : String = edit_username.text.toString()
        val passward : String = edit_password.text.toString()
        val city : String = edit_city.text.toString()

        // 读取数据库
        var db : SQLiteDatabase = UserDB.writableDatabase

        // 判断两项是否为空
        if(username==""||passward==""||city==""){
            ToastUtil.showMsg(this@RegisterActivity,"红框选项不能为空")
        }
        else{
            // 判断是否已经注册
            var cursor : Cursor = db.query("User",null,null,null,null,null,null)
            var hasRegisted : Boolean = false
            var num : Int = 0
            if(cursor.moveToFirst()){
                do{
                    num++
                    if(cursor.getString(cursor.getColumnIndex("user_login"))==username){
                        hasRegisted = true
                    }
                }while(cursor.moveToNext())
            }
            cursor.close()
            if(hasRegisted){
                ToastUtil.showMsg(this,"此账号已注册")
            }
            else{
                id = num + 1
                // 读取EditText
                var values = ContentValues()
                values.put("user_id",id)
                values.put("user_department",edit_department.text.toString())
                values.put("user_name",edit_name.text.toString())
                values.put("user_login",username)
                values.put("user_password",passward)
                values.put("user_tel",edit_tel.text.toString())
                values.put("user_city",city)

                // 插入至User表中
                db.insert("User",null,values)
                values.clear()
                ToastUtil.showMsg(this,"注册成功")

                // 跳转
                val intent: Intent = Intent(this@RegisterActivity, MainActivity::class.java)
                //传递信息
                startActivity(intent)
                exitProcess(0)
            }
        }
    }
}