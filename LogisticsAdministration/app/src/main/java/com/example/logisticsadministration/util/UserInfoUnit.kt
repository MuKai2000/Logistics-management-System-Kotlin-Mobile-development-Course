package com.example.logisticsadministration.util

public class UserInfoUnit{

    private var id : Int? = -1;
    private var department : String? = "";
    private var name : String? = "";
    private var username : String? = "";
    private var password : String? = "";
    private var tel : String? = "";
    private var start : String? = ""

    // 修改值
    public fun editStart(new_start:String?){
        start = new_start;
    }

    public fun editId(new_id:Int?){
        id = new_id;
    }

    public fun editDepartment(new_department:String?){
        department = new_department;
    }

    public fun editName(new_name:String?){
        name = new_name;
    }
    public fun editUsername(new_name:String?){
        username = new_name;
    }

    public fun editPassword(new_pd:String?){
        password = new_pd;
    }

    public fun editTel(new_tel:String?){
        tel = new_tel;
    }

    // 获取值
    public fun getStart():String?{
        return start;
    }

    public fun getId():Int?{
        return id;
    }
    public  fun getDepartment():String?{
        return department;
    }
    public  fun getName():String?{
        return name;
    }
    public fun getUsername():String?{
        return username;
    }
    public  fun getPassword():String?{
        return password;
    }
    public  fun getTel():String?{
        return tel;
    }

}