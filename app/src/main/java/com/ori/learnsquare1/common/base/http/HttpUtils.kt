package com.ori.learnsquare1.common.base.http

import android.util.Log
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.ori.learnsquare1.business.App
import com.ori.learnsquare1.business.util.ApiService
import com.ori.learnsquare1.common.util.Constant
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * 创建人: zhengpf
 * 修改时间: 2020/6/27 11:54
 * 类说明:
 */
object HttpUtils {


    private const val DEFAULT_TIMEOUT = 30L

    private var retrofit: Retrofit

    private var cookieJar: PersistentCookieJar

    private var apiService: ApiService

    init {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        //cache
        val cacheFile = File(App.getApp().getCacheDir(), "cache")
        //100Mb
        val cache = Cache(cacheFile, 1024 * 1024 * 100)

        //cookie
        cookieJar = PersistentCookieJar(SetCookieCache(),
            SharedPrefsCookiePersistor(App.getApp()))

        val client = OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .addInterceptor(httpLoggingInterceptor)
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .cookieJar(cookieJar)
            .cache(cache)
            .build()


        retrofit = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }


    fun getRetrofitInstance(): Retrofit {
        Log.d("HttpUtils", retrofit.toString())
        return retrofit
    }


    fun getApiService() = apiService


    fun clearCookie() {
        if (null != cookieJar) {
            cookieJar.clear()
        }
    }
}