package com.ori.learnsquare1.business.ui.system

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.ori.learnsquare1.business.entity.SystemValue
import com.ori.learnsquare1.R
import com.ori.learnsquare1.business.adapter.FragmentTitleListAdapter
import com.ori.learnsquare1.business.ui.system.item.ItemFragment
import com.ori.learnsquare1.common.base.fragment.BaseVMFragment
import com.ori.learnsquare1.common.base.fragment.BaseViewBindingVMFragment
import com.ori.learnsquare1.databinding.FrgSystemBinding

/**
 * 创建人 zhengpf
 * 时间 2020/7/1
 * 类说明:体系
 */
class SystemFragment : BaseViewBindingVMFragment<FrgSystemBinding, SystemViewModel>() {

    private var fragments: MutableList<Fragment> = mutableListOf()
    private var titles: MutableList<String> = mutableListOf()
    private var itemList: MutableList<SystemValue> = mutableListOf()


    override fun setRootView(): Int {
        return R.layout.frg_system
    }

    override fun initView() {
        viewModel.apply {
            tabList.observe(viewLifecycleOwner, Observer {
                it?.let { list ->
                    itemList.addAll(list)
                    viewBinding.ivFilter.visibility = View.VISIBLE
                    viewBinding.tlLayout.visibility = View.VISIBLE
                    viewBinding.vpList.visibility = View.VISIBLE
                    showTabList()
                }
            })

            loadingStatus.observe(viewLifecycleOwner, Observer {
                viewBinding.cpbLoading.isVisible = it
            })
        }
    }

    override fun initData() {
        viewModel.getTabList()

    }

    override fun setViewModelClass(): Class<SystemViewModel> {
        return SystemViewModel::class.java
    }

    private fun showTabList() {
        itemList.forEach {
            it.name?.let { it1 -> titles.add(it1) }

            var fragment = ItemFragment()
            var bundle = Bundle()
            bundle.putParcelableArrayList("cateList", it.children)
            fragment.arguments = bundle
            fragments.add(fragment)
        }

        viewBinding.vpList.adapter = FragmentTitleListAdapter(fragments, titles,  childFragmentManager)
        viewBinding.vpList.offscreenPageLimit = 6
        viewBinding.tlLayout.setupWithViewPager(viewBinding.vpList)
    }
}