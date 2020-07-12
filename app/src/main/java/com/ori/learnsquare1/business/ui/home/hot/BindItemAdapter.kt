package com.ori.learnsquare1.business.ui.home.hot

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ori.learnsquare.business.entity.ArticleValue
import com.ori.learnsquare1.business.adapter.ArticleAdapter

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/11 11:36
 * 类说明:
 */
object BindItemAdapter {

    @BindingAdapter("bindHotList")
    @JvmStatic fun bindingHotList(rv: RecyclerView, list: MutableList<ArticleValue.DatasBean>?) {
        list?.let {
            rv.adapter = ArticleAdapter(it)
        }

    }
}