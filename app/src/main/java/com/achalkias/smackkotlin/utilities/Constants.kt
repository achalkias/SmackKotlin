package com.achalkias.smackkotlin.utilities

/**
 * Created by tolis on 11/5/2017.
 */

const val URL_BASE = "https://achdevsmack.herokuapp.com/v1/"
const val SOCKET_URL = "https://achdevsmack.herokuapp.com/"
const val URL_REGISTER = "${URL_BASE}account/register"
const val URL_LOGIN = "${URL_BASE}account/login"
const val URL_CREATE_USER = "${URL_BASE}user/add"
const val URL_GET_USER = "${URL_BASE}user/byEmail/"
const val URL_GET_CHANNELS = "${URL_BASE}channel/"
const val URL_GET_MESSAGES = "${URL_BASE}message/byChannel/"

//BroadCast Constants
const val BROADCAST_USER_DATA_CHANGE = "broadcast_user_data_change"