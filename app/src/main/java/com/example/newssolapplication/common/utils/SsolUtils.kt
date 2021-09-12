package com.example.newssolapplication.common.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.util.Log
import android.view.WindowManager

object SsolUtils {

    fun showCustomMsgWindow(activity: Activity?, title: String?, msg: String?, btnMsg: String?, cancel: Boolean, listener: DialogInterface.OnClickListener) {
        try {
            val alertDialog = AlertDialog.Builder(activity)
            alertDialog.setTitle(title)
                .setMessage(msg)
                .setPositiveButton(btnMsg, listener)
                .setCancelable(cancel)
                .create().show()
        } catch (e: WindowManager.BadTokenException) {
            // TODO: handle exception
            Log.e("error", e.message.toString())
        } catch (e: IllegalArgumentException) {
            // TODO: handle exception
            Log.e("error", e.message.toString())
        }
    }
    fun showCustomWindow(activity: Activity?, title: String?, msg: String?, btnMsg: String?, cancel: Boolean, listener: DialogInterface.OnClickListener,cancelListener: DialogInterface.OnClickListener) {
        try {
            val alertDialog = AlertDialog.Builder(activity)
            alertDialog.setTitle(title)
                .setMessage(msg)
                .setPositiveButton(btnMsg, listener)
                .setNegativeButton("취소", cancelListener)
                .setCancelable(cancel)
                .create().show()
        } catch (e: WindowManager.BadTokenException) {
            // TODO: handle exception
            Log.e("error", e.message.toString())
        } catch (e: IllegalArgumentException) {
            // TODO: handle exception
            Log.e("error", e.message.toString())
        }
    }
}