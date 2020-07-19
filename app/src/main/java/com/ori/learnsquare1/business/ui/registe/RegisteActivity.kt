package com.ori.learnsquare1.business.ui.registe

import com.ori.learnsquare1.R
import com.ori.learnsquare1.common.base.activity.BaseVMActivity

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/19 11:38
 * 类说明:注册
 */
class RegisteActivity : BaseVMActivity<RegisteViewModel>() {



    override fun setViewModelClass(): Class<RegisteViewModel> {
        return RegisteViewModel::class.java
    }

    override fun setRootView(): Int {
        return R.layout.act_registe
    }

    override fun init() {
        
    }
}