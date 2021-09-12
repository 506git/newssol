package com.example.newssolapplication.common

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.newssolapplication.R
import com.example.newssolapplication.common.utils.ImageLoader
import com.example.newssolapplication.ui.main.MainFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: HomeViewModel
    lateinit var behavior: BottomSheetBehavior<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        initViewModel()
        findViewById<ImageView>(R.id.title).setOnClickListener {
            mainViewModel.setState(BottomSheetBehavior.STATE_COLLAPSED)
        }
        behavior = BottomSheetBehavior.from(findViewById(R.id.bottomSheet)).apply {
            this.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_EXPANDED) {
//                        val fragment = MusicPlayerFragment()
//                        CustomFragmentManger(this@MainActivity).addFragment(fragment, "musicPlayer")
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    findViewById<View>(R.id.collapseView).animate()
                        .alpha(1.0f - slideOffset * 2).duration = 0
                    findViewById<View>(R.id.fragment_view).animate()
                        .alpha(slideOffset * 2).duration = 0
                }
            })
        }
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, MainFragment.newInstance())
                .commitNow()
        }
    }

    override fun onStart() {
        super.onStart()
        val auth = Firebase.auth
        if(auth != null){
            val currentUser = auth.currentUser
            mainViewModel.setUser(currentUser?.displayName.toString(), currentUser?.email.toString(), currentUser?.photoUrl.toString())
        }
    }

    private fun initViewModel() {
        mainViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        mainViewModel.user.observe(this, Observer {
            ImageLoader(this).imageCircleLoadWithURL(
                it.profile,
                findViewById<ImageView>(R.id.profile)
            )
        })
        mainViewModel.bottomState.observe(this, Observer {
            behavior.state = it
        })
    }
}