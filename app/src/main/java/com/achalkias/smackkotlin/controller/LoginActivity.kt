package com.achalkias.smackkotlin.controller

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.achalkias.smackkotlin.R
import com.achalkias.smackkotlin.services.AuthService
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun loginCreateUserBtnClicked(view: View) {
        val createUserIntent = Intent(this, CreateUserActivity::class.java)
        startActivity(createUserIntent)
        finish()
    }

    fun loginLoginBtnClicked(view: View) {
        val email = loginEmailTxt.text.toString()
        val password = loginPassTxt.text.toString()

        if (email.isEmpty()) {
            loginEmailTxt.setError(getString(R.string.email_error))
            loginEmailTxt.requestFocus()
            return
        }
        if (password.isEmpty()) {
            loginPassTxt.setError(getString(R.string.pass_error))
            loginPassTxt.requestFocus()
            return
        }

        AuthService.loginUser(this, email, password) { loginSuccess, message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            if (loginSuccess) {
                AuthService.findUserByEmail(this) { findSuccess, message ->
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    if (findSuccess) {
                        finish()
                    }
                }
            }

        }


    }

}
