package com.ori.learnsquare1.common.util

import android.content.Context
import java.math.BigDecimal
import java.text.DecimalFormat

/**
 * 创建人 zhengpf
 * 时间 2021/5/31 14:55
 * 类说明:
 */
object FormatUtil {

    /**
     * 商品价格格式(保留4位小数)
     */
    const val PRICE_DF = "###0.0000"

    const val MONEY_DF = "###0.00"

    /**
     * 定义Double 显示的格式###.00
     */
    var DF = DecimalFormat(MONEY_DF)

    private val format = DecimalFormat(PRICE_DF)


    /**
     * 格式化浮点型
     *
     * @param value
     * @param pattern 格式 默认:#,###.00
     * @return
     */
    fun formatNum(value: Double?, pattern: String?): String? {
        var pattern = pattern
        if (pattern == null || "" == pattern.trim { it <= ' ' }) {
            pattern = "#,##0.00"
        }
        val decimalFormat = DecimalFormat(pattern)
        return decimalFormat.format(value)
    }


    fun formatNum(value: Double?): String? {
        return formatNum(value, null)
    }


    fun formatInt(value: Int?): String? {
        return formatInt(value, null)
    }

    fun formatInt(value: Int?, pattern: String?): String? {
        var pattern = pattern
        if (pattern == null || "" == pattern.trim { it <= ' ' }) {
            pattern = "#,##0"
        }
        val decimalFormat = DecimalFormat(pattern)
        return decimalFormat.format(value)
    }

    /**
     * 将以0结尾的浮点型数据格式化为整数显示
     * @param value
     * @return
     */
    fun formatDoubleToInt(value: Double): String? {
        val str = format.format(value)
        val decimalFormat = DecimalFormat("###################.###########")
        var result: String? = ""
        val index = str.indexOf(".") //小数点位置
        val s = str.substring(index + 1) //获取小数部分
        val num = s.toInt() //将代表小数部分的字符串转化为整数
        result = if (num == 0) {
            decimalFormat.format(value)
        } else {
            str
        }
        return result
    }

    /**
     * 格式化数值(去掉小数位后无用的0)
     * @return
     */
    fun formatDoubleValueUselessZero(value: Double): String? {
        var str = format.format(value)
        if (str.indexOf(".") > 0) {
            //正则表达
            //去掉后面无用的零
            str = str.replace("0+?$".toRegex(), "")
            //如小数点后面全是零则去掉小数点
            str = str.replace("[.]$".toRegex(), "")
        }
        return str
    }

    /**
     * 格式化double类型数据,直接截断保留两位小数,不足以0填充
     * @param value
     * @return
     */
    fun formatDoubleNoRound(value: Double): Double {
        val strValue = format.format(value)
        var result: String? = ""
        val index = strValue.indexOf(".") //小数点位置
        val intStr = strValue.substring(0, index) //整数部分
        val s = strValue.substring(index + 1) //获取小数部分
        val num = s.toInt() //将代表小数部分的字符串转化为整数
        result = if (num == 0) {
            formatNum(value, MONEY_DF)
        } else {
            if (s.length >= 2) {
                intStr + "." + s.substring(0, 2)
            } else {
                formatNum(value, MONEY_DF)
            }
        }
        return java.lang.Double.valueOf(result)
    }

    /**
     * 金额舍分取整(保留1位有效小数,第2位小数为0)
     * @param value
     * @return
     */
    fun formatDoubleRoundPoint(value: Double): Double {
        val strValue = format.format(value)
        var result: String? = ""
        val index = strValue.indexOf(".") //小数点位置
        val intStr = strValue.substring(0, index) //整数部分
        val s = strValue.substring(index + 1) //获取小数部分
        val num = s.toInt() //将代表小数部分的字符串转化为整数
        result = if (num == 0) {
            formatNum(value, MONEY_DF)
        } else {
            if (s.length >= 1) {
                intStr + "." + s.substring(0, 1) + "0"
            } else {
                formatNum(value, MONEY_DF)
            }
        }
        return java.lang.Double.valueOf(result)
    }

    /**
     * 四舍五入
     * @param value
     * @return
     */
    fun formatDoubleRoundUpAndDown(value: Double): Double {
        val str = format.format(value)
        val decimal = BigDecimal(str).setScale(2, BigDecimal.ROUND_HALF_UP)
        return decimal.toDouble()
    }


    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue
     * @param scale
     * （DisplayMetrics类中属性density）
     * @return
     */
    fun px2dip(pxValue: Float, scale: Float): Int {
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * @param scale
     * （DisplayMetrics类中属性density）
     * @return
     */
    fun dip2px(dipValue: Float, scale: Float): Int {
        return (dipValue * scale + 0.5f).toInt()
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @param fontScale
     * （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    fun px2sp(pxValue: Float, fontScale: Float): Int {
        return (pxValue / fontScale + 0.5f).toInt()
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param fontScale
     * （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    fun sp2px(spValue: Float, fontScale: Float): Int {
        return (spValue * fontScale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }
}