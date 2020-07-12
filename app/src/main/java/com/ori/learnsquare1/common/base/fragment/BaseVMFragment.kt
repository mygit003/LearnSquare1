package com.ori.learnsquare1.common.base.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.ori.learnsquare1.common.base.viewmodel.BaseViewModel

/**
 * 创建人: zhengpf
 * 修改时间: 2020/6/26 18:10
 * 类说明:基于ViewModel封装
 */
abstract class BaseVMFragment<VM : BaseViewModel> : BaseFragment() {

    protected lateinit var viewModel: VM


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(setViewModelClass())
        lifecycle.addObserver(viewModel)
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
        lifecycle.removeObserver(viewModel)
    }



    abstract fun initView()
    abstract fun initData()
    abstract fun setViewModelClass(): Class<VM>
}