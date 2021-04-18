package com.ori.learnsquare1.common.util

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.res.Configuration
import android.util.DisplayMetrics
import android.util.Log

/**
 * 创建人 zhengpf
 * 时间 2021/3/24
 * 类说明:
 */
object DensityUtil {

    private val TAG: String = "DensityUtil"

    //设计图宽度
    private val width: Float = 360f
    //屏幕比例参数
    private var density: Float = 0f
    //字体比例参数
    private var scaleDensity: Float = 0f


    /**
     * 设置屏幕比例
     */
    fun setDensity(application: Application, activity: Activity) {
        val appDisplayMetrics: DisplayMetrics = application.resources.displayMetrics
        if (density == 0f) {
            density = appDisplayMetrics.density
            scaleDensity = appDisplayMetrics.scaledDensity
            Log.w(TAG, "density:$density")
            Log.w(TAG, "scaleDensity:$scaleDensity")

            //监听字体变化
            application.registerComponentCallbacks(object : ComponentCallbacks {
                override fun onConfigurationChanged(newConfig: Configuration) {
                    if (null != newConfig && newConfig.fontScale > 0) {
                        scaleDensity = application.resources.displayMetrics.scaledDensity
                    }

                }

                override fun onLowMemory() {

                }

            })
        }

        //根据设计图计算出屏幕的density scaleDensity, dpi的值
        //px = dp * (dpi / 160), 即px = dp * density, density为屏幕像素密度比值(DisplayMetrics#density)
        val targetDensity: Float = appDisplayMetrics.widthPixels / width
        val targetScaleDensity: Float = targetDensity * (scaleDensity / density)
        val targetDensityDpi: Int = (targetScaleDensity * 160).toInt()

        //设置新的屏幕参数
        appDisplayMetrics.density = targetDensity
        appDisplayMetrics.scaledDensity = targetScaleDensity
        appDisplayMetrics.densityDpi = targetDensityDpi

        //设置页面参数
        val activityDisplayMetrics: DisplayMetrics = activity.resources.displayMetrics
        activityDisplayMetrics.density = targetDensity
        activityDisplayMetrics.scaledDensity = targetScaleDensity
        activityDisplayMetrics.densityDpi = targetDensityDpi
    }
}