package hardcoder.dev.dice.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.google.android.material.navigation.NavigationBarView
import hardcoder.dev.dice.R
import hardcoder.dev.dice.ui.history.HistoryFragment
import hardcoder.dev.dice.ui.roll.RollFragment
import hardcoder.dev.dice.ui.settings.SettingsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: NavigationBarView
    private val sharedPreferences by lazy { PreferenceManager.getDefaultSharedPreferences(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpTheme()
        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        supportFragmentManager.setFragmentResultListener(
            "onLinkClicked",
            this
        ) { _, _ ->
            onLinkClicked()
        }

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            val fragment = when (menuItem.itemId) {
                R.id.rollOption -> RollFragment()
                R.id.historyOption -> HistoryFragment()
                R.id.settingsOption -> SettingsFragment()
                else -> return@setOnItemSelectedListener false
            }

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.mainFragmentContainerView, fragment)
                .commit()

            return@setOnItemSelectedListener true
        }
    }

    private fun onLinkClicked() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainFragmentContainerView, RollFragment::class.java, null)
            .commit()
        bottomNavigationView.selectedItemId = R.id.rollOption
    }

    private fun setUpTheme() {
        val theme = when (sharedPreferences.getString("theme_type", getString(R.string.system_theme_key))) {
            getString(R.string.system_theme_key) -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            getString(R.string.light_theme_key) -> AppCompatDelegate.MODE_NIGHT_NO
            getString(R.string.dark_theme_key) -> AppCompatDelegate.MODE_NIGHT_YES
            else -> error("")
        }

        AppCompatDelegate.setDefaultNightMode(theme)
    }
}