package com.testportal.suitmedia.views.thirdscreen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.testportal.suitmedia.R
import com.testportal.suitmedia.data.adapter.UserAdapter
import com.testportal.suitmedia.data.remote.Result
import com.testportal.suitmedia.data.remote.response.DataItem
import com.testportal.suitmedia.databinding.ActivityThirdBinding
import com.testportal.suitmedia.utils.ViewModelFactory
import com.testportal.suitmedia.views.secondscreen.SecondActivity

class ThirdActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThirdBinding
    private val viewModel by viewModels<ThirdScreenViewModel>() {
        ViewModelFactory.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        fetchUserList()
        binding.swipeRefresh.setOnRefreshListener {
            fetchUserList()
        }
    }

    private fun fetchUserList() {
        viewModel.getListUser(CURRENT_PAGE, PAGE_SIZE).observe(this) { response ->
            if (response != null) {
                when(response) {
                    is Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Success -> {
                        showLoading(false)
                        binding.swipeRefresh.isRefreshing = false
                        userList(response.data)
                    }
                    is Result.Error -> {
                        showLoading(false)
                        showError(true)
                        binding.swipeRefresh.isRefreshing = false
                        Toast.makeText(this, response.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun userList(userList: PagingData<DataItem>) {
        val userAdapter = UserAdapter()
        userAdapter.submitData(lifecycle, userList)
        binding.rvListUser.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showError(isError: Boolean) {
        binding.tvError.visibility = if (isError) View.VISIBLE else View.GONE
        binding.ivError.visibility = if (isError) View.VISIBLE else View.GONE
    }

    companion object {
        const val CURRENT_PAGE = 1
        const val PAGE_SIZE = 5
        const val EXTRA_NAME = "extra_name"
    }
}