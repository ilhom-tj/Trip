package tj.ilhom.trip.ui.excurseList.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import me.relex.circleindicator.CircleIndicator2
import tj.ilhom.trip.R
import tj.ilhom.trip.models.excurse.Excurse
import tj.ilhom.trip.models.excurse.Photo


class ExcurseAdapter(
    fragment: Fragment,
    private val excursionEvent: ExcursionEvent
) :
    PagingDataAdapter<Excurse, ExcurseAdapter.ExcurseViewHolder>(diffUtil) {

    var attached: Boolean = false
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExcurseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.excursion_item_2, parent, false)
        return ExcurseViewHolder(view)
    }

    inner class ExcurseViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        var excurseName: TextView = view.findViewById(R.id.excursion_title)
        val excurseReview: TextView = view.findViewById(R.id.review_qty)
        val excursePrice: TextView = view.findViewById(R.id.excurse_price)
        val excurseDuration: TextView = view.findViewById(R.id.guide_duration)
        val rating: RatingBar = view.findViewById(R.id.ratingBar)
        val backgroundImage: RecyclerView = view.findViewById(R.id.imageSlider)
        val indicator: CircleIndicator2 = view.findViewById(R.id.indicator)
        val description: TextView = view.findViewById(R.id.description)
    }

    @SuppressLint("SetTextI18n", "CheckResult")
    override fun onBindViewHolder(holder: ExcurseViewHolder, position: Int) {

        val excurse: Excurse? = getItem(position)

        holder.description.text = excurse?.tagline
        holder.excurseName.text = excurse?.title
        holder.excurseReview.text = "${excurse?.review_count} отзыва"
        val curency = when (excurse?.price?.currency) {
            "EUR" -> {
                "€"
            }
            "USD" -> {
                "$"
            }
            else -> "₽"
        }
        if (excurse?.price?.per_person?.isNotEmpty() == true) {
            holder.excursePrice.text = "${excurse.price.per_person[0].value} $curency"
        } else if (excurse?.price?.per_group != null) {
            holder.excursePrice.text = "${excurse.price.per_group.value} $curency"
        } else {
            holder.excursePrice.text = "${excurse?.price?.value} $curency"
        }
        holder.excurseDuration.text = "${excurse?.duration} часа"
        holder.rating.rating = excurse?.rating?.toFloat() ?: 0f

        holder.itemView.setOnClickListener {
            if (excurse != null) {
                excursionEvent.excursionClick(excurse)
            }
        }
        val imageAdapter = ImageSliderAdapter(R.layout.image_slider)
        holder.backgroundImage.adapter = imageAdapter
        if (excurse?.photos?.isNotEmpty() == true) {
            // FIXME ILHOM recycler view ай пушти хами гум мешид, надо разобраться
//            if (excurse.photos.size > 4) {
//                val newImageArr = mutableListOf<Photo>()
//                excurse.photos.forEachIndexed { index, photo ->
//                    if (index < 4) {
//                        Log.e("Index", index.toString())
//                        newImageArr.add(photo)
//                    }
//                }
//                imageAdapter.submitList(newImageArr ?: emptyList())
//            }
//        } else {
            imageAdapter.submitList(excurse.photos)
        }

        if (holder.backgroundImage.onFlingListener == null) {
            val pagerSnapHelper = PagerSnapHelper()
            pagerSnapHelper.attachToRecyclerView(holder.backgroundImage)
            holder.indicator.attachToRecyclerView(holder.backgroundImage, pagerSnapHelper)
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