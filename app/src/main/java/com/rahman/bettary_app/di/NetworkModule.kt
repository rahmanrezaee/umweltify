package com.rahman.bettary_app.di

import com.google.gson.GsonBuilder
import com.rahman.bettary_app.network.BatteryService
import com.rahman.bettary_app.network.model.BatteryDtoMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideLangMapper():  BatteryDtoMapper{
        return  BatteryDtoMapper();
    }

    @Singleton
    @Provides
    fun provideLangServer(): BatteryService{
        return Retrofit.Builder()
            .baseUrl("http://54.241.7.222:3000/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(BatteryService::class.java)
    }

    @Singleton
    @Provides
    @Named("token_auth")
    fun provideToken():String{
        return "Token asdasdew12313eqwdas";
    }

}