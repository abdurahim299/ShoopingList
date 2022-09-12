package com.timesoft.shoopinglist.db

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.timesoft.shoopinglist.entities.*

@Database(entities = [LibraryItem::class, NoteItem::class,
        ShopListItem::class, ShopListNameItem::class, TestItem::class], version = 3,
    exportSchema = true, autoMigrations = [AutoMigration(from = 2, to = 3)]
)
abstract class MainDatabase : RoomDatabase() {
    abstract fun getDao(): Dao

    companion object {

        @Volatile
        private var INSTANCE: MainDatabase? = null
        fun getDataBase(context: Context): MainDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDatabase::class.java,
                    "shopping_list_db"
                ).build()
                instance
            }
        }

    }

}