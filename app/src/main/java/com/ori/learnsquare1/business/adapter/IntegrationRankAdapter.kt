package com.ori.learnsquare1.business.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.ori.learnsquare.business.entity.RankValue
import com.ori.learnsquare1.R

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/19 10:50
 * 类说明:
 */
class IntegrationRankAdapter(list: MutableList<RankValue>) :
    BaseQuickAdapter<RankValue, BaseViewHolder>(R.layout.item_rank, list) {

    override fun convert(helper: BaseViewHolder, item: RankValue?) {
        item?.let {
            helper.setText(R.id.tv_rank, "${helper.adapterPosition + 1}")
            helper.setText(R.id.tv_rank_user_name, it.username)
            helper.setText(R.id.tv_integration, it.coinCount.toString())
        }
    }
}