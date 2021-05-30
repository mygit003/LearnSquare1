package com.ori.learnsquare1.business.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.ori.learnsquare1.R
import com.ori.learnsquare1.business.entity.BusEvent
import com.ori.learnsquare1.business.entity.UserValue
import com.ori.learnsquare1.common.base.http.HttpUtils
import com.ori.learnsquare1.common.util.Constant
import com.ori.learnsquare1.common.util.CoroutineScopeUtil
import com.ori.learnsquare1.common.util.MsgType
import com.ori.learnsquare1.common.util.PrefUtils
import com.ori.learnsquare1.databinding.DlgLoginBinding
import kotlinx.coroutines.MainScope
import org.greenrobot.eventbus.EventBus


/**
 * 创建人: zhengpf
 * 修改时间: 2021/5/29 16:57
 * 类说明:
 */
class LoginDialog(val mContext: Context) : Dialog(mContext, R.style.CustomDialog) {

    private lateinit var viewBinding: DlgLoginBinding

    private val loadingDialog by lazy { LoadingDialog(mContext) }

    private var userValue = UserValue()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DlgLoginBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        init()
    }

    private fun init() {
        CoroutineScopeUtil.getInstance().init(MainScope())
        initUI()
    }

    private fun initUI() {
        viewBinding.ivBack.setOnClickListener{
            dismiss()
        }

        viewBinding.tvGoRegiste.setOnClickListener {
            //toActivity(RegisteActivity::class.java)
        }


        viewBinding.btnLogin.setOnClickListener {
            var account = viewBinding.tietAccount.text.toString()
            var pwd = viewBinding.tietPwd.text.toString()
            if (TextUtils.isEmpty(account)) {
                showToast("请输入登录名")
                return@setOnClickListener
            }else {
                if (account.length < 3) {
                    showToast("登录名长度不能小于3")
                    return@setOnClickListener
                }
            }

            if (TextUtils.isEmpty(pwd)) {
                showToast("请输入登录密码")
                return@setOnClickListener
            }else {
                if (pwd.length < 6) {
                    showToast("密码长度不能小于6位")
                    return@setOnClickListener
                }
            }

            loadingDialog.setTip("登录中...")
            loadingDialog.show()
            login(account, pwd)
        }
    }


    private fun login(account: String, pwd: String) {
        CoroutineScopeUtil.getInstance().launch(
            block = {
                val data = HttpUtils.getApiService().login(account, pwd)
                userValue = data.getResult()!!
                PrefUtils.setObject(Constant.SpKey.SP_USER_INFO, userValue)
                PrefUtils.setBoolean(Constant.SpKey.SP_LOGIN_FLAG, true)
                EventBus.getDefault().post(BusEvent().apply {
                    msgId = MsgType.MSG_UPDATE_USER_INFO
                    msg = "update user info"
                })
                loadingDialog.dismiss()
                showToast("登录成功")
                dismiss()
            },
            error = {
                loadingDialog.dismiss()
                it?.let {
                    it.message?.let { it1 -> showToast(it1) }
                }
            }
        )
    }


    override fun dismiss() {
        super.dismiss()
        CoroutineScopeUtil.getInstance().cancel()
    }


    fun showToast(tip: String) {
        Toast.makeText(mContext, "" + tip, Toast.LENGTH_SHORT).show()
    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        window!!.getDecorView().setPadding(0, 0, 0, 0)
        val params: WindowManager.LayoutParams = window!!.getAttributes()
        //获取屏幕宽度和高度
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.MATCH_PARENT
        params.gravity = Gravity.CENTER
        window!!.setAttributes(params)

    }
}