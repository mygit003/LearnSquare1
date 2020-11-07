package com.ori.learnsquare1.business.entity

/**
 * 创建人 zhengpf
 * 时间 2020/5/21
 * 类说明:
 */
class BusEvent() {

    constructor(msgId: Int, msg: String): this(){
        this.msgId = msgId
        this.msg = msg
    }

    var msgId: Int = 0
    var msg: String? = null
}