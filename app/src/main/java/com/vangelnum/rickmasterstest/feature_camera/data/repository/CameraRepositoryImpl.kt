package com.vangelnum.rickmasterstest.feature_camera.data.repository

import com.vangelnum.rickmasterstest.feature_camera.data.api.CameraApiService
import com.vangelnum.rickmasterstest.feature_camera.data.model.CameraModel
import com.vangelnum.rickmasterstest.feature_camera.domain.repository.CameraRepository
import com.vangelnum.rickmasterstest.feature_core.helpers.Resource
import javax.inject.Inject

class CameraRepositoryImpl @Inject constructor(
    private val apiService: CameraApiService
) : CameraRepository {
    override suspend fun getCameras(): Resource<CameraModel> {
        return try {
            val result = apiService.getCameras()
            Resource.Success(result)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An error occurred")
        }
    }
}