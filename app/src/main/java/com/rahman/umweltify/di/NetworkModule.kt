package com.rahman.umweltify.di

import com.rahman.umweltify.network.AppInterceptor
import com.rahman.umweltify.network.AppRequestService
import com.rahman.umweltify.network.ResultCallAdapterFactory
import com.rahman.umweltify.network.model.BatteryDtoMapper
import com.rahman.umweltify.persentation.constants.constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(constant.BASE_URL)
            .addCallAdapterFactory(ResultCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: AppInterceptor): OkHttpClient {

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRequestServer(retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient): AppRequestService {
        return retrofitBuilder.client(okHttpClient).build().create(AppRequestService::class.java)
    }

    @Singleton
    @Provides
    fun provideLangMapper():  BatteryDtoMapper{
        return  BatteryDtoMapper();
    }

}

