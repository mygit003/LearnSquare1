package com.ori.learnsquare1.business.ui.home

import com.ori.learnsquare1.business.util.ApiService
import com.ori.learnsquare1.common.base.http.HttpUtils

/**
 * 创建人 zhengpf
 * 时间 2020/7/2
 * 类说明:
 */
class HomeRepository {

    /**
     * 查询banner数据
     */
    suspend fun getBannerList() = HttpUtils.getApiService().getBannerList().data
}