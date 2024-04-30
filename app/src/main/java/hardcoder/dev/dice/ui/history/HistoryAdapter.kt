package hardcoder.dev.dice.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hardcoder.dev.dice.R
import java.text.DateFormat

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private var diceRollItemsList = emptyList<DiceRollItem>()

    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val diceImageView: ImageView = itemView.findViewById(R.id.diceImageView)
        private val dateOfRollTextView: TextView = itemView.findViewById(R.id.dateOfRollTextView)
        private val numberOfRollTextView: TextView =
            itemView.findViewById(R.id.numberOfRollTextView)

        fun bind(diceRollItem: DiceRollItem) {
            val dateTimeInstance =
                DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT)
            dateOfRollTextView.text = itemView.context.getString(
                R.string.date_of_roll_format,
                dateTimeInstance.format(diceRollItem.dateTime)
            )
            numberOfRollTextView.text = itemView.context.getString(
                R.string.number_of_roll_format,
                diceRollItem.diceNumber
            )
            diceImageView.setImageResource(
                when (diceRollItem.diceNumber) {
                    1 -> R.drawable.dice1
                    2 -> R.drawable.dice2
                    3 -> R.drawable.dice3
                    4 -> R.drawable.dice4
                    5 -> R.drawable.dice5
                    6 -> R.drawable.dice6
                    else -> error("no image with this number found!")
                }
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HistoryViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
    )

    override fun getItemCount() = diceRollItemsList.size

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) =
        holder.bind(diceRollItemsList[position])

    fun addItems(list: List<DiceRollItem>) {
        diceRollItemsList = list
    }

    fun clearItems() {
        notifyItemRangeRemoved(0, diceRollItemsList.size)
        diceRollItemsList = emptyList()
    }
}