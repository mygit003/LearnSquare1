package com.ori.learnsquare1.business.ui.home.wechat

import com.ori.learnsquare.business.entity.ArticleValue
import com.ori.learnsquare1.common.base.http.BaseResponse
import com.ori.learnsquare1.common.base.http.HttpUtils

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/11 11:28
 * 类说明:
 */
class WeChatRepository {

    suspend fun getItemTabList() = HttpUtils.getApiService().getAccountsTabList().data
    suspend fun getArticlesList(pageIndex: Int, cateId: Int) = HttpUtils.getApiService().getAccountList(cateId, pageIndex).data
}