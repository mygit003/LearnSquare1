package com.ori.learnsquare1.business.ui.home.lastest

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ori.learnsquare.business.entity.ArticleValue
import com.ori.learnsquare1.common.base.viewmodel.BaseViewModel

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/11 11:18
 * 类说明:
 */
class LastestViewModel : BaseViewModel() {

    private val mRepository by lazy { LastestRepository() }

    var articleList = MutableLiveData<MutableList<ArticleValue.DatasBean>>()



    fun getLastestArticleList(pageIndex: Int) {
        launch(
            block = {
                val data = mRepository.getArticlesList(pageIndex)
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

}