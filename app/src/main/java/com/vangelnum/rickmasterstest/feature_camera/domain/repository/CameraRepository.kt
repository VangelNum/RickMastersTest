package com.vangelnum.rickmasterstest.feature_camera.domain.repository

import com.vangelnum.rickmasterstest.feature_camera.data.model.CameraModel
import com.vangelnum.rickmasterstest.feature_core.helpers.Resource

interface CameraRepository {
    suspend fun getCameras(): Resource<CameraModel>
}