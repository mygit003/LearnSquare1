package com.ori.learnsquare1.business.ui.login

import android.text.TextUtils
import androidx.lifecycle.Observer
import com.ori.learnsquare1.R
import com.ori.learnsquare1.business.dialog.LoadingDialog
import com.ori.learnsquare1.business.entity.BusEvent
import com.ori.learnsquare1.business.ui.registe.RegisteActivity
import com.ori.learnsquare1.common.base.activity.BaseViewBindingVMActivity
import com.ori.learnsquare1.common.util.Constant
import com.ori.learnsquare1.common.util.MsgType
import com.ori.learnsquare1.common.util.PrefUtils
import com.ori.learnsquare1.databinding.ActLoginBinding
import org.greenrobot.eventbus.EventBus

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/19 11:38
 * 类说明:登录
 */
class LoginActivity : BaseViewBindingVMActivity<ActLoginBinding, LoginViewModel>() {


    private val loadingDialog by lazy { LoadingDialog(this) }


    override fun setViewModelClass(): Class<LoginViewModel> {
        return LoginViewModel::class.java
    }

    override fun setRootView(): Int {
        return R.layout.act_login
    }

    override fun init() {
        initListener()
        initData()
    }



    private fun initListener() {
        viewBinding.ivBack.setOnClickListener{
            finish()
        }

        viewBinding.tvGoRegiste.setOnClickListener {
            toActivity(RegisteActivity::class.java)
        }


        viewBinding.btnLogin.setOnClickListener {
            var account = viewBinding.tietAccount.text.toString()
            var pwd = viewBinding.tietPwd.text.toString()
            if (TextUtils.isEmpty(account)) {
                showToast("请输入登录名")
                return@setOnClickListener
            }else {
                if (account.length < 3) {
                    showToast("登录名长度不能小于3")
                    return@setOnClickListener
                }
            }

            if (TextUtils.isEmpty(pwd)) {
                showToast("请输入登录密码")
                return@setOnClickListener
            }else {
                if (pwd.length < 6) {
                    showToast("密码长度不能小于6位")
                    return@setOnClickListener
                }
            }

            loadingDialog.setTip("登录中...")
            loadingDialog.show()
            viewModel.login(account, pwd)
        }
    }


    private fun initData() {
        viewModel.run {
            userValue.observe(this@LoginActivity, Observer {
                it?.let {user ->
                    PrefUtils.setObject(Constant.SpKey.SP_USER_INFO, user)
                    PrefUtils.setBoolean(Constant.SpKey.SP_LOGIN_FLAG, true)
                    EventBus.getDefault().post(BusEvent().apply {
                        msgId = MsgType.MSG_UPDATE_USER_INFO
                        msg = "update user info"
                    })
                    finish()
                }
            })

            loginStatus.observe(this@LoginActivity, Observer {
                loadingDialog.dismiss()
            })

            loginMessage.observe(this@LoginActivity, Observer {
                if (!TextUtils.isEmpty(it)) {
                    showToast(it)
                }
            })
        }
    }
}