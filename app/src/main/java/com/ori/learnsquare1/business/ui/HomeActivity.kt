package com.ori.learnsquare1.business.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.util.Log
import android.view.MenuItem
import android.view.ViewPropertyAnimator
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle
import com.google.android.material.animation.AnimationUtils
import com.ori.learnsquare1.R
import com.ori.learnsquare1.common.base.activity.BaseActivity
import com.ori.learnsquare1.common.util.ActivityManage
import com.ori.learnsquare1.business.ui.discovery.DiscoveryFragment
import com.ori.learnsquare1.business.ui.home.HomeFragment
import com.ori.learnsquare1.business.ui.navi.NaviFragment
import com.ori.learnsquare1.business.ui.system.SystemFragment
import com.ori.learnsquare1.business.ui.user.UserFragment
import com.ori.learnsquare1.common.base.activity.BaseViewBindingActivity
import com.ori.learnsquare1.databinding.ActHomeBinding

class HomeActivity : BaseViewBindingActivity<ActHomeBinding>() {

    private var previousPos: Int = 0
    private var fragments: MutableList<Fragment> = mutableListOf()
    private var bottomNavigationViewAnimtor: ViewPropertyAnimator? = null
    private var currentBottomNavagtionState = true

    override fun setRootView(): Int {
        return R.layout.act_home
    }


    override fun init() {
        Log.w(TAG, "onCreate")
        initFragment()
        switchByBottomItem()
    }

    private fun initFragment() {
        //首页
        fragments.add(HomeFragment())

        //体系
        val projectFrg = SystemFragment()
        fragments.add(projectFrg)

        //发现
        fragments.add(DiscoveryFragment())

        //导航
        val accountFrg = NaviFragment()
        fragments.add(accountFrg)

        //个人中心
        fragments.add(UserFragment())

        switchFragment(0)
    }

    private fun switchByBottomItem() {
        viewBinding.bnvMenu.setOnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.item_home -> switchFragment(0)
                R.id.item_subject -> switchFragment(1)
                R.id.item_discovery -> switchFragment(2)
                R.id.item_navi -> switchFragment(3)
                R.id.item_user -> switchFragment(4)
            }
            true
        }

    }

    private fun switchFragment(pos: Int) {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        val curFrg = fragments[pos]
        val lastFrg = fragments[previousPos]
        previousPos = pos
        ft.hide(lastFrg)
        if (!curFrg.isAdded) {
            ft.add(R.id.fl_content, curFrg)
            ft.setMaxLifecycle(curFrg, Lifecycle.State.RESUMED)
        }
        ft.show(curFrg)
        ft.commit()
    }


    var lastTime: Long = 0
    override fun onBackPressed() {
        if (System.currentTimeMillis() - this.lastTime > 2000L) {
            showToast("再按一次退出程序")
            this.lastTime = System.currentTimeMillis()
            return
        } else {
            //销毁所有activity
            ActivityManage.getInstance().appExit()
            super.onBackPressed()
        }
    }


    fun animateBottomNavigationView(show: Boolean) {
        if (currentBottomNavagtionState == show) {
            return
        }
        if (bottomNavigationViewAnimtor != null) {
            bottomNavigationViewAnimtor?.cancel()
            viewBinding.bnvMenu.clearAnimation()
        }
        currentBottomNavagtionState = show
        val targetY = if (show) 0F else viewBinding.bnvMenu.measuredHeight.toFloat()
        //val duration = if (show) 225L else 175L
        bottomNavigationViewAnimtor = viewBinding.bnvMenu.animate()
            .translationY(targetY)
            .setDuration(200L)
            .setInterpolator(AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    bottomNavigationViewAnimtor = null
                }
            })
    }


    override fun onResume() {
        super.onResume()
        Log.w(TAG, "onResume")
        Log.w(TAG, "pos:$previousPos")
    }

    override fun onPause() {
        super.onPause()
        Log.w(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.w(TAG, "onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.w(TAG, "onDestroy")
    }


}
