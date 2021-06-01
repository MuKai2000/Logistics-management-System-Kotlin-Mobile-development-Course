package com.example.logisticsadministration.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.logisticsadministration.R


class LogisticsInfoRvAdapter(mList: List<LogisticsInfo>) : RecyclerView.Adapter<LogisticsInfoRvAdapter.ViewHolder>() {
    private  val mLogisticsList: List<LogisticsInfo> = mList
    class ViewHolder : RecyclerView.ViewHolder{
        var no : TextView? = null
        var start : TextView? = null
        var end : TextView? = null
        var goods : TextView? = null
        var num : TextView? = null
        var sender : TextView? = null
        var sender_tel : TextView? = null
        var name : TextView? = null
        var tel : TextView? = null
        var address : TextView? = null
        var cost : TextView? = null
        constructor(view : View):super(view){
            no = view.findViewById(R.id.item_no)
            start = view.findViewById(R.id.item_start)
            end = view.findViewById(R.id.item_end)
            goods = view.findViewById(R.id.item_goods)
            num = view.findViewById(R.id.item_num)
            sender = view.findViewById(R.id.item_sender)
            sender_tel = view.findViewById(R.id.item_sender_tel)
            name = view.findViewById(R.id.item_name)
            tel = view.findViewById(R.id.item_tel)
            address = view.findViewById(R.id.item_address)
            cost = view.findViewById(R.id.item_cost)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.getContext()).inflate(R.layout.logistics_info_item_new,parent,false)
        val holder  =  ViewHolder(view)
        return holder
    }

    override fun getItemCount(): Int {
        return mLogisticsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var Info : LogisticsInfo = mLogisticsList.get(position)
        holder.no?.setText(Info.no)
        holder.start?.setText(Info.start)
        holder.end?.setText(Info.end)
        holder.goods?.setText(Info.goods)
        holder.num?.setText(Info.num)
        holder.sender?.setText(Info.sender)
        holder.sender_tel?.setText(Info.sender_tel)
        holder.name?.setText(Info.name)
        holder.tel?.setText(Info.tel)
        holder.address?.setText(Info.address)
        holder.cost?.setText(Info.cost)
    }
}