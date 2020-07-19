package com.ori.learnsquare1.common.base.http

/**
 * 创建人 zhengpf
 * 时间 2020/5/6
 * 类说明:
 */
class BaseResponse<T> {

    var data: T? = null
    var errorCode: Int = 0
    var errorMsg: String = ""

    fun getResult() : T? {
        if (errorCode == 0) {
            return data
        }else {
            throw ApiException(errorCode, errorMsg)
        }
    }
}