package me.project.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.project.data.dataSource.NewsDataSource
import me.project.data.repositories.NewsRepository
import me.project.data.repositories.NewsRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideNewsRepository(dataSource: NewsDataSource): NewsRepository {
        return NewsRepositoryImpl(dataSource)
    }
}