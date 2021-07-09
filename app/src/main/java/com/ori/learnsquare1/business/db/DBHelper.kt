package com.ori.learnsquare1.business.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ori.learnsquare1.business.db.dao.HistoryDao
import com.ori.learnsquare1.business.db.entity.BrowseHistoryValue
import com.ori.learnsquare1.common.util.Constant

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/26 9:55
 * 类说明:
 */
@Database(entities = [BrowseHistoryValue::class], version = Constant.DB_VERSION)
abstract class DBHelper : RoomDatabase() {

    companion object {
        @Volatile
        private var instance: DBHelper? = null

        fun getInstance(context: Context) : DBHelper =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }


        private fun buildDatabase(context: Context): DBHelper =
            Room.databaseBuilder(context, DBHelper::class.java, "appdb.db")
                .addMigrations(migration_1_2)
                .build()

        //版本1 升到 版本2
        val migration_1_2 = object : Migration(1, 2){
            override fun migrate(database: SupportSQLiteDatabase) {
                //修改数据库表数据

            }
        }
    }


    abstract fun histroyDao() : HistoryDao

}