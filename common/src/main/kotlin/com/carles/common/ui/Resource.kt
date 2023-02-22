package com.carles.common.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

typealias ResourceLiveData<T> = LiveData<Resource<T>>
typealias MutableResourceLiveData<T> = MutableLiveData<Resource<T>>

data class Resource<out T> constructor(
    val state: ResourceState,
    val data: T? = null,
    val message: String? = null
)

sealed class ResourceState
object LOADING : ResourceState()
object SUCCESS : ResourceState()
object ERROR : ResourceState()

fun <T> MutableResourceLiveData<T>.setSuccess(data: T) =
    postValue(Resource(SUCCESS, data))

fun <T> MutableResourceLiveData<T>.setLoading() =
    postValue(Resource(LOADING, value?.data))

fun <T> MutableResourceLiveData<T>.setError(message: String? = null) =
    postValue(Resource(ERROR, value?.data, message))