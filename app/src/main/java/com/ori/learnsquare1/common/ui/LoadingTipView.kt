package com.ori.learnsquare1.common.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.ori.learnsquare1.R
import com.ori.learnsquare1.common.listener.ReloadListener
import com.wang.avi.AVLoadingIndicatorView

/**
 * 创建人 zhengpf
 * 时间 2020/5/8
 * 类说明:
 */
class LoadingTipView : RelativeLayout {

    private var ll_empty: LinearLayout? = null
    private var ll_error: LinearLayout? = null
    private var avlivView: AVLoadingIndicatorView? = null
    private var reloadListener: ReloadListener? = null



    constructor(context: Context) : super(context) {
        initView(context)
    }


    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(context)
    }


    fun initView(context: Context) {
        var view = View.inflate(context, R.layout.layout_loading, this)
        ll_empty = view.findViewById(R.id.ll_empty)
        ll_error = view.findViewById(R.id.ll_error)
        avlivView = view.findViewById(R.id.avliv_view)
        ll_error?.setOnClickListener {
            if (null != reloadListener) {
                reloadListener?.reload()
            }
        }
    }

    fun setReloadListener(listener: ReloadListener) {
        this.reloadListener = listener;
    }



    /**
     * 显示空白页
     */
    fun showEmpty() {
        visibility = View.VISIBLE
        ll_empty?.visibility = View.VISIBLE
        avlivView?.visibility = View.GONE
        avlivView?.hide()
        ll_error?.visibility = View.GONE
    }

    /**
     * 显示网络错误
     */
    fun showInternetError() {
        visibility = View.VISIBLE
        ll_error?.visibility = View.VISIBLE
        ll_empty?.visibility = View.GONE
        avlivView?.visibility = View.GONE
        avlivView?.hide()
    }

    /**
     * 加载
     */
    fun loading() {
        visibility = View.VISIBLE
        avlivView?.visibility = View.VISIBLE
        avlivView?.show()
        ll_error?.visibility = View.GONE
        ll_empty?.visibility = View.GONE

    }

    /**
     * 隐藏loadingTip
     */
    fun dismiss() {
        avlivView?.hide()
        visibility = View.GONE
    }


}