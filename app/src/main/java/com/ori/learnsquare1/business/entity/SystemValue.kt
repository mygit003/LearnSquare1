package com.ori.learnsquare.business.entity

import android.os.Parcel
import android.os.Parcelable

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
    var children: ArrayList<ItemValue>? = null

    class ItemValue() : Parcelable {

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

        constructor(parcel: Parcel) : this() {
            courseId = parcel.readInt()
            id = parcel.readInt()
            name = parcel.readString()
            order = parcel.readInt()
            parentChapterId = parcel.readInt()
            userControlSetTop = parcel.readByte() != 0.toByte()
            visible = parcel.readInt()
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(courseId)
            parcel.writeInt(id)
            parcel.writeString(name)
            parcel.writeInt(order)
            parcel.writeInt(parentChapterId)
            parcel.writeByte(if (userControlSetTop) 1 else 0)
            parcel.writeInt(visible)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<ItemValue> {
            override fun createFromParcel(parcel: Parcel): ItemValue {
                return ItemValue(parcel)
            }

            override fun newArray(size: Int): Array<ItemValue?> {
                return arrayOfNulls(size)
            }
        }
    }
}