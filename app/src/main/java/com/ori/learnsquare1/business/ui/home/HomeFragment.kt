package com.ori.learnsquare1.business.ui.home

import com.ori.learnsquare1.R
import com.ori.learnsquare1.common.base.fragment.BaseFragment
import com.ori.learnsquare1.common.base.fragment.BaseVMFragment

/**
 * 创建人: zhengpf
 * 修改时间: 2020/6/27 18:18
 * 类说明:首页
 */
class HomeFragment : BaseVMFragment<HomeViewModel>() {




    override fun setRootView(): Int {
        return R.layout.frg_home
    }

    override fun initView() {

    }

    override fun initData() {
        //初始化加载轮播图数据
        viewModel.getBannerData()
    }

    override fun setViewModelClass(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }
}