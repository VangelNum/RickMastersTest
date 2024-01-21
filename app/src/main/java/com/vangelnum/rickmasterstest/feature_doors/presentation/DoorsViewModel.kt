package com.vangelnum.rickmasterstest.feature_doors.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vangelnum.rickmasterstest.feature_core.helpers.Resource
import com.vangelnum.rickmasterstest.feature_db_doors.domain.repository.DoorsDbDao
import com.vangelnum.rickmasterstest.feature_doors.data.model.DoorsData
import com.vangelnum.rickmasterstest.feature_doors.domain.repository.DoorsRepository
import com.vangelnum.rickmasterstest.feature_doors.presentation.mappers.toDoorsDataList
import com.vangelnum.rickmasterstest.feature_doors.presentation.mappers.toDoorsDbModelList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoorsViewModel @Inject constructor(
    private val doorsRepository: DoorsRepository,
    private val doorsDbDao: DoorsDbDao
) : ViewModel() {

    private val _doorsState = MutableLiveData<Resource<List<DoorsData>>>()
    val doorsState: LiveData<Resource<List<DoorsData>>> = _doorsState

    private val _revealedDoorsIdsList = MutableLiveData<List<Int>>()
    val revealedDoorsIdsList: LiveData<List<Int>> get() = _revealedDoorsIdsList

    init {
        getDoors()
    }

    private fun getDoors() {
        viewModelScope.launch {
            _doorsState.value = Resource.Loading
            val localCameras = doorsDbDao.getAllRooms()
            delay(1000) //just for see loading, can be delete

            if (localCameras.isNotEmpty()) {
                _doorsState.value = Resource.Success(localCameras.toDoorsDataList())
            } else {

                val result = doorsRepository.getDoors()

                if (result is Resource.Success) {
                    val data = result.resourceData.data
                    doorsDbDao.insertOrUpdateDoors(data.toDoorsDbModelList())
                    _doorsState.value = Resource.Success(data)
                } else if (result is Resource.Error) {
                    _doorsState.value = Resource.Error(result.message)
                }
            }
        }
    }

    fun refreshDoors() {
        viewModelScope.launch {
            _doorsState.value = Resource.Loading
            val result = doorsRepository.getDoors()
            delay(1000) //just for see loading, can be delete

            if (result is Resource.Success) {
                val data = result.resourceData.data
                doorsDbDao.insertOrUpdateDoors(data.toDoorsDbModelList())
                _doorsState.value = Resource.Success(data)
            } else if (result is Resource.Error) {
                _doorsState.value = Resource.Error(result.message)
            }
        }
    }

    fun onItemExpanded(cardId: Int) {
        val currentList = _revealedDoorsIdsList.value.orEmpty()
        if (currentList.contains(cardId)) return

        val newList = currentList.toMutableList().also { list ->
            list.add(cardId)
        }
        _revealedDoorsIdsList.value = newList
    }

    fun onItemCollapsed(cardId: Int) {
        val currentList = _revealedDoorsIdsList.value.orEmpty()
        if (!currentList.contains(cardId)) return

        val newList = currentList.toMutableList().also { list ->
            list.remove(cardId)
        }
        _revealedDoorsIdsList.value = newList
    }
}
