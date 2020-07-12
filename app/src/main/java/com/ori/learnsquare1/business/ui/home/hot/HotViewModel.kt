package com.ori.learnsquare1.business.ui.home.hot

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ori.learnsquare.business.entity.ArticleValue
import com.ori.learnsquare.business.entity.BannerValue
import com.ori.learnsquare1.business.ui.home.HomeRepository
import com.ori.learnsquare1.common.base.viewmodel.BaseViewModel

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/11 11:18
 * 类说明:
 */
class HotViewModel : BaseViewModel() {

    private val mRepository by lazy { HotRepository() }

    var articleList = MutableLiveData<MutableList<ArticleValue.DatasBean>>()



    fun getArticleList(pageIndex: Int) {
        launch(
            block = {
                val data = mRepository.getHotArticles(pageIndex)
                if (pageIndex == 0) {
                    if (data != null) {
                        data.datas?.let { getTopArticleList(it) }
                    }
                }else {
                    articleList.value = data?.datas
                }

            },
            error = {
                Log.e("TAG", "errorMsg:${it.message}")
            }

        )
    }


    private fun getTopArticleList(list: MutableList<ArticleValue.DatasBean>) {
        launch(
            block = {
                val data = mRepository.getTopArticles()
                if (null != data) {
                    data?.let {
                        list.addAll(0, it)
                    }
                }
                articleList.value = list

            },
            error = {
                Log.e("TAG", "errorMsg:${it.message}")
            }
        )
    }
}