package com.ori.learnsquare1.business.ui.history

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ori.learnsquare1.business.entity.ArticleValue
import com.ori.learnsquare1.business.db.entity.BrowseHistoryValue
import com.ori.learnsquare1.common.base.viewmodel.BaseViewModel
import com.ori.learnsquare1.common.util.JsonUtil

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/26 9:41
 * 类说明:
 */
class HistoryViewModel : BaseViewModel() {

    private val mRepository by lazy { HistoryRepository() }

    var historyList = MutableLiveData<MutableList<ArticleValue.DatasBean>>()
    var updateHistoryResult = MutableLiveData<Int>()
    var delHistoryResult = MutableLiveData<Int>()
    var clearHistoryResult = MutableLiveData<Int>()
    var collectStatus = MutableLiveData<Boolean>()


    fun getHistoryList(pageSize: Int, pageIndex: Int) {
        launch(
            block = {
                val data = mRepository.query(pageSize, pageIndex)
                val list: MutableList<ArticleValue.DatasBean> = mutableListOf()
                if (null != data && !data.isEmpty()) {
                    data.forEach {
                        var value: ArticleValue.DatasBean = ArticleValue.DatasBean()
                        value._id = it._id
                        value.id = it.articleId
                        value.title = it.articleTitle
                        value.author = it.articleAuthor
                        value.niceDate = it.articlePublishTime
                        value.superChapterName = it.articleChapter
                        value.link = it.articleLink
                        value.collect = (if (it.articleCollect == 0) false else true)
                        value.envelopePic = it.articleLinkPic
                        list.add(value)
                    }
                }
                Log.d("list", "${JsonUtil.toJson(list)}")
                historyList.value = list

            },
            error = {

            }
        )
    }

    fun updateHistory(historyValue: BrowseHistoryValue) {
        launch(
            block = {
                var res = mRepository.update(historyValue)
                updateHistoryResult.value = res

            },
            error = {

            }
        )
    }

    fun deleteHistoryById(id: Int) {
        launch(
            block = {
                val res = mRepository.deleteById(id)
                Log.d("deleteHistoryById", "res:$res")
                delHistoryResult.value = res

            },
            error = {

            }
        )
    }

    fun clearHistory() {
        launch(
            block = {
                val res = mRepository.clear()
                Log.d("clearHistory", "res:$res")
                clearHistoryResult.value = res
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