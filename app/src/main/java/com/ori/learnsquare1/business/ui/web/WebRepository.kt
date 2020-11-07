package com.ori.learnsquare1.business.ui.web

import com.ori.learnsquare1.business.App
import com.ori.learnsquare1.business.db.DBHelper
import com.ori.learnsquare1.business.db.entity.BrowseHistoryValue

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/26 11:29
 * 类说明:
 */
class WebRepository {

    private val historyDao by lazy { DBHelper.getInstance(App.getApp()).histroyDao() }

    suspend fun add(historyValue: BrowseHistoryValue) = historyDao.insert(historyValue)
}
