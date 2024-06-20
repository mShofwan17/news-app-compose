package me.project.local.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.project.local.NewsDatabase

@EntryPoint
@InstallIn(SingletonComponent::class)
interface RoomDependencies {
    fun provideDatabase(): NewsDatabase
}