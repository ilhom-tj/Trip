package tj.ilhom.trip.ui.excurseList.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import tj.ilhom.trip.R
import tj.ilhom.trip.models.excurse.Photo

class ImageSliderAdapter() :
    RecyclerView.Adapter<ImageSliderAdapter.ViewHolder>() {
    private var data = emptyList<Photo>()

    fun setData(list: List<Photo>) {
        data = list
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.image_slider, viewGroup, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val image: Photo = data[position]
        Glide.with(viewHolder.itemView.context)
            .load(image.medium)
            .into(viewHolder.image)
    }

    override fun getItemCount() = data.size

    private fun getLastIndex() = data.lastIndex
}
