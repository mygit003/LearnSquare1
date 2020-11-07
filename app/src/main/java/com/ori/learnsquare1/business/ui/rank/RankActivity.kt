package com.ori.learnsquare1.business.ui.rank

import androidx.lifecycle.Observer
import com.chad.library.adapter.base.BaseQuickAdapter
import com.ori.learnsquare.business.entity.RankValue
import com.ori.learnsquare.business.entity.UserAccountValue
import com.ori.learnsquare1.R
import com.ori.learnsquare1.business.adapter.IntegrationRankAdapter
import com.ori.learnsquare1.common.base.activity.BaseDataBindingVMActivity
import com.ori.learnsquare1.common.util.Constant
import com.ori.learnsquare1.common.util.PrefUtils
import com.ori.learnsquare1.databinding.ActRankBinding
import kotlinx.android.synthetic.main.act_rank.*

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/25 10:17
 * 类说明:
 */
class RankActivity : BaseDataBindingVMActivity<ActRankBinding, RankViewModel>() {


    private var account: UserAccountValue? = null
    private var adapter: IntegrationRankAdapter? = null
    private val recordList: MutableList<RankValue> = mutableListOf()
    private var pageIndex = 0
    private var pageSize = 30
    private var hasNextPage = false

    override fun setViewModelClass(): Class<RankViewModel> {
        return RankViewModel::class.java
    }

    override fun setRootView(): Int {
        return R.layout.act_rank
    }

    override fun init() {
        initUI()
        initData()
    }


    private fun initUI() {
        iv_back.setOnClickListener { finish() }

        viewModel.apply {
            rankList.observe(this@RankActivity, Observer {
                refreshDone()
                it?.let {list ->
                    hasNextPage = list.size >= pageSize

                    recordList.addAll(list)
                    refreshListData()
                }
            })
        }

        viewDataBinding.srlRefresh.apply {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener {
                if (!recordList.isEmpty()) {
                    recordList.clear()
                }
                adapter?.notifyDataSetChanged()
                pageIndex = 0
                viewModel.getRankList(pageIndex)
            }
        }
    }


    private fun initData() {
        ltv_loading.loading()
        PrefUtils.getObject(Constant.SpKey.SP_INTEGRAL_INFO)?.let {
            account = it as UserAccountValue
        }

        if (null != account) {
            viewDataBinding.account = account
        }


        viewModel.getRankList(pageIndex)
    }


    private fun refreshListData() {
        if (null == adapter) {
            adapter = BindItemAdapter.bindingList(viewDataBinding.rvList, recordList)

            adapter?.setOnLoadMoreListener(object : BaseQuickAdapter.RequestLoadMoreListener {
                override fun onLoadMoreRequested() {
                    if (hasNextPage) {
                        pageIndex++
                        viewModel.getRankList(pageIndex)
                    }
                }

            }, viewDataBinding.rvList)

            adapter?.apply {
                setOnLoadMoreListener(object : BaseQuickAdapter.RequestLoadMoreListener {
                    override fun onLoadMoreRequested() {
                        if (hasNextPage) {
                            pageIndex++
                            viewModel.getRankList(pageIndex)
                        }
                    }

                }, viewDataBinding.rvList)
            }
        }

        if (hasNextPage) {
            adapter?.loadMoreComplete()
        }else {
            adapter?.loadMoreEnd()
        }
    }

    private fun refreshDone() {
        viewDataBinding.ltvLoading.dismiss()
        viewDataBinding.srlRefresh.isRefreshing = false
    }
}