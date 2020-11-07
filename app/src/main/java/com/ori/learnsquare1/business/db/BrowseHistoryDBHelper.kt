package com.ori.learnsquare1.business.db

import androidx.lifecycle.ViewModel
import com.ori.learnsquare1.business.App
import com.ori.learnsquare1.common.base.viewmodel.ViewModelLifecycle
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/26 11:17
 * 类说明:
 */
class BrowseHistoryDBHelper private constructor(){

    private val historyDao by lazy { DBHelper.getInstance(App.getApp()).histroyDao() }

    companion object {
        @Volatile
        private var instance: BrowseHistoryDBHelper? = null

        fun getInstance() : BrowseHistoryDBHelper =
            instance ?: synchronized(this) {
                instance ?: BrowseHistoryDBHelper().also { instance = it }
            }
    }




}