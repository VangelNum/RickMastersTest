package com.vangelnum.rickmasterstest.feature_camera.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vangelnum.rickmasterstest.feature_camera.data.model.Camera
import com.vangelnum.rickmasterstest.feature_camera.domain.repository.CameraRepository
import com.vangelnum.rickmasterstest.feature_camera.presentation.mappers.toCameraDbModelList
import com.vangelnum.rickmasterstest.feature_camera.presentation.mappers.toCameraList
import com.vangelnum.rickmasterstest.feature_core.helpers.Resource
import com.vangelnum.rickmasterstest.feature_db_camera.domain.repository.CameraDbDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val cameraRepository: CameraRepository,
    private val cameraDbDao: CameraDbDao
) : ViewModel() {

    private val _cameras: MutableLiveData<Resource<List<Camera>>> = MutableLiveData()
    val cameras: LiveData<Resource<List<Camera>>> get() = _cameras

    private val _revealedCamerasIdsList = MutableLiveData<List<Int>>()
    val revealedCamerasIdsList: LiveData<List<Int>> get() = _revealedCamerasIdsList

    init {
        getCameras()
    }


    fun getCameras() {
        viewModelScope.launch {
            _cameras.value = Resource.Loading
            val localCameras = cameraDbDao.getAllCameras()
            delay(1000) //just for see loading, can be delete

            if (localCameras.isNotEmpty()) {
                _cameras.value = Resource.Success(localCameras.toCameraList())
            } else {

                val result = cameraRepository.getCameras()

                if (result is Resource.Success) {
                    val data = result.resourceData.data.cameras
                    cameraDbDao.insertOrUpdateCamera(data.toCameraDbModelList())
                    _cameras.value = Resource.Success(data)
                } else if (result is Resource.Error) {
                    _cameras.value = Resource.Error(result.message)
                }
            }
        }
    }

    fun refreshCameras() {
        viewModelScope.launch {
            _cameras.value = Resource.Loading
            val result = cameraRepository.getCameras()
            delay(1000) //just for see loading, can be delete
            if (result is Resource.Success) {
                val data = result.resourceData.data.cameras
                cameraDbDao.insertOrUpdateCamera(data.toCameraDbModelList())
                _cameras.value = Resource.Success(data)
            } else if (result is Resource.Error) {
                _cameras.value = Resource.Error(result.message)
            }
        }
    }

    fun onItemExpanded(cardId: Int) {
        val currentList = _revealedCamerasIdsList.value.orEmpty()
        if (currentList.contains(cardId)) return

        val newList = currentList.toMutableList().also { list ->
            list.add(cardId)
        }
        _revealedCamerasIdsList.value = newList
    }

    fun onItemCollapsed(cardId: Int) {
        val currentList = _revealedCamerasIdsList.value.orEmpty()
        if (!currentList.contains(cardId)) return

        val newList = currentList.toMutableList().also { list ->
            list.remove(cardId)
        }
        _revealedCamerasIdsList.value = newList
    }
}