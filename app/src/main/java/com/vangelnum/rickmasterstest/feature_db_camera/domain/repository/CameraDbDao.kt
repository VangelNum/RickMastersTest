package com.vangelnum.rickmasterstest.feature_db_camera.domain.repository

import com.vangelnum.rickmasterstest.feature_db_camera.data.model.CameraDbModel

interface CameraDbDao {
    suspend fun getAllCameras(): List<CameraDbModel>
    suspend fun insertOrUpdateCamera(cameraList: List<CameraDbModel>)
}