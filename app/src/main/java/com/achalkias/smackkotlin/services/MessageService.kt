package com.achalkias.smackkotlin.services

import android.util.Log
import com.achalkias.smackkotlin.App
import com.achalkias.smackkotlin.Model.Channel
import com.achalkias.smackkotlin.utilities.URL_GET_CHANNELS
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import org.json.JSONException


/**
 * Created by tolis on 11/5/2017.
 */
object MessageService {


    val channels = ArrayList<Channel>()

    fun getChannels(complete: (Boolean) -> Unit) {

        val channelsRequest = object : JsonArrayRequest(Method.GET, URL_GET_CHANNELS, null, Response.Listener { response ->
            try {
                for (x in 0 until response.length()) {
                    val channel = response.getJSONObject(x)
                    val name = channel.getString("name")
                    val chanDescr = channel.getString("description")
                    val chanId = channel.getString("_id")
                    val newChannel = Channel(name, chanDescr, chanId)
                    this.channels.add(newChannel)
                }

                complete(true)

            } catch (e: JSONException) {
                Log.d("JSON", "EXC" + e.localizedMessage)
                complete(false)
            }

        }, Response.ErrorListener { error ->
            Log.d("ERROR", "Could not find user $error")
            complete(false)

        }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Authorization", "Bearer ${App.prefs.authToken}")
                return headers
            }
        }

        App.volleyQueue.add(channelsRequest)

    }


}