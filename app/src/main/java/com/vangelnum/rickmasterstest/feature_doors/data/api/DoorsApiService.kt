package com.vangelnum.rickmasterstest.feature_doors.data.api

import com.vangelnum.rickmasterstest.BuildConfig
import com.vangelnum.rickmasterstest.feature_core.api.ApiEndpoints
import com.vangelnum.rickmasterstest.feature_doors.data.model.DoorsModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class DoorsApiService(
    private val httpClient: HttpClient
) {
    suspend fun getCameras(): DoorsModel {
        val url = "${BuildConfig.BASE_URL}${ApiEndpoints.DOORS}"
        return httpClient.get(url).body()
    }
}