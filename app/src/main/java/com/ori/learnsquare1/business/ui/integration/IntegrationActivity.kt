package com.ori.learnsquare1.business.ui.integration

import android.os.Bundle
import androidx.lifecycle.Observer
import com.chad.library.adapter.base.BaseQuickAdapter
import com.ori.learnsquare1.business.entity.IntegrationRecordValue
import com.ori.learnsquare1.business.entity.UserAccountValue
import com.ori.learnsquare1.R
import com.ori.learnsquare1.business.adapter.IntegrationRecordAdapter
import com.ori.learnsquare1.business.ui.web.WebActivity
import com.ori.learnsquare1.common.base.activity.BaseDataBindingVMActivity
import com.ori.learnsquare1.common.util.Constant
import com.ori.learnsquare1.common.util.PrefUtils
import com.ori.learnsquare1.databinding.ActIntegrationBinding
import kotlinx.android.synthetic.main.act_integration.*

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/19 9:30
 * 类说明:
 */
class IntegrationActivity : BaseDataBindingVMActivity<ActIntegrationBinding, IntegrationViewModel>() {


    private var account: UserAccountValue? = null
    private val recordList: MutableList<IntegrationRecordValue> = mutableListOf()
    private var mAdapter: IntegrationRecordAdapter? = null
    private val pageSize = 20
    private var pageIndex = 1
    private var hasNextPage = false

    override fun setRootView(): Int {
        return R.layout.act_integration
    }



    override fun init() {
        initListener()
        initData()
    }

    private fun initListener() {
        iv_back.setOnClickListener {
            finish()
        }

        iv_rule.setOnClickListener {
            // 查看积分规则
            toActivity(WebActivity::class.java, Bundle().apply {
                putString(Constant.WebParam.PARAM_URL, Constant.WebUrl.INTEGRAL_RULE)
                putString(Constant.WebParam.PARAM_TITLE, "积分规则")
            })
        }

        srl_refresh.apply {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener {
                if (!recordList.isEmpty()) {
                    recordList.clear()
                }
                mAdapter?.notifyDataSetChanged()
                pageIndex = 1
                viewModel.getIntegrationRecord(pageIndex)
            }
        }
    }

    private fun initData() {
        bindList()
        viewModel.apply {
            userAccount.observe(this@IntegrationActivity, Observer {
                it?.let {account ->
                    viewDataBinding.account = account
                    PrefUtils.setObject(Constant.SpKey.SP_INTEGRAL_INFO, account)
            }
            })

            integrationList.observe(this@IntegrationActivity, Observer {
                srl_refresh.isRefreshing = false
                ltv_loading.dismiss()
                if (null != it) {
                    if (!it.isEmpty()) {
                        recordList.addAll(it)
                        mAdapter?.setNewData(recordList)
                        if (it.size < pageSize) {
                            mAdapter?.loadMoreEnd()
                            hasNextPage = false
                        }else {
                            mAdapter?.loadMoreComplete()
                            hasNextPage = true
                        }
                    }else {
                        hasNextPage = false
                        mAdapter?.loadMoreEnd()
                    }
                }
            })
        }

        ltv_loading.loading()

        //显示积分数据
        PrefUtils.getObject(Constant.SpKey.SP_INTEGRAL_INFO).let {
            account = it as UserAccountValue
        }

        if (null == account) {
            viewModel.getUserAccount()
        }else {
            viewDataBinding.account = account
        }
        //获取积分签到记录
        viewModel.getIntegrationRecord(pageIndex)
    }

    override fun setViewModelClass(): Class<IntegrationViewModel> {
        return IntegrationViewModel::class.java
    }


    private fun bindList() {
        if (null == mAdapter) {
            mAdapter = IntegrationRecordAdapter(recordList);

            rv_list.adapter = mAdapter

            mAdapter?.setOnLoadMoreListener(object : BaseQuickAdapter.RequestLoadMoreListener{
                override fun onLoadMoreRequested() {
                    if (hasNextPage) {
                        pageIndex++
                        viewModel.getIntegrationRecord(pageIndex)
                    }
                }

            }, rv_list)
        }
    }
}