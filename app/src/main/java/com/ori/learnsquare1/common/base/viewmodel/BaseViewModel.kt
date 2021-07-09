package com.ori.learnsquare1.common.base.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.google.gson.JsonParseException
import com.ori.learnsquare1.business.App
import com.ori.learnsquare1.business.ui.user.UserRepository
import com.ori.learnsquare1.common.base.http.ApiException
import com.ori.learnsquare1.common.base.http.BaseResponse
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
 * 修改时间: 2020/6/26 14:42
 * 类说明:
 */
typealias Block<T> = suspend () -> T
typealias Error = suspend (e: Exception) -> Unit
typealias Cancel = suspend (e: Exception) -> Unit


abstract class BaseViewModel : ViewModel(), ViewModelLifecycle {

    private val TAG = this.javaClass.simpleName;

    private lateinit var lifcycleOwner: LifecycleOwner

    val loadingStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()

    protected val userRepository by lazy { UserRepository() }


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

    override fun onAny(owner: LifecycleOwner, event: Lifecycle.Event) {
        this.lifcycleOwner = owner
    }

    override fun onCreate() {
        Log.d(TAG, "onCreate")
    }

    override fun onStart() {
        Log.d(TAG, "onStart")

    }

    override fun onResume() {
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
    }


}