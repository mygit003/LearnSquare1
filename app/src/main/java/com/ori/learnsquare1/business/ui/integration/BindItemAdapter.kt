package com.ori.learnsquare1.business.ui.integration

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ori.learnsquare.business.entity.ArticleValue
import com.ori.learnsquare.business.entity.IntegrationRecordValue
import com.ori.learnsquare1.business.adapter.ArticleAdapter
import com.ori.learnsquare1.business.adapter.IntegrationRecordAdapter

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/11 11:36
 * 类说明:
 */
object BindItemAdapter {

    @BindingAdapter("bindList")
    @JvmStatic fun bindingList(rv: RecyclerView, list: MutableList<IntegrationRecordValue>?) {
        list?.let {
            rv.adapter = IntegrationRecordAdapter(it)
        }

    }
}