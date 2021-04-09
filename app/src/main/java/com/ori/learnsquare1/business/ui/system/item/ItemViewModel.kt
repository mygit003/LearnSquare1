package com.ori.learnsquare1.business.ui.system.item

import androidx.lifecycle.MutableLiveData
import com.ori.learnsquare1.business.entity.ArticleValue
import com.ori.learnsquare1.common.base.http.HttpUtils
import com.ori.learnsquare1.common.base.viewmodel.BaseViewModel

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/12 11:12
 * 类说明:
 */
class ItemViewModel : BaseViewModel() {

    private val mRepository by lazy { ItemRepository() }

    var articleList = MutableLiveData<MutableList<ArticleValue.DatasBean>>()
    var collectStatus = MutableLiveData<Boolean>()

    fun getSystemArticle(pageIndex: Int, cateId: Int) {
        launch(
            block = {
                val data = mRepository.getSystemArticle(pageIndex, cateId)
                data?.datas.let {
                    articleList.value = it
                }
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