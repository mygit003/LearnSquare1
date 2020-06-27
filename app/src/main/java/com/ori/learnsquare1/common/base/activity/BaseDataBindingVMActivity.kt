package com.ori.learnsquare1.common.base.activity

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.ori.learnsquare1.common.base.viewmodel.BaseViewModel

/**
 * 创建人: zhengpf
 * 修改时间: 2020/6/26 17:34
 * 类说明:
 */
abstract class BaseDataBindingVMActivity<DB : ViewDataBinding, VM : BaseViewModel> : BaseActivity() {

    protected lateinit var viewDataBinding: DB
    protected lateinit var mViewModel: VM


    override fun setActivityContent() {
        viewDataBinding = DataBindingUtil.setContentView(this, setRootView())
        viewDataBinding.lifecycleOwner = this
        mViewModel = ViewModelProvider(this).get(setViewModelClass())
    }


    override fun onDestroy() {
        super.onDestroy()
        viewDataBinding.unbind()
    }



    abstract fun setViewModelClass(): Class<VM>
}