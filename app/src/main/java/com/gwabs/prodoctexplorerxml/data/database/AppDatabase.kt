package com.gwabs.prodoctexplorerxml.data.database



import androidx.room.Database
import androidx.room.RoomDatabase
import com.gwabs.prodoctexplorerxml.data.model.Product

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}
