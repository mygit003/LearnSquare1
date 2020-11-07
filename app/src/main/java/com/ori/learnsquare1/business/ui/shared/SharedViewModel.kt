package com.ori.learnsquare1.business.ui.shared

import androidx.lifecycle.MutableLiveData
import com.ori.learnsquare.business.entity.ArticleValue
import com.ori.learnsquare1.common.base.http.HttpUtils
import com.ori.learnsquare1.common.base.viewmodel.BaseViewModel

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/25 15:52
 * 类说明:
 */
class SharedViewModel : BaseViewModel() {

    private val mRepository by lazy { SharedRepository() }
    var articleList = MutableLiveData<MutableList<ArticleValue.DatasBean>>()
    var collectStatus = MutableLiveData<Boolean>()


    fun getMyArticles(pageIndex: Int) {
        launch(
            block = {
                val data = mRepository.getSharedList(pageIndex)
                articleList.value = data

            },
            error = {

            }
        )
    }


    fun unCollect(aid: Int) {
        launch(
            block = {
                userRepository.unCollectArticle(aid)
                collectStatus.value = false
            },
            error = {

            }
        )
    }

    fun collect(aid: Int) {
        launch(
            block = {
                userRepository.collectArticle(aid)
                collectStatus.value = true
            },
            error = {

            }
        )
    }

}