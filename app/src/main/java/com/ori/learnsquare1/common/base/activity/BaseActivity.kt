package com.ori.learnsquare1.common.base.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ori.learnsquare1.common.util.ActivityManage
import com.ori.learnsquare1.common.util.ColorUtils
import com.ori.learnsquare1.common.util.StatusUtils

/**
 * 创建人: zhengpf
 * 修改时间: 2020/6/26 11:40
 * 类说明:
 */
abstract class BaseActivity : AppCompatActivity() {

    private val TAG = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActivityContent()
        setStatusColor()
        setSystemInvadeBlack()
        ActivityManage.getInstance().pushActivity(this)
        init()
    }


    protected open fun setActivityContent() {
        setContentView(setRootView())
    }


    protected abstract fun setRootView(): Int;
    protected abstract fun init();








    protected fun toActivity(clazz: Class<*>) {
        startActivity(Intent(this, clazz))
    }


    protected fun toActivity(clazz: Class<*>, bundle: Bundle) {
        startActivity(
            Intent(this, clazz).apply {
                putExtras(bundle)
            }
        )
    }


    /**
     * 设置状态栏背景颜色
     */
    protected open fun setStatusColor() {
        StatusUtils.setUseStatusBarColor(this, ColorUtils.parseColor("#00ffffff"))
    }

    /**
     * 沉浸式状态
     */
    protected open fun setSystemInvadeBlack() {
        //第二个参数是是否沉浸,第三个参数是状态栏字体是否为黑色。
        StatusUtils.setSystemStatus(this, true, true)
    }

    protected fun showToast(tip: String) {
        Toast.makeText(this, "" + tip, Toast.LENGTH_SHORT).show()
    }


}