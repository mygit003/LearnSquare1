package com.ori.learnsquare1.business.entity


/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/19 9:59
 * 类说明:
 */
class PageValue<T> {

    var curPage: Int = 0
    var offset: Int = 0
    var over: Boolean = false
    var pageCount: Int = 0
    var size: Int = 0
    var total: Int = 0
    var datas: MutableList<T>? = null
}