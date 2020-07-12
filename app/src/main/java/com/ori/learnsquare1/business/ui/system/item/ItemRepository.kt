package com.ori.learnsquare1.business.ui.system.item

import com.ori.learnsquare1.common.base.http.HttpUtils

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/12 11:12
 * 类说明:
 */
class ItemRepository {

    suspend fun getSystemArticle(pageIndex: Int, cateId: Int) = HttpUtils.getApiService().getSystemCateArticle(pageIndex, cateId).data
}