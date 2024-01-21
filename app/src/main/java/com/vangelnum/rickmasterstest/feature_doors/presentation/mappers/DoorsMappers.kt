package com.vangelnum.rickmasterstest.feature_doors.presentation.mappers

import com.vangelnum.rickmasterstest.feature_camera.data.model.Camera
import com.vangelnum.rickmasterstest.feature_db_camera.data.model.CameraDbModel
import com.vangelnum.rickmasterstest.feature_db_doors.data.model.DoorsDbModel
import com.vangelnum.rickmasterstest.feature_doors.data.model.DoorsData
import com.vangelnum.rickmasterstest.feature_doors.data.model.DoorsModel

fun DoorsData.toDoorsDbModel(): DoorsDbModel {
    return DoorsDbModel(
        id = this.id,
        favorites = this.favorites,
        name = this.name,
        room = this.room,
        snapshot = this.snapshot
    )
}

fun List<DoorsData>.toDoorsDbModelList(): List<DoorsDbModel> {
    return this.map { it.toDoorsDbModel() }
}

fun DoorsDbModel.toDoorsData(): DoorsData {
    return DoorsData(
        id = this.id,
        favorites = this.favorites,
        name = this.name,
        room = this.room,
        snapshot = this.snapshot
    )
}

fun List<DoorsDbModel>.toDoorsDataList(): List<DoorsData> {
    return this.map { it.toDoorsData() }
}

