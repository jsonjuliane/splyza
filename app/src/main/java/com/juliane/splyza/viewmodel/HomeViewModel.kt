package com.juliane.splyza.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juliane.splyza.model.BRole
import com.juliane.splyza.network.DataState
import com.juliane.splyza.repository.TeamsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val teamsRepository: TeamsRepository) :
    ViewModel() {

    val dataState: MutableLiveData<DataState<Response<ResponseBody>>> =
        MutableLiveData<DataState<Response<ResponseBody>>>()

    val roleState: MutableLiveData<DataState<Response<ResponseBody>>> =
        MutableLiveData<DataState<Response<ResponseBody>>>()

    fun getTeams() {
        viewModelScope.launch {
            teamsRepository.getTeams().onEach {
                dataState.value = it
            }.launchIn(viewModelScope)
        }
    }

    fun updateRole(teamId: String, payload: BRole) {
        viewModelScope.launch {
            teamsRepository.updateRole(teamId, payload).onEach {
                roleState.value = it
            }.launchIn(viewModelScope)
        }
    }

}