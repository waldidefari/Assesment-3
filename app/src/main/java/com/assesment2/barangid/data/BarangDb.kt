package com.assesment2.barangid.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class BarangDb : RoomDatabase() {

    abstract fun itemDao(): BarangDao

    companion object {
        @Volatile
        private var INSTANCE: BarangDb? = null

        fun getDatabase(context: Context): BarangDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BarangDb::class.java,
                    "item_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}