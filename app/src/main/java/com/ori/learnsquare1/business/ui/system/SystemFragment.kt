package com.ori.learnsquare1.business.ui.system

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.ori.learnsquare.business.entity.ArticleValue
import com.ori.learnsquare.business.entity.BannerValue
import com.ori.learnsquare.business.entity.SystemValue
import com.ori.learnsquare1.R
import com.ori.learnsquare1.business.adapter.ArticleAdapter
import com.ori.learnsquare1.business.adapter.FragmentListAdapter
import com.ori.learnsquare1.business.adapter.FragmentTitleListAdapter
import com.ori.learnsquare1.business.ui.system.item.ItemFragment
import com.ori.learnsquare1.common.base.fragment.BaseFragment
import com.ori.learnsquare1.common.base.fragment.BaseVMFragment
import kotlinx.android.synthetic.main.frg_system.*
import java.util.Collections.addAll

/**
 * 创建人 zhengpf
 * 时间 2020/7/1
 * 类说明:体系
 */
class SystemFragment : BaseVMFragment<SystemViewModel>() {

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
                    iv_filter.visibility = View.VISIBLE
                    tl_layout.visibility = View.VISIBLE
                    vp_list.visibility = View.VISIBLE
                    showTabList()
                }
            })

            loadingStatus.observe(viewLifecycleOwner, Observer {
                cpb_loading.isVisible = it
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

        vp_list.adapter = FragmentTitleListAdapter(fragments, titles,  childFragmentManager)
        vp_list.offscreenPageLimit = 6
        tl_layout.setupWithViewPager(vp_list)
    }
}