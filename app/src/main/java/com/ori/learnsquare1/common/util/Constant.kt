package com.ori.learnsquare1.common.util

/**
 * 创建人: zhengpf
 * 修改时间: 2020/6/27 11:55
 * 类说明:
 */
object Constant {


    const val BASE_URL = "https://www.wanandroid.com"

    const val DB_VERSION = 1


    /**
     * 多布局中带图片
     */
    const val ITEM_ARTICLE_WITH_PIC = 10
    /**
     * 多布局中不带图片
     */
    const val ITEM_ARTICLE_NO_PIC = 20


    object WebParam {
        val PARAM_TITLE = "title"
        val PARAM_URL = "url"
        val PARAM_ITEM = "item"
    }


    object WebUrl {
        /**
         * wanandroid
         */
        val WEB_URL = "https://www.wanandroid.com"

        /**
         * app github
         */
        val APP_GITHUB = "https://github.com/zskingking/WanAndroid-ZS"


        /**
         * wan android 官网
         */
        const val INTEGRAL_RULE = "https://www.wanandroid.com/blog/show/2653"
    }


    object SpKey {
        val SP_USER_INFO = "USERINFO";
        val SP_INTEGRAL_INFO = "INTEGRALINFO"
        val SP_LOGIN_FLAG = "LOGINFLAG"
        var SP_MODE_CONFIG = "MODECONFIG"
    }
}