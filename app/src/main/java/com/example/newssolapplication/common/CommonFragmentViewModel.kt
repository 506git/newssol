package com.example.newssolapplication.common

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class CommonFragmentViewModel (application: Application): AndroidViewModel(application) {

    private val _visible = MutableLiveData<Boolean>().apply {
        value = false
    }

    val visible: LiveData<Boolean> = _visible


    fun progress(visible: Boolean){
        _visible.value = visible
    }
}