package com.ori.learnsquare1.business.ui.home.project

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ori.learnsquare1.business.entity.ArticleValue
import com.ori.learnsquare1.business.entity.TabValue
import com.ori.learnsquare1.common.base.viewmodel.BaseViewModel

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/11 11:18
 * 类说明:
 */
class ProjectViewModel : BaseViewModel() {

    private val mRepository by lazy { ProjectRepository() }

    var tabList = MutableLiveData<MutableList<TabValue>>()
    var articleList = MutableLiveData<MutableList<ArticleValue.DatasBean>>()
    var collectStatus = MutableLiveData<Boolean>()


    fun getItemTab() {
        launch(
            block = {
                val tabData = mRepository.getItemTabList()
                tabList.value = tabData


            },
            error = {

            }
        )
    }

    fun getProjectArticlesList(pageIndex: Int, cateId: Int) {
        launch(
            block = {
                val data = mRepository.getArticlesList(pageIndex, cateId)
                if (null != data) {
                    data?.datas.let {
                        articleList.value = it
                    }
                }

            },
            error = {
                Log.e("TAG", "errorMsg:${it.message}")
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