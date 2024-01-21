package com.vangelnum.rickmasterstest.feature_db_doors.data.repository

import com.vangelnum.rickmasterstest.feature_db_doors.data.model.DoorsDbModel
import com.vangelnum.rickmasterstest.feature_db_doors.domain.repository.DoorsDbDao
import io.realm.Realm
import javax.inject.Inject

class DoorsDbDaoImpl @Inject constructor(
    private val realm: Realm
) : DoorsDbDao {
    override suspend fun getAllRooms(): List<DoorsDbModel> {
        val realmResults = realm.where(DoorsDbModel::class.java).findAllAsync()
        return realm.copyFromRealm(realmResults)
    }

    override suspend fun insertOrUpdateDoors(doorList: List<DoorsDbModel>) {
        realm.executeTransactionAsync { bgRealm ->
            bgRealm.insertOrUpdate(doorList)
        }
    }
}