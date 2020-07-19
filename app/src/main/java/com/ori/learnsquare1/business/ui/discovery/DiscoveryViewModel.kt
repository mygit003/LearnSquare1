package com.ori.learnsquare1.business.ui.discovery

import androidx.lifecycle.MutableLiveData
import com.ori.learnsquare1.business.entity.HotWordValue
import com.ori.learnsquare1.business.entity.OfterSearchValue
import com.ori.learnsquare1.common.base.viewmodel.BaseViewModel

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/18 10:54
 * 类说明:
 */
class DiscoveryViewModel : BaseViewModel() {

    private val mRepository by lazy { DiscoveryRepository() }

    var hotKey = MutableLiveData<MutableList<HotWordValue>>()
    var oftenSearchList = MutableLiveData<MutableList<OfterSearchValue>>()


    fun getSearchItem() {
        launch(
            block = {
                val hotKeyList = mRepository.getHotKeyList()
                val oftenList = mRepository.getOftenSeachList()
                hotKey.value = hotKeyList
                oftenSearchList.value = oftenList

            },
            error = {

            }
        )
    }


}