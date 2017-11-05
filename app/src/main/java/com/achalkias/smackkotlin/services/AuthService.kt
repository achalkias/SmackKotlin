package com.achalkias.smackkotlin.services

import android.content.Context
import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import com.achalkias.smackkotlin.App
import com.achalkias.smackkotlin.utilities.*
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by tolis on 11/5/2017.
 */
object AuthService {

//    var isLoggedIn = false
//    var userEmail = ""
//    var authToken = ""

    fun registerUser(email: String, password: String, complete: (Boolean, String) -> Unit) {

        val jsonBody = JSONObject()
        jsonBody.put("email", email)
        jsonBody.put("password", password)
        val requestBody = jsonBody.toString()

        val registerRequest = object : StringRequest(Method.POST, URL_REGISTER, Response.Listener { response ->
            println(response)
            complete(true, response)

        }, Response.ErrorListener { error ->
            Log.d("ERROR", "Could not register user $error")
            complete(false, error.toString())
        }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }
        }

        App.volleyQueue.add(registerRequest)

    }

    fun loginUser(email: String, password: String, complete: (Boolean, String) -> Unit) {

        val jsonBody = JSONObject()
        jsonBody.put("email", email)
        jsonBody.put("password", password)
        val requestBody = jsonBody.toString()

        val loginRequest = object : JsonObjectRequest(Method.POST, URL_LOGIN, null, Response.Listener { response ->
            println(response)
            try {
                App.prefs.authToken = response.getString("token")
                App.prefs.userEmail = response.getString("user")
                App.prefs.isLoggedIn = true
                complete(true, "Successfully logged in")
            } catch (e: JSONException) {
                complete(false, "Try again later")
                Log.d("JSON", "Could not Login user ${e.localizedMessage}")
            }
        }, Response.ErrorListener { error ->
            Log.d("ERROR", "Could not Login user $error")
            complete(false, error.toString())

        }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }
        }

        App.volleyQueue.add(loginRequest)

    }

    fun createUser(email: String, name: String, avatarName: String, avatarColor: String, complete: (Boolean, String) -> Unit) {

        val jsonBody = JSONObject()
        jsonBody.put("name", name)
        jsonBody.put("email", email)
        jsonBody.put("avatarName", avatarName)
        jsonBody.put("avatarColor", avatarColor)
        val requestBody = jsonBody.toString()


        val createRequest = object : JsonObjectRequest(Method.POST, URL_CREATE_USER, null, Response.Listener { response ->
            println(response)
            try {

                UserDataService.name = response.getString("name")
                UserDataService.email = response.getString("email")
                UserDataService.avatarName = response.getString("avatarName")
                UserDataService.avatarColor = response.getString("avatarColor")
                UserDataService.id = response.getString("_id")

                complete(true, "User created Successfully")

            } catch (e: JSONException) {
                complete(false, "Try again later")
                Log.d("JSON", "EXC " + e.localizedMessage)
            }

        }, Response.ErrorListener { error ->
            Log.d("ERROR", "Could not create user $error")
            complete(false, error.toString())
        }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Authorization", "Bearer ${App.prefs.authToken}")
                return headers
            }
        }
        App.volleyQueue.add(createRequest)

    }

    fun findUserByEmail(context: Context, complete: (Boolean, String) -> Unit) {
        val url = "$URL_GET_USER${App.prefs.userEmail}"
        print(url)
        val findUserRequest = object : JsonObjectRequest(Method.GET, url, null, Response.Listener { response ->
            println("Find user by email: $response")
            try {
                UserDataService.name = response.getString("name")
                UserDataService.email = response.getString("email")
                UserDataService.avatarName = response.getString("avatarName")
                UserDataService.avatarColor = response.getString("avatarColor")
                UserDataService.id = response.getString("_id")

                val userDataChange = Intent(BROADCAST_USER_DATA_CHANGE)
                LocalBroadcastManager.getInstance(context).sendBroadcast(userDataChange)
                complete(true, "User found")

            } catch (e: JSONException) {
                Log.d("JSON", "EXC" + e.localizedMessage)
                complete(false, "There was an error")
            }

        }, Response.ErrorListener { error ->
            Log.d("ERROR", "Could not find user $error")
            complete(false, error.toString())
        }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Authorization", "Bearer ${App.prefs.authToken}")
                headers.put("Accept", "application/json")
                return headers
            }
        }

        App.volleyQueue.add(findUserRequest)
    }


}