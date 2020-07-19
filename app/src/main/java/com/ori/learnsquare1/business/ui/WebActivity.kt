package com.ori.learnsquare1.business.ui

import android.text.Html
import android.util.Log
import android.view.KeyEvent
import android.webkit.*
import com.ori.learnsquare.business.entity.ArticleValue
import com.ori.learnsquare.business.entity.UserValue
import com.ori.learnsquare1.R
import com.ori.learnsquare1.common.base.activity.BaseActivity
import com.ori.learnsquare1.common.util.Constant
import com.ori.learnsquare1.common.util.JsonUtil
import kotlinx.android.synthetic.main.act_web.*

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/18 11:37
 * 类说明:
 */
class WebActivity : BaseActivity() {

    private var webUrl: String = ""
    private var webTitle: String = ""
    private var datasBean: ArticleValue.DatasBean? = null
    private var userValue: UserValue? = null

    override fun setRootView(): Int {
        return R.layout.act_web
    }

    override fun init() {
        var bundle = intent.extras
        bundle?.let {
            webTitle = it.getString(Constant.WebParam.PARAM_TITLE, "")
            webUrl = it.getString(Constant.WebParam.PARAM_URL, "")
            Log.w(TAG, "url:$webUrl")
            var itemStr = it.getString(Constant.WebParam.PARAM_ITEM, "")
            itemStr?.let {
                datasBean = JsonUtil.jsonToJavaBean(it, ArticleValue.DatasBean::class.java)
            }
        }

        iv_back.setOnClickListener {
            finish()
        }

        tv_title.text = Html.fromHtml(webTitle)
        initWebView()
    }


    private fun initWebView() {
        val setting: WebSettings = wv_view.settings
        setting.javaScriptEnabled = true
        setting.cacheMode = WebSettings.LOAD_NO_CACHE

        //自适应屏幕
        wv_view.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        wv_view.settings.loadWithOverviewMode = true


        //如果不设置WebViewClient，请求会跳转系统浏览器
        wv_view.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址）
                //均交给webView自己处理，这也是此方法的默认处理
                return false
            }
        }
        ltv_loading.loading()
        wv_view.loadUrl(webUrl)
        wv_view.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (newProgress == 100) ltv_loading.dismiss()
            }
        }
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && wv_view.canGoBack()) {
            //返回上个页面
            wv_view.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}