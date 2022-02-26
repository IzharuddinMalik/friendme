package com.friendme.utils

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.widget.Toast
import com.friendme.R
import kotlinx.android.synthetic.main.custom_toast.view.*

class CustomToast {

    fun customToast(context : Context, message : String) {
        val inflater = (context as Activity).layoutInflater
        val customLayout = inflater.inflate(R.layout.custom_toast, null)
        customLayout.tvToastMessage.setText(message)
        val toast = Toast(context)
        toast.duration = Toast.LENGTH_SHORT
        toast.setGravity(Gravity.BOTTOM, 0, 0)
        toast.view = customLayout
        toast.show()
    }

}