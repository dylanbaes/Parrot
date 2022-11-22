package com.CSE3311.parrot.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * code used based on demo code provided by virgil security under apache 2.0 license
 * Preferences
 */
class Preferences(context: Context) {

    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }
    //this sets the token
    fun setVirgilToken(virgilToken: String) {
        with(sharedPreferences.edit()) {
            putString(KEY_VIRGIL_TOKEN, virgilToken)
            apply()
        }
    }
    //this is the token with the login info im pretty sure its just the public key
    fun virgilToken(): String? {
        with(sharedPreferences) {
            return getString(KEY_VIRGIL_TOKEN, null)
        }
    }
    //call this to clear the token on logout
    fun clearVirgilToken() {
        with(sharedPreferences.edit()) {
            remove(KEY_VIRGIL_TOKEN)
            apply()
        }
    }

    companion object {
        private const val PREFERENCES_NAME = "ethree_back4app_prefs"

        private const val KEY_VIRGIL_TOKEN = "KEY_VIRGIL_TOKEN"

        @Volatile
        private var INSTANCE: Preferences? = null

        fun instance(context: Context): Preferences = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Preferences(context).also { INSTANCE = it }
        }
    }
}