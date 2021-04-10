package com.ori.learnsquare1.business.ui.setting

import android.util.Log
import androidx.lifecycle.Observer
import com.ori.learnsquare1.R
import com.ori.learnsquare1.common.base.activity.BaseDataBindingVMActivity
import com.ori.learnsquare1.common.util.Constant
import com.ori.learnsquare1.common.util.PrefUtils
import com.ori.learnsquare1.common.util.setNightMode
import com.ori.learnsquare1.databinding.ActSettingBinding

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/25 17:47
 * 类说明:
 */
class SettingActivity : BaseDataBindingVMActivity<ActSettingBinding, SettingViewModel>() {



    override fun setViewModelClass(): Class<SettingViewModel> {
        return SettingViewModel::class.java
    }

    override fun setRootView(): Int {
        return R.layout.act_setting
    }

    override fun init() {
        initUI()
        initData()
    }


    private fun initUI() {
        viewDataBinding.ivBack.setOnClickListener { finish() }
    }


    private fun initData() {
        viewDataBinding.viewmodel = viewModel

        viewModel.apply {
            mode.observe(this@SettingActivity, Observer {
                Log.w(TAG, "mode:${it}")
                if (it == 1) {
                    //night mode
                    setNightMode(true)
                }else if (it == 0) {
                    //day mode
                    setNightMode(false)
                }
                PrefUtils.setInt(Constant.SpKey.SP_MODE_CONFIG, it)
            })

        }
    }
}