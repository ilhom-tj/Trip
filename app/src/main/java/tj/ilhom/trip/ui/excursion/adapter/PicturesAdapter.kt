package tj.ilhom.trip.ui.excursion.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import tj.ilhom.trip.R
import tj.ilhom.trip.models.excurse.Photo

class PicturesAdapter(val mContext: Fragment) :
    RecyclerView.Adapter<PicturesAdapter.ViewHolder>() {

    var showMore: Boolean = false
    private var data = emptyList<Photo>()

    fun setData(list: List<Photo>) {
        data = list
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.photo)
        val showMorePhoto: ConstraintLayout = view.findViewById(R.id.show_more_photo)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.excurse_photo, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val photo: Photo = data[position]
        Glide.with(viewHolder.itemView.context).load(photo.thumbnail).into(viewHolder.icon)
        if (position == getLastIndex()){
            viewHolder.showMorePhoto.isVisible = showMore
        }
    }

    override fun getItemCount() = data.size

    private fun getLastIndex() = data.lastIndex
}
