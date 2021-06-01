package com.example.logisticsadministration

import android.app.DownloadManager
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.textclassifier.ConversationActions
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.logisticsadministration.util.AppDatabaseHelper
import com.example.logisticsadministration.util.LogisticsInfo
import com.example.logisticsadministration.util.LogisticsInfoRvAdapter
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader
import kotlin.system.exitProcess

class SearchCompanyJSONActivity : AppCompatActivity() {
    // 公司搜索 xml
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
    private var finish : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_company_json)

        // 接受信息
        val intent = intent
        user = intent.getStringExtra("username")!!
        pd = intent.getStringExtra("password")!!
        city = intent.getStringExtra("city")!!
        // 数据库绑定
        UserDB = AppDatabaseHelper(this, UserDatabase, null, 1)
        // 控件绑定
        button_back = findViewById(R.id.button_back_6)
        RCycleView = findViewById(R.id.recycle_view_6)

        // 数据读入
        initInfoData()

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
        var num:Int = 0
        var jsonString : String
        var json : String = "http://60.12.122.142:6080/simulated-Waybills-db.xml"
        Log.d("Log_out",json.toString())
        Thread(Runnable(){
            try{
                var client : OkHttpClient = OkHttpClient()
                var request : Request = Request.Builder()
                    // 指定访问的服务器地址
                    .url("http://60.12.122.142:6080/simulated-Waybills-db.json")
                    .get()
                    .build()
                var response : Response = client.newCall(request).execute()
                // 保存json文件中的信息为字符串
                jsonString = response.body!!.string()
                Log.d("Log_out","进入解析函数")
                // 处理Json字符串
                jsonString = jsonString.substring(24,jsonString.length-1)
                if(jsonString != null && jsonString.startsWith("\ufeff"))
                {
                    jsonString =  jsonString.substring(1);
                }
                Log.d("Log_out",jsonString)
                // 这个函数用于解析json格式的字符串信息 并保存至mList
                num = parseJSONWithJSONObject(jsonString)
            } catch (e:Exception) {
                Log.d("Log_out", "访问服务器Exception")
                e.printStackTrace()
            }
        }).start()


        while(true){
            Log.d("Log_out", "建立页面")
            Log.d("Log_out", mList.size.toString())
            var layout : LinearLayoutManager = LinearLayoutManager(this)
            RCycleView.layoutManager = layout
            var adapter : LogisticsInfoRvAdapter = LogisticsInfoRvAdapter(mList)
            RCycleView.adapter = adapter
            if(finish==1)
                break;
        }
        return num
    }

    private fun parseJSONWithJSONObject(jsonString:String): Int {
        var num_info :Int = 0
        try{ // 初始化
            // 信息
            var start : String = ""; var end : String = "" ; var address : String = ""; var goods : String = "" ;
            var num : String = "" ; var no : String = "" ; var name : String = "" ; var tel : String = "" ;
            var name_sender : String = ""; var tel_sender : String = ""; var cost_paid : String = ""; var cost : String = ""

            Log.d("Log_out", "解析开始")

            var jsonArray : JSONArray = JSONArray(jsonString)
            var i = 0;
            Log.d("Log_out_ja_len", jsonArray.length().toString())

            while(i<jsonArray.length()){
                var jsonobject : JSONObject = jsonArray.getJSONObject(i)

                no = jsonobject.getString("waybillNo")
                name_sender = jsonobject.getString("consignor")
                tel_sender = jsonobject.getString("consignorPhoneNumber")
                name = jsonobject.getString("consignee")
                tel = jsonobject.getString("consigneePhoneNumber")
                start = jsonobject.getString("transportationDepartureStation")
                end = jsonobject.getString("transportationArrivalStation")
                address = jsonobject.getString("goodsDistributionAddress")
                goods = jsonobject.getString("goodsName")
                num = jsonobject.getString("numberOfPackages")
                cost = jsonobject.getString("freightPaidByTheReceivingParty")
                cost_paid = jsonobject.getString("freightPaidByConsignor")

                var info : LogisticsInfo = LogisticsInfo(no ,start,end,goods,num,
                    name_sender,tel_sender,name,tel,address,cost,cost_paid)

                mList += info
                num_info += 1
                i++
            }

        }catch(e:Exception){
            e.printStackTrace()
            Log.d("Log_out", "解析出错：${e} ")
        }
        Log.d("Log_out", "解析完成 ")
        Log.d("Log_out", mList.size.toString())
        // 修改标志位 表示json文件已处理完 生成完整的mList
        finish = 1;

        return num_info
    }

}