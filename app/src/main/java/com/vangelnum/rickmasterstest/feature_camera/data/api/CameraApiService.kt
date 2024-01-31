package com.vangelnum.rickmasterstest.feature_camera.data.api

import com.vangelnum.rickmasterstest.BuildConfig
import com.vangelnum.rickmasterstest.feature_camera.data.model.CameraModel
import com.vangelnum.rickmasterstest.feature_core.api.ApiEndpoints
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class CameraApiService(
    private val httpClient: HttpClient
) {
    suspend fun getCameras(): CameraModel {
        val url = "${BuildConfig.BASE_URL}${ApiEndpoints.CAMERAS}"
        return httpClient.get(url).body()
    }
}