package com.example.newssolapplication.common.room

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import java.lang.Appendable
import java.lang.Exception

class LikeContactRepository(application: Application) {
    private val likeContactDatabase = LikeContactDatabase.getInstance(application)!!
    private val likeContactDao: LikeContactDao = likeContactDatabase.likeContactDao()
    private val likeContacts: LiveData<List<LikeContact>> = likeContactDao.getLikeList()

    fun getLikeAll(): LiveData<List<LikeContact>> {
        return likeContacts
    }

    fun insert(likeContact: LikeContact) {
        try {
            CoroutineScope(IO).launch {
                likeContactDao.insert(likeContact)
            }
        } catch (e: Exception) {
            Log.d("ssolError", e.toString())
        }
    }

    fun delete(likeContact: LikeContact) {
        try {
            CoroutineScope(IO).launch {
                likeContactDao.delete(likeContact)
            }
        } catch (e: Exception) {
            Log.d("ssolError", e.toString())
        }
    }

    fun select(likeContact: LikeContact): List<String>? {
        var list: List<String>? = null
        try {
            val job = CoroutineScope(IO).launch {
                list = withContext(IO) {
                    likeContactDao.selectTitle()
                }
            }

            runBlocking {
                job.join()
            }

            return list
        } catch (e: Exception) {
            Log.d("ssolError", e.toString())
            return null
        }
    }

}