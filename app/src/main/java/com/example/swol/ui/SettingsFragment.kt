package com.example.swol.ui

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.swol.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }

}