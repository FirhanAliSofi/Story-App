package com.firhanalisofi.submissionstoryapp.view.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.firhanalisofi.submissionstoryapp.databinding.ActivityRegisterBinding
import com.firhanalisofi.submissionstoryapp.view.ViewModelFactory
import com.firhanalisofi.submissionstoryapp.view.login.LoginActivity
import com.firhanalisofi.submissionstoryapp.helper.Result

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private val registerViewModel: RegisterViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        viewModelFactory = ViewModelFactory.getInstance(binding.root.context)
        showLoading(false)
        setupAnimation()

        binding.RegisterButton.setOnClickListener {
            sendDataUser()
        }
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun sendDataUser() {
        val name = binding.edName.text.toString().trim()
        val email = binding.edEmail.text.toString().trim()
        val password = binding.edPassword.text.toString().trim()

        if (!TextUtils.isEmpty(name) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() && !TextUtils.isEmpty(password)) {
            processRegister(name, email, password)
        } else {
            Toast.makeText(this, "Invalid Input. Check your data.", Toast.LENGTH_SHORT).show()
        }

    }

    private fun formRegister() {
        binding.edName.text?.clear()
        binding.edEmail.text?.clear()
        binding.edPassword.text?.clear()
    }

    private fun processRegister(name: String, email: String, password: String) {
        registerViewModel.postRegister(name, email, password).observe(this) {
            when (it) {
                is Result.Loading -> {
                    showLoading(true)
                    Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show()
                }
                is Result.Error -> {
                    showLoading(false)
                    Toast.makeText(this, "There Is an Error", Toast.LENGTH_SHORT).show()
                }
                is Result.Success -> {
                    formRegister()
                    showSuccessDialog(email)
                }
            }
        }
    }

    private fun showSuccessDialog(email: String) {
        AlertDialog.Builder(this).apply {
            setTitle(DIALOG_TITLE)
            setMessage(DIALOG_MESSAGE.format(email))
            setPositiveButton("OK") { _, _ ->
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                finish()
            }
            create().show()
        }
    }


    private fun setupAnimation() {

        val image = ObjectAnimator.ofFloat(binding.imageView, View.ALPHA, 1f).setDuration(500)
        val tvSignup = ObjectAnimator.ofFloat(binding.tvSignUp, View.ALPHA, 1f).setDuration(500)
        val edName = ObjectAnimator.ofFloat(binding.edName, View.ALPHA, 1f).setDuration(500)
        val edEmail = ObjectAnimator.ofFloat(binding.emailTextInputLayout, View.ALPHA, 1f).setDuration(500)
        val edPassword = ObjectAnimator.ofFloat(binding.passwordTextInputLayout, View.ALPHA, 1f).setDuration(500)
        val btnRegister2 = ObjectAnimator.ofFloat(binding.RegisterButton, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {

            playSequentially(
                image,
                AnimatorSet().apply { playTogether(tvSignup, edName, edEmail, edPassword) },
                btnRegister2,
            )
            start()
        }
    }
    private companion object {
        const val DIALOG_TITLE = "Sign Up Successful"
        const val DIALOG_MESSAGE = "Akun dengan email %s sudah berhasil dibuat. Anda sekarang dapat login."
    }
}