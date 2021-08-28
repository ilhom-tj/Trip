package tj.ilhom.trip.ui.cities.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import tj.ilhom.trip.R
import tj.ilhom.trip.models.excurse.Excurse

class ExcurseAdapter(
    fragment: Fragment
) :
    PagingDataAdapter<Excurse, ExcurseAdapter.ExcurseViewHolder>(diffUtil) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExcurseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.excursion_item, parent, false)
        return ExcurseViewHolder(view)
    }

    inner class ExcurseViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        var excurseName: TextView = view.findViewById(R.id.excursion_title)
        val excurseReview: TextView = view.findViewById(R.id.review_qty)
        val excursePrice: TextView = view.findViewById(R.id.excurse_price)
        val excureseGuideName: TextView = view.findViewById(R.id.guide_name)
        val excurseDuration: TextView = view.findViewById(R.id.guide_duration)
        val backgroundImage: ImageView = view.findViewById(R.id.imageView)

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ExcurseViewHolder, position: Int) {
        val excurse: Excurse? = getItem(position)
        holder.excurseName.text = excurse?.title
        holder.excurseReview.text = "${excurse?.rating} - ${excurse?.review_count} отзыва"
        holder.excursePrice.text = "${excurse?.price?.value} ₽"
        holder.excureseGuideName.text = "${excurse?.guide?.first_name}"
        holder.excurseDuration.text = "${excurse?.duration} часа"
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Excurse>() {
            override fun areItemsTheSame(oldItem: Excurse, newItem: Excurse) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Excurse, newItem: Excurse) =
                oldItem == newItem
        }
    }
}