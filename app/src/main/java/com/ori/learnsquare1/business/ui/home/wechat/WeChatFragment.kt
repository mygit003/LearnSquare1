package com.ori.learnsquare1.business.ui.home.wechat

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.chad.library.adapter.base.BaseQuickAdapter
import com.ori.learnsquare1.business.entity.ArticleValue
import com.ori.learnsquare1.business.entity.TabValue
import com.ori.learnsquare1.R
import com.ori.learnsquare1.business.adapter.ArticleAdapter
import com.ori.learnsquare1.business.adapter.ItemAdapter
import com.ori.learnsquare1.business.ui.web.WebActivity
import com.ori.learnsquare1.common.base.fragment.BaseViewBindingVMFragment
import com.ori.learnsquare1.common.util.Constant
import com.ori.learnsquare1.common.util.JsonUtil
import com.ori.learnsquare1.databinding.FrgWechatBinding

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/11 12:18
 * 类说明:
 */
class WeChatFragment : BaseViewBindingVMFragment<FrgWechatBinding, WechatViewModel>() {


    private var tabList = mutableListOf<TabValue>()
    private var tabAdapter: ItemAdapter? = null

    private var articleList = mutableListOf<ArticleValue.DatasBean>()
    private var articleAdapter: ArticleAdapter? = null

    private var pageIndex = 1
    private var curCateId = 0
    private var pageSize = 20
    private var hasNextPage = false
    private var itemIndex = 0

    override fun setRootView(): Int {
        return R.layout.frg_wechat
    }

    override fun initView() {
        bindTabList()
        bindListData()

        viewModel.tabList.observe(viewLifecycleOwner, Observer {
            it?.let { list ->
                tabList.addAll(list)
                tabList[0].checked = true
                curCateId = tabList[0].id
                viewModel.getWeChatArticlesList(pageIndex, curCateId)
            }
            tabAdapter?.setNewData(tabList)
        })

        viewModel.articleList.observe(viewLifecycleOwner, Observer {
            completeRefresh()

            if (pageIndex == 1) {
                if (!articleList.isEmpty()) {
                    articleList.clear()
                }
                articleAdapter?.notifyDataSetChanged()
            }
            if (!it.isEmpty()) {
                hasNextPage = it.size >= pageSize
                articleList.addAll(it)
                articleAdapter?.setNewData(articleList)
            }else {
                hasNextPage = false
            }

            if (hasNextPage) {
                articleAdapter?.loadMoreComplete()
            }else {
                articleAdapter?.loadMoreEnd()
            }

        })

        viewModel.collectStatus.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    showToast("添加收藏成功")
                }else {
                    showToast("取消收藏成功")
                }
                articleAdapter?.getItem(itemIndex)?.collect = it
                articleAdapter?.notifyItemChanged(itemIndex)
            }
        })


        viewBinding.srlRefresh.apply {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener {
                if (!articleList.isEmpty()) {
                    articleList.clear()
                }
                articleAdapter?.notifyDataSetChanged()
                pageIndex = 1
                viewModel.getWeChatArticlesList(pageIndex, curCateId)
            }
        }
    }

    override fun initData() {
        viewModel.getItemTab()
    }

    override fun setViewModelClass(): Class<WechatViewModel> {
        return WechatViewModel::class.java
    }


    private fun bindTabList() {
        if (null == tabAdapter) {
            tabAdapter = ItemAdapter(R.layout.tag_item_layout, tabList)

            viewBinding.rvTabList.adapter = tabAdapter
        }

        tabAdapter?.run {
            setOnItemClickListener { adapter, view, position ->
                refreshTabSelect(position)
                val tv = adapter.getItem(position) as TabValue
                curCateId = tv.id
                pageIndex = 1
                viewModel.getWeChatArticlesList(pageIndex, curCateId)
            }
        }
    }

    private fun refreshTabSelect(position: Int) {
        tabList.indices.forEach {
            tabList[it].checked = it == position
        }
        tabAdapter?.notifyDataSetChanged()
    }


    private fun completeRefresh() {
        viewBinding.srlRefresh.isRefreshing = false
    }


    private fun bindListData() {
        if (null == articleAdapter) {
            articleAdapter = ArticleAdapter(articleList)

            viewBinding.rvList.adapter = articleAdapter
        }

        articleAdapter?.run {
            setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
                if (hasNextPage) {
                    Log.w(TAG, "onRefresh")
                    pageIndex++
                    viewModel.getWeChatArticlesList(pageIndex, curCateId)
                }

            }, viewBinding.rvList)

            setOnItemChildClickListener { adapter, view, position ->
                when (view.id) {
                    R.id.iv_collect -> {
                        //Toast.makeText(activity, "点击了:$position" + "项 收藏", Toast.LENGTH_SHORT).show()
                        itemIndex = position
                        var datasBean = articleList.get(position)
                        datasBean?.let {
                            if (it.collect) {
                                viewModel.unCollect(it.id)
                            }else {
                                viewModel.collect(it.id)
                            }
                        }
                    }
                }
            }

            setOnItemClickListener { adapter, view, position ->
//                Toast.makeText(activity, "点击了:$position" + "项", Toast.LENGTH_SHORT).show()
                var datasBean = articleList?.get(position)
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