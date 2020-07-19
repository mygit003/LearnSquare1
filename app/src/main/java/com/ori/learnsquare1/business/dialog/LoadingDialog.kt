package com.ori.learnsquare1.business.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.ori.learnsquare1.R
import kotlinx.android.synthetic.main.dlg_loading.*

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/19 15:46
 * 类说明:
 */
class LoadingDialog(context: Context) : Dialog(context, R.style.CustomDialog) {

    private var tip:String = ""

    constructor(context: Context, tip: String) : this(context) {
        this.tip = tip
    }


    open fun setTip(tip: String) {
        this.tip = tip
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dlg_loading)
        tv_tip.text = tip
        setCancelable(false)
        setCanceledOnTouchOutside(false)
    }
}