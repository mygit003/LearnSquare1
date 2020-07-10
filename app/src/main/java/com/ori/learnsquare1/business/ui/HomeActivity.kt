package com.ori.learnsquare1.business.ui

import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle
import com.ori.learnsquare1.R
import com.ori.learnsquare1.common.base.activity.BaseActivity
import com.ori.learnsquare1.common.util.ActivityManage
import com.ori.learnsquare1.business.ui.discovery.DiscoveryFragment
import com.ori.learnsquare1.business.ui.home.HomeFragment
import com.ori.learnsquare1.business.ui.navi.NaviFragment
import com.ori.learnsquare1.business.ui.system.SystemFragment
import com.ori.learnsquare1.business.ui.user.UserFragment
import kotlinx.android.synthetic.main.act_home.*

class HomeActivity : BaseActivity() {

    private var previousPos: Int = 0
    private var fragments: MutableList<Fragment> = mutableListOf()

    override fun setRootView(): Int {
        return R.layout.act_home
    }


    override fun init() {
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
        bnv_menu.setOnNavigationItemSelectedListener { item: MenuItem ->
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
}
