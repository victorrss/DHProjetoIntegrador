package com.kotlin.marvelgeek.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kotlin.marvelgeek.services.Repository

class MyViewModelFactory(
    private val someParam: Repository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(someParam) as T
    }
}