package com.rahman.bettary_app.di

import android.content.Context
import com.rahman.bettary_app.persentation.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app:Context): BaseApplication {
        return  app as BaseApplication
    }

    @Singleton
    @Provides
    @Named("random_string")
    fun provideRandomString():String{
        return  "hello world random String";
    }


}