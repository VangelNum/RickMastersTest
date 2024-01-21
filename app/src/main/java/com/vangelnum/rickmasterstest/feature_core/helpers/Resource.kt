package com.vangelnum.rickmasterstest.feature_core.helpers

sealed class Resource<out T> {
    data class Success<out T>(val resourceData: T) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()
    data object Loading : Resource<Nothing>()
}