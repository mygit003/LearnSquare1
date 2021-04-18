package com.ori.learnsquare1.business.ui.system

import androidx.lifecycle.MutableLiveData
import com.ori.learnsquare1.business.entity.ArticleValue
import com.ori.learnsquare1.business.entity.SystemValue
import com.ori.learnsquare1.common.base.viewmodel.BaseViewModel

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/12 10:50
 * 类说明:
 */
class SystemViewModel : BaseViewModel() {

    private val mRepository by lazy { SystemRepository() }

    var tabList = MutableLiveData<MutableList<SystemValue>>()
    var articleList = MutableLiveData<MutableList<ArticleValue.DatasBean>>()


    fun getTabList() {
        loadingStatus.value = true
        reloadStatus.value = false
        launch(
            block = {
                val data = mRepository.getSystemTabList()
                data?.let {
                    tabList.value = it
                }
                loadingStatus.value = false
            }
            ,error = {
                loadingStatus.value = false
                reloadStatus.value = true
            }
        )
    }


    fun getArticleList(pageIndex: Int, cateId: Int) {
        loadingStatus.value = true
        reloadStatus.value = false
        launch(
            block = {
                val data = mRepository.getSystemArticleList(pageIndex, cateId)
                data?.datas.let {
                    articleList.value = it
                }
                loadingStatus.value = false

            },
            error = {
                loadingStatus.value = false
                reloadStatus.value = true
            }
        )
    }
}