package com.example.newssolapplication.common.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newssolapplication.common.dto.CategoryVO
import com.example.newssolapplication.common.dto.LikeDTO
import com.example.newssolapplication.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class FirebaseRepository() {

    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val auth = Firebase.auth.currentUser
    init {
        database.child("userInf").addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(!snapshot.hasChild(auth!!.uid)){
                    Log.d("test2","auth!!.uid")
                    val update: MutableMap<String, Any> = HashMap()
                    update["category/${auth?.uid!!}/city/name"] = "도시"
                    update["category/${auth?.uid!!}/city/count"] = "0"
                    update["category/${auth?.uid!!}/rainy/name"] = "비"
                    update["category/${auth?.uid!!}/rainy/count"] = "0"
                    update["category/${auth?.uid!!}/rest/name"] = "휴식"
                    update["category/${auth?.uid!!}/rest/count"] = "0"
                    update["category/${auth?.uid!!}/nature/name"] = "자연"
                    update["category/${auth?.uid!!}/nature/count"] = "0"
                    update["category/${auth?.uid!!}/ocean/name"] = "바다"
                    update["category/${auth?.uid!!}/ocean/count"] = "0"

                    database.updateChildren(update)
                    val uid: String? = Firebase.auth.currentUser?.uid

                    val user = User(
                        Firebase.auth.currentUser?.displayName, Firebase.auth.currentUser?.uid,
                        Firebase.auth.currentUser?.photoUrl.toString()
                    )

                    database.child("userInf").child(uid.toString()).setValue(user)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("TESTDD", error.toString())
            }

        })
    }

    fun getCategoryAll(): LiveData<List<CategoryVO>>{
        val mutableData = MutableLiveData<List<CategoryVO>>()
        val list: MutableList<CategoryVO> = mutableListOf<CategoryVO>()
        Log.d("testdddd","its.child().value.toString()")
        database.child("category").child(auth?.uid!!).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d("testdddd","its.child().value.toString()")
                list.clear()
                for (its in dataSnapshot.children ) {
                    val data = CategoryVO(its.child("name").value.toString(), its.child("count").value.toString().toInt(),
                        its.key
                    )
                    Log.d("testdddd",its.child("name").value.toString())
                    list.add(data)
                }
                mutableData.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("TESTDD", error.toString())
            }
        })
        return mutableData
    }

    fun getAll(): LiveData<List<LikeDTO>>{
        val mutableData = MutableLiveData<List<LikeDTO>>()
        val list: MutableList<LikeDTO> = mutableListOf<LikeDTO>()
        database.child("category").child(auth?.uid!!).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                list.clear()
                for (its in dataSnapshot.children ) {
                    val data = LikeDTO(its.child("name").value.toString(), its.key)
                    list.add(data)
                }
                mutableData.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("TESTDD", error.toString())
            }
        })
        return mutableData
    }

    fun menuIncrement(menuId : String){
        val update: MutableMap<String, Any> = HashMap()
        Log.d("TESTex",menuId)
        update["category/${auth?.uid!!}/${menuId}/count"] = ServerValue.increment(1)
        database.updateChildren(update)
    }

}
