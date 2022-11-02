package com.challenges.bill.business

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.challenges.bill.utils.imageRepo

class MainVMFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(imageRepo) as T
        }
    }
}