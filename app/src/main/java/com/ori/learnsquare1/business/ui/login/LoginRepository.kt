package com.ori.learnsquare1.business.ui.login

import com.ori.learnsquare1.common.base.http.HttpUtils

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/19 11:37
 * 类说明:
 */
class LoginRepository {

    suspend fun login(account: String, pwd: String) = HttpUtils.getApiService().login(account, pwd).getResult()
}