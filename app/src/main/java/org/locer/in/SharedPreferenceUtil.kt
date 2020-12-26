package org.locer.`in`

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class SharedPreferenceUtil(context: Context) {

    private val sharedPreferences: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    val prefIsFirstRun: Boolean
        get() = sharedPreferences.getBoolean(PREF_IS_FIRST_RUN, false)

    val isLoggedIn: Boolean
        get() = sharedPreferences.getBoolean(LOGGED_IN, false)

    fun putPrefIsFirstRun(isFirstRun: Boolean) =
        sharedPreferences.edit { putBoolean(PREF_IS_FIRST_RUN, isFirstRun) }

    fun showIntro(): Boolean = sharedPreferences.getBoolean(LAUNCHED_INTRO, true)
    fun setIntroShown() = sharedPreferences.edit { putBoolean(LAUNCHED_INTRO, false) }

    fun putPrefLoggedIn() = sharedPreferences.edit { putBoolean(LOGGED_IN, isLoggedIn) }
    fun setLoggedIn() = sharedPreferences.edit { putBoolean(LOGGED_IN, true) }

    companion object {
        private const val LOGGED_IN = "User logged in"
        const val LAUNCHED_INTRO = "Launched intro screen once"
        private const val PREF_IS_FIRST_RUN = "isFirstRun"
    }

}
