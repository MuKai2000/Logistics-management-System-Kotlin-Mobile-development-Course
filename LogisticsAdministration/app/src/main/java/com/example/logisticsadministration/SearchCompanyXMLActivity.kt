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
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader
import kotlin.system.exitProcess

class SearchCompanyXMLActivity : AppCompatActivity() {
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
        setContentView(R.layout.activity_search_company_xml)

        // 接受信息
        val intent = intent
        user = intent.getStringExtra("username")!!
        pd = intent.getStringExtra("password")!!
        city = intent.getStringExtra("city")!!
        // 数据库绑定
        UserDB = AppDatabaseHelper(this, UserDatabase, null, 1)
        // 控件绑定
        button_back = findViewById(R.id.button_back_5)
        RCycleView = findViewById(R.id.recycle_view_5)

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
        var xmlString : String
        var xml : String = "http://60.12.122.142:6080/simulated-Waybills-db.xml"
        Log.d("Log_out",xml.toString())
        Thread(Runnable(){
            try{
                var client : OkHttpClient = OkHttpClient()
                var request : Request = Request.Builder()
                    // 指定访问的服务器地址
                    .url("http://60.12.122.142:6080/simulated-Waybills-db.xml")
                    .get()
                    .build()
                var response : Response = client.newCall(request).execute()
                xmlString = response.body!!.string()
                Log.d("Log_out","进入解析函数")
                Log.d("Log_out",xmlString.length.toString())
                // 调用此函数处理xml文件的数据 并生成mList
                num = parseXMLwithPull(xmlString)
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

    private fun parseXMLwithPull(xmlString:String): Int {
        var num_info :Int = 0
        try{ // 初始化  获取xmlpull解析器工厂
            val factory = XmlPullParserFactory.newInstance()    // 利用工厂生成一个解析器
            val parser = factory.newPullParser()    // 放入解析源
            parser.setInput(StringReader(xmlString))
            var currentType = parser.eventType
            // 信息
            var start : String = ""; var end : String = "" ; var address : String = ""; var goods : String = "" ;
            var num : String = "" ; var no : String = "" ; var name : String = "" ; var tel : String = "" ;
            var name_sender : String = ""; var tel_sender : String = ""; var cost_paid : String = ""; var cost : String = ""

            Log.d("Log_out", "解析开始")
            // 如果事件类型没有遇到文档结束这个事件，就会一直扫描下去
            while(currentType != XmlPullParser.END_DOCUMENT){
                // 思路：
                // 1、扫描到标签开始时，如果这个标签name是id、name、version
                //		则赋值给上面定义的变量
                // 2、扫描到结束标签时，这个标签的name时app时，则打印上述三个变量
                // 3、在单次while循环结束之前，currentType 要赋值下一个事件
                when(currentType){
                    XmlPullParser.START_TAG ->
                        when(parser.name){
                            "waybillNo" -> no = parser.nextText()
                            "consignor" -> name_sender = parser.nextText()
                            "consignorPhoneNumber" -> tel_sender = parser.nextText()
                            "consignee" -> name = parser.nextText()
                            "consigneePhoneNumber" -> tel = parser.nextText()
                            "transportationDepartureStation" -> start = parser.nextText()
                            "transportationArrivalStation" -> end = parser.nextText()
                            "goodsDistributionAddress" -> address = parser.nextText()
                            "goodsName" -> goods = parser.nextText()
                            "numberOfPackages" -> num = parser.nextText()
                            "freightPaidByTheReceivingParty" -> cost = parser.nextText()
                            "freightPaidByConsignor" -> cost_paid = parser.nextText()
                        }
                    XmlPullParser.END_TAG ->
                        if(parser.name == "waybillRecord"){
                            var info : LogisticsInfo = LogisticsInfo(no ,start,end,goods,num,
                                name_sender,tel_sender,name,tel,address,cost,cost_paid)
                            mList += info
                            num_info += 1
                        }
                }
                currentType = parser.next()
            }
        }catch(e:Exception){
            e.printStackTrace()
            Log.d("Log_out", "解析出错：${e} ")
        }
        Log.d("Log_out", "解析完成 ")
        Log.d("Log_out", mList.size.toString())
        finish = 1;

        return num_info
    }

}