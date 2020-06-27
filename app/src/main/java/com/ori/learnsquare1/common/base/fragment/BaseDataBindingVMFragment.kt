package com.ori.learnsquare1.common.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.ori.learnsquare1.common.base.viewmodel.BaseViewModel

/**
 * 创建人: zhengpf
 * 修改时间: 2020/6/26 18:06
 * 类说明:
 */
abstract class BaseDataBindingVMFragment<DB : ViewDataBinding, VM : BaseViewModel> : BaseFragment() {


    private lateinit var dataBinding: DB
    private lateinit var viewModel: VM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (null == mView) {
            dataBinding = DataBindingUtil.inflate(inflater, setRootView(), container, false)
            dataBinding.lifecycleOwner = this
            mView = dataBinding.root
        }
        return mView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(setViewModelClass())
        initView()
    }


    override fun onResume() {
        super.onResume()
        lazyInit()
    }


    private fun lazyInit() {
        if (!isLoaded && !isHidden) {
            initData()
            isLoaded = true
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        isLoaded = false
    }



    override fun onDestroy() {
        super.onDestroy()
        if (null != dataBinding) {
            dataBinding.unbind()
        }
    }


    abstract fun initView()
    abstract fun initData()
    abstract fun setViewModelClass(): Class<VM>


}