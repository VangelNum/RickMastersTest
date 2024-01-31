package com.vangelnum.rickmasterstest.feature_doors.data.model

import kotlinx.serialization.Serializable

@Serializable
data class DoorsData(
    val favorites: Boolean,
    val id: Int,
    val name: String,
    val room: String?,
    val snapshot: String? = null
)