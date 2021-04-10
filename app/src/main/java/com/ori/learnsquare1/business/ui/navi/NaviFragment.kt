package com.ori.learnsquare1.business.ui.navi

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.donkingliang.labels.LabelsView
import com.ori.learnsquare1.business.entity.ArticleValue
import com.ori.learnsquare1.business.entity.NavigationValue
import com.ori.learnsquare1.R
import com.ori.learnsquare1.business.ui.web.WebActivity
import com.ori.learnsquare1.common.base.fragment.BaseViewBindingVMFragment
import com.ori.learnsquare1.common.util.Constant
import com.ori.learnsquare1.common.util.JsonUtil
import com.ori.learnsquare1.databinding.FrgNaviBinding

/**
 * 创建人 zhengpf
 * 时间 2020/7/1
 * 类说明:导航
 */
class NaviFragment : BaseViewBindingVMFragment<FrgNaviBinding, NaviViewModel>() {

    private val items: MutableList<NavigationValue> = mutableListOf()
    private var mAdapter: BaseQuickAdapter<NavigationValue, BaseViewHolder>? = null
    private var currentPosition = 0

    override fun setRootView(): Int {
        return R.layout.frg_navi
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initView() {
        viewModel.run {
            naviItems.observe(viewLifecycleOwner, Observer {
                viewBinding.ltvLoading.dismiss()
                it?.let {
                    items.addAll(it)
                    viewBinding.tvFloatTitle.visibility = View.VISIBLE
                    viewBinding.tvFloatTitle.text = items[0].name
                }
                showItemData()
            })
        }

        viewBinding.rvList.setOnScrollChangeListener(object : View.OnScrollChangeListener{
            override fun onScrollChange(
                v: View?,
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int
            ) {
                if (scrollX < oldScrollY) {
                    viewBinding.tvFloatTitle.text = mAdapter?.getItem(currentPosition)?.name
                }

                val lm = viewBinding.rvList.layoutManager as LinearLayoutManager
                //获取下一个item rootView
                val nextItemView = lm.findViewByPosition(currentPosition + 1)
                if (null != nextItemView) {
                    viewBinding.tvFloatTitle.y = if (nextItemView.top < viewBinding.tvFloatTitle.measuredHeight) {
                        (nextItemView.top - viewBinding.tvFloatTitle.measuredHeight).toFloat()
                    } else {
                        0f
                    }
                }
                currentPosition = lm.findFirstVisibleItemPosition()
                if (scrollY > oldScrollY) {
                    viewBinding.tvFloatTitle.text = mAdapter?.getItem(currentPosition)?.name
                }
            }

        })
    }

    override fun initData() {
        viewBinding.ltvLoading.loading()
        viewModel.getNavigationItem()

    }

    override fun setViewModelClass(): Class<NaviViewModel> {
        return NaviViewModel::class.java
    }


    private fun showItemData() {
        if (null == mAdapter) {
            mAdapter = object : BaseQuickAdapter<NavigationValue, BaseViewHolder>(R.layout.item_tag_layout, items) {
                override fun convert(helper: BaseViewHolder, item: NavigationValue) {
                    bindItemData(helper, item)
                }

            }
            viewBinding.rvList.adapter = mAdapter
        }else {
            mAdapter?.setNewData(items)
        }
    }


    private fun bindItemData(holder: BaseViewHolder, item: NavigationValue) {
        item?.let {
            holder.setText(R.id.tvTitle, it.name)
            holder.getView<LabelsView>(R.id.lv_label).apply {
                setLabels(it.articles, object : LabelsView.LabelTextProvider<ArticleValue.DatasBean>{
                    override fun getLabelText(
                        label: TextView?,
                        position: Int,
                        data: ArticleValue.DatasBean?
                    ): CharSequence {
                        return data?.let { it.title } as CharSequence
                    }

                })

                setOnLabelClickListener { label, data, position ->
                    var datasBean = it.articles?.get(position)
                    var bundle = Bundle().apply {
                        putString(Constant.WebParam.PARAM_TITLE, datasBean?.title)
                        putString(Constant.WebParam.PARAM_URL, datasBean?.link)
                        putString(Constant.WebParam.PARAM_ITEM, JsonUtil.toJson(datasBean))
                    }
                    toActivity(WebActivity::class.java, bundle)
                }
            }
        }
    }
}