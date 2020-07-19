package com.ori.learnsquare1.business.ui.user

import com.ori.learnsquare1.common.base.http.HttpUtils

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/18 17:34
 * 类说明:
 */
class UserRepository {

    suspend fun getUserInfo() = HttpUtils.getApiService().getAccountData().data


}