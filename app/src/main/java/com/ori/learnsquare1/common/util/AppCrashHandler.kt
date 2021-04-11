package com.ori.learnsquare1.common.util

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.os.Looper
import android.util.Log
import android.widget.Toast
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * 创建人: zhengpf
 * 修改时间: 2021/4/11 21:31
 * 类说明:
 */
class AppCrashHandler private constructor() : Thread.UncaughtExceptionHandler {

    private val TAG = this.javaClass.simpleName

    /**
     * 系统默认UncaughtExceptionHandler
     */
    private var mDefaultHandler: Thread.UncaughtExceptionHandler? = null

    /**
     * context
     */
    private var mContext: Context? = null

    /**
     * 存储异常和参数信息
     */
    private val paramsMap: MutableMap<String, String> = HashMap()

    /**
     * 格式化时间
     */
    private val format = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")

    companion object {
        @Volatile
        private var instance: AppCrashHandler? = null

        fun getInstance(): AppCrashHandler = instance?: synchronized(this) {
            instance ?: AppCrashHandler().also { instance = it }
        }

    }



    fun init(context: Context?) {
        mContext = context
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        //设置该CrashHandler为系统默认的
        Thread.setDefaultUncaughtExceptionHandler(this)
    }


    override fun uncaughtException(thread: Thread?, throwable: Throwable?) {
        if (null != mDefaultHandler && !handleException(throwable)) {
            //系统处理异常
            mDefaultHandler!!.uncaughtException(thread, throwable)
        } else {
            //自己处理异常
            try {
                //延迟2秒杀进程
                Thread.sleep(2000)
            } catch (e: InterruptedException) {
                Log.e(TAG, "error : ", e)
            }
            //退出程序
            ActivityManage.getInstance().appExit()
        }
    }


    private fun handleException(ex: Throwable?): Boolean {
        if (ex == null) {
            return false
        }
        //收集设备参数信息
        collectAppInfo(mContext)
        //使用Toast来显示异常信息
        object : Thread() {
            override fun run() {
                Looper.prepare()
                Toast.makeText(mContext, "程序出现异常，即将退出...", Toast.LENGTH_SHORT).show()
                Looper.loop()
            }
        }.start()
        //保存日志文件
        saveCrashInfoToFile(ex)
        return true
    }


    /**
     * 收集应用信息
     * @param ctx
     */
    private fun collectAppInfo(ctx: Context?) {
        //获取versionName,versionCode
        try {
            val pm = ctx!!.packageManager
            val pi = pm.getPackageInfo(ctx.packageName, PackageManager.GET_ACTIVITIES)
            if (pi != null) {
                val versionName = if (pi.versionName == null) "null" else pi.versionName
                val versionCode = pi.versionCode.toString() + ""
                paramsMap["versionName"] = versionName
                paramsMap["versionCode"] = versionCode
            }
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e(TAG, "an error occured when collect package info", e)
        }
        //获取所有系统信息
        val fields = Build::class.java.declaredFields
        for (field in fields) {
            try {
                field.isAccessible = true
                paramsMap[field.name] = field[null].toString()
            } catch (e: Exception) {
                Log.e(TAG, "an error occured when collect crash info", e)
            }
        }
    }


    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return  返回文件名称,便于将文件传送到服务器
     */
    fun saveCrashInfoToFile(ex: Throwable): String? {
        val sb = StringBuffer()
        for ((key, value) in paramsMap) {
            sb.append("$key=$value\n")
        }
        val writer: Writer = StringWriter()
        val printWriter = PrintWriter(writer)
        ex.printStackTrace(printWriter)
        var cause = ex.cause
        while (cause != null) {
            cause.printStackTrace(printWriter)
            cause = cause.cause
        }
        printWriter.close()
        val result = writer.toString()
        sb.append(result)
        try {
            val timestamp = System.currentTimeMillis()
            val time = format.format(Date())
            val fileName = "crash-$time-$timestamp.log"
            if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                val path = Environment.getExternalStorageDirectory().absolutePath + "/crash/"
                val dir = File(path)
                if (!dir.exists()) {
                    dir.mkdirs()
                }
                val fos = FileOutputStream(path + fileName)
                fos.write(sb.toString().toByteArray())
                fos.close()
            }
            return fileName
        } catch (e: Exception) {
            Log.e(TAG, "an error occured while writing file...", e)
        }
        return null
    }
}