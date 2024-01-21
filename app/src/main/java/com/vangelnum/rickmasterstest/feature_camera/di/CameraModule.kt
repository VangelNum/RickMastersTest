package com.vangelnum.rickmasterstest.feature_camera.di

import com.vangelnum.rickmasterstest.feature_camera.data.api.CameraApiService
import com.vangelnum.rickmasterstest.feature_camera.data.repository.CameraRepositoryImpl
import com.vangelnum.rickmasterstest.feature_camera.domain.repository.CameraRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CameraModule {
    @Provides
    @Singleton
    fun provideApiService(httpClient: HttpClient): CameraApiService {
        return CameraApiService(httpClient)
    }

    @Provides
    @Singleton
    fun provideCameraRepository(apiService: CameraApiService): CameraRepository {
        return CameraRepositoryImpl(apiService)
    }
}