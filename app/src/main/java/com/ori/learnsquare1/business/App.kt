package com.ori.learnsquare1.business

import android.app.Application

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

    }
}