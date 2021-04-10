package com.ori.learnsquare1.common.base.activity

import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.viewbinding.ViewBinding
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType

/**
 * 创建人: zhengpf
 * 修改时间: 2021/4/10 16:48
 * 类说明: ViewBinding
 */
abstract class BaseViewBindingActivity<VB : ViewBinding> : BaseActivity() {

    private var _bind: VB? = null
    protected val viewBinding get() = _bind!!

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
    }


    override fun onDestroy() {
        super.onDestroy()
        _bind = null
    }

}