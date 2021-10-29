package com.grizzlyorange.writernotes.ui.data.errors

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.grizzlyorange.writernotes.domain.errors.DomainErrors
import javax.inject.Inject

class ErrorsStorage @Inject constructor() {
    private val _errors: MutableLiveData<Set<DomainErrors>> = MutableLiveData(setOf())
    val errors: LiveData<Set<DomainErrors>> get() = _errors

    fun removeAllBesides(errors: Set<DomainErrors>, isBackgoundProcess: Boolean = false) {
        val e:MutableSet<DomainErrors> = _errors.value?.toMutableSet() ?: mutableSetOf()
        val toRemove = e.minus(errors)
        e.removeAll(toRemove)
        setErrorsValue(e, isBackgoundProcess)
    }

    fun addErrors(errors: Set<DomainErrors>, isBackgoundProcess: Boolean = false) {
        val e:MutableSet<DomainErrors> = _errors.value?.toMutableSet() ?: mutableSetOf()
        e.addAll(errors)
        setErrorsValue(e, isBackgoundProcess)
    }

    fun resetErrors(isBackgoundProcess: Boolean = false) {
        setErrorsValue(setOf(), isBackgoundProcess)
    }

    fun hasErrors(): Boolean {
        return (_errors.value?.isNotEmpty()) ?: false
    }

    private fun setErrorsValue(errorsValue: Set<DomainErrors>, isBackgoundProcess: Boolean = false) {
        if (isBackgoundProcess) {
            _errors.postValue(errorsValue)
        } else {
            _errors.value = errorsValue
        }
    }
}