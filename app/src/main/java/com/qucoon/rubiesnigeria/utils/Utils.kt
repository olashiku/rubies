package com.qucoon.rubiesnigeria.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject


object Utils {
    fun delayTimer(timer:Long = 3000,action:()->Unit){
        Handler(Looper.getMainLooper()).postDelayed({action()},timer)
    }

    fun calculateBalance(balance:Float):Float{
        return balance/100
    }


    fun Context.openWebBrowser(url:String){
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    fun getSocketAction(data: String): String {
        var obj = JSONObject(data)
        return obj["action"] as String
    }
}

fun <R> convertRequest(request: R): String {
    return Gson().toJson(request)
}

inline fun <reified T> String.getObject(): T {
    val type = object : TypeToken<T>() {}.type
    return Gson().fromJson(this, type)
}





