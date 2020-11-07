package com.ori.learnsquare1.business.ui.registe

import com.ori.learnsquare1.common.base.http.HttpUtils

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/19 11:37
 * 类说明:
 */
class RegisteRepository {

    suspend fun registe(account: String, pwd: String, repwd: String) = HttpUtils.getApiService().register(account, pwd, repwd).getResult()
}