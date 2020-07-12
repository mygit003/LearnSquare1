package com.ori.learnsquare1.business.ui.system

import com.ori.learnsquare1.common.base.http.HttpUtils

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/12 10:41
 * 类说明:
 */
class SystemRepository {

    /**
     * 获取体系下面的tab列表
     */
    suspend fun getSystemTabList() = HttpUtils.getApiService().getSystemTabList().data

    /**
     * 获取tab下的文章
     */
    suspend fun getSystemArticleList(pageIndex: Int, cateId: Int) = HttpUtils.getApiService().getSystemCateArticle(pageIndex, cateId).data
}