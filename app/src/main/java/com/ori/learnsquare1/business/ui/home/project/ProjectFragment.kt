package com.ori.learnsquare1.business.ui.home.project

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import com.chad.library.adapter.base.BaseQuickAdapter
import com.ori.learnsquare.business.entity.ArticleValue
import com.ori.learnsquare.business.entity.TabValue
import com.ori.learnsquare1.R
import com.ori.learnsquare1.business.adapter.ArticleAdapter
import com.ori.learnsquare1.business.adapter.ItemAdapter
import com.ori.learnsquare1.common.base.fragment.BaseVMFragment
import kotlinx.android.synthetic.main.frg_project.*

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/11 12:18
 * 类说明:首页--项目
 */
class ProjectFragment : BaseVMFragment<ProjectViewModel>() {


    private var tabList = mutableListOf<TabValue>()
    private var tabAdapter: ItemAdapter? = null

    private var articleList = mutableListOf<ArticleValue.DatasBean>()
    private var articleAdapter: ArticleAdapter? = null

    private var pageIndex = 1
    private var curCateId = 0
    private var pageSize = 15
    private var hasNextPage = false


    override fun setRootView(): Int {
        return R.layout.frg_project
    }

    override fun initView() {
        bindTabList()
        bindListData()

        viewModel.tabList.observe(viewLifecycleOwner, Observer {
            it?.let { list ->
                tabList.addAll(list)
                tabList[0].checked = true
                curCateId = tabList[0].id
                viewModel.getProjectArticlesList(pageIndex, curCateId)
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

        srl_refresh.setOnRefreshListener {
            pageIndex = 1
            viewModel.getProjectArticlesList(pageIndex, curCateId)
        }
    }

    override fun initData() {
        viewModel.getItemTab()
    }

    override fun setViewModelClass(): Class<ProjectViewModel> {
        return ProjectViewModel::class.java
    }

    private fun bindTabList() {
        if (null == tabAdapter) {
            tabAdapter = ItemAdapter(R.layout.tag_item_layout, tabList)

            rv_tab_list.adapter = tabAdapter
        }

        tabAdapter?.run {
            setOnItemClickListener { adapter, view, position ->
                refreshTabSelect(position)
                val tv = adapter.getItem(position) as TabValue
                curCateId = tv.id
                pageIndex = 1
                viewModel.getProjectArticlesList(pageIndex, curCateId)
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
        srl_refresh.isRefreshing = false
    }


    private fun bindListData() {
        if (null == articleAdapter) {
            articleAdapter = ArticleAdapter(articleList)

            rv_list.adapter = articleAdapter
        }

        articleAdapter?.run {
            setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
                if (hasNextPage) {
                    Log.w(TAG, "onRefresh")
                    pageIndex++
                    viewModel.getProjectArticlesList(pageIndex, curCateId)
                }

            }, rv_list)

            setOnItemChildClickListener { adapter, view, position ->
                when (view.id) {
                    R.id.iv_collect -> {
                        Toast.makeText(activity, "点击了:$position" + "项 收藏", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }

            setOnItemClickListener { adapter, view, position ->
                Toast.makeText(activity, "点击了:$position" + "项", Toast.LENGTH_SHORT).show()
            }
        }
    }
}