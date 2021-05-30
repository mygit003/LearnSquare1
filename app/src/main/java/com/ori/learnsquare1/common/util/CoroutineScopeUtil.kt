package com.ori.learnsquare1.common.util

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.JsonParseException
import com.ori.learnsquare1.business.App
import com.ori.learnsquare1.business.db.DBHelper
import com.ori.learnsquare1.common.base.http.ApiException
import com.ori.learnsquare1.common.ext.showToast
import kotlinx.coroutines.*
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.net.UnknownServiceException
import java.text.ParseException

/**
 * 创建人: zhengpf
 * 修改时间: 2021/5/29 16:14
 * 类说明:
 */

typealias Block<T> = suspend () -> T
typealias Error = suspend (e: Exception) -> Unit
typealias Cancel = suspend (e: Exception) -> Unit


class CoroutineScopeUtil private constructor() {


    companion object {
        @Volatile
        private var instance: CoroutineScopeUtil? = null

        fun getInstance() : CoroutineScopeUtil =
            instance ?: synchronized(this) {
                instance ?: CoroutineScopeUtil().also { instance = it }
            }
    }

    private lateinit var coroutineScope: CoroutineScope

    fun init(scope: CoroutineScope) {
        coroutineScope = scope
    }

    fun cancel() {
        if (::coroutineScope.isInitialized) {
            if (null != coroutineScope && coroutineScope.isActive) {
                coroutineScope.cancel()
            }
        }
    }

    fun launch(block: Block<Unit>, error: Error? = null, cancel: Cancel? = null): Job {
        return coroutineScope.launch {
            try {
                block.invoke()
            } catch (e: Exception) {
                when (e) {
                    is CancellationException -> {
                        cancel?.invoke(e)
                    }
                    else -> {
                        onError(e)
                        error?.invoke(e)
                    }
                }
            }
        }
    }


    private fun onError(e: Exception) {
        when (e) {
            //通过判断Retrofit HttpException类型判断返回的请求码，做相应的业务处理
            is HttpException -> {
                when (e.code()) {
                    -1001 -> {
                        // 登录失效

                    }
                    else -> {
                        // 其他错误
                        e.let {
                            //App.getApp().showToast("code: ${it.code()}, message: ${it.message()}")
                        }
                    }
                }
            }
            is ApiException -> {
                when (e.code) {
                    -1001 -> {
                        // 登录失效

                    }
                    else -> {
                        // 其他错误
                        e.let {
                            //App.getApp().showToast("code: ${it.code}, message: ${it.message}")
                        }
                    }
                }
            }
            //链接错误
            is ConnectException,
            is UnknownHostException,
            is UnknownServiceException -> {
                e.message?.let {
                    App.getApp().showToast("message: ${it}")
                }
            }
            //请求超时
            is SocketTimeoutException,
            is InterruptedException -> {
                e.message?.let {
                    App.getApp().showToast("message: ${it}")
                }
            }
            //数据解析错误
            is JsonParseException,
            is JSONException,
            is ParseException -> {
                e.message?.let {
                    App.getApp().showToast("message: ${it}")
                }
            }
            else -> {
                // 其他错误
                e.message?.let { App.getApp().showToast("message: ${it}") }
            }
        }
    }


}