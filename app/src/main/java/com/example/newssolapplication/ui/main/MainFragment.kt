package com.example.newssolapplication.ui.main

import android.animation.ObjectAnimator
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newssolapplication.R
import com.example.newssolapplication.common.CommonFragment
import com.example.newssolapplication.common.dto.CategoryVO
import com.example.newssolapplication.common.dto.ItemDTO
import com.example.newssolapplication.common.room.LikeContact
import com.example.newssolapplication.databinding.MainFragmentBinding
import com.example.newssolapplication.ui.main.adapter.CategoryAdapter
import com.example.newssolapplication.ui.main.adapter.ItemAdapter
import com.example.newssolapplication.ui.main.adapter.LikeListAdapter
import com.example.ssolrangapplication.common.setSafeOnClickListener
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class MainFragment : CommonFragment(), NumberPicker.OnValueChangeListener{

    companion object {
        fun newInstance() = MainFragment()
        var timer = 0
    }

    private lateinit var mainViewModel: MainViewModel
    private lateinit var mBinding: MainFragmentBinding
    private var hour = 0
    private var min = 0
    private var btnStartClick = false
    private var btnCancelClick = false
    private var btnPauseClick = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        mBinding = DataBindingUtil.inflate<MainFragmentBinding>(inflater, R.layout.main_fragment, container, false).apply {
            viewModel = mainViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        mBinding.btnStart.setSafeOnClickListener {
            mainViewModel.setProgressTimer(hour * 60 + min)
            mainViewModel.timerStatus("GONE")
            Toast.makeText(context, timer.toString(), Toast.LENGTH_SHORT).show()
            mainViewModel.startTimer()
        }

        mBinding.btnCancel.setSafeOnClickListener {
            mainViewModel.timerStatus("VISIBLE")
            mainViewModel.stopTimer()
        }
        mBinding.btnPause.setSafeOnClickListener {
            mainViewModel.pauseTimer()
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

        mainViewModel.textTime.observe(viewLifecycleOwner, Observer {
            mBinding.textTime.text = it.toString()
        })

        mainViewModel.progressTime.observe(viewLifecycleOwner, Observer {
            mBinding.progressTimer.smoothProgress(it.toInt())
            timer = it.toInt()
        })

        mainViewModel.timerStatus.observe(viewLifecycleOwner, Observer {
            mBinding.btnStart.visibility = it.toInt()
        })

        mainViewModel.timerRevStatus.observe(viewLifecycleOwner, Observer {
            mBinding.btnPause.visibility = it.toInt()
            mBinding.btnCancel.visibility = it.toInt()
        })


        val likeListAdapter = LikeListAdapter { category ->
            Toast.makeText(activity, category.title, Toast.LENGTH_LONG).show()
        }

        val categoryAdapter = CategoryAdapter { category ->
            Toast.makeText(activity, category.name, Toast.LENGTH_LONG).show()
        }

        val itemAdapter = ItemAdapter { item ->
            Toast.makeText(activity, item.title, Toast.LENGTH_LONG).show()
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

        mBinding.recyclerMusic.apply {
            adapter = itemAdapter
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
        }

        mainViewModel.getLikeAll().observe(viewLifecycleOwner, Observer<List<LikeContact>> { contacts ->
                likeListAdapter.setLikeList(contacts!! as MutableList<LikeContact>)
            })

        mainViewModel.getCategoryAll().observe(viewLifecycleOwner, Observer<List<CategoryVO>> { categoryList ->
                categoryAdapter.setCategory(categoryList)
            })

        mainViewModel.getItemAll().observe(viewLifecycleOwner, Observer<List<ItemDTO>> { categoryList ->
                itemAdapter.setItemList(categoryList)
            })
    }

    private fun ProgressBar.smoothProgress(percent: Int) {
        val animation = ObjectAnimator.ofInt(this, "progress", percent)
        animation.apply {
            duration = 400
            interpolator = DecelerateInterpolator()
            start()
        }
    }

    private fun NumberPicker.smoothValue(value: Int) {
        val animation = ObjectAnimator.ofInt(this, "value", value)
        animation.apply {
            duration = 300
            interpolator = DecelerateInterpolator()
            start()
        }
    }

    override fun onValueChange(numberPicker: NumberPicker?, oldVal: Int, newVal: Int) {
        when (numberPicker?.id) {
            R.id.timer_hour -> {
                mainViewModel.setTimeHour(newVal)
            }
            R.id.timer_min -> {
                mainViewModel.setTimeMin(newVal)
            }
        }

    }
}