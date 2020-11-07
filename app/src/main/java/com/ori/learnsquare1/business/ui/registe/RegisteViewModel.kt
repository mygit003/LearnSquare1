package com.ori.learnsquare1.business.ui.registe

import androidx.lifecycle.MutableLiveData
import com.ori.learnsquare1.common.base.viewmodel.BaseViewModel

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/19 11:37
 * 类说明:
 */
class RegisteViewModel : BaseViewModel() {

    private val mRepository by lazy { RegisteRepository() }

    var registeStatus = MutableLiveData<String>()


    fun registe(accont: String, pwd: String, repwd: String) {
        launch(
            block = {
                mRepository.registe(accont, pwd, repwd)
                registeStatus.value = "success"
            },
            error = {
                registeStatus.value = "" + it.message
            }
        )
    }

}