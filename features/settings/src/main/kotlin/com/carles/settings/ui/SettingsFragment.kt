package com.carles.settings.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.carles.common.Navigate
import com.carles.common.databinding.FragmentSettingsBinding
import com.carles.common.ui.component.HasToolbar
import com.carles.settings.R
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SettingsFragment : PreferenceFragmentCompat(), HasToolbar {

    private val viewModel: SettingsViewModel by viewModel()
    private val navigate: Navigate by lazy {
        (requireActivity() as AndroidScopeComponent).scope.get { parametersOf(requireActivity()) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSettingsBinding.bind(view)

        binding.settingsToolbar.toolbar.apply {
            initDefaultToolbar(this, activity as AppCompatActivity, navigate)
            setTitle(R.string.settings)
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        val cacheListPreference = findPreference<ListPreference>(getString(R.string.preferences_cache_key))
        cacheListPreference?.summaryProvider = ListPreference.SimpleSummaryProvider.getInstance()
        cacheListPreference?.setOnPreferenceChangeListener { preference, _ ->
            when (preference.key) {
                getString(R.string.preferences_cache_key) -> viewModel.onPreferenceCacheChanged()
            }
            true
        }
    }
}