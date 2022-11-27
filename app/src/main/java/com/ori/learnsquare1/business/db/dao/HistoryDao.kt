package com.ori.learnsquare1.business.db.dao

import androidx.room.*
import com.ori.learnsquare1.business.db.entity.BrowseHistoryValue

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/26 10:03
 * 类说明:
 */
@Dao
interface HistoryDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = BrowseHistoryValue::class)
    fun insert(history: BrowseHistoryValue) : Long


    @Query("SELECT * FROM browse_history ORDER BY browse_time DESC LIMIT :pageSize OFFSET (:pageSize * (:pageIndex - 1))")
    fun queryHistory(pageSize: Int, pageIndex: Int) : MutableList<BrowseHistoryValue>

    @Update
    fun updateHistory(history: BrowseHistoryValue) : Int

    @Transaction
    @Query("DELETE FROM browse_history WHERE _id = :id")
    fun delete(id: Int) : Int

    @Transaction
    @Query("DELETE FROM browse_history")
    fun clear() : Int
}