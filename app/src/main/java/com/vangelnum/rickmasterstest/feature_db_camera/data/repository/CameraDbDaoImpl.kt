package com.vangelnum.rickmasterstest.feature_db_camera.data.repository

import com.vangelnum.rickmasterstest.feature_db_camera.data.model.CameraDbModel
import com.vangelnum.rickmasterstest.feature_db_camera.domain.repository.CameraDbDao
import io.realm.Realm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CameraDbDaoImpl @Inject constructor(
    private val realm: Realm
) : CameraDbDao {
    override suspend fun getAllCameras(): List<CameraDbModel> {
        val realmResults = realm.where(CameraDbModel::class.java).findAllAsync()
        return realm.copyFromRealm(realmResults)
    }

    override suspend fun insertOrUpdateCamera(cameraList: List<CameraDbModel>) {
        realm.executeTransactionAsync { bgRealm ->
            bgRealm.insertOrUpdate(cameraList)
        }
    }
}