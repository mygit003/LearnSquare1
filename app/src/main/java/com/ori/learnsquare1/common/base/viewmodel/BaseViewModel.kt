package com.ori.learnsquare1.common.base.viewmodel

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonParseException
import com.ori.learnsquare1.App
import com.ori.learnsquare1.ext.showToast
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
 * 修改时间: 2020/6/26 14:42
 * 类说明:
 */
typealias Block<T> = suspend () -> T
typealias Error = suspend (e: Exception) -> Unit
typealias Cancel = suspend (e: Exception) -> Unit


class BaseViewModel : ViewModel() {


    private val TAG = this.javaClass.simpleName;


    protected fun launch(block: Block<Unit>, error: Error? = null, cancel: Cancel? = null): Job {
        return viewModelScope.launch {
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


    protected fun <T> async(block: Block<T>): Deferred<T> {
        return viewModelScope.async { block.invoke() }
    }


    protected fun cancelJob(job: Job) {
        if (null != job && job.isActive && !job.isCancelled && !job.isCompleted) {
            job.cancel()
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
                        e?.let {
                            App.getApp().showToast("code: ${it.code()}, message: ${it.message()}")
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