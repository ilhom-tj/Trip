package tj.ilhom.trip.ui.excursion.adapter

import android.annotation.SuppressLint
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tj.ilhom.trip.R
import tj.ilhom.trip.models.excurse.PerPerson
import java.util.*
import kotlin.math.roundToInt

class PerPersonAdapter(val curency: String) :
    RecyclerView.Adapter<PerPersonAdapter.ViewHolder>() {

    private var data = emptyList<PerPerson>()

    fun setData(list: List<PerPerson>) {
        val mutableList = list.toMutableList()
        for (i in 0 until mutableList.size) {
            if (i >= mutableList.size) break
            val tempArray = mutableList.filter { it.value == mutableList[i].value }
            if (tempArray.size > 1) {
                val perPerson = PerPerson(tempArray.first().id, "", false, tempArray.first().value)
                tempArray.forEach {
                    if (perPerson.title.isEmpty()) {
                        perPerson.title = it.title
                    } else perPerson.title = perPerson.title + ", ${it.title.lowercase(Locale.getDefault())}"
                }
                mutableList.removeAll(tempArray)
                mutableList.add(perPerson)
            }
        }
        data = mutableList
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val price: TextView = view.findViewById(R.id.standard_price)
        val label: TextView = view.findViewById(R.id.standard_label)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.per_person_item, viewGroup, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val pricePerson = data[position]
        viewHolder.label.text = pricePerson.title
        if (pricePerson.value == 0.0) viewHolder.price.text = "бесплатно"
        else {
            if (position == 0) viewHolder.price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30f)
            else viewHolder.price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f)
            viewHolder.price.text = pricePerson.value.roundToInt().toString() + " $curency"
        }
    }

    override fun getItemCount() = data.size

    private fun getLastIndex() = data.lastIndex
}