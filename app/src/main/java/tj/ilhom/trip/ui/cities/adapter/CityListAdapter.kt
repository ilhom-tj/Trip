package tj.ilhom.trip.ui.cities.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import tj.ilhom.trip.R
import tj.ilhom.trip.models.City

class CityListAdapter(
    fragment : Fragment
) :
    PagingDataAdapter<City, CityListAdapter.ProductsViewHolder>(CityListAdapter.diffUtil) {

    private lateinit var context: Context
    var builder: AlertDialog.Builder? = null



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        context = parent.context
        val view: View = inflater.inflate(R.layout.city_list_item, null)
        builder = AlertDialog.Builder(context)
        return ProductsViewHolder(view)
    }

    inner class ProductsViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        var cityName: TextView = view.findViewById(R.id.city_name)
        var locationQty : TextView = view.findViewById(R.id.locations_qty)
    }

    interface ProductsActions {
        fun goToProduct(id: Int)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val city: City? = getItem(position)
        Log.e("CITY",city?.name_ru.toString())
        holder.cityName.text = city?.name_ru
        holder.locationQty.text = city?.experience_count.toString()


    }
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<City>() {
            override fun areItemsTheSame(oldItem: City, newItem: City) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: City, newItem: City) =
                oldItem == newItem
        }
    }
}