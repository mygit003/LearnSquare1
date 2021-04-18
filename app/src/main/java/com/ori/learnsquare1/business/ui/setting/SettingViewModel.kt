package com.ori.learnsquare1.business.ui.setting

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.MutableLiveData
import com.ori.learnsquare1.business.App
import com.ori.learnsquare1.common.base.viewmodel.BaseViewModel
import com.ori.learnsquare1.common.util.*

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/25 17:46
 * 类说明:
 */
class SettingViewModel : BaseViewModel(), Observable {

    private val callbacks: PropertyChangeRegistry = PropertyChangeRegistry()


    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(callback)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.add(callback)
    }


    fun notifyChange() {
        notifyPropertyChanged(0)
        //callbacks.notifyCallbacks(this, 0, null)
    }

    fun notifyPropertyChanged(fieldId: Int) {
        callbacks.notifyCallbacks(this, fieldId, null)
    }

    var mode = MutableLiveData<Int>()
    var cacheValue = ""
    var version = ""

    init {
        mode.value = PrefUtils.getInt(Constant.SpKey.SP_MODE_CONFIG, 0)
        cacheValue = CacheDataManager.getTotalCacheSize(App.getApp())
        version = "v" + AppUtil.getAppVersionName(App.getApp())
    }


    var modeConfig: Boolean
        @Bindable get() {
        return mode.value != 0
    }
    set(value) {
        if (value) mode.value = 1 else mode.value = 0
    }


}