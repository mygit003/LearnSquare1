package com.ori.learnsquare1.common.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewbinding.ViewBinding
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType

/**
 * 创建人: zhengpf
 * 修改时间: 2021/4/10 17:07
 * 类说明: ViewBinding
 */
abstract class BaseViewBindingFragment<VB: ViewBinding> : BaseFragment() {

    private var _bind: VB? = null
    protected val viewBinding get() = _bind!!


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



    abstract fun initView()
    abstract fun initData()
}