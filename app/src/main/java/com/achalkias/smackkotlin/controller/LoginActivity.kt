package com.achalkias.smackkotlin.controller

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.achalkias.smackkotlin.R
import com.achalkias.smackkotlin.services.AuthService
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginSpinner.visibility = View.INVISIBLE
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

        enableSpinner(true)
        hideKeyboard()

        AuthService.loginUser(email, password) { loginSuccess, message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            if (loginSuccess) {
                AuthService.findUserByEmail(this) { findSuccess, m ->
                    Toast.makeText(this, m, Toast.LENGTH_SHORT).show()
                    if (findSuccess) {
                        enableSpinner(false)
                        finish()
                    } else {
                        enableSpinner(false)
                    }
                }
            } else {
                enableSpinner(false)
            }

        }
    }

    fun enableSpinner(enable: Boolean) {
        if (enable) {
            loginSpinner.visibility = View.VISIBLE
        } else {
            loginSpinner.visibility = View.INVISIBLE
        }
        loginLoginBtn.isEnabled = !enable
        loginCreateUserBtn.isEnabled = !enable
    }

    fun hideKeyboard() {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (inputManager.isAcceptingText) {
            inputManager.hideSoftInputFromInputMethod(currentFocus.windowToken, 0)
        }
    }


}
