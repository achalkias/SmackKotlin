package com.achalkias.smackkotlin

import android.app.Application
import com.achalkias.smackkotlin.utilities.SharedPrefs
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

/**
 * Created by tolis on 11/5/2017.
 */
class App : Application() {

    companion object {
        lateinit var prefs: SharedPrefs
        lateinit var volleyQueue: RequestQueue
    }


    override fun onCreate() {
        prefs = SharedPrefs(applicationContext)
        volleyQueue = Volley.newRequestQueue(applicationContext)
        super.onCreate()
    }
}