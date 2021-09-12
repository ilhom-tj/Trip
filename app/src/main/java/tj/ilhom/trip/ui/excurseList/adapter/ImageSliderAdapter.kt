package tj.ilhom.trip.ui.excurseList.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import tj.ilhom.trip.R
import tj.ilhom.trip.models.excurse.Photo

class ImageSliderAdapter(val layout: Int) :
    ListAdapter<Photo, ImageSliderAdapter.ViewHolder>(diffUtil) {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(layout, viewGroup, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        getItem(position)?.let {
            Glide.with(viewHolder.itemView.context)
                .load(it.medium)
                .into(viewHolder.image)
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo) =
                oldItem.medium == newItem.medium

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo) = oldItem == newItem

        }
    }
}
