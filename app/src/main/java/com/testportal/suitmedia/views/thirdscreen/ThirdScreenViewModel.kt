package com.testportal.suitmedia.views.thirdscreen

import androidx.lifecycle.ViewModel
import com.testportal.suitmedia.data.repository.UserRepository

class ThirdScreenViewModel(private val repository: UserRepository): ViewModel() {
    fun getListUser(page: Int, perPage: Int) = repository.getListUsers(page, perPage)
}