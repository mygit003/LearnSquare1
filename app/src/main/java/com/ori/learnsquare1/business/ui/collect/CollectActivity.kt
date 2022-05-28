package com.ori.learnsquare1.business.ui.collect

import android.os.Bundle
import androidx.lifecycle.Observer
import com.ori.learnsquare1.business.entity.ArticleValue
import com.ori.learnsquare1.R
import com.ori.learnsquare1.business.adapter.ArticleAdapter
import com.ori.learnsquare1.business.ui.web.WebActivity
import com.ori.learnsquare1.business.ui.rank.BindItemAdapter
import com.ori.learnsquare1.common.base.activity.BaseDataBindingVMActivity
import com.ori.learnsquare1.common.util.Constant
import com.ori.learnsquare1.common.util.JsonUtil
import com.ori.learnsquare1.databinding.ActCollectBinding

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/25 16:30
 * 类说明:
 */
class CollectActivity : BaseDataBindingVMActivity<ActCollectBinding, CollectViewModel>() {

    private var adapter: ArticleAdapter? = null
    private var articles: MutableList<ArticleValue.DatasBean> = mutableListOf()
    private var pageIndex = 0
    private var pageSize = 20
    private var hasNextPage = false
    private var itemIndex = 0


    override fun setViewModelClass(): Class<CollectViewModel> {
        return CollectViewModel::class.java
    }

    override fun setRootView(): Int {
        return R.layout.act_collect
    }

    override fun init() {
        initUI()
        initData()
    }


    private fun initUI() {
        viewDataBinding.ivBack.setOnClickListener { finish() }

        viewModel.apply {
            articleList.observe(this@CollectActivity, Observer {
                refreshDone()
                if (null != it && !it.isEmpty()) {
                    hasNextPage = it.size >= pageSize
                    articles.addAll(it)
                    refreshListData()

                }else {
                    hasNextPage = false
                    adapter?.notifyDataSetChanged()
                    if (pageIndex == 0) {
                        viewDataBinding.ltvLoading.showEmpty()
                    }
                }
            })

            collectStatus.observe(this@CollectActivity, Observer {
                it?.let {
                    if (it) {
                        showToast("添加收藏成功")
                    }else {
                        showToast("取消收藏成功")
                    }
//                    adapter?.getItem(itemIndex)?.collect = it
//                    adapter?.notifyItemChanged(itemIndex)
                    loadList()
                }
            })
        }

        viewDataBinding.srlRefresh.apply {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener {
                loadList()
            }
        }

    }


    private fun initData() {
        viewDataBinding.ltvLoading.loading()
        viewModel.getCollectList(pageIndex)
    }

    private fun refreshDone() {
        viewDataBinding.ltvLoading.dismiss()
        viewDataBinding.srlRefresh.isRefreshing = false
    }

    private fun refreshListData() {
        if (null == adapter) {
            adapter = BindItemAdapter.bindArticleList(viewDataBinding.rvList, articles)

            adapter?.apply {
                loadMoreModule.isEnableLoadMore = true
                loadMoreModule.setOnLoadMoreListener {
                    if (hasNextPage) {
                        pageIndex++
                        viewModel.getCollectList(pageIndex)
                    }
                }

                setOnItemClickListener { adapter, view, position ->
                    var datasBean = articles.get(position)
                    var bundle = Bundle().apply {
                        putString(Constant.WebParam.PARAM_TITLE, datasBean?.title)
                        putString(Constant.WebParam.PARAM_URL, datasBean?.link)
                        putString(Constant.WebParam.PARAM_ITEM, JsonUtil.toJson(datasBean))
                    }
                    toActivity(WebActivity::class.java, bundle)
                }

                setOnItemChildClickListener { adapter, view, position ->
                    when(view.id) {
                        R.id.iv_collect -> {
                            itemIndex = position
                            var datasBean = articles.get(position)
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
            }
        }else {
            adapter?.setNewData(articles)
        }

        if (hasNextPage) {
            adapter?.loadMoreModule?.loadMoreComplete()
        }else {
            adapter?.loadMoreModule?.loadMoreEnd()
        }
    }

    private fun loadList() {
        if (!articles.isEmpty()) {
            articles.clear()
        }
        adapter?.notifyDataSetChanged()
        pageIndex = 0
        viewModel.getCollectList(pageIndex)
    }
}