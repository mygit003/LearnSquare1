package com.ori.learnsquare.business.entity

/**
 * 创建人: zhengpf
 * 修改时间: 2020/5/16 17:39
 * 类说明:
 */
class SystemValue {


    /**
     * children : [{"children":[],"courseId":13,"id":60,"name":"Android Studio相关","order":1000,"parentChapterId":150,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":169,"name":"gradle","order":1001,"parentChapterId":150,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":269,"name":"官方发布","order":1002,"parentChapterId":150,"userControlSetTop":false,"visible":1}]
     * courseId : 13
     * id : 150
     * name : 开发环境
     * order : 1
     * parentChapterId : 0
     * userControlSetTop : false
     * visible : 1
     */

    var courseId: Int = 0
    var id = 0
    var name: String? = null
    var order = 0
    var parentChapterId = 0
    var userControlSetTop = false
    var visible = 0
    var children: List<ItemValue>? = null

    inner class ItemValue {

        /**
         * children : []
         * courseId : 13
         * id : 60
         * name : Android Studio相关
         * order : 1000
         * parentChapterId : 150
         * userControlSetTop : false
         * visible : 1
         */

        var courseId: Int = 0
        var id = 0
        var name: String? = null
        var order = 0
        var parentChapterId = 0
        var userControlSetTop = false
        var visible = 0
        var children: List<*>? = null
    }
}