package com.ori.learnsquare1.business.ui.integration

import androidx.lifecycle.MutableLiveData
import com.ori.learnsquare.business.entity.IntegrationRecordValue
import com.ori.learnsquare.business.entity.UserAccountValue
import com.ori.learnsquare1.business.ui.user.UserRepository
import com.ori.learnsquare1.common.base.http.HttpUtils
import com.ori.learnsquare1.common.base.viewmodel.BaseViewModel

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/19 9:33
 * 类说明:
 */
class IntegrationViewModel : BaseViewModel() {

    private val mRepository by lazy { IntegrationRepository() }
    private val mUserRepository by lazy { UserRepository() }

    val integrationList = MutableLiveData<MutableList<IntegrationRecordValue>>()
    val userAccount = MutableLiveData<UserAccountValue>()


    fun getUserAccount() {
        launch(
            block = {
                val data = mUserRepository.getUserInfo()
                userAccount.value = data

            },
            error = {

            }
        )
    }

    fun getIntegrationRecord(pageNo: Int) {
        launch(
            block = {
                val data = mRepository.getUserIntegration(pageNo)
                integrationList.value = data

            },
            error = {

            }
        )
    }
}