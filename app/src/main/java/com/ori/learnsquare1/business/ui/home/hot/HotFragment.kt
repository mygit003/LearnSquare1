package com.ori.learnsquare1.business.ui.home.hot

import android.os.Bundle
import androidx.lifecycle.Observer
import com.chad.library.adapter.base.BaseQuickAdapter
import com.ori.learnsquare1.business.entity.ArticleValue
import com.ori.learnsquare1.R
import com.ori.learnsquare1.business.adapter.ArticleAdapter
import com.ori.learnsquare1.business.ui.web.WebActivity
import com.ori.learnsquare1.common.base.fragment.BaseViewBindingVMFragment
import com.ori.learnsquare1.common.util.Constant
import com.ori.learnsquare1.common.util.JsonUtil
import com.ori.learnsquare1.databinding.FrgHotBinding

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/11 11:12
 * 类说明:首页--热门
 */
class HotFragment : BaseViewBindingVMFragment<FrgHotBinding, HotViewModel>() {

    private var articleList = mutableListOf<ArticleValue.DatasBean>()
    private var pageIndex = 0
    private var pageSize = 20
    private var adapter: ArticleAdapter? = null
    private var hasNextPage = false
    private var itemIndex = 0


    override fun initView() {
        bindListData()

        viewModel.articleList.observe(viewLifecycleOwner, Observer {
            viewBinding.srlRefresh.isRefreshing = false
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

        viewModel.collectStatus.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    showToast("添加收藏成功")
                }else {
                    showToast("取消收藏成功")
                }
                adapter?.getItem(itemIndex)?.collect = it
                adapter?.notifyItemChanged(itemIndex)
            }
        })

        viewBinding.srlRefresh.apply {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener {
                if (!articleList.isEmpty()) {
                    articleList.clear()
                }
                adapter?.notifyDataSetChanged()
                pageIndex = 0
                viewModel.getArticleList(pageIndex)
            }
        }

    }



    private fun bindListData() {
        if (null == adapter) {
            adapter = ArticleAdapter(articleList)

            viewBinding.rvList.adapter = adapter
        }

        adapter?.run {
            setEnableLoadMore(true)
            setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
                pageIndex++
                viewModel.getArticleList(pageIndex)

            }, viewBinding.rvList)

            setOnItemChildClickListener { adapter, view, position ->
                when(view.id) {
                    R.id.iv_collect -> {
                        itemIndex = position
                        //Toast.makeText(activity, "点击了:$position" + "项 收藏", Toast.LENGTH_SHORT).show()
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
                //Toast.makeText(activity, "点击了:$position" + "项", Toast.LENGTH_SHORT).show()
                var datasBean = articleList.get(position)
                var bundle = Bundle().apply {
                    putString(Constant.WebParam.PARAM_TITLE, datasBean?.title)
                    putString(Constant.WebParam.PARAM_URL, datasBean?.link)
                    putString(Constant.WebParam.PARAM_ITEM, JsonUtil.toJson(datasBean))
                }
                toActivity(WebActivity::class.java, bundle)
            }
        }
    }

    override fun initData() {
        viewModel.getArticleList(pageIndex)

    }

    override fun setViewModelClass(): Class<HotViewModel> {
        return HotViewModel::class.java
    }

    override fun setRootView(): Int {
        return R.layout.frg_hot
    }
}