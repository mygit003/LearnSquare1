package com.ori.learnsquare1.business.ui.rank

import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import com.ori.learnsquare.business.entity.RankValue
import com.ori.learnsquare1.common.base.viewmodel.BaseViewModel

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/25 10:20
 * 类说明:
 */
class RankViewModel : BaseViewModel() {

    private val mRepository by lazy { RankRepository() }

    var rankList = MutableLiveData<MutableList<RankValue>>()


    fun getRankList(pageIndex: Int) {
        launch(
            block = {
                val data = mRepository.getRankList(pageIndex)
                rankList.value = data

            },
            error = {

            }
        )
    }
}