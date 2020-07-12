package com.ori.learnsquare1.business.ui.home.square

import android.widget.Toast
import androidx.lifecycle.Observer
import com.chad.library.adapter.base.BaseQuickAdapter
import com.ori.learnsquare.business.entity.ArticleValue
import com.ori.learnsquare1.R
import com.ori.learnsquare1.business.adapter.ArticleAdapter
import com.ori.learnsquare1.common.base.fragment.BaseVMFragment
import kotlinx.android.synthetic.main.frg_square.*

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/11 12:17
 * 类说明:首页--广场
 */
class SquareFragment : BaseVMFragment<SquareViewModel>() {

    private var articleList = mutableListOf<ArticleValue.DatasBean>()
    private var pageIndex = 0
    private var pageSize = 20
    private var adapter: ArticleAdapter? = null
    private var hasNextPage = false


    override fun setRootView(): Int {
        return R.layout.frg_square
    }

    override fun initView() {
        bindListData()

        viewModel.articleList.observe(viewLifecycleOwner, Observer {
            srl_refresh.isRefreshing = false
            it?.let { list ->
                hasNextPage = list.size >= pageSize
                articleList.addAll(list)
            }
            adapter?.setNewData(articleList)

            if (hasNextPage) {
                adapter?.loadMoreComplete()
            }else {
                adapter?.loadMoreEnd()
            }
        })

        srl_refresh.setOnRefreshListener {
            if (!articleList.isEmpty()) {
                articleList.clear()
            }
            adapter?.notifyDataSetChanged()
            pageIndex = 0
            viewModel.getSquareArticlesList(pageIndex)
        }
    }

    override fun initData() {
        viewModel.getSquareArticlesList(pageIndex)
    }

    override fun setViewModelClass(): Class<SquareViewModel> {
        return SquareViewModel::class.java
    }


    private fun bindListData() {
        if (null == adapter) {
            adapter = ArticleAdapter(articleList)

            rv_list.adapter = adapter
        }

        adapter?.run {
            setEnableLoadMore(true)
            setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
                pageIndex++
                viewModel.getSquareArticlesList(pageIndex)

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