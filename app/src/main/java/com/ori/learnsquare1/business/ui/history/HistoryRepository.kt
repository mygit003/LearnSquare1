package com.ori.learnsquare1.business.ui.history

import com.ori.learnsquare1.business.App
import com.ori.learnsquare1.business.db.DBHelper
import com.ori.learnsquare1.business.db.entity.BrowseHistoryValue

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/26 9:40
 * 类说明:
 */
class HistoryRepository {

    private val historyDao by lazy { DBHelper.getInstance(App.getApp()).histroyDao() }

    suspend fun add(historyValue: BrowseHistoryValue) = historyDao.insert(historyValue)

    suspend fun query(pageSize: Int, pageIndex: Int) = historyDao.queryHistory(pageSize, pageIndex)

    suspend fun update(history: BrowseHistoryValue) = historyDao.updateHistory(history)

    suspend fun deleteById(id: Int) = historyDao.delete(id)

    suspend fun clear() = historyDao.clear()

}