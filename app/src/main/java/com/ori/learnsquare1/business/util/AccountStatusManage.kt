package com.ori.learnsquare1.business.util

import com.ori.learnsquare1.business.entity.BusEvent
import com.ori.learnsquare1.common.util.Constant
import com.ori.learnsquare1.common.util.MsgType
import com.ori.learnsquare1.common.util.PrefUtils
import org.greenrobot.eventbus.EventBus

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/19 11:42
 * 类说明:
 */
class AccountStatusManage {


    companion object{
        /**
         * 登录状态
         */
        fun isLogin():Boolean {
            return PrefUtils.getBoolean(Constant.SpKey.SP_LOGIN_FLAG, false)
        }

        /**
         * 退出登录，重置用户状态
         */
        fun resetUser() {
            //发送退出登录消息
            EventBus.getDefault().post(BusEvent().apply {
                msgId = MsgType.MSG_UPDATE_USER_LOGOUT
                msg = "user logout"
            })
            PrefUtils.setBoolean(Constant.SpKey.SP_LOGIN_FLAG, false)
            PrefUtils.removeKey(Constant.SpKey.SP_USER_INFO)
            PrefUtils.removeKey(Constant.SpKey.SP_INTEGRAL_INFO)
        }

    }


}