package com.vangelnum.rickmasterstest.feature_camera.presentation.mappers

import com.vangelnum.rickmasterstest.feature_camera.data.model.Camera
import com.vangelnum.rickmasterstest.feature_db_camera.data.model.CameraDbModel

fun List<CameraDbModel>.toCameraList(): List<Camera> {
    return map { cameraDbModel ->
        Camera(
            favorites = cameraDbModel.favorites,
            id = cameraDbModel.id,
            name = cameraDbModel.name,
            rec = cameraDbModel.rec,
            room = cameraDbModel.room,
            snapshot = cameraDbModel.snapshot
        )
    }
}

fun List<Camera>.toCameraDbModelList(): List<CameraDbModel> {
    return map { camera ->
        CameraDbModel(
            favorites = camera.favorites,
            id = camera.id,
            name = camera.name,
            rec = camera.rec,
            room = camera.room,
            snapshot = camera.snapshot
        )
    }
}
