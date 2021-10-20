package com.example.newssolapplication.ui.main

import android.app.Application
import android.opengl.Visibility
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newssolapplication.common.dto.CategoryVO
import com.example.newssolapplication.common.dto.ItemDTO
import com.example.newssolapplication.common.dto.LikeDTO
import com.example.newssolapplication.common.repository.FirebaseRepository
import com.example.newssolapplication.common.room.LikeContact
import com.example.newssolapplication.common.room.LikeContactRepository
import kotlinx.coroutines.*

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _progressTimer = MutableLiveData<Int>()
    private val _timerStatus = MutableLiveData<Int>()
    private val _timerRevStatus = MutableLiveData<Int>()
    private val _timePause = MutableLiveData<Int>()
    private val _timeClick = MutableLiveData<Int>()
    private val _textTime = MutableLiveData<String>()
    private val firebaseRepository = FirebaseRepository()
    private val roomRepository = LikeContactRepository(application)

    private val likeList = roomRepository.getLikeAll()
    private val categoryList = firebaseRepository.getCategoryAll()
    private val itemList = firebaseRepository.getItemAll()

    private lateinit var job: Job
    private var hour : Int = 0
    private var min : Int = 0
    private var jobTime: Int = 0
    var count = 0

    private fun initJob(){
        job = Job()
    }

    init {
        _progressTimer.value = 0
        _timerStatus.value = View.VISIBLE
        _timerRevStatus.value = View.GONE
        _timePause.value = View.GONE
        _textTime.value = "00 : 00"
    }

    fun getCategoryAll() : LiveData<List<CategoryVO>>{
        return this.categoryList
    }

    fun getLikeAll(): LiveData<List<LikeContact>> {
        return this.likeList
    }

    fun getItemAll(): LiveData<List<ItemDTO>> {
        return this.itemList
    }

    val textTime : LiveData<String>
        get() = _textTime

    val timerStatus : LiveData<Int>
        get() = _timerStatus

    val timerRevStatus : LiveData<Int>
        get() = _timerRevStatus

    val progressTime : LiveData<Int>
        get() = _progressTimer

    fun timerStatus(status: String){
        if(status.equals("GONE",true)) {
            _timerStatus.value = View.GONE
            _timerRevStatus.value = View.VISIBLE
        } else {
            _timerRevStatus.value = View.GONE
            _timerStatus.value =  View.VISIBLE
        }
    }

    fun setTimeHour(num: Int){
        hour = num
    }

    fun setTimeMin(num: Int){
        min = num
    }


    fun setProgressTimer(num: Int){
        _progressTimer.value = num
    }

    fun insert(likeContact: LikeContact){
        roomRepository.insert(likeContact)
    }
    fun startTimer() {
        if(jobTime == 0) {
            jobTime = hour * 60 + min
        }
        count++
        initJob()
        CoroutineScope(Dispatchers.IO + job).launch {
            withContext(Dispatchers.Main) {
                do {
                    setProgressTimer(jobTime)
                    calTimer(jobTime)
                    //                    delay(60000L) // 1분
                    delay(1000L) // 1초
                    jobTime -= 1
                } while (jobTime >= 0)
                jobTime = 0
            }
        }
    }

    fun pauseTimer(){
        job.cancel()
        count++
        initJob()
    }

    fun stopTimer() {
        job.cancel()
        count = 0
        setProgressTimer(0)
        _textTime.value = "00 : 00"
    }

    private fun calTimer(jobTime: Int) {
        Log.d("testTimed", (jobTime % 60).toString())
        var jobHour = (jobTime/60).toString()
        var jobMin = (jobTime%60).toString()

        if (jobHour.toInt() < 10){
            jobHour="0$jobHour"
        }
        if (jobMin.toInt() < 10){
            jobMin="0$jobMin"
        }
        _textTime.value = "$jobHour : $jobMin"
    }
}