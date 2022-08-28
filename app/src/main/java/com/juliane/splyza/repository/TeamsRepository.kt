package com.juliane.splyza.repository

import com.juliane.splyza.model.BRole
import com.juliane.splyza.network.DataState
import com.juliane.splyza.network.ServicesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.Response

class TeamsRepository constructor(private val servicesApi: ServicesApi) {
    suspend fun getTeams(): Flow<DataState<Response<ResponseBody>>> = flow {
        emit(DataState.Loading)
        delay(1000)
        try {
            val entry = servicesApi.getTeams()
            emit(DataState.Success(entry.execute()))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun updateRole(teamId: String, payload: BRole): Flow<DataState<Response<ResponseBody>>> = flow {
        emit(DataState.Loading)
        delay(1000)
        try {
            val entry = servicesApi.updateRole(teamId, payload)
            emit(DataState.Success(entry.execute()))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}