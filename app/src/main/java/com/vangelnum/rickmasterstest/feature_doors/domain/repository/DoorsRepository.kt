package com.vangelnum.rickmasterstest.feature_doors.domain.repository

import com.vangelnum.rickmasterstest.feature_core.helpers.Resource
import com.vangelnum.rickmasterstest.feature_doors.data.model.DoorsModel

interface DoorsRepository {
    suspend fun getDoors(): Resource<DoorsModel>
}