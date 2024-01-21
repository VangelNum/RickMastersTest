package com.vangelnum.rickmasterstest.feature_doors.data.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class DoorsModel(
    val data: List<DoorsData>,
    val success: Boolean
)