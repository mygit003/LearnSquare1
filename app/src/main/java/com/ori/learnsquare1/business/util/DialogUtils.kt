package com.ori.learnsquare1.business.util

import android.app.Dialog
import android.content.Context
import android.app.AlertDialog
import android.view.LayoutInflater
import android.widget.TextView
import com.ori.learnsquare1.R


/**
 *
 */
class DialogUtils {

    companion object {
        private var dialog: Dialog? = null

        /**
         * 二次确认对话框
         */
        fun confirm(context: Context, tips: String, listener: OnConfrimClickListener?) {
            var dialog: Dialog? = null
            val builder = AlertDialog.Builder(context)
            var view = LayoutInflater.from(context).inflate(R.layout.dialog_confirm, null)
            var tvContent = view.findViewById<TextView>(R.id.tvContent)
            tvContent.text = tips
            builder.setView(view)
                .setPositiveButton("确定") { _, _ ->
                    listener?.onConfirmClick()
                    dialog?.dismiss()
                }.setNegativeButton("取消") { _, _ -> dialog?.dismiss() }
            dialog = builder.create()
            dialog.show()
        }

        /**
         * 提示对话框
         */
        fun tips(context: Context, tips: String, listener: OnConfrimClickListener?) {
            var dialog: Dialog? = null
            val builder = AlertDialog.Builder(context)
            var view = LayoutInflater.from(context).inflate(R.layout.dialog_confirm, null)
            var tvContent = view.findViewById<TextView>(R.id.tvContent)
            tvContent.text = tips
            builder.setView(view)
                .setPositiveButton("确定") { _, _ ->
                    listener?.onConfirmClick()
                    dialog?.dismiss()
                }
            dialog = builder.create()
            dialog.show()
        }
    }

    interface OnConfrimClickListener {
        fun onConfirmClick()
    }
}