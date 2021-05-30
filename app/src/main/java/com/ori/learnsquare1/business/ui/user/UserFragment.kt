package com.ori.learnsquare1.business.ui.user

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.ori.learnsquare1.business.entity.UserValue
import com.ori.learnsquare1.R
import com.ori.learnsquare1.business.dialog.LoginDialog
import com.ori.learnsquare1.business.entity.BusEvent
import com.ori.learnsquare1.business.ui.web.WebActivity
import com.ori.learnsquare1.business.ui.collect.CollectActivity
import com.ori.learnsquare1.business.ui.history.HistoryActivity
import com.ori.learnsquare1.business.ui.integration.IntegrationActivity
import com.ori.learnsquare1.business.ui.login.LoginActivity
import com.ori.learnsquare1.business.ui.rank.RankActivity
import com.ori.learnsquare1.business.ui.setting.SettingActivity
import com.ori.learnsquare1.business.ui.shared.SharedActivity
import com.ori.learnsquare1.business.util.AccountStatusManage
import com.ori.learnsquare1.common.base.fragment.BaseViewBindingVMFragment
import com.ori.learnsquare1.common.util.Constant
import com.ori.learnsquare1.common.util.MsgType
import com.ori.learnsquare1.common.util.PrefUtils
import com.ori.learnsquare1.databinding.FrgUserBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 创建人 zhengpf
 * 时间 2020/7/1
 * 类说明:我的
 */
class UserFragment : BaseViewBindingVMFragment<FrgUserBinding, UserViewModel>(), View.OnClickListener {



    override fun setRootView(): Int {
        return R.layout.frg_user
    }


    override fun initView() {
        EventBus.getDefault().register(this)
        viewBinding.clUserInfo.setOnClickListener(this)
        viewBinding.llIntegration.setOnClickListener(this)
        viewBinding.llRank.setOnClickListener(this)
        viewBinding.llShare.setOnClickListener(this)
        viewBinding.llCollect.setOnClickListener(this)
        viewBinding.llHistory.setOnClickListener(this)
        viewBinding.llWebsite.setOnClickListener(this)
        viewBinding.llAbout.setOnClickListener(this)
        viewBinding.llSet.setOnClickListener(this)

        viewModel.apply {
            userAccountValue.observe(viewLifecycleOwner, Observer {
                it?.let { user ->
                    viewBinding.tvUserName.text = user.username
                    viewBinding.tvUserId.text = user.userId
                    viewBinding.tvLoginRegiste.visibility = View.GONE
                    viewBinding.tvUserName.visibility = View.VISIBLE
                    viewBinding.tvUserId.visibility = View.VISIBLE
                    //缓存积分信息
                    PrefUtils.setObject(Constant.SpKey.SP_INTEGRAL_INFO, it)
                }

            })
        }

        loadUserData()

    }

    fun loadUserData() {
        if (null != (PrefUtils.getObject(Constant.SpKey.SP_USER_INFO))) {
            val userValue = PrefUtils.getObject(Constant.SpKey.SP_USER_INFO) as UserValue
            if (null != userValue) {
                viewBinding.tvUserName.text = userValue.username
                viewBinding.tvUserId.text = userValue.id
                viewBinding.tvLoginRegiste.visibility = View.GONE
                viewBinding.tvUserName.visibility = View.VISIBLE
                viewBinding.tvUserId.visibility = View.VISIBLE
            }else {
                viewBinding.tvLoginRegiste.visibility = View.VISIBLE
                viewBinding.tvUserName.visibility = View.GONE
                viewBinding.tvUserId.visibility = View.GONE
            }
        }else {
            viewBinding.tvLoginRegiste.visibility = View.VISIBLE
            viewBinding.tvUserName.visibility = View.GONE
            viewBinding.tvUserId.visibility = View.GONE
        }
    }
    override fun initData() {
        //当前用户若已经登录，则刷新积分信息
        //获取用户积分信息
        if (AccountStatusManage.isLogin()) {
            viewModel.getUserInfo()
        }
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
                //toActivity(LoginActivity::class.java)
                val dialog = activity?.let { LoginDialog(it) }
                dialog?.show()
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
                if (AccountStatusManage.isLogin()) {
                    toActivity(RankActivity::class.java)
                }else {
                    toActivity(LoginActivity::class.java)
                }


            }
            R.id.ll_share -> {
                //分享
                if (AccountStatusManage.isLogin()) {
                    toActivity(SharedActivity::class.java)
                }else {
                    toActivity(LoginActivity::class.java)
                }

            }
            R.id.ll_collect -> {
                //收藏
                if (AccountStatusManage.isLogin()) {
                    toActivity(CollectActivity::class.java)
                }else {
                    toActivity(LoginActivity::class.java)
                }

            }
            R.id.ll_history -> {
                //浏览历史
                if (AccountStatusManage.isLogin()) {
                    toActivity(HistoryActivity::class.java)
                }else {
                    toActivity(LoginActivity::class.java)
                }

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
                toActivity(SettingActivity::class.java)

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