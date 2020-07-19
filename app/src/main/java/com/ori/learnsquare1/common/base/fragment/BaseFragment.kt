package com.ori.learnsquare1.common.base.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * 创建人: zhengpf
 * 修改时间: 2020/6/26 16:18
 * 类说明:
 */
abstract class BaseFragment : Fragment() {

    protected val TAG = this.javaClass.simpleName


    protected var isLoaded = false;

    protected lateinit var mView: View


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(setRootView(), null)
        return mView
    }


    protected abstract fun setRootView(): Int


    protected fun toActivity(clazz: Class<*>) {
        startActivity(Intent(activity, clazz))
    }


    protected fun toActivity(clazz: Class<*>, bundle: Bundle) {
        var intent = Intent(activity, clazz).apply {
            putExtras(bundle)
        }
        startActivity(intent)

    }
}