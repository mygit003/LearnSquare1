package com.ori.learnsquare1.business.ui.login

import androidx.lifecycle.MutableLiveData
import com.ori.learnsquare.business.entity.UserValue
import com.ori.learnsquare1.common.base.viewmodel.BaseViewModel

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/19 11:37
 * 类说明:
 */
class LoginViewModel : BaseViewModel() {

    private val mRepository by lazy { LoginRepository() }

    var userValue = MutableLiveData<UserValue>()
    var loginStatus = MutableLiveData<Boolean>()
    var loginMessage = MutableLiveData<String>()

    fun login(account: String, pwd: String) {
        launch(
            block = {
                val data = mRepository.login(account, pwd)
                userValue.value = data
                loginStatus.value = true
            },
            error = {
                loginMessage.value = it.message
                loginStatus.value = false
            }
        )
    }

}