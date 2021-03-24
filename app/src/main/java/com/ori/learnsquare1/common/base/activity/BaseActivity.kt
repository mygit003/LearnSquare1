package com.ori.learnsquare1.common.base.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ori.learnsquare1.R
import com.ori.learnsquare1.common.util.ActivityManage
import com.ori.learnsquare1.common.util.StatusUtils

/**
 * 创建人: zhengpf
 * 修改时间: 2020/6/26 11:40
 * 类说明:
 */
abstract class BaseActivity : AppCompatActivity() {

    protected val TAG = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setActivityContent()
        setStatusColor()
        setSystemInvadeBlack(false)
        ActivityManage.getInstance().pushActivity(this)

        // 因为Activity恢复后savedInstanceState不为null，
        // 重新恢复后会自动从ViewModel中的LiveData恢复数据，
        // 不需要重新初始化数据。
        if (savedInstanceState == null) {
        }
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
        //#00000000 #00ffffff
        StatusUtils.setUseStatusBarColor(this, resources.getColor(android.R.color.transparent))
    }

    /**
     * 沉浸式状态
     */
    protected open fun setSystemInvadeBlack(isImmersive: Boolean) {
        //第二个参数是是否沉浸,第三个参数是状态栏字体是否为黑色。
        StatusUtils.setSystemStatus(this, isImmersive, true)
    }

    protected fun showToast(tip: String) {
        Toast.makeText(this, "" + tip, Toast.LENGTH_SHORT).show()
    }


}