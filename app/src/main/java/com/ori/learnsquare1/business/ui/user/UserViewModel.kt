package com.ori.learnsquare1.business.ui.user

import androidx.lifecycle.MutableLiveData
import com.ori.learnsquare1.business.entity.UserAccountValue
import com.ori.learnsquare1.common.base.viewmodel.BaseViewModel

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/18 17:34
 * 类说明:
 */
class UserViewModel : BaseViewModel() {


    var userAccountValue = MutableLiveData<UserAccountValue>()


    fun getUserInfo() {
        launch(
            block = {
                val data = userRepository.getUserInfo()
                userAccountValue.value = data

            },
            error = {

            }
        )
    }


}