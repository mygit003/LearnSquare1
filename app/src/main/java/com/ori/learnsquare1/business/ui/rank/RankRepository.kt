package com.ori.learnsquare1.business.ui.rank

import com.ori.learnsquare1.common.base.http.HttpUtils

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/25 10:20
 * 类说明:
 */
class RankRepository {

    suspend fun getRankList(pageIndex: Int) = HttpUtils.getApiService().getMyRank(pageIndex).getResult()?.datas
}