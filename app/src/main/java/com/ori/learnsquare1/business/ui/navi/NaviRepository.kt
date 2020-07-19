package com.ori.learnsquare1.business.ui.navi

import com.ori.learnsquare1.common.base.http.HttpUtils

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/18 15:26
 * 类说明:
 */
class NaviRepository {

    suspend fun getNavigationItem() = HttpUtils.getApiService().getNavigationTabList().data
}