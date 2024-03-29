package com.ori.learnsquare1.business

import android.app.Application
import com.ori.learnsquare1.common.util.AppCrashHandler
import com.ori.learnsquare1.common.util.Constant
import com.ori.learnsquare1.common.util.PrefUtils
import com.ori.learnsquare1.common.util.setNightMode

/**
 * 创建人: zhengpf
 * 修改时间: 2020/6/26 15:25
 * 类说明:
 */
class App : Application() {

    companion object {
        private lateinit var app: App

        fun getApp() : App {
            return app
        }
    }


    override fun onCreate() {
        super.onCreate()
        app = this
        //setDayNightMode()
        if (!Constant.DEBUG) {
            AppCrashHandler.getInstance().init(this)
        }
    }


    private fun setDayNightMode() {
        var mode = PrefUtils.getInt(Constant.SpKey.SP_MODE_CONFIG, 0)
        setNightMode((mode == 1))
    }
}