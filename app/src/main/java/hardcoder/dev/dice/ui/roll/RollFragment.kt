package hardcoder.dev.dice.ui.roll

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import hardcoder.dev.dice.R
import kotlin.random.Random
import kotlin.random.nextInt

class RollFragment : Fragment(R.layout.fragment_roll) {

    private val sharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(
            requireContext()
        )
    }
    private lateinit var diceImageView: ImageView
    private val diceEdgesSet = setOf(
        R.drawable.dice1,
        R.drawable.dice2,
        R.drawable.dice3,
        R.drawable.dice4,
        R.drawable.dice5,
        R.drawable.dice6
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        diceImageView = view.findViewById(R.id.diceImageView)

        val lastNumber = sharedPreferences.getInt("lastNumber", -1)
        if (lastNumber != -1) diceImageView.setImageResource(diceEdgesSet.elementAt(lastNumber))

        diceImageView.setOnClickListener {
            val randomNumber = Random.nextInt(0..5)
            diceImageView.setImageResource(diceEdgesSet.elementAt(randomNumber))
            sharedPreferences.edit { putInt("lastNumber", randomNumber) }
        }
    }
}