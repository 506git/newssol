package com.example.newssolapplication.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.newssolapplication.R
import com.example.newssolapplication.common.CommonFragment
import com.example.newssolapplication.databinding.MainFragmentBinding

class MainFragment : CommonFragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var mainViewModel: MainViewModel
    private var mBinding: MainFragmentBinding?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainViewModel =
            ViewModelProvider(this,).get(MainViewModel::class.java).apply {
//                showProgress()
            }

        mBinding = DataBindingUtil.inflate<MainFragmentBinding>(inflater,R.layout.main_fragment, container, false).apply {
            viewModel = mainViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return inflater.inflate(R.layout.main_fragment, container, false)
    }


}