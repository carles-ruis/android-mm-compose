package com.carles.settings.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.carles.settings.R
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.android.viewmodel.ext.android.viewModel

class SettingsFragment : PreferenceFragmentCompat() {

    private val viewModel by viewModel<SettingsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = settings_toolbar as MaterialToolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setTitle(R.string.settings)
        }
        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        val cacheListPreference = findPreference<ListPreference>(getString(R.string.preferences_cache_key))
        cacheListPreference?.summaryProvider = ListPreference.SimpleSummaryProvider.getInstance()
        cacheListPreference?.setOnPreferenceChangeListener { preference, _ ->
            when (preference.key) {
                getString(R.string.preferences_cache_key) -> viewModel.updateCacheExpiration()
            }
            true
        }
    }
}