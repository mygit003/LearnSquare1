package com.ori.learnsquare1.business.ui.history

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.chad.library.adapter.base.BaseQuickAdapter
import com.ori.learnsquare.business.entity.ArticleValue
import com.ori.learnsquare.business.entity.UserValue
import com.ori.learnsquare1.R
import com.ori.learnsquare1.business.adapter.ArticleAdapter
import com.ori.learnsquare1.business.db.entity.BrowseHistoryValue
import com.ori.learnsquare1.business.ui.rank.BindItemAdapter
import com.ori.learnsquare1.business.ui.web.WebActivity
import com.ori.learnsquare1.business.util.DialogUtils
import com.ori.learnsquare1.common.base.activity.BaseDataBindingVMActivity
import com.ori.learnsquare1.common.util.Constant
import com.ori.learnsquare1.common.util.DateUtil
import com.ori.learnsquare1.common.util.JsonUtil
import com.ori.learnsquare1.common.util.PrefUtils
import com.ori.learnsquare1.databinding.ActHistoryBinding
import kotlinx.android.synthetic.main.act_history.*

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/26 10:45
 * 类说明:
 */
class HistoryActivity : BaseDataBindingVMActivity<ActHistoryBinding, HistoryViewModel>() {


    private val articles: MutableList<ArticleValue.DatasBean> = mutableListOf()
    private var adapter: ArticleAdapter? = null
    private var pageIndex = 1
    private var pageSize = 10
    private var hasNextPage = false
    private var itemIndex = 0
    private var userValue: UserValue? = null

    override fun setViewModelClass(): Class<HistoryViewModel> {
        return HistoryViewModel::class.java
    }

    override fun setRootView(): Int {
        return R.layout.act_history
    }

    override fun init() {
        initUI()
        initData()
    }


    private fun initUI() {
        viewDataBinding.ivBack.setOnClickListener { finish() }
        viewDataBinding.tvClear.setOnClickListener {
            //清空数据
            viewModel.clearHistory()
        }

        viewModel.apply {
            historyList.observe(this@HistoryActivity, Observer {
                refreshDone()
                if (null != it && !it.isEmpty()) {
                    Log.w(TAG, "it:${it.size}")
                    hasNextPage = it.size >= pageSize
                    articles.addAll(it)
                    refresListData()
                }else {
                    hasNextPage = false
                    if (pageIndex == 1) {
                        viewDataBinding.ltvLoading.showEmpty()
                    }
                }
            })

            delHistoryResult.observe(this@HistoryActivity, Observer {
                if (null != it && it > 0) {
                    showToast("数据删除成功")
                    if (!articles.isEmpty()) {
                        articles.removeAt(itemIndex)
                    }
                    adapter?.notifyDataSetChanged()
                    if (articles.isEmpty()) {
                        viewDataBinding.ltvLoading.showEmpty()
                    }
                }
            })

            clearHistoryResult.observe(this@HistoryActivity, Observer {
                if (null != it && it > 0) {
                    showToast("数据清空成功")
                    if (!articles.isEmpty()) {
                        articles.clear()
                    }
                    adapter?.notifyDataSetChanged()
                    viewDataBinding.ltvLoading.showEmpty()
                }
            })

            collectStatus.observe(this@HistoryActivity, Observer {
                it?.let {
                    if (it) {
                        showToast("添加收藏成功")
                    }else {
                        showToast("取消收藏成功")
                    }
                    adapter?.getItem(itemIndex)?.collect = it
                    adapter?.notifyItemChanged(itemIndex)
                    //更新本地数据库数据
                    updateLocalArticleCollectStatus()
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
        viewModel.getHistoryList(pageSize, pageIndex)

        PrefUtils.getObject(Constant.SpKey.SP_USER_INFO)?.let {
            userValue = it as UserValue
        }
    }


    private fun refreshDone() {
        viewDataBinding.srlRefresh.isRefreshing = false
        viewDataBinding.ltvLoading.dismiss()
    }


    private fun refresListData() {
        if (null == adapter) {
            adapter = BindItemAdapter.bindArticleList(viewDataBinding.rvList, articles)

            adapter?.apply {
                setOnLoadMoreListener(object : BaseQuickAdapter.RequestLoadMoreListener{
                    override fun onLoadMoreRequested() {
                        if (hasNextPage) {
                            pageIndex++
                            viewModel.getHistoryList(pageSize, pageIndex)
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

                setOnItemLongClickListener { adapter, view, position ->
                    //显示对话框确认删除当前选择项
                    DialogUtils.confirm(this@HistoryActivity, "确认删除当前项？", object : DialogUtils.OnConfrimClickListener{
                        override fun onConfirmClick() {
                            itemIndex = position
                            deleteItem(position)
                        }

                    })

                    true
                }

                setOnItemChildClickListener { adapter, view, position ->
                    when(view.id) {
                        R.id.iv_collect -> {
                            showToast("暂未实现该功能")
//                            itemIndex = position
//                            var datasBean = articles.get(position)
//                            datasBean?.let {
//                                if (it.collect) {
//                                    viewModel.unCollect(it.id)
//                                }else {
//                                    viewModel.collect(it.id)
//                                }
//                            }
                        }
                    }
                }
            }
        }

        if (hasNextPage) {
            adapter?.loadMoreComplete()
        }else {
            adapter?.loadMoreEnd()
        }
    }

    private fun loadList() {
        if (!articles.isEmpty()) {
            articles.clear()
        }
        adapter?.notifyDataSetChanged()
        pageIndex = 1
        viewModel.getHistoryList(pageSize, pageIndex)
    }

    private fun updateLocalArticleCollectStatus() {
        var datasBean = articles.get(itemIndex)
        datasBean.collect = !datasBean.collect
        BrowseHistoryValue().apply {
            datasBean?.let {
                _id = it._id
                articleId = it.id
                userId = userValue?.id
                articleTitle = it.title
                articleAuthor = it.author
                articleChapter = it.superChapterName
                articleLink = it.link
                articleLinkPic = it.envelopePic
                articleCollect = if (it.collect ) 1 else 0
                articlePublishTime = it.niceDate
                browseTime = DateUtil.getCurTime()
                //保存数据
                viewModel.updateHistory(this)
            }
        }
    }


    private fun deleteItem(position: Int) {
        var datasBean = articles.get(position)
        viewModel.deleteHistoryById(datasBean._id)
    }
}