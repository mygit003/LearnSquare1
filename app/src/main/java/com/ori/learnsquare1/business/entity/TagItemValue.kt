package com.ori.learnsquare1.business.entity

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/18 11:11
 * 类说明:
 */
class TagItemValue {


    var title: String = ""
    var items: MutableList<ItemValue>? = null


    class ItemValue {
        var id:Int = 0
        var name:String = ""
        var link:String = ""
    }
}