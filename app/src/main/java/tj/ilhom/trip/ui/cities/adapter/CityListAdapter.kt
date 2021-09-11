package tj.ilhom.trip.ui.cities.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import tj.ilhom.trip.R
import tj.ilhom.trip.models.city.City

class CityListAdapter(private val cityEvents: CityEvents) :
    PagingDataAdapter<City, CityListAdapter.ProductsViewHolder>(diffUtil) {

    var builder: AlertDialog.Builder? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.city_list_item, parent, false)
        builder = AlertDialog.Builder(parent.context)
        return ProductsViewHolder(view)
    }

    inner class ProductsViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        var cityName: TextView = view.findViewById(R.id.city_name)
        var locationQty: TextView = view.findViewById(R.id.locations_qty)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val city: City? = getItem(position)
        Log.e("CITY", city?.name_ru.toString())
        holder.cityName.text = city?.name_ru
        holder.locationQty.text = city?.experience_count.toString()
        holder.itemView.setOnClickListener {
            if (city != null) {
                cityEvents.cityOnClick(city)
            }
        }
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