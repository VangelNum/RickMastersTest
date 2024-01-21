package com.vangelnum.rickmasterstest.feature_db_camera.di

import com.vangelnum.rickmasterstest.feature_db_camera.data.repository.CameraDbDaoImpl
import com.vangelnum.rickmasterstest.feature_db_camera.domain.repository.CameraDbDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.Realm
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CameraDbModule {
    @Provides
    @Singleton
    fun provideCameraDbDaoImpl(
        realm: Realm
    ): CameraDbDao {
        return CameraDbDaoImpl(realm)
    }
}