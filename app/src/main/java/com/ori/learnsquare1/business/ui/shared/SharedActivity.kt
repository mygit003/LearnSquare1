package com.ori.learnsquare1.business.ui.shared

import android.os.Bundle
import androidx.lifecycle.Observer
import com.chad.library.adapter.base.BaseQuickAdapter
import com.ori.learnsquare.business.entity.ArticleValue
import com.ori.learnsquare1.R
import com.ori.learnsquare1.business.adapter.ArticleAdapter
import com.ori.learnsquare1.business.ui.web.WebActivity
import com.ori.learnsquare1.business.ui.rank.BindItemAdapter
import com.ori.learnsquare1.common.base.activity.BaseDataBindingVMActivity
import com.ori.learnsquare1.common.util.Constant
import com.ori.learnsquare1.common.util.JsonUtil
import com.ori.learnsquare1.databinding.ActSharedBinding
import kotlinx.android.synthetic.main.act_shared.*

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/25 15:52
 * 类说明:
 */
class SharedActivity : BaseDataBindingVMActivity<ActSharedBinding, SharedViewModel>() {


    private var adapter: ArticleAdapter? = null
    private val articles: MutableList<ArticleValue.DatasBean> = mutableListOf()
    private var pageIndex = 0
    private var pageSize = 30
    private var hasNextPage = false
    private var itemIndex = 0


    override fun setViewModelClass(): Class<SharedViewModel> {
        return SharedViewModel::class.java
    }

    override fun setRootView(): Int {
        return R.layout.act_shared
    }

    override fun init() {
        initUI()
        initData()
    }


    private fun initUI() {
        viewDataBinding.ivBack.setOnClickListener { finish() }

        viewModel.apply {
            articleList.observe(this@SharedActivity, Observer {
                refreshDone()
                if (null != it && !it.isEmpty()) {
                    hasNextPage = it.size >= pageSize
                    articles.addAll(it)
                    refreshListData()
                }else {
                    hasNextPage = false
                    adapter?.notifyDataSetChanged()
                    viewDataBinding.ltvLoading.showEmpty()
                }

            })

            collectStatus.observe(this@SharedActivity, Observer {
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
        }

        viewDataBinding.srlRefresh.apply {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener {
                if (!articles.isEmpty()) {
                    articles.clear()
                }
                adapter?.notifyDataSetChanged()
                pageIndex = 0
                viewModel.getMyArticles(pageIndex)
            }
        }
    }


    private fun initData() {
        viewDataBinding.ltvLoading.loading()
        viewModel.getMyArticles(pageIndex)
    }

    private fun refreshDone() {
        viewDataBinding.srlRefresh.isRefreshing = false
        viewDataBinding.ltvLoading.dismiss()
    }

    private fun refreshListData() {
        if (null == adapter) {
            adapter = BindItemAdapter.bindArticleList(viewDataBinding.rvList, articles)

            adapter?.apply {
                setOnLoadMoreListener(object : BaseQuickAdapter.RequestLoadMoreListener{
                    override fun onLoadMoreRequested() {
                        if (hasNextPage) {
                            pageIndex++
                            viewModel.getMyArticles(pageIndex)
                        }
                    }

                }, viewDataBinding.rvList)

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
            adapter?.loadMoreComplete()
        }else {
            adapter?.loadMoreEnd()
        }
    }
}