package com.ori.learnsquare1.common.base.activity

import androidx.lifecycle.ViewModelProvider
import com.ori.learnsquare1.common.base.viewmodel.BaseViewModel

/**
 * 创建人: zhengpf
 * 修改时间: 2020/6/26 17:25
 * 类说明:
 */
abstract class BaseVMActivity<VM : BaseViewModel> : BaseActivity() {


    protected lateinit var mViewModel: VM


    override fun setActivityContent() {
        super.setActivityContent()
        mViewModel = ViewModelProvider(this).get(setViewModelClass())
    }


    abstract fun setViewModelClass(): Class<VM>


}