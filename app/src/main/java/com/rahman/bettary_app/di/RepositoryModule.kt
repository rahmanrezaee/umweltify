package com.rahman.bettary_app.di

import com.rahman.bettary_app.db.BatteryDao
import com.rahman.bettary_app.repository.BatteryRepository
import com.rahman.educationinfo.repository.BatteryRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {


    @Provides
    fun providesDbRepository(batteryDao: BatteryDao) = BatteryRepositoryImp(batteryDao)

}