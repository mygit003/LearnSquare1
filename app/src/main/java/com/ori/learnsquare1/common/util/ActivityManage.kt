package com.ori.learnsquare1.common.util

import android.app.Activity
import android.os.Process
import android.util.Log
import java.lang.Exception
import java.util.*

/**
 * 创建人 zhengpf
 * 时间 2020/5/6
 * 类说明:
 */
class ActivityManage private constructor() {

    private val TAG: String = this.javaClass.simpleName

    private var activityStack: Stack<Activity>? = null

    companion object{
        @Volatile
        private var instance: ActivityManage? = null

        fun getInstance(): ActivityManage =
            instance ?: synchronized(this) {
                instance ?: ActivityManage().also { instance = it }
            }
    }




    fun pushActivity(baseActivity: Activity) {
        if (null == activityStack) {
            activityStack = Stack<Activity>()
        }
        activityStack!!.push(baseActivity)
        Log.d(TAG, "size = " + activityStack!!.size)
    }


    fun popActivity() {
        if (null != activityStack && activityStack!!.isNotEmpty()) {
            var activity: Activity? = activityStack!!.pop()
            if (null != activity) {
                activity.finish()
                activity = null
            }
        }
    }


    fun finishAllActivity() {
        if (null != activityStack) {
            while (activityStack!!.isNotEmpty()) {
                popActivity()
            }
        }
    }


    fun appExit() {
        try {
            popActivity()
            Process.killProcess(Process.myPid())
            System.exit(1)
        }catch (e: Exception) {
            Log.e(TAG, "errMsg:" + e.message)
        }
    }


}