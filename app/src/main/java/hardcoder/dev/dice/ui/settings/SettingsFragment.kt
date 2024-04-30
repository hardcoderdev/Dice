package hardcoder.dev.dice.ui.settings

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import hardcoder.dev.dice.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findPreference<ListPreference>("theme_type")?.setOnPreferenceChangeListener { _, newValue ->
            val theme = when (newValue) {
                getString(R.string.system_theme_key) -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                getString(R.string.light_theme_key) -> AppCompatDelegate.MODE_NIGHT_NO
                getString(R.string.dark_theme_key) -> AppCompatDelegate.MODE_NIGHT_YES
                else -> error("no theme found associated with this key")
            }

            AppCompatDelegate.setDefaultNightMode(theme)
            true
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}