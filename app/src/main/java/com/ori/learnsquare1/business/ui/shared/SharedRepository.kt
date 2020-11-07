package com.ori.learnsquare1.business.ui.shared

import com.ori.learnsquare1.common.base.http.HttpUtils

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/25 15:52
 * 类说明:
 */
class SharedRepository {

    suspend fun getSharedList(pageIndex: Int) = HttpUtils.getApiService().getMyArticle(pageIndex).data?.shareArticles?.datas
}