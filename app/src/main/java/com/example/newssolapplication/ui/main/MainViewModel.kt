package com.example.newssolapplication.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newssolapplication.common.dto.CategoryVO
import com.example.newssolapplication.common.dto.LikeDTO
import com.example.newssolapplication.common.repository.FirebaseRepository
import com.example.newssolapplication.common.room.LikeContact
import com.example.newssolapplication.common.room.LikeContactRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _timeHour = MutableLiveData<Int>()
    private val _timeMin = MutableLiveData<Int>()
    private val _progressTimer = MutableLiveData<Int>()

    private val firebaseRepository = FirebaseRepository()
    private val roomRepository = LikeContactRepository(application)

    private val likeList = roomRepository.getLikeAll()
    private val categoryList = firebaseRepository.getCategoryAll()

    init {
        _timeHour.value = 0
        _timeMin.value = 0
        _progressTimer.value = 0
    }

    fun getCategoryAll() : LiveData<List<CategoryVO>>{
        return this.categoryList
    }

    fun getLikeAll(): LiveData<List<LikeContact>> {
        return this.likeList
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
        _timeHour.value = _timeHour.value?.minus(1)
    }

    fun minusTimeMin(){
        _timeMin.value = _timeMin.value?.minus(1)
//        _timeMin.value?.minus(1)
    }

    fun setProgressTimer(num: Int){
        _progressTimer.value = num
    }

    fun insert(likeContact: LikeContact){
        roomRepository.insert(likeContact)
    }

}