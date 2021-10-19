package com.example.newssolapplication.ui.main

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.NumberPicker
import android.widget.ProgressBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newssolapplication.R
import com.example.newssolapplication.common.CommonFragment
import com.example.newssolapplication.common.room.LikeContact
import com.example.newssolapplication.databinding.MainFragmentBinding
import com.example.newssolapplication.ui.main.adapter.CategoryAdapter
import com.example.newssolapplication.ui.main.adapter.LikeListAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainFragment : CommonFragment(), NumberPicker.OnValueChangeListener {

    companion object {
        fun newInstance() = MainFragment()
        var timer = 0
    }

    private lateinit var mainViewModel: MainViewModel
    private lateinit var mBinding: MainFragmentBinding
    private var hour = 0
    private var min = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        mBinding = DataBindingUtil.inflate<MainFragmentBinding>(
                inflater,
                R.layout.main_fragment,
                container,
                false
        ).apply {
            viewModel = mainViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        mBinding.btnStart.setOnClickListener {
            mainViewModel.setProgressTimer(hour * 60 + min)
            Toast.makeText(context, timer.toString(), Toast.LENGTH_SHORT).show()
            startTimer()
        }

        initView()
        return mBinding.root
    }

    private fun initView() {
        mBinding.progressTimer.apply {
            progress = 0
            timer = 0
            max = 719
        }

        mBinding.timerHour.apply {
            minValue = 0
            maxValue = 11
            wrapSelectorWheel = false
            value = 0
            setOnValueChangedListener(this@MainFragment)
        }

        mBinding.timerMin.apply {
            minValue = 0
            maxValue = 59
            wrapSelectorWheel = false
            value = 0
            setOnValueChangedListener(this@MainFragment)
        }

        mainViewModel.timeHour.observe(viewLifecycleOwner, Observer {
            mBinding.timerHour.value = it.toInt()
            hour = it.toInt()
        })

        mainViewModel.timeMin.observe(viewLifecycleOwner, Observer {
            mBinding.timerMin.value = it.toInt()
            min = it.toInt()
        })

        mainViewModel.progressTime.observe(viewLifecycleOwner, Observer {
            mBinding.progressTimer.smoothProgress(it.toInt())
            timer = it.toInt()
        })
        val contact = LikeContact(id, "테스트", "music1" ,"music2","music3")
//        mainViewModel.insert(contact)
        val likeListAdapter = LikeListAdapter { category ->
            Toast.makeText(activity, category.title, Toast.LENGTH_LONG).show()
        }

        val categoryAdapter = CategoryAdapter { category ->
            Toast.makeText(activity, category.name, Toast.LENGTH_LONG).show()
        }

        mBinding.recyclerLike.apply {
            adapter = likeListAdapter
            layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
            setHasFixedSize(true)
        }

        mBinding.recyclerCategory.apply {
            adapter = categoryAdapter
            layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
            setHasFixedSize(true)
        }

//        mBinding.recyclerMusic.apply {
//            setAdapter()
//            layoutManager = LinearLayoutManager(context).apply {
//            orientation = LinearLayoutManager.VERTICAL
//        }
//            setHasFixedSize(true)
//        }

        mainViewModel.getLikeAll().observe(viewLifecycleOwner, Observer<List<LikeContact>> {
                contacts -> likeListAdapter.setLikeList(contacts!!)
        })

        mainViewModel.getCategoryAll().observe(viewLifecycleOwner, Observer { categoryList ->
            categoryAdapter.setCategory(categoryList)
        })


    }

    private fun startTimer() {
        var jobTime = timer
        CoroutineScope(IO).launch {
            withContext(Main) {
                do {
//                    mBinding.progressTimer.smoothProgress(jobTime)
                    mainViewModel.setProgressTimer(jobTime)
                    Log.d("testTime", jobTime.toString())
                    calTimer(jobTime)
//                    delay(60000L) // 1분
                    delay(1000L) // 1초
                    jobTime -= 1
                } while (jobTime >= 0)
                timer = 0
            }
            withContext(Main) {
                Toast.makeText(context, "finish", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun calTimer(jobTime: Int) {
        Log.d("testTimed", (jobTime % 60).toString())
        mainViewModel.setTimeMin(jobTime % 60)
        if (jobTime % 60 == 59) {
            mainViewModel.minusTimeHour()
        }
    }

    private fun ProgressBar.smoothProgress(percent: Int) {
        val animation = ObjectAnimator.ofInt(this, "progress", percent)
        animation.apply {
            duration = 400
            interpolator = DecelerateInterpolator()
            start()
        }
    }

    override fun onValueChange(numberPicker: NumberPicker?, p1: Int, p2: Int) {
        when (numberPicker?.id) {
            R.id.timer_hour -> {
                mainViewModel.setTimeHour(p2)
            }
            R.id.timer_min -> {
                mainViewModel.setTimeMin(p2)
            }
        }

    }
}