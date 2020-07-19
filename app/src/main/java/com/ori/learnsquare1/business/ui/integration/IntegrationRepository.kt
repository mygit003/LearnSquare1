package com.ori.learnsquare1.business.ui.integration

import com.ori.learnsquare1.common.base.http.HttpUtils

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/19 9:32
 * 类说明:
 */
class IntegrationRepository {


    suspend fun getUserIntegration(pageNo: Int) = HttpUtils.getApiService().getIntegralRecord(pageNo).data?.datas
}