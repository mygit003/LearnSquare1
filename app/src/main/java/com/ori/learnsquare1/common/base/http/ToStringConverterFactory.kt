package com.ori.learnsquare1.common.base.http

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type


/**
 * 创建人: zhengpf
 * 修改时间: 2021/9/19 11:51
 * 类说明: 处理http请求text/plain格式通信
 * request: text/plain;charset=UTF-8
 * response: text/plain
 * 还有另外一种方式
 * build.gradle添加依赖
 * implementation 'com.squareup.retrofit2:converter-scalars:2.9.0'
 * 添加
 * .addConverterFactory(ScalarsConverterFactory.create())
 * 在第一个转换器位置
 */
class ToStringConverterFactory : Converter.Factory() {

    private val MEDIA_TYPE: MediaType = "text/plain;charset=UTF-8".toMediaType()


    override fun requestBodyConverter(
        type: Type, parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>, retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        //return super.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit)
        if (String::class.java == type) {
            return Converter<String, RequestBody> { value -> value.toRequestBody(MEDIA_TYPE) }
        }
        return null
    }


    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        //return super.responseBodyConverter(type, annotations, retrofit)
        if (String::class.java == type) {
            return Converter<ResponseBody, String> { value -> value.string() }
        }
        return null
    }
}