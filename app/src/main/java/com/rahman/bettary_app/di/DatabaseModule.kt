package com.rahman.bettary_app.di

import android.content.Context
import androidx.room.Room
import com.rahman.bettary_app.db.BatteryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun providesBatteryDatabase(@ApplicationContext context: Context): BatteryDatabase {
        return Room.databaseBuilder(context, BatteryDatabase::class.java, BatteryDatabase.DB_NAME)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providesBatteryDao(batteryDatabase: BatteryDatabase) = batteryDatabase.getBatteryDao()

    @Singleton
    @Provides
    fun providesAddressDao(batteryDatabase: BatteryDatabase) = batteryDatabase.getAddressDao()


}