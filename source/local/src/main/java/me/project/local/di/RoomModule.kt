package me.project.local.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.project.local.NewsDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    @Singleton
    fun provideRoomDb(
        @ApplicationContext context: Context
    ): NewsDatabase {
        return Room.databaseBuilder(
            context = context,
            NewsDatabase::class.java,
            name = "db_bookmark"
        ).build()
    }
}