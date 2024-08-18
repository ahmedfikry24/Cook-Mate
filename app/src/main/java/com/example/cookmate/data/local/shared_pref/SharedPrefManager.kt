package com.example.cookmate.data.local.shared_pref

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object SharedPrefManager {

    private var sharedPref: SharedPreferences? = null

    private const val SHARED_NAME = "shredPrefName"
    private const val LOGIN_KEY = "isLoginKey"

    fun init(context: Context): SharedPreferences {
        return sharedPref ?: run {
            sharedPref = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)
            sharedPref!!
        }
    }

    var isLogin: Boolean
        get() = sharedPref?.getBoolean(LOGIN_KEY, false) ?: false
        set(value) = sharedPref?.edit()?.putBoolean(LOGIN_KEY, value)?.apply() ?: Unit
}
