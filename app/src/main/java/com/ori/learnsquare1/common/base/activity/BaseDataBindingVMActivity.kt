package com.ori.learnsquare1.common.base.activity

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.ori.learnsquare1.common.base.viewmodel.BaseViewModel

/**
 * 创建人: zhengpf
 * 修改时间: 2020/6/26 17:34
 * 类说明: ViewDataBinding + ViewModel
 */
abstract class BaseDataBindingVMActivity<DB : ViewDataBinding, VM : BaseViewModel> : BaseActivity() {

    protected lateinit var viewDataBinding: DB
    protected lateinit var viewModel: VM


    override fun setActivityContent() {
        viewDataBinding = DataBindingUtil.setContentView(this, setRootView())
        viewDataBinding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(setViewModelClass())
        lifecycle.addObserver(viewModel)
    }


    override fun onDestroy() {
        super.onDestroy()
        viewDataBinding.unbind()
        lifecycle.removeObserver(viewModel)
    }



    abstract fun setViewModelClass(): Class<VM>
}