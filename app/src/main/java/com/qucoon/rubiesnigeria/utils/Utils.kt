package com.qucoon.rubiesnigeria.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat.startActivity



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
}



