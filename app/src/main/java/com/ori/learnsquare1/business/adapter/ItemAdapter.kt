package com.ori.learnsquare1.business.adapter

import android.widget.CheckedTextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.ori.learnsquare1.business.entity.TabValue
import com.ori.learnsquare1.R

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/11 19:40
 * 类说明:
 */
class ItemAdapter(layoutId: Int, tabList: MutableList<TabValue>) :
    BaseQuickAdapter<TabValue, BaseViewHolder>(layoutId, tabList), LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: TabValue) {
        item?.let {
            val ctv: CheckedTextView = holder.getView<CheckedTextView>(R.id.tv_item_title);
            ctv.text = it.name
            ctv.isChecked = it.checked
        }
    }
}