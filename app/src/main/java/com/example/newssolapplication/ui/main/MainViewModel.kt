package com.example.newssolapplication.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private val _timeHour = MutableLiveData<Int>()
    private val _timeMin = MutableLiveData<Int>()
    private val _progressTimer = MutableLiveData<Int>()

    init {
        _timeHour.value = 0
        _timeMin.value = 0
        _progressTimer.value = 0
    }

    val timeHour : LiveData<Int>
        get() = _timeHour


    val timeMin : LiveData<Int>
        get() = _timeMin

    val progressTime : LiveData<Int>
        get() = _progressTimer


    fun setTimeHour(num: Int){
        _timeHour.value = num
    }

    fun setTimeMin(num: Int){
        _timeMin.value = num
    }

    fun minusTimeHour(){
        _timeHour.value?.minus(1)
    }

    fun minusTimeMin(){
        _timeMin.value?.minus(1)
    }

    fun setProgressTimer(num: Int){
        _progressTimer.value = num
    }

}