package com.ori.learnsquare1.common.base.activity

import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.ori.learnsquare1.common.base.viewmodel.BaseViewModel
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType

/**
 * 创建人: zhengpf
 * 修改时间: 2021/4/10 16:57
 * 类说明: ViewBinding + ViewModel
 */
abstract class BaseViewBindingVMActivity<VB : ViewBinding, VM : BaseViewModel> : BaseActivity() {


    private var _bind: VB? = null
    protected val viewBinding: VB get() = _bind!!
    protected lateinit var viewModel: VM


    override fun setActivityContent() {
        try {
            val vmClass = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VB>
            val method: Method = vmClass.getMethod("inflate", LayoutInflater::class.java)
            _bind = method.invoke(null, layoutInflater) as VB
            setContentView(viewBinding.root)
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
        viewModel = ViewModelProvider(this).get(setViewModelClass())
        lifecycle.addObserver(viewModel)
    }


    override fun onDestroy() {
        super.onDestroy()
        _bind = null
        lifecycle.removeObserver(viewModel)
    }



    abstract fun setViewModelClass(): Class<VM>
}