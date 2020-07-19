package com.ori.learnsquare.business.entity

import java.io.Serializable

/**
 * 创建人 zhengpf
 * 时间 2020/5/21
 * 类说明:
 */
class UserValue : Serializable {

    /**
     * admin : false
     * chapterTops : []
     * collectIds : [10479,12202,12148,10916,12175]
     * email :
     * icon :
     * id : 36628
     * nickname : 18616720137
     * password :
     * publicName : 18616720137
     * token :
     * type : 0
     * username : 18616720137
     * {"data":{"admin":false,"chapterTops":[],"coinCount":0,"collectIds":[],"email":"","icon":"","id":64502,"nickname":"jkboy","password":"","publicName":"jkboy","token":"","type":0,"username":"jkboy"},"errorCode":0,"errorMsg":""}
     */


    var admin: Boolean = false
    var email: String? = null
    var icon: String? = null
    var id: String? = null
    var nickName: String? = null
    var password: String? = null
    var publicName: String? = null
    var token: String? = null
    var type: Int = 0
    var username: String? = null

}