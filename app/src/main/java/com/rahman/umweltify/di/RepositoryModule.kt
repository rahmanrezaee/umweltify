package com.rahman.umweltify.di

import android.content.SharedPreferences
import com.rahman.umweltify.db.AddressDao
import com.rahman.umweltify.db.BatteryDao
import com.rahman.umweltify.network.AppRequestService
import com.rahman.umweltify.persentation.BaseApplication
import com.rahman.umweltify.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideBatteryDBRepository(
        batteryDao: BatteryDao,
                                   sharedPreferences: SharedPreferences,
                                   baseApplication: BaseApplication
    ): BatteryRepository {
        return BatteryRepositoryImp(
            batteryDao,
            baseApplication,
            sharedPreferences,
        )
    }

    @Provides
    @Singleton
    fun provideLoginRepository(
        request: AppRequestService
    ): AuthRepository {
        return AuthRepositoryImp(
            request
        )
    }


    @Provides
    @Singleton
    fun provideAddressDBRepository(addressDao: AddressDao): AddressRepository {
        return AddressRepositoryImp(addressDao)
    }


}