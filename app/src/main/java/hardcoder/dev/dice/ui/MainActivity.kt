package hardcoder.dev.dice.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.navigation.NavigationBarView
import hardcoder.dev.dice.R
import hardcoder.dev.dice.ui.history.HistoryFragment
import hardcoder.dev.dice.ui.roll.RollFragment
import hardcoder.dev.dice.ui.settings.SettingsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: NavigationBarView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
}