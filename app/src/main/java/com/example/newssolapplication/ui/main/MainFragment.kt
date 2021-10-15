package com.example.newssolapplication.ui.main

import android.animation.ObjectAnimator
import android.os.Bundle
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
import com.example.newssolapplication.R
import com.example.newssolapplication.common.CommonFragment
import com.example.newssolapplication.databinding.MainFragmentBinding
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mainViewModel =
                ViewModelProvider(this).get(MainViewModel::class.java).apply {
                }

        mBinding = DataBindingUtil.inflate<MainFragmentBinding>(inflater, R.layout.main_fragment, container, false).apply {
            viewModel = mainViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        mBinding.progressTimer.apply {
            timer = 0
            max = 780
        }

        mBinding.timerHour.apply {
            minValue = 0
            maxValue = 12
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

        var hour = 0
        var min = 0

        mainViewModel.timeHour.observe(viewLifecycleOwner, Observer {
            hour = it.toInt()
        })

        mainViewModel.timeMin.observe(viewLifecycleOwner, Observer {
            min = it.toInt()
        })

        mainViewModel.progressTime.observe(viewLifecycleOwner, Observer {
            timer = it.toInt()
        })

        mBinding.btnStart.setOnClickListener {
            mainViewModel.setProgressTimer(hour*60+min)
            Toast.makeText(context,timer.toString(),Toast.LENGTH_SHORT).show()
            startTimer()
        }
        return mBinding.root
    }

    private fun startTimer() {
        var jobTime = timer
        CoroutineScope(IO).launch {
            do{
                withContext(Main){
                    mBinding.progressTimer.smoothProgress(jobTime)
                    Toast.makeText(context,jobTime.toString(),Toast.LENGTH_SHORT).show()
                    calTimer(jobTime)
                }
                jobTime -= 1
                delay(1000)
            }while (jobTime == 0)
            withContext(Main){
            Toast.makeText(context,"finish",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun calTimer(jobTime: Int) {
        if (jobTime % 60 == 0){
            mainViewModel.minusTimeHour()
        } else {
            mainViewModel.minusTimeMin()
        }
    }

    private fun ProgressBar.smoothProgress(percent: Int){
        val animation = ObjectAnimator.ofInt(this,"progress",percent)
        animation.apply {
            duration = 400
            interpolator = DecelerateInterpolator()
            start()
        }
    }
    override fun onValueChange(numberPicker: NumberPicker?, p1: Int, p2: Int) {
        when (numberPicker?.id){
            R.id.timer_hour -> {
                mainViewModel.setTimeHour(p2)
            }
            R.id.timer_min -> {
                mainViewModel.setTimeMin(p2)
            }
        }

    }
}