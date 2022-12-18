package com.quandoo.androidtask.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class BaseViewModelFactory <T> ( val creator: () -> T) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return creator() as T
    }
}