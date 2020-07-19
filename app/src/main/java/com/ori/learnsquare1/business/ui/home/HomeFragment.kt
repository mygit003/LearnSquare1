package com.ori.learnsquare1.business.ui.home

import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.appbar.AppBarLayout
import com.ori.learnsquare.business.entity.BannerValue
import com.ori.learnsquare1.R
import com.ori.learnsquare1.business.adapter.FragmentListAdapter
import com.ori.learnsquare1.business.adapter.TabAdapter
import com.ori.learnsquare1.business.ui.HomeActivity
import com.ori.learnsquare1.business.ui.home.hot.HotFragment
import com.ori.learnsquare1.business.ui.home.lastest.LastestFragment
import com.ori.learnsquare1.business.ui.home.project.ProjectFragment
import com.ori.learnsquare1.business.ui.home.square.SquareFragment
import com.ori.learnsquare1.business.ui.home.wechat.WeChatFragment
import com.ori.learnsquare1.business.util.BannerUtil
import com.ori.learnsquare1.common.base.fragment.BaseFragment
import com.ori.learnsquare1.common.base.fragment.BaseVMFragment
import com.ori.learnsquare1.common.listener.OnTabClickListener
import kotlinx.android.synthetic.main.frg_home.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator

/**
 * 创建人: zhengpf
 * 修改时间: 2020/6/27 18:18
 * 类说明:首页
 */
class HomeFragment : BaseVMFragment<HomeViewModel>() {

    private var fragments: MutableList<Fragment> = mutableListOf()
    private var bannerList: MutableList<BannerValue> = mutableListOf()
    private lateinit var tagList: MutableList<String>
    private var offSet = 0

    override fun setRootView(): Int {
        return R.layout.frg_home
    }

    override fun initView() {
        initItemFragment()

        //监听页面滑动，隐藏底部导航栏
        abl_layout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (activity is HomeActivity && offSet != verticalOffset) {
                (activity as HomeActivity).animateBottomNavigationView(verticalOffset > offSet)
                offSet = verticalOffset
            }

            val bannerHeight = banner.measuredHeight
//            Log.d(TAG, "offSet:${verticalOffset}")
//            Log.d(TAG, "barHeight=${bannerHeight}")
            val scrollY = Math.abs(verticalOffset)
//            val alpha = if (scrollY >= 0) {
//                iv_home_search.isEnabled = true
//                scrollY.toFloat() / 300.toFloat()
//            }else {
//                iv_home_search.isEnabled = false
//                0f
//            }
//            Log.d(TAG, "alpha=${alpha}")
//            ll_search.alpha = alpha
            if (scrollY >= bannerHeight) {
                ll_search.visibility = View.VISIBLE
            }else {
                ll_search.visibility = View.GONE
            }

        })

        //监听轮播图片数据变化更新UI
        viewModel.bannerList.observe(viewLifecycleOwner, Observer {
            ltv_loading.dismiss()
            if (it.isNotEmpty()) {
                bannerList.addAll(it)
            }
            val urlList: MutableList<String> = mutableListOf()
            bannerList.forEach {
                urlList.add(it.imagePath!!)
            }
            BannerUtil.setBannerAttrs(activity, banner, urlList)
        })
    }

    override fun initData() {
        ltv_loading.loading()
        //初始化加载轮播图数据
        viewModel.getBannerData()
    }

    override fun setViewModelClass(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }


    private fun initItemFragment() {
        tagList = mutableListOf("热门", "最新", "广场", "项目", "公众号")

        fragments.add(HotFragment())
        fragments.add(LastestFragment())
        fragments.add(SquareFragment())
        fragments.add(ProjectFragment())
        fragments.add(WeChatFragment())

        vp_list.offscreenPageLimit = fragments.size
        vp_list.adapter = FragmentListAdapter(fragments, childFragmentManager)

        val commonNavigator = CommonNavigator(context)
        val navigatorAdapter = TabAdapter(tagList)

        commonNavigator.adapter = navigatorAdapter
        mi_indicator.navigator = commonNavigator;
        navigatorAdapter.setOnTabClickListenr(object : OnTabClickListener {
            override fun onTabClick(view: View, index: Int) {
                vp_list.currentItem = index
            }
        })

        ViewPagerHelper.bind(mi_indicator, vp_list)
    }
}