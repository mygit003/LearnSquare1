package com.ori.learnsquare1.business.ui.user

import android.os.Bundle
import android.view.View
import android.widget.Switch
import androidx.lifecycle.Observer
import com.ori.learnsquare.business.entity.UserValue
import com.ori.learnsquare1.R
import com.ori.learnsquare1.business.entity.BusEvent
import com.ori.learnsquare1.business.ui.WebActivity
import com.ori.learnsquare1.business.ui.integration.IntegrationActivity
import com.ori.learnsquare1.business.ui.login.LoginActivity
import com.ori.learnsquare1.business.util.AccountStatusManage
import com.ori.learnsquare1.common.base.fragment.BaseFragment
import com.ori.learnsquare1.common.base.fragment.BaseVMFragment
import com.ori.learnsquare1.common.util.Constant
import com.ori.learnsquare1.common.util.MsgType
import com.ori.learnsquare1.common.util.PrefUtils
import kotlinx.android.synthetic.main.frg_user.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 创建人 zhengpf
 * 时间 2020/7/1
 * 类说明:我的
 */
class UserFragment : BaseVMFragment<UserViewModel>(), View.OnClickListener {



    override fun setRootView(): Int {
        return R.layout.frg_user
    }


    override fun initView() {
        EventBus.getDefault().register(this)
        cl_user_info.setOnClickListener(this)
        ll_integration.setOnClickListener(this)
        ll_rank.setOnClickListener(this)
        ll_share.setOnClickListener(this)
        ll_collect.setOnClickListener(this)
        ll_history.setOnClickListener(this)
        ll_website.setOnClickListener(this)
        ll_about.setOnClickListener(this)
        ll_set.setOnClickListener(this)

        viewModel.apply {
            userAccountValue.observe(viewLifecycleOwner, Observer {
                it?.let { user ->
                    tv_user_name.text = user.username
                    tv_user_id.text = user.userId
                    tv_login_registe.visibility = View.GONE
                    tv_user_name.visibility = View.VISIBLE
                    tv_user_id.visibility = View.VISIBLE
                }

            })
        }

        loadUserData()

    }

    fun loadUserData() {
        val userValue = PrefUtils.getObject(Constant.SpKey.SP_USER_INFO) as UserValue
        if (null != userValue) {
            tv_user_name.text = userValue.username
            tv_user_id.text = userValue.id
            tv_login_registe.visibility = View.GONE
            tv_user_name.visibility = View.VISIBLE
            tv_user_id.visibility = View.VISIBLE
        }else {
            tv_login_registe.visibility = View.VISIBLE
            tv_user_name.visibility = View.GONE
            tv_user_id.visibility = View.GONE
        }
    }
    override fun initData() {

    }

    override fun setViewModelClass(): Class<UserViewModel> {
        return UserViewModel::class.java
    }


    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.cl_user_info -> {
                //登录/注册
                if (!AccountStatusManage.isLogin()) {
                }
                toActivity(LoginActivity::class.java)
            }
            R.id.ll_integration -> {
                //积分
                if (AccountStatusManage.isLogin()) {
                    toActivity(IntegrationActivity::class.java)
                }else {
                    toActivity(LoginActivity::class.java)
                }

            }
            R.id.ll_rank -> {
                //排行


            }
            R.id.ll_share -> {
                //分享

            }
            R.id.ll_collect -> {
                //收藏

            }
            R.id.ll_history -> {
                //浏览历史

            }
            R.id.ll_website -> {
                //网站
                toActivity(WebActivity::class.java, Bundle().apply {
                    putString(Constant.WebParam.PARAM_TITLE, resources.getString(R.string.app_name))
                    putString(Constant.WebParam.PARAM_URL, Constant.WebUrl.WEB_URL)
                })

            }
            R.id.ll_about -> {
                //关于

            }
            R.id.ll_set -> {
                //设置

            }
        }
    }


    @Subscribe
    fun doSubscribeEvent(event: BusEvent) {
        when(event.msgId) {
            MsgType.MSG_UPDATE_USER_INFO -> {
                //获取用户积分信息
                viewModel.getUserInfo()
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}