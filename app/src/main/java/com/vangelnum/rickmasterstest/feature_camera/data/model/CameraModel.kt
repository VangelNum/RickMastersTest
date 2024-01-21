package com.vangelnum.rickmasterstest.feature_camera.data.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class CameraModel(
    val data: CameraData,
    val success: Boolean
)