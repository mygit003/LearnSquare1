package com.ori.learnsquare1.business.ui.system.item

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.chad.library.adapter.base.BaseQuickAdapter
import com.ori.learnsquare1.business.entity.ArticleValue
import com.ori.learnsquare1.business.entity.SystemValue
import com.ori.learnsquare1.business.entity.TabValue
import com.ori.learnsquare1.R
import com.ori.learnsquare1.business.adapter.ArticleAdapter
import com.ori.learnsquare1.business.adapter.ItemAdapter
import com.ori.learnsquare1.business.ui.web.WebActivity
import com.ori.learnsquare1.common.base.fragment.BaseVMFragment
import com.ori.learnsquare1.common.base.fragment.BaseViewBindingVMFragment
import com.ori.learnsquare1.common.util.Constant
import com.ori.learnsquare1.common.util.JsonUtil
import com.ori.learnsquare1.databinding.FrgItemBinding

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/12 11:02
 * 类说明:
 */
class ItemFragment : BaseViewBindingVMFragment<FrgItemBinding, ItemViewModel>() {


    private var itemList: ArrayList<SystemValue.ItemValue> = arrayListOf()

    private var tabList: MutableList<TabValue> = mutableListOf()
    private var tabAdapter: ItemAdapter? = null

    private var articles = mutableListOf<ArticleValue.DatasBean>()
    private var articleAdapter: ArticleAdapter? = null

    private var pageIndex = 0
    private var curCateId = 0
    private var pageSize = 20
    private var hasNextPage = false
    private var itemIndex = 0



    override fun initView() {
        arguments?.let {
            itemList = it.getParcelableArrayList("cateList")!!
        }

        viewModel.apply {
            articleList.observe(viewLifecycleOwner, Observer {
                viewBinding.ltvLoading.dismiss()
                refreshComplete()
                if (pageIndex == 0) {
                    if (!articles.isEmpty()) {
                        articles.clear()
                    }
                }

                if (!it.isEmpty()) {
                    hasNextPage = it.size >= pageSize
                    articles.addAll(it)
                    Log.w(TAG, "articleAdapter:${articleAdapter.toString()}")
                    articleAdapter?.setNewData(articles)
                }else {
                    hasNextPage = false
                }

                if (hasNextPage) {
                    articleAdapter?.loadMoreComplete()
                }else {
                    articleAdapter?.loadMoreEnd()
                }
            })

            collectStatus.observe(viewLifecycleOwner, Observer {
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
        }

        viewBinding.srlRefresh.apply {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener {
                if (!articles.isEmpty()) {
                    articles.clear()
                }
                articleAdapter?.notifyDataSetChanged()
                pageIndex = 0
                viewModel.getSystemArticle(pageIndex, curCateId)
            }
        }
    }

    override fun initData() {
        viewBinding.ltvLoading.loading()
        Log.w(TAG, "hasNextPage:${hasNextPage}")
        if (!tabList.isEmpty()) {
            tabList.clear()
        }

        itemList?.forEach {
            var tv = TabValue().apply {
                id = it.id
                name = it.name
            }
            tabList.add(tv)
        }
        if (!tabList.isEmpty()) {
            tabList[0].checked = true
            curCateId = tabList[0].id
            viewModel.getSystemArticle(pageIndex, curCateId)
        }
        bindTabList()
        bindArticlesList()
    }




    override fun setViewModelClass(): Class<ItemViewModel> {
        return ItemViewModel::class.java
    }

    override fun setRootView(): Int {
        return R.layout.frg_item
    }

    private fun bindTabList() {
        if (null == tabAdapter) {
            tabAdapter = ItemAdapter(R.layout.tag_item_layout, tabList)
            tabAdapter?.setOnItemClickListener { adapter, view, position ->
                refreshTabSelect(position)
                var tv = adapter.getItem(position) as TabValue
                curCateId = tv.id
                pageIndex = 0
                viewModel.getSystemArticle(pageIndex, curCateId)
            }
        }

        viewBinding.rvTabList.adapter = tabAdapter
    }

    private fun bindArticlesList() {
        articleAdapter = ArticleAdapter(articles)

        articleAdapter?.run {
            setEnableLoadMore(true)
            setOnItemChildClickListener { adapter, view, position ->
                when(view.id) {
                    R.id.iv_collect -> {
                        //Toast.makeText(activity, "点击了:$position" + "项 收藏", Toast.LENGTH_SHORT).show()
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

            setOnItemClickListener { adapter, view, position ->
                //Toast.makeText(activity, "点击了:$position" + "项", Toast.LENGTH_SHORT).show()
                var datasBean = articles[position]
                var bundle = Bundle().apply {
                    putString(Constant.WebParam.PARAM_TITLE, datasBean?.title)
                    putString(Constant.WebParam.PARAM_URL, datasBean?.link)
                    putString(Constant.WebParam.PARAM_ITEM, JsonUtil.toJson(datasBean))
                }
                toActivity(WebActivity::class.java, bundle)
            }


            setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
                if (hasNextPage) {
                    Log.w(TAG, "onRefresh")
                    pageIndex++
                    viewModel.getSystemArticle(pageIndex, curCateId)
                }
            }, viewBinding.rvList)
        }
//        if (null == articleAdapter) {
//
//        }


        viewBinding.rvList.adapter = articleAdapter
    }


    private fun refreshTabSelect(position: Int) {
        tabList.indices.forEach {
            tabList[it].checked = position == it
        }
        tabAdapter?.notifyDataSetChanged()
    }

    private fun refreshComplete() {
        viewBinding.srlRefresh.isRefreshing = false
    }
}