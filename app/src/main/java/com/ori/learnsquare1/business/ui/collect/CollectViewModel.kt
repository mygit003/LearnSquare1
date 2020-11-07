package com.ori.learnsquare1.business.ui.collect

import android.renderscript.Script
import androidx.lifecycle.MutableLiveData
import com.ori.learnsquare.business.entity.ArticleValue
import com.ori.learnsquare1.common.base.viewmodel.BaseViewModel

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/25 15:52
 * 类说明:
 */
class CollectViewModel : BaseViewModel() {

    private val mRepository by lazy { CollectRepository() }

    var articleList = MutableLiveData<MutableList<ArticleValue.DatasBean>>()
    var collectStatus = MutableLiveData<Boolean>()


    fun getCollectList(pageIndex: Int) {
        launch(
            block = {
                val data = mRepository.getCollectList(pageIndex)
                data?.let {
                    val list:MutableList<ArticleValue.DatasBean> = mutableListOf()
                    it.forEach {item ->
                        var bean: ArticleValue.DatasBean = ArticleValue.DatasBean()
                        bean.title = item.title
                        bean.author = item.author
                        bean.desc = item.desc
                        bean.niceDate = item.niceDate
                        bean.superChapterName = item.chapterName
                        bean.id = item.originId
                        bean.collect = true
                        bean.envelopePic = item.envelopePic
                        bean.link = item.link

                        list.add(bean)
                    }
                    articleList.value = list
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