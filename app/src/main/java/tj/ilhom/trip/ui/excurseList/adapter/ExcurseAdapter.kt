package tj.ilhom.trip.ui.excurseList.adapter

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
import com.bumptech.glide.Glide
import tj.ilhom.trip.R
import tj.ilhom.trip.models.excurse.Excurse

class ExcurseAdapter(
    fragment: Fragment,
    private val excursionEvent: ExcursionEvent
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

    @SuppressLint("SetTextI18n", "CheckResult")
    override fun onBindViewHolder(holder: ExcurseViewHolder, position: Int) {
        val excurse: Excurse? = getItem(position)
        holder.excurseName.text = excurse?.title
        holder.excurseReview.text = "${excurse?.rating} - ${excurse?.review_count} отзыва"
        if (excurse?.price?.per_person?.isNotEmpty() == true) {
            holder.excursePrice.text = "${excurse.price.per_person[0].value} ₽"
        } else if (excurse?.price?.per_group != null) {
            holder.excursePrice.text = "${excurse.price.per_group.value} ₽"
        }else{
            holder.excursePrice.text = "${excurse?.price?.value} ₽"
        }
        holder.excureseGuideName.text = "${excurse?.guide?.first_name}"
        holder.excurseDuration.text = "${excurse?.duration} часа"
        Glide.with(holder.itemView.context)
            .load(excurse?.photos?.get(0)?.thumbnail)
            .into(holder.backgroundImage)

        holder.itemView.setOnClickListener {
            if (excurse != null) {
                excursionEvent.excursionClick(excurse)
            }
        }
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