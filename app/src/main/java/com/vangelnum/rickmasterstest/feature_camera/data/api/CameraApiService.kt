package com.vangelnum.rickmasterstest.feature_camera.data.api

import com.vangelnum.rickmasterstest.feature_camera.data.model.CameraModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class CameraApiService(
    private val httpClient: HttpClient
) {
    suspend fun getCameras(): CameraModel {
        return httpClient.get("http://cars.cprogroup.ru/api/rubetek/cameras/").body()
    }
}