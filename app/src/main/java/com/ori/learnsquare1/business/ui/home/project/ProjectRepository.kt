package com.ori.learnsquare1.business.ui.home.project

import com.ori.learnsquare1.business.entity.ArticleValue
import com.ori.learnsquare1.common.base.http.BaseResponse
import com.ori.learnsquare1.common.base.http.HttpUtils

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/11 11:28
 * 类说明:
 */
class ProjectRepository {

    suspend fun getItemTabList() = HttpUtils.getApiService().getProjectTabList().data

    suspend fun getArticlesList(pageIndex: Int, cateId: Int) = HttpUtils.getApiService().getProjectList(pageIndex, cateId).data
}