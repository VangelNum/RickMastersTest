package com.vangelnum.rickmasterstest.feature_db_doors.domain.repository

import com.vangelnum.rickmasterstest.feature_db_doors.data.model.DoorsDbModel

interface DoorsDbDao {
    suspend fun getAllRooms(): List<DoorsDbModel>
    suspend fun insertOrUpdateDoors(doorList: List<DoorsDbModel>)
}