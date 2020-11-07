package com.ori.learnsquare1.business.ui.web

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ori.learnsquare1.business.db.entity.BrowseHistoryValue
import com.ori.learnsquare1.common.base.viewmodel.BaseViewModel

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/26 11:30
 * 类说明:
 */
class WebViewModel : BaseViewModel() {

    private val mRepository by lazy { WebRepository() }
    var addHistoryResult = MutableLiveData<Long>()


    fun addHistory(historyValue: BrowseHistoryValue) {
        launch(
            block = {
                val res = mRepository.add(historyValue)
                addHistoryResult.value = res

            },
            error = {
                Log.e("err", it.message)
            }
        )
    }
}