package com.ori.learnsquare1.common.util

import android.content.Context
import android.content.pm.PackageManager


/**
 * 创建人: zhengpf
 * 修改时间: 2020/5/24 12:36
 * 类说明:
 */
object AppUtil {



    fun getAppVersionName(context: Context): String {
        val pm = context.packageManager
        //获取包信息
        try {
            val packageInfo = pm.getPackageInfo(context.packageName, 0)
            //返回版本名称
            return packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return ""
    }


    fun getAppVersionCode(context: Context): Int {
        val pm = context.packageManager
        //获取包信息
        try {
            val packageInfo = pm.getPackageInfo(context.packageName, 0)
            //返回版本名称
            return packageInfo.versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return 0
    }
}