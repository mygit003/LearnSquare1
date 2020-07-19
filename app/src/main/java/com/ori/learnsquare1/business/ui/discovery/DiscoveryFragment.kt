package com.ori.learnsquare1.business.ui.discovery

import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Observer
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.donkingliang.labels.LabelsView
import com.ori.learnsquare1.R
import com.ori.learnsquare1.business.entity.TagItemValue
import com.ori.learnsquare1.business.ui.WebActivity
import com.ori.learnsquare1.common.base.fragment.BaseFragment
import com.ori.learnsquare1.common.base.fragment.BaseVMFragment
import com.ori.learnsquare1.common.util.Constant
import com.ori.learnsquare1.common.util.JsonUtil
import kotlinx.android.synthetic.main.frg_discovery.*

/**
 * 创建人 zhengpf
 * 时间 2020/7/1
 * 类说明:发现
 */
class DiscoveryFragment : BaseVMFragment<DiscoveryViewModel>() {



    private var tagList: MutableList<TagItemValue> = mutableListOf()
    private var adapter: BaseQuickAdapter<TagItemValue, BaseViewHolder>? = null



    override fun setRootView(): Int {
        return R.layout.frg_discovery
    }

    override fun initView() {
        viewModel.apply {
            hotKey.observe(viewLifecycleOwner, Observer { list ->
                list?.let {
                    val itemList: MutableList<TagItemValue.ItemValue> = mutableListOf()
                    it.forEach { item ->
                        var iv = TagItemValue.ItemValue().apply {
                            id = item.id
                            name = item.name
                            link = item.link
                        }
                        itemList.add(iv)
                    }
                    var tiv = TagItemValue().apply {
                        title = "大家都在搜"
                        items = itemList
                    }
                    tagList.add(tiv)
                }
            })

            oftenSearchList.observe(viewLifecycleOwner, Observer { list ->
                list?.let {
                    val itemList: MutableList<TagItemValue.ItemValue> = mutableListOf()
                    it.forEach { item ->
                        var iv = TagItemValue.ItemValue().apply {
                            id = item.id
                            name = item.name
                            link = item.link
                        }
                        itemList.add(iv)
                    }
                    var tiv = TagItemValue().apply {
                        title = "常用网址"
                        items = itemList
                    }
                    tagList.add(tiv)
                    showTagList()
                }
            })
        }

    }

    private fun showTagList() {
        ltv_loading.dismiss()
        if (null == adapter) {
            adapter = object : BaseQuickAdapter<TagItemValue, BaseViewHolder>(R.layout.item_tag_layout, tagList) {
                override fun convert(helper: BaseViewHolder, item: TagItemValue) {
                    bindItemView(helper, item)
                }

            }

            rv_list.adapter = adapter
        }else {
            adapter?.setNewData(tagList)
        }
    }

    private fun bindItemView(holder: BaseViewHolder, item: TagItemValue) {
        item?.let {
            holder.setText(R.id.tvTitle, it.title)
            holder.getView<LabelsView>(R.id.lv_label).apply {
                setLabels(it.items, object : LabelsView.LabelTextProvider<TagItemValue.ItemValue>{
                    override fun getLabelText(
                        label: TextView?,
                        position: Int,
                        data: TagItemValue.ItemValue?
                    ): CharSequence {
                        return data?.let { it.name } as CharSequence
                    }

                })


                setOnLabelClickListener { label, data, position ->
                    var datasBean = it.items?.get(position)
                    var bundle = Bundle().apply {
                        putString(Constant.WebParam.PARAM_TITLE, datasBean?.name)
                        putString(Constant.WebParam.PARAM_URL, datasBean?.link)
                        putString(Constant.WebParam.PARAM_ITEM, JsonUtil.toJson(datasBean))
                    }
                    toActivity(WebActivity::class.java, bundle)
                }
            }

        }

    }

    override fun initData() {
        ltv_loading.loading()
        viewModel.getSearchItem()
    }

    override fun setViewModelClass(): Class<DiscoveryViewModel> {
        return DiscoveryViewModel::class.java
    }
}