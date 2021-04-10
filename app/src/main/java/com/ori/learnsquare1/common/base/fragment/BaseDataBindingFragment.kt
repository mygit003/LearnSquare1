package com.ori.learnsquare1.common.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * 创建人: zhengpf
 * 修改时间: 2020/6/26 17:52
 * 类说明: ViewDataBinding
 */
abstract class BaseDataBindingFragment<DB: ViewDataBinding> : BaseFragment() {

    private lateinit var dataBinding: DB

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate(inflater, setRootView(), container, false)
        dataBinding.lifecycleOwner = this
        mView = dataBinding.root
        return mView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
}