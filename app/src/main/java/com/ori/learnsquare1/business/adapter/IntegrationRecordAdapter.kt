package com.ori.learnsquare1.business.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.ori.learnsquare1.business.entity.IntegrationRecordValue
import com.ori.learnsquare1.R
import com.ori.learnsquare1.common.ext.toDateTime

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/19 10:50
 * 类说明:
 */
class IntegrationRecordAdapter(list: MutableList<IntegrationRecordValue>) :
    BaseQuickAdapter<IntegrationRecordValue, BaseViewHolder>(R.layout.item_integration, list) {

    override fun convert(helper: BaseViewHolder, item: IntegrationRecordValue?) {
        item?.let {
            helper.setText(R.id.tv_reason, it.reason)
            helper.setText(R.id.tv_date, it.date.toDateTime("YYYY-MM-dd HH:mm:ss"))
            helper.setText(R.id.tv_integration, "+${it.coinCount}")
        }
    }
}