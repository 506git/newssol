package com.example.newssolapplication.common

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newssolapplication.R
import com.example.newssolapplication.model.UserModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeViewModel(application: Application): AndroidViewModel(application) {


    private val _user = MutableLiveData<UserModel>().apply {

    }

    private val _bottomState = MutableLiveData<Int>().apply{
        value = BottomSheetBehavior.STATE_HIDDEN
    }

    val user:LiveData<UserModel> = _user

    val bottomState:LiveData<Int> = _bottomState

    fun setState(bottomState: Int){
        _bottomState.value = bottomState
    }

    fun setUser(name: String, id: String, photoUrl: String){
        _user.value = UserModel(name,id,photoUrl)
    }
}