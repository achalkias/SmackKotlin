package com.achalkias.smackkotlin.services

import android.graphics.Color
import java.util.*

/**
 * Created by tolis on 11/5/2017.
 */
object UserDataService {

    var id = ""
    var avatarColor = ""
    var avatarName = ""
    var email = ""
    var name = ""


    fun logout() {
        id = ""
        avatarColor = ""
        avatarName = ""
        email = ""
        name = ""
        AuthService.authToken = ""
        AuthService.userEmail = ""
        AuthService.isLoggedIn = false
    }


    fun returnAvatarColor(components: String): Int {
        val strippedColor = components
                .replace("[", "")
                .replace("]", "")
                .replace(",", "")
        var r = 0
        var g = 0
        var b = 0
        var scanner = Scanner(strippedColor)
        if (scanner.hasNext()) {
            r = (scanner.nextDouble() * 255).toInt()
            g = (scanner.nextDouble() * 255).toInt()
            b = (scanner.nextDouble() * 255).toInt()
        }
        return Color.rgb(r, g, b)
    }

}