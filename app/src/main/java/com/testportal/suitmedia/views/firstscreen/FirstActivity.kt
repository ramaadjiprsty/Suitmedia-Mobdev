package com.testportal.suitmedia.views.firstscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.testportal.suitmedia.R
import com.testportal.suitmedia.databinding.ActivityFirstBinding
import com.testportal.suitmedia.views.secondscreen.SecondActivity

class FirstActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.edName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //unused
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //unused
            }

            override fun afterTextChanged(s: Editable?) {
                binding.btnNext.isEnabled = !s.isNullOrEmpty()
                binding.btnNext.setOnClickListener {
                    val name = binding.edName.text.toString()
                    val toSecondScreen = Intent(this@FirstActivity, SecondActivity::class.java)
                    toSecondScreen.putExtra(EXTRA_NAME, name)
                    startActivity(toSecondScreen)
                }
            }

        })

        binding.edPalindrome.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //unused
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //unused
            }
            override fun afterTextChanged(s: Editable?) {
                binding.btnCheck.isEnabled = !s.isNullOrEmpty()
                binding.btnCheck.setOnClickListener {
                    isPalindrome(s.toString())
                }
            }
        })
    }

    // Method 1
    private fun isPalindrome(text: String): Boolean {
        val normalizedText = text.filter { it.isLetterOrDigit() }.lowercase()
        var leftIndex = 0
        var rightIndex = normalizedText.length - 1

        while (leftIndex < rightIndex) {
            if (normalizedText[leftIndex] != normalizedText[rightIndex]) {
                isPalindromeDialog("Not Palindrome")
                return false
            }
            leftIndex++
            rightIndex--
        }
        isPalindromeDialog("isPalindrome")
        return true
    }

    private fun isPalindromeDialog(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Palindrome Result")
            .setMessage(message)
            .setCancelable(true)
            .setPositiveButton("OK") { _, _ ->

            }
        val alert = builder.create()
        alert.show()
    }

    companion object {
        const val EXTRA_NAME = "extra_name"
    }
}