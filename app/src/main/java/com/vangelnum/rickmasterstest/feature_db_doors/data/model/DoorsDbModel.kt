package com.vangelnum.rickmasterstest.feature_db_doors.data.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class DoorsDbModel(
    @PrimaryKey
    var id: Int = 0,
    var favorites: Boolean = false,
    var name: String = "",
    var room: String? = null,
    var snapshot: String? = null
) : RealmObject()