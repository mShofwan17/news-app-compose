package me.project.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import me.project.local.entity.BookmarkNewsEntity

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM tb_bookmark")
    suspend fun getAll(): List<BookmarkNewsEntity>


    @Query("SELECT * FROM tb_bookmark WHERE title=:title")
    suspend fun find(title: String): BookmarkNewsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(item: BookmarkNewsEntity): Long

    @Delete
    suspend fun delete(item: BookmarkNewsEntity): Int

    @Query("DELETE FROM tb_bookmark")
    suspend fun deleteAll(): Int
}