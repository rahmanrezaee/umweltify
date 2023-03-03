package com.rahman.bettary_app.di

import com.google.gson.GsonBuilder
import com.rahman.bettary_app.R
import com.rahman.bettary_app.network.AppRequestService
import com.rahman.bettary_app.network.model.BatteryDtoMapper
import com.rahman.bettary_app.persentation.BaseApplication
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
    fun provideRequestServer(
        context: BaseApplication
    ): AppRequestService{
        return Retrofit.Builder()
            .baseUrl(context.getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(AppRequestService::class.java)

    }

    @Singleton
    @Provides
    @Named("token_auth")
    fun provideToken():String{
        return "Token asdasdew12313eqwdas";
    }

}