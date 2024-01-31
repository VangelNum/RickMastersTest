package com.vangelnum.rickmasterstest.feature_camera.data.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable


@Immutable
@Serializable
data class CameraData(
    val cameras: List<Camera>,
    val room: List<String>
)