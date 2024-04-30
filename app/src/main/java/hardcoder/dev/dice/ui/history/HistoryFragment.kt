package hardcoder.dev.dice.ui.history

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.core.content.edit
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import hardcoder.dev.dice.R

class HistoryFragment : Fragment(R.layout.fragment_history) {

    private val sharedPrefs by lazy { PreferenceManager.getDefaultSharedPreferences(requireContext()) }
    private val historyAdapter = HistoryAdapter()
    private lateinit var historyRecyclerView: RecyclerView
    private lateinit var noDiceRollPerformedTextView: TextView
    private lateinit var letsRollTextView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        findViews()

        letsRollTextView.setOnClickListener {
            setFragmentResult("onLinkClicked", bundleOf())
        }

        val allDiceRollItemsList = getAllDiceHistory()
        historyRecyclerView.adapter = historyAdapter
        historyAdapter.addItems(list = allDiceRollItemsList.reversed())
        setUpViews(isDiceHistoryEmpty = allDiceRollItemsList.isEmpty())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.history_toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.clearHistoryOption) {
            sharedPrefs.edit { remove("diceHistoryListJson") }
            historyAdapter.clearItems()
            setUpViews(isDiceHistoryEmpty = true)
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun findViews() {
        historyRecyclerView = requireView().findViewById(R.id.historyRecyclerView)
        noDiceRollPerformedTextView = requireView().findViewById(R.id.noDiceRollPerformedTextView)
        letsRollTextView = requireView().findViewById(R.id.letsRollTextView)
    }

    private fun setUpViews(isDiceHistoryEmpty: Boolean) {
        historyRecyclerView.isVisible = !isDiceHistoryEmpty
        noDiceRollPerformedTextView.isVisible = isDiceHistoryEmpty
        letsRollTextView.isVisible = isDiceHistoryEmpty
    }

    private fun getAllDiceHistory(): List<DiceRollItem> {
        val diceHistoryListJson = sharedPrefs.getString("diceHistoryListJson", "")
        return if (!diceHistoryListJson.isNullOrEmpty()) {
            Gson().fromJson(diceHistoryListJson, Array<DiceRollItem>::class.java).toList()
        } else {
            emptyList()
        }
    }
}