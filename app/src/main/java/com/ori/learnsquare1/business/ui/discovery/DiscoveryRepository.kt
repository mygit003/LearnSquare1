package com.ori.learnsquare1.business.ui.discovery

import com.ori.learnsquare1.business.entity.HotWordValue
import com.ori.learnsquare1.common.base.http.HttpUtils
import retrofit2.http.HTTP

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/18 10:53
 * 类说明:
 */
class DiscoveryRepository {

    suspend fun getHotKeyList() = HttpUtils.getApiService().getHotWords().data

    suspend fun getOftenSeachList() = HttpUtils.getApiService().getFrequentlyWebsites().data
}