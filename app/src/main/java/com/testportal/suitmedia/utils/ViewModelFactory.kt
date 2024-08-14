package com.testportal.suitmedia.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.testportal.suitmedia.data.di.Injection
import com.testportal.suitmedia.data.repository.UserRepository
import com.testportal.suitmedia.views.thirdscreen.ThirdScreenViewModel

class ViewModelFactory(private val repository: UserRepository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThirdScreenViewModel::class.java)) {
            return ThirdScreenViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository())
            }.also { instance = it }
    }
}