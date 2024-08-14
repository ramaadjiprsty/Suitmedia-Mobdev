package com.testportal.suitmedia.views.secondscreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.testportal.suitmedia.R
import com.testportal.suitmedia.databinding.ActivitySecondBinding
import com.testportal.suitmedia.views.thirdscreen.ThirdActivity

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        username = if (savedInstanceState?.let { onSaveInstanceState(it) } != null) {
            savedInstanceState.getString(EXTRA_NAME)
        } else {
            intent.getStringExtra(EXTRA_NAME)
        }

        val username = intent.getStringExtra(EXTRA_NAME)
        if (username != null) {
            binding.username.text = username
        } else {
            binding.username.text = getString(R.string.dummy_username)
        }
        val selectedUser = intent.getStringExtra(EXTRA_USER)
        if (selectedUser != null) {
            binding.selectedUser.text = selectedUser
        }

        binding.btnChooseUser.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            intent.putExtra(EXTRA_NAME, username)
            startActivity(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EXTRA_NAME, username)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        username = savedInstanceState.getString(EXTRA_NAME)
    }

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_USER = "extra_user"
    }
}