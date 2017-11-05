package com.achalkias.smackkotlin.Model

/**
 * Created by tolis on 11/5/2017.
 */
class Channel(val name: String, val description: String, val id: String) {

    override fun toString(): String {
        return "#$name"
    }

}