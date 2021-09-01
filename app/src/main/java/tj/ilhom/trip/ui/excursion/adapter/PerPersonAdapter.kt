package tj.ilhom.trip.ui.excursion.adapter

import android.annotation.SuppressLint
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import tj.ilhom.trip.R
import tj.ilhom.trip.models.excurse.PerPerson
import tj.ilhom.trip.models.excurse.Photo

class PerPersonAdapter(val mContext: Fragment) :
    RecyclerView.Adapter<PerPersonAdapter.ViewHolder>() {

    var showMore: Boolean = false
    private var data = emptyList<PerPerson>()

    fun setData(list: List<PerPerson>) {
        data = list
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val price: TextView = view.findViewById(R.id.standard_price)
        val label : TextView = view.findViewById(R.id.standard_label)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.per_person_item, viewGroup, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val pricePerson : PerPerson = data[position]
        viewHolder.label.text = pricePerson.title
        if (pricePerson.value == 0.0){
            viewHolder.price.text = "бесплатно"
        }else{
            if (position == 0){
                viewHolder.price.setTextSize(TypedValue.COMPLEX_UNIT_SP,30f)
            }else{
                viewHolder.price.setTextSize(TypedValue.COMPLEX_UNIT_SP,24f)

            }
            viewHolder.price.text = pricePerson.value.toString() + " ₽"
        }

    }

    override fun getItemCount() = data.size

    private fun getLastIndex() = data.lastIndex
}
