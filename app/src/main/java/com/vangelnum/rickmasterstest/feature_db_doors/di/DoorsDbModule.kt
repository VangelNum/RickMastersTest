package com.vangelnum.rickmasterstest.feature_db_doors.di

import com.vangelnum.rickmasterstest.feature_db_doors.data.repository.DoorsDbDaoImpl
import com.vangelnum.rickmasterstest.feature_db_doors.domain.repository.DoorsDbDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.Realm
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DoorsDbModule {
    @Provides
    @Singleton
    fun provideDoorsDbDaoImpl(
        realm: Realm
    ): DoorsDbDao {
        return DoorsDbDaoImpl(realm)
    }
}