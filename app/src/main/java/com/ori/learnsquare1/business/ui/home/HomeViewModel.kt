package com.ori.learnsquare1.business.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ori.learnsquare1.business.entity.BannerValue
import com.ori.learnsquare1.common.base.viewmodel.BaseViewModel

/**
 * 创建人 zhengpf
 * 时间 2020/7/2
 * 类说明:
 */
class HomeViewModel : BaseViewModel() {


    private val homeRepository by lazy { HomeRepository() }


    val bannerList = MutableLiveData<MutableList<BannerValue>>(mutableListOf())


    fun getBannerData() {
        launch(
            block = {
                val data = homeRepository.getBannerList()
                bannerList.value = data
            },
            error = {
                Log.e("TAG", "errMsg:${it.message}")
            }
        )
    }

}