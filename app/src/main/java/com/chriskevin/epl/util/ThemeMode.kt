package com.chriskevin.epl.util

import androidx.appcompat.app.AppCompatDelegate

enum class ThemeMode(val mode: Int, val index: Int) {
    FOLLOW_SYSTEM(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM, 0),
    LIGHT(AppCompatDelegate.MODE_NIGHT_NO, 1),
    DARK(AppCompatDelegate.MODE_NIGHT_YES, 2)
}