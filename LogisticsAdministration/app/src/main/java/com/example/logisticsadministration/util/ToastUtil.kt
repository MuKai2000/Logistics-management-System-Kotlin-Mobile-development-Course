package com.example.logisticsadministration.util

import android.content.Context
import android.widget.Toast
import com.example.logisticsadministration.MainActivity

object ToastUtil {
    private var mToast: Toast? = null
    fun showMsg(context: Context, msg: String?) {
        if (ToastUtil.mToast == null) {
            ToastUtil.mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
        } else {
            ToastUtil.mToast!!.setText(msg)
        }
        ToastUtil.mToast?.show()
    }
}