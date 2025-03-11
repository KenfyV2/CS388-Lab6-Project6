package com.codepath.articlesearch

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.codepath.articlesearch.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private lateinit var darkModeSwitch: Switch

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the fragment layout
        val binding = FragmentSettingsBinding.inflate(inflater, container, false)

        // Find the dark mode switch
        darkModeSwitch = binding.switchDarkMode

        // Load the current setting for dark mode from SharedPreferences
        val prefs = requireActivity().getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        val isDarkModeEnabled = prefs.getBoolean("darkMode", false)
        darkModeSwitch.isChecked = isDarkModeEnabled

        // Set a listener to save the setting when it changes
        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            val editor = prefs.edit()
            editor.putBoolean("darkMode", isChecked)
            editor.apply()

            // Optionally, apply the dark mode change immediately
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            requireActivity().recreate()
        }

        return binding.root
    }
}
