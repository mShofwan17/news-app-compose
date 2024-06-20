package me.project.local

import androidx.room.Database
import androidx.room.RoomDatabase
import me.project.local.dao.BookmarkDao
import me.project.local.entity.BookmarkNewsEntity

@Database(
    entities = [
        BookmarkNewsEntity::class,
    ], version = 1, exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun bookmarkDao(): BookmarkDao
}