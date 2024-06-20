package me.project.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.project.network.BuildConfig
import me.project.network.services.NewsService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SerivcesModule {
    @Provides
    @Singleton
    fun provideNewsService(
        retrofit: Retrofit.Builder,
        okHttpClient: OkHttpClient.Builder
    ) : NewsService {
        return retrofit.client(okHttpClient.build()).baseUrl(BuildConfig.BASE_V2).build()
            .create(NewsService::class.java)
    }
}