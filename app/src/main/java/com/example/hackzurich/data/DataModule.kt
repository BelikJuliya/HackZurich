package com.example.hackzurich.data

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.hackzurich.domain.IChatBotRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext appContext: Context): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(ChuckerInterceptor(appContext))
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(BASE_URL: String, client: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .baseUrl(BASE_URL)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ChatBotApiService = retrofit.create(ChatBotApiService::class.java)

    @Provides
    @Singleton
    fun provideChatBotRepository(service: ChatBotApiService): IChatBotRepository {
        return ChatBotRepositoryImpl(service)
    }

    @Provides
    @Singleton
    fun providesBaseUrl(): String = "https://api.apilayer.com/exchangerates_data/" // TODO set real url
}