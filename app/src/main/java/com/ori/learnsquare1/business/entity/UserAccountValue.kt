package com.ori.learnsquare.business.entity

import java.io.Serializable

/**
 * 创建人 zhengpf
 * 时间 2020/5/21
 * 类说明:
 */
class UserAccountValue : Serializable {

    /**
     * "data":{
     * "coinCount":10,
     * "level":1,
     * "rank":"21397",
     * "userId":64502,
     * "username":"j**oy"
     * },
     * "errorCode":0,
     * "errorMsg":""
     */

    var coinCount: Int = 0
    var level: Int = 0
    var rank: Int = 0
    var userId: String? = null
    var username: String? = null
}