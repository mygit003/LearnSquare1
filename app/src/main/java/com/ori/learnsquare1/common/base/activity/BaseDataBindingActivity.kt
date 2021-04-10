package com.ori.learnsquare1.common.base.activity

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.viewbinding.ViewBinding

/**
 * 创建人: zhengpf
 * 修改时间: 2020/6/26 11:48
 * 类说明: ViewDataBinding
 */
abstract class BaseDataBindingActivity<V : ViewDataBinding> : BaseActivity() {

    protected lateinit var viewDataBinding: V

    override fun setActivityContent() {
        viewDataBinding = DataBindingUtil.setContentView(this, setRootView())
        viewDataBinding.lifecycleOwner = this
    }


    override fun onDestroy() {
        super.onDestroy()
        viewDataBinding.unbind()
    }

}