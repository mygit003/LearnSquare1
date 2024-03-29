package com.ori.learnsquare1.business.ui.registe

import android.text.TextUtils
import androidx.lifecycle.Observer
import com.ori.learnsquare1.R
import com.ori.learnsquare1.common.base.activity.BaseViewBindingVMActivity
import com.ori.learnsquare1.databinding.ActRegisteBinding

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/19 11:38
 * 类说明:注册
 */
class RegisteActivity : BaseViewBindingVMActivity<ActRegisteBinding, RegisteViewModel>() {



    override fun setViewModelClass(): Class<RegisteViewModel> {
        return RegisteViewModel::class.java
    }

    override fun setRootView(): Int {
        return R.layout.act_registe
    }

    override fun init() {
        initUI()
    }


    private fun initUI() {
        viewBinding.ivBack.setOnClickListener { finish() }

        viewBinding.btnRegiste.setOnClickListener {
            doRegiste()
        }

        viewModel.apply {
            registeStatus.observe(this@RegisteActivity, Observer {
                if (!TextUtils.isEmpty(it)) {
                    if (it.equals("success")) {
                        showToast("注册成功")
                        finish()
                    }else {
                        showToast(it)
                    }
                }
            })
        }
    }

    private fun doRegiste() {
        val account = viewBinding.tietAccount.text.toString()
        val pwd = viewBinding.tietPwd.text.toString()
        val repwd = viewBinding.tietRepwd.text.toString()

        if (TextUtils.isEmpty(account)) {
            showToast("请输入用户名")
            return
        }else {
            if (account.length < 3) {
                showToast("登录名长度不能小于3")
                return
            }
        }

        if (TextUtils.isEmpty(pwd)) {
            showToast("请输入登录密码")
            return
        }else {
            if (pwd.length < 6) {
                showToast("密码长度不能小于6位")
                return
            }
        }

        if (TextUtils.isEmpty(repwd)) {
            showToast("请再次输入登录密码")
            return
        }else {
            if (pwd.length < 6) {
                showToast("密码长度不能小于6位")
                return
            }
        }

        viewModel.registe(account, pwd, repwd)
    }
}