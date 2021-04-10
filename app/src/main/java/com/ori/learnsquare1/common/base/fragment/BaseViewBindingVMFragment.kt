package com.ori.learnsquare1.common.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.ori.learnsquare1.common.base.viewmodel.BaseViewModel
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType

/**
 * 创建人: zhengpf
 * 修改时间: 2021/4/10 17:10
 * 类说明: ViewBinding + ViewModel
 */
abstract class BaseViewBindingVMFragment<VB : ViewBinding, VM : BaseViewModel> : BaseFragment() {


    private var _bind: VB? = null
    protected val viewBinding get() = _bind!!
    protected lateinit var viewModel: VM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        try {
            val vmClass = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VB>
            val method: Method = vmClass.getMethod("inflate", LayoutInflater::class.java)
            _bind = method.invoke(null, layoutInflater) as VB
            mView = viewBinding.root
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
        return mView
    }


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
        _bind = null
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