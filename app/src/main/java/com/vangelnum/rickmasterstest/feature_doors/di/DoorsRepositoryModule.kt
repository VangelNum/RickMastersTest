package com.vangelnum.rickmasterstest.feature_doors.di

import com.vangelnum.rickmasterstest.feature_camera.data.api.CameraApiService
import com.vangelnum.rickmasterstest.feature_camera.data.repository.CameraRepositoryImpl
import com.vangelnum.rickmasterstest.feature_camera.domain.repository.CameraRepository
import com.vangelnum.rickmasterstest.feature_doors.data.api.DoorsApiService
import com.vangelnum.rickmasterstest.feature_doors.data.repository.DoorsRepositoryImpl
import com.vangelnum.rickmasterstest.feature_doors.domain.repository.DoorsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DoorsModule {
    @Provides
    @Singleton
    fun provideDoorsApiService(httpClient: HttpClient): DoorsApiService {
        return DoorsApiService(httpClient)
    }

    @Provides
    @Singleton
    fun provideDoorsRepository(apiService: DoorsApiService): DoorsRepository {
        return DoorsRepositoryImpl(apiService)
    }
}