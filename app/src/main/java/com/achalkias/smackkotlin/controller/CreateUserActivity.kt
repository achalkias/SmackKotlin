package com.achalkias.smackkotlin.controller

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.achalkias.smackkotlin.R
import com.achalkias.smackkotlin.services.AuthService
import com.achalkias.smackkotlin.services.UserDataService
import com.achalkias.smackkotlin.utilities.BROADCAST_USER_DATA_CHANGE
import kotlinx.android.synthetic.main.activity_create_user.*
import java.util.*

class CreateUserActivity : AppCompatActivity() {

    var userAvatar = "profileDefault"
    var avatarColor = "[0.5, 0.5, 0.5, 1]"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
        createSpinner.visibility = View.INVISIBLE
    }

    fun generateUserAvatar(view: View) {
        val random = Random()
        val color = random.nextInt(2)
        val avatar = random.nextInt(28)
        if (color == 0) {
            userAvatar = "light$avatar"
        } else {
            userAvatar = "dark$avatar"
        }

        val resourceId = resources.getIdentifier(userAvatar, "drawable", packageName)
        createAvatarImageView.setImageResource(resourceId)

    }

    fun createUserBtnClicked(view: View) {

        val userName = crateUserNameText.text.toString()
        val userEmail = createEmailText.text.toString()
        val userPasss = createPasswordText.text.toString()

        if (userName.isEmpty()) {
            crateUserNameText.setError(getString(R.string.name_error))
            crateUserNameText.requestFocus()
            return
        }

        if (userEmail.isEmpty()) {
            createEmailText.setError(getString(R.string.email_error))
            createEmailText.requestFocus()
            return
        }

        if (userPasss.isEmpty()) {
            createPasswordText.setError(getString(R.string.pass_error))
            createPasswordText.requestFocus()
            return
        }

        enableSpinner(true)

        AuthService.registerUser(this, userEmail, userPasss) { registerSuccess, message ->
            if (registerSuccess) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                //Login the user
                AuthService.loginUser(this, userEmail, userPasss) { loginSuccess, message ->
                    if (loginSuccess) {
                        println(AuthService.authToken)
                        println(AuthService.userEmail)
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                        // Create the user
                        AuthService.createUser(this, userName, userEmail, userAvatar, avatarColor) { createUserSucces, message ->
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                            if (createUserSucces) {
                                println(UserDataService.name)
                                println(UserDataService.id)
                                val userDataChange = Intent(BROADCAST_USER_DATA_CHANGE)
                                LocalBroadcastManager.getInstance(this).sendBroadcast(userDataChange)
                                enableSpinner(false)
                                finish()
                            } else {
                                enableSpinner(false)
                            }
                        }
                    } else {
                        enableSpinner(false)
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    }
                }

            } else {
                enableSpinner(false)
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun backgroundColorBtnClicked(view: View) {
        val random = Random()
        val r = random.nextInt(255)
        val g = random.nextInt(255)
        val b = random.nextInt(255)
        createAvatarImageView.setBackgroundColor(Color.rgb(r, g, b))
        val savedR = r.toDouble() / 255
        val savedG = g.toDouble() / 255
        val savedB = b.toDouble() / 255
        avatarColor = "[$savedR, $savedG, $savedB]"
        println(avatarColor)
    }

    fun enableSpinner(enable: Boolean) {
        if (enable) {
            createSpinner.visibility = View.VISIBLE
        } else {
            createSpinner.visibility = View.INVISIBLE
        }
        createUserBtn.isEnabled = !enable
        createAvatarImageView.isEnabled = !enable
        backgroundColorBtn.isEnabled = !enable
    }


}
