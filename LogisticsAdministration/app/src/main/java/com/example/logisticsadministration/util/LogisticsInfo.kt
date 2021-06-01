package com.example.logisticsadministration.util

class LogisticsInfo {
    var no : String = ""
    var start : String = ""
    var end : String = ""
    var goods : String = ""
    var num : String = ""
    var sender : String = ""
    var sender_tel : String = ""
    var name : String = ""
    var tel : String = ""
    var address : String = ""
    var cost : String = ""

    constructor(new_no : String , new_start : String, new_end : String, new_goods : String, new_num : String,
                new_sender : String, new_sender_tel : String, new_name : String, new_tel : String,
                new_address : String, new_cost : String, new_cost_paid : String){
        no = "No: $new_no"
        start = new_start
        end = new_end
        goods = new_goods
        num = new_num + "件"
        sender = "寄件人: $new_sender"
        sender_tel = "联系电话: $new_sender_tel"
        name = "收件人: $new_name"
        tel = "联系电话: $new_tel"
        address = if(new_address != ""){
            "配送地址: $new_address"
        } else{
            ""
        }
        cost = "已付: " + new_cost_paid + "元\n到付: " + new_cost
    }
}