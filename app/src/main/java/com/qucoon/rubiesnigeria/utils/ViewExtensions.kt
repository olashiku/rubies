package com.qucoon.rubiesnigeria.utils

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import java.io.Serializable

fun <T:Fragment> T.addBundle(vararg value: Pair<Any, Any>):Bundle{
    val bundle = Bundle()
    value.forEach{
        bundle.putSerializable(it.first.toString(),it.second as Serializable)
    }
    return bundle
}

fun <T:Any> Fragment.argument(key:String) = lazy { arguments?.get(key) as T}


fun ImageView.getImageFromUrl(imageUrl:String){
   // Picasso.get().load(imageUrl).into(this)
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun EditText.getString(): String {
    return this.text.toString()
}

fun Context.keypadVisibilityStatus():Boolean{
    val imm: InputMethodManager = this
        .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
  return imm.isAcceptingText
}

fun MotionLayout.performAutoTransition() {
    this.transitionToEnd()
    return this.setTransitionListener(object : MotionLayout.TransitionListener {
        var looped = true
        override fun onTransitionStarted(motionLayout: MotionLayout, i: Int, i1: Int) {}
        override fun onTransitionChange(
            motionLayout: MotionLayout, i: Int, i1: Int, v: Float) {}

        override fun onTransitionCompleted(motionLayout: MotionLayout, currentid: Int) {

            if(!looped)
                motionLayout.transitionToStart();

            else
                motionLayout.transitionToEnd();

            looped = !looped;
        }

        override fun onTransitionTrigger(motionLayout: MotionLayout, i: Int, b: Boolean, v: Float) {} })

}


