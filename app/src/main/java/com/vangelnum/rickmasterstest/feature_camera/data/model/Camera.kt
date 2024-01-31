package com.vangelnum.rickmasterstest.feature_camera.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Camera(
    val favorites: Boolean,
    val id: Int,
    val name: String,
    val rec: Boolean,
    val room: String?,
    val snapshot: String
)