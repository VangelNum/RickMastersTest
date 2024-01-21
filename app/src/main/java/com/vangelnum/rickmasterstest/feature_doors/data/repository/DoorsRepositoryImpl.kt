package com.vangelnum.rickmasterstest.feature_doors.data.repository

import com.vangelnum.rickmasterstest.feature_core.helpers.Resource
import com.vangelnum.rickmasterstest.feature_doors.data.api.DoorsApiService
import com.vangelnum.rickmasterstest.feature_doors.data.model.DoorsModel
import com.vangelnum.rickmasterstest.feature_doors.domain.repository.DoorsRepository
import javax.inject.Inject

class DoorsRepositoryImpl @Inject constructor(
    private val api: DoorsApiService
): DoorsRepository {
    override suspend fun getDoors(): Resource<DoorsModel> {
        return try {
            val result = api.getCameras()
            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }
}