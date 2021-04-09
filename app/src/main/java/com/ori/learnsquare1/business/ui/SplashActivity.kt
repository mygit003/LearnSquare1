package com.ori.learnsquare1.business.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.ori.learnsquare1.R
import com.ori.learnsquare1.common.base.activity.BaseActivity
import kotlinx.android.synthetic.main.act_splash.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

/**
 * 创建人 zhengpf
 * 时间 2020/7/1
 * 类说明:
 */
class SplashActivity : BaseActivity(), EasyPermissions.PermissionCallbacks {


    private val context: Context = this@SplashActivity;

    private val permission_request_code = 100

    private var job: Job? = null

    override fun setRootView(): Int {
        window.setBackgroundDrawable(null)
        setFullScreen()
        return R.layout.act_splash
    }

    override fun init() {
        setSystemInvadeBlack(true)
        checkAppPermission()
    }


    fun checkAppPermission() {
        val perms = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (EasyPermissions.hasPermissions(this, *perms)) {
            //进行页面跳转
            startMain()
        } else {
            EasyPermissions.requestPermissions(this, "请求运行时权限", permission_request_code, *perms)
        }
    }

    fun startMain() {
        job = lifecycleScope.launch {
            delay(1500)
            startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
            finish()
        }
//        ivLogo.postDelayed(object : Runnable {
//            override fun run() {
//                startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
//                finish()
//            }
//
//        }, 1500)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }


    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }

    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        startMain()
    }


    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        if (event!!.keyCode == KeyEvent.KEYCODE_BACK) {
            if (null != job && !job!!.isCancelled) {
                job?.cancel()
                job = null
            }
        }
        return super.dispatchKeyEvent(event)
    }

    override fun onPause() {
        super.onPause()
        if (null != job && !job!!.isCancelled) {
            job?.cancel()
            job = null
        }
    }


    override fun onDestroy() {
        super.onDestroy()

    }

    private fun setFullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // 延伸显示区域到刘海
            val lp: WindowManager.LayoutParams = window.attributes
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            window.attributes = lp
            // 设置页面全屏显示
            val decorView: View = window.decorView
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
    }

}