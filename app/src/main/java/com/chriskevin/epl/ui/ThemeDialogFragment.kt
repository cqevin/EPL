package com.chriskevin.epl.ui

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.DialogFragment
import com.chriskevin.epl.R
import com.chriskevin.epl.util.ThemeMode
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ThemeDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val items = resources.getStringArray(R.array.theme_mode)

        val sharedPref = requireActivity().getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )
        val getMode = sharedPref.getString(
            getString(R.string.pref_theme_mode_key), ThemeMode.FOLLOW_SYSTEM.name
        ) ?: ThemeMode.FOLLOW_SYSTEM.name
        val currentMode = ThemeMode.valueOf(getMode).index

        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.dark_mode))
            .setSingleChoiceItems(items, currentMode) { _, which ->
                when (which) {
                    0 -> updateTheme(sharedPref, ThemeMode.FOLLOW_SYSTEM.name)
                    1 -> updateTheme(sharedPref, ThemeMode.LIGHT.name)
                    2 -> updateTheme(sharedPref, ThemeMode.DARK.name)
                }
            }
            .create()
    }

    private fun updateTheme(sharedPref: SharedPreferences, modeName: String) {
        sharedPref.edit().apply {
            putString(getString(R.string.pref_theme_mode_key), modeName)
            apply()
        }
        AppCompatDelegate.setDefaultNightMode(ThemeMode.valueOf(modeName).mode)
    }

    companion object {
        const val TAG = "ThemeDialog"
    }
}