package com.quandoo.androidtask.ui.main

import android.view.LayoutInflater
import com.quandoo.androidtask.databinding.ActivityMainBinding
import com.quandoo.androidtask.ui.base.BaseBindingActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseBindingActivity<ActivityMainBinding> (){

    @Inject
    lateinit var mainViewModel: MainViewModel

    override fun bindingInflater(inflater: LayoutInflater): ActivityMainBinding =
        ActivityMainBinding.inflate(inflater)

    override fun initView() {}


}