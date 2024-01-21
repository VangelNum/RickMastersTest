package com.vangelnum.rickmasterstest.feature_db_camera.data.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class CameraDbModel(
    var favorites: Boolean = false,
    @PrimaryKey
    var id: Int = 0,
    var name: String = "",
    var rec: Boolean = false,
    var room: String? = null,
    var snapshot: String = ""
) : RealmObject()