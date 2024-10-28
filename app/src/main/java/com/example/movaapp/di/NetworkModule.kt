package com.example.movaapp.di

import com.example.movaapp.api.ApiKeyInterceptor
import com.example.movaapp.api.MovieService
import com.example.movaapp.utils.base_url
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideApiKeyInterceptor():ApiKeyInterceptor{
        return ApiKeyInterceptor()
    }

    @Provides
    @Singleton
    fun provideOkHttp(apiKeyInterceptor: ApiKeyInterceptor):OkHttpClient{
        return OkHttpClient().newBuilder().addInterceptor(apiKeyInterceptor).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client:OkHttpClient) : Retrofit {
        return Retrofit.Builder().baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create()).client(client).build()
    }

    @Provides
    @Singleton
    fun provideMovieService(retrofit: Retrofit) : MovieService {
        return retrofit.create(MovieService::class.java)
    }
}