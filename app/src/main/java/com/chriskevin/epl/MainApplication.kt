package com.chriskevin.epl

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.chriskevin.epl.util.ThemeMode
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        val sharedPref =
            getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE)
        val defaultValue = ThemeMode.FOLLOW_SYSTEM.name
        val mode = sharedPref.getString(getString(R.string.pref_theme_mode_key), defaultValue) ?: defaultValue
        AppCompatDelegate.setDefaultNightMode(ThemeMode.valueOf(mode).mode)
    }
}