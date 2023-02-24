package com.rahman.bettary_app.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.rahman.bettary_app.db.BatteryDao
import com.rahman.bettary_app.db.BatteryDatabase
import com.rahman.bettary_app.repository.BatteryRepository
import com.rahman.educationinfo.repository.BatteryRepositoryImp
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


}