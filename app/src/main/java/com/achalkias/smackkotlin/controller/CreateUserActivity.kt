package com.achalkias.smackkotlin.controller

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.achalkias.smackkotlin.R
import com.achalkias.smackkotlin.services.AuthService
import kotlinx.android.synthetic.main.activity_create_user.*
import java.util.*

class CreateUserActivity : AppCompatActivity() {

    var userAvatar = "profileDefault"
    var avatarColor = "[0.5, 0.5, 0.5, 1]"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
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

        val userEmail = createEmailText.text.toString()
        val userPasss = createPasswordText.text.toString()

        AuthService.registerUser(this, userEmail, userPasss) { registerSuccess, message ->
            if (registerSuccess) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                //Login the user
                AuthService.loginUser(this, userEmail, userPasss) { loginSuccess, message ->
                    if (loginSuccess) {
                        println(AuthService.authToken)
                        println(AuthService.userEmail)
                    }
                }

            } else {
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


}
