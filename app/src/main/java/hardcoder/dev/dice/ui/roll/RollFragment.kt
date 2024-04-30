package hardcoder.dev.dice.ui.roll

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.view.animation.BounceInterpolator
import android.widget.ImageView
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import hardcoder.dev.dice.R
import hardcoder.dev.dice.ui.history.DiceRollItem
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
    private var diceRollItemsList = mutableListOf<DiceRollItem>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        diceImageView = view.findViewById(R.id.diceImageView)

        val lastNumber = sharedPreferences.getInt("lastNumber", -1)
        if (lastNumber != -1) diceImageView.setImageResource(diceEdgesSet.elementAt(lastNumber))

        diceImageView.setOnClickListener {
            rollDice()
        }
    }

    private fun rollDice() {
        val randomNumber = Random.nextInt(0..5)
        playSound()
        playDiceAnimation(randomNumber)
        sharedPreferences.edit { putInt("lastNumber", randomNumber) }

        if (diceRollItemsList.isEmpty()) {
            val diceHistoryListJson = sharedPreferences.getString("diceHistoryListJson", "")
            diceRollItemsList = if (diceHistoryListJson.isNullOrBlank()) {
                mutableListOf()
            } else {
                Gson().fromJson(
                    diceHistoryListJson,
                    Array<DiceRollItem>::class.java
                ).toMutableList()
            }
        }

        diceRollItemsList.add(
            DiceRollItem(
                diceNumber = randomNumber.inc(),
                dateTime = System.currentTimeMillis()
            )
        )

        val diceHistoryListJson = Gson().toJson(diceRollItemsList)
        sharedPreferences.edit { putString("diceHistoryListJson", diceHistoryListJson) }
    }

    private fun playSound() {
        val soundToPlay = setOf(R.raw.dice_sound_1, R.raw.dice_sound_2)
        val player = MediaPlayer.create(requireContext(), soundToPlay.random())
        player.start()
    }

    private fun playDiceAnimation(randomNumber: Int) {
        diceImageView
            .animate()
            .rotationBy(360f)
            .setDuration(1000)
            .setInterpolator(BounceInterpolator())
            .withStartAction {
                diceImageView.isEnabled = false
            }
            .withEndAction {
                diceImageView.apply {
                    setImageResource(diceEdgesSet.elementAt(randomNumber))
                    isEnabled = true
                }
            }
    }
}