package com.ori.learnsquare1.business.ui.collect

import com.ori.learnsquare1.common.base.http.HttpUtils

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/25 15:51
 * 类说明:
 */
class CollectRepository {

    suspend fun getCollectList(pageIndex: Int) = HttpUtils.getApiService().getMyCollectData(pageIndex).data?.datas
}