package com.ori.learnsquare1.common.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.text.TextUtils
import androidx.core.content.FileProvider
import java.io.File


/**
 * 创建人: zhengpf
 * 修改时间: 2020/5/24 12:36
 * 类说明:
 */
object AppUtil {

    const val PACKAGE_WECHAT = "com.tencent.mm"
    const val PACKAGE_MOBILE_QQ = "com.tencent.mobileqq"
    const val PACKAGE_QZONE = "com.qzone"
    const val PACKAGE_SINA = "com.sina.weibo"

    // 微信好友
    const val ACTIVITY_SHARE_WECHAT_FRIEND = "com.tencent.mm.ui.tools.ShareImgUI"

    // 朋友圈
    const val ACTIVITY_SHARE_WECHAT_MOMENT = "com.tencent.mm.ui.tools.ShareToTimeLineUI"

    // QQ 分为图片和纯文本
    const val ACTIVITY_SHARE_QQ_FRIEND = "com.tencent.mobileqq.activity.JumpActivity"

    // QQ空间
    const val ACTIVITY_SHARE_QQ_ZONE = "com.qzonex.module.operation.ui.QZonePublishMoodActivity"

    // 微博好友  setType("text/plain");
    const val ACTIVITY_SHARE_SINA_FRIEND = "com.sina.weibo.weiyou.share.WeiyouShareDispatcher"

    // 微博内容  setType("image/*");
    const val ACTIVITY_SHARE_SINA_CONTENT = "com.sina.weibo.composerinde.ComposerDispatchActivity"


    fun isInstallApp(context: Context, app_package: String): Boolean {
        val packageManager = context.getPackageManager()
        val packList: MutableList<PackageInfo> = packageManager.getInstalledPackages(0)
        if (packList != null && !packList.isEmpty()) {
            for (pack in packList) {
                val packName = pack.packageName
                if (app_package.equals(packName)) {
                    return true
                }
            }
        }
        return false
    }


    fun isWeChatAvailable(context: Context): Boolean {
        return isInstallApp(context, PACKAGE_WECHAT)
    }


    fun isQQClientAvailable(context: Context): Boolean {
        return isInstallApp(context, PACKAGE_MOBILE_QQ)
    }


    /**
     * app版本名称
     */
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


    /**
     * app版本号
     */
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

    /**
     * 获取App的名称
     *
     * @param context 上下文
     *
     * @return 名称
     */
    fun getAppName(context: Context): String? {
        val pm = context.packageManager
        //获取包信息
        try {
            val packageInfo = pm.getPackageInfo(context.packageName, 0)
            //获取应用 信息
            val applicationInfo = packageInfo.applicationInfo
            //获取albelRes
            val labelRes = applicationInfo.labelRes
            //返回App的名称
            return context.resources.getString(labelRes)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return null
    }




    /**
     * 安装apk文件
     */
    fun installApk(context: Context, filePath: String) {
        if (TextUtils.isEmpty(filePath)) return
        if (FileUtil.isExistFile(filePath)) return
        val intent = Intent()
        intent.setAction("android.intent.action.VIEW")
        intent.addCategory("android.intent.category.DEFAULT")
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        FileProviderUtil.setIntentDataAndType(context, intent, "application/vnd.android.package-archive", File(filePath), true)
        context.startActivity(intent)
    }
}