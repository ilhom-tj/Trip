package tj.ilhom.trip.ui.excursion.commentDialog.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import tj.ilhom.trip.R
import tj.ilhom.trip.models.review.Review

class ReviewAdapter(
    val fragment: Fragment
) :
    PagingDataAdapter<Review, ReviewAdapter.ProductsViewHolder>(diffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.review_item, parent, false)
        return ProductsViewHolder(view)
    }

    inner class ProductsViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        val authorName: TextView = view.findViewById(R.id.author_name)
        val authorAvatar: CircleImageView = view.findViewById(R.id.circleImageView)
        val reviewBody: TextView = view.findViewById(R.id.review_body)
        val date: TextView = view.findViewById(R.id.review_date)
        val guideLay: ConstraintLayout = view.findViewById(R.id.guide_answer_layout)
        val guideAvatar: CircleImageView = view.findViewById(R.id.guide_avatar)
        val guideName: TextView = view.findViewById(R.id.guide_name)
        val guideReviewBody: TextView = view.findViewById(R.id.guide_review_body)

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val review: Review? = getItem(position)
        Log.e(review?.name , review?.created_on.toString())
        holder.authorName.text = review?.name
        holder.reviewBody.text = review?.text
        if (review?.avatar?.small != null) {
            Glide.with(fragment.requireContext()).load(review.avatar.medium)
                .into(holder.authorAvatar)
        }
        holder.date.text = review?.created_on
        holder.guideLay.isVisible = review?.guide_reply != null
        if (holder.guideLay.isVisible) {
            holder.guideName.text = review?.guide_reply?.name
            holder.guideReviewBody.text = review?.guide_reply?.name
            if (review?.guide_reply?.avatar?.medium != null) {
                Glide.with(fragment.requireContext()).load(review.guide_reply.avatar.medium)
                    .into(holder.guideAvatar)
            }
        }
        holder.apply {

        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Review>() {
            override fun areItemsTheSame(oldItem: Review, newItem: Review) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Review, newItem: Review) =
                oldItem == newItem
        }
    }
}