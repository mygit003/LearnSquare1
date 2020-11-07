package com.ori.learnsquare1.business.ui.rank

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ori.learnsquare.business.entity.ArticleValue
import com.ori.learnsquare.business.entity.IntegrationRecordValue
import com.ori.learnsquare.business.entity.RankValue
import com.ori.learnsquare1.business.adapter.ArticleAdapter
import com.ori.learnsquare1.business.adapter.IntegrationRankAdapter
import com.ori.learnsquare1.business.adapter.IntegrationRecordAdapter

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/11 11:36
 * 类说明:
 */
object BindItemAdapter {

    @BindingAdapter("bindList")
    @JvmStatic fun bindingList(rv: RecyclerView, list: MutableList<RankValue>?) : IntegrationRankAdapter? {
        var rAdapter: IntegrationRankAdapter? = null
        list?.let {
            if (null == rAdapter) {
                rAdapter = IntegrationRankAdapter(it)
                rv.adapter = rAdapter
            }else {
                rAdapter?.setNewData(list)
            }
        }
        return rAdapter
    }



    @BindingAdapter("bindArticleList")
    @JvmStatic fun bindArticleList(rv: RecyclerView, list: MutableList<ArticleValue.DatasBean>?) : ArticleAdapter? {
        var rAdapter: ArticleAdapter? = null
        list?.let {
            if (null == rAdapter) {
                rAdapter = ArticleAdapter(it)
                rv.adapter = rAdapter
            }
        }
        return rAdapter
    }
}