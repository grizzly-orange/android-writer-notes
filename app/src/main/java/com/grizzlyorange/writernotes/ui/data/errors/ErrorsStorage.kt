package com.grizzlyorange.writernotes.ui.data.errors

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class ErrorsStorage @Inject constructor() {
    private val _errors: MutableLiveData<Set<Errors.ErrorType>> = MutableLiveData(setOf())
    val errors: LiveData<Set<Errors.ErrorType>> get() = _errors

    fun removeError(error: Errors.ErrorType, isBackgoundProcess: Boolean = false) {
        val e:MutableSet<Errors.ErrorType> = _errors.value?.toMutableSet() ?: mutableSetOf()
        e.remove(error)
        setErrorsValue(e, isBackgoundProcess)
    }

    fun addError(error: Errors.ErrorType, isBackgoundProcess: Boolean = false) {
        val e:MutableSet<Errors.ErrorType> = _errors.value?.toMutableSet() ?: mutableSetOf()
        e.add(error)
        setErrorsValue(e, isBackgoundProcess)
    }

    fun resetErrors(isBackgoundProcess: Boolean = false) {
        setErrorsValue(setOf(), isBackgoundProcess)
    }

    fun hasErrors(): Boolean {
        return (_errors.value?.isNotEmpty()) ?: false
    }

    private fun setErrorsValue(errorsValue: Set<Errors.ErrorType>, isBackgoundProcess: Boolean = false) {
        if (isBackgoundProcess) {
            _errors.postValue(errorsValue)
        } else {
            _errors.value = errorsValue
        }
    }
}