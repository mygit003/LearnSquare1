package com.ori.learnsquare1.common.base.viewmodel

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/12 9:25
 * 类说明:
 */
interface ViewModelLifecycle : LifecycleObserver {


    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onAny(owner: LifecycleOwner, event: Lifecycle.Event)

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate()

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume()

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause()

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop()

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy()
}