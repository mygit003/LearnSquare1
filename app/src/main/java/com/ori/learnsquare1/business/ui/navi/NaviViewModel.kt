package com.ori.learnsquare1.business.ui.navi

import androidx.lifecycle.MutableLiveData
import com.ori.learnsquare.business.entity.NavigationValue
import com.ori.learnsquare1.common.base.viewmodel.BaseViewModel

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/18 15:28
 * 类说明:
 */
class NaviViewModel : BaseViewModel() {

    private val mRepository by lazy { NaviRepository() }

    var naviItems = MutableLiveData<MutableList<NavigationValue>>()


    fun getNavigationItem() {
        launch(
            block = {
                val data = mRepository.getNavigationItem()
                naviItems.value = data
            },
            error = {

            }
        )
    }
}