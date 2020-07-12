package com.ori.learnsquare1.business.ui.home.hot

import com.ori.learnsquare1.common.base.http.HttpUtils

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/11 11:28
 * 类说明:
 */
class HotRepository {

    suspend fun getTopArticles() = HttpUtils.getApiService().getTopArticleList().data

    suspend fun getHotArticles(pageIndex: Int) = HttpUtils.getApiService().getArticleList(pageIndex).data
}