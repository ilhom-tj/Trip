package tj.ilhom.trip.ui.excurseFilter.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.suke.widget.SwitchButton
import tj.ilhom.trip.R

class FilterAdapter(val mContext: Fragment, val onClickEvent: SwitcherOnclick) :
    RecyclerView.Adapter<FilterAdapter.ViewHolder>() {

    private var data = emptyList<String>()
    private val swictchers = arrayListOf<SwitchButton>()

    fun setData(list: List<String>) {
        data = list
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.option_title)
        val switcher: SwitchButton = view.findViewById(R.id.switch_button)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.filter_item, viewGroup, false)
        return ViewHolder(view)
    }

    fun checkAllToggles() {
        var count = 0
        swictchers.forEachIndexed { index, switchButton ->
            if (index != 0) {
                if (switchButton.isChecked) {
                    count++
                }
            }
        }
        swictchers[0].isChecked = count == swictchers.size - 1

    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val filter: String = data[position]
        swictchers.add(viewHolder.switcher)
        Log.e("FILETR", filter)
        viewHolder.title.text = filter

        viewHolder.switcher.setOnCheckedChangeListener { view, isChecked ->
            onClickEvent.toggle(filter, isChecked)
        }
    }

    fun toggleAll() {
        swictchers.forEachIndexed { index, switchButton ->
            if (index != 0) {
                switchButton.toggle()
            }
        }
    }

    override fun getItemCount() = data.size

    private fun getLastIndex() = data.lastIndex
}

interface SwitcherOnclick {
    fun toggle(filter: String, isChecked: Boolean)
}
