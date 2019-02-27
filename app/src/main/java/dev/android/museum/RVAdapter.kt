import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.android.museum.R
import dev.android.museum.model.Museum
import kotlinx.android.synthetic.main.museum_list_item.view.*

class RVAdapter(val items: ArrayList<Museum>, val context: Context) : RecyclerView.Adapter<RVAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.museum_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(items[position])
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindItems(museum: Museum) {
            itemView.name.text = museum.name
            itemView.address.text = museum.address
            itemView.lat.text = museum.lat.toString()
            itemView.lng.text = museum.lng.toString()
        }
    }
}
