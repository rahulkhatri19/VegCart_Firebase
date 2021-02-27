package `in`.rahulkhatri.vegcartpro.adapter

import `in`.rahulkhatri.vegcartpro.R
import `in`.rahulkhatri.vegcartpro.model.ItemModel
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class HomeAdapter(var itemList: List<ItemModel>?) : RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {
    //    private var itemList: List<ItemModel>? = null
    private var clickListener: ClickListener? = null
    /*fun MyAdapter(itemList: List<ItemModel>?) {
        this.itemList = itemList
    }*/

    class MyViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {
        var title: TextView
        var subtitle: TextView
        var icon: ImageView
        var main: LinearLayout

        init {
            title = parent.findViewById(R.id.title)
            subtitle = parent.findViewById(R.id.subtitle)
            icon = parent.findViewById(R.id.icon)
            main = parent.findViewById(R.id.main)
//            main.setOnClickListener { v ->
//                //   Toast.makeText(itemView.getContext(), "Position:" + Integer.toString(getPosition()), Toast.LENGTH_SHORT).show();
//               /* Log.e("Position adapter:", position.toString())
//                if (clickListener != null) {
//                    clickListener.itemClicked(v, adapterPosition)
//                }*/
//            }
        }
    }

    fun setClickListener(clickListener: ClickListener?) {
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val row: ItemModel = itemList!![position]
        holder.title.setText(row.title)
        holder.subtitle.setText(row.subtitle)
        holder.icon.setImageResource(row.imageId)
        holder.main.setOnClickListener {
            Log.e("Position adapter:", position.toString())
            if (clickListener != null) {
                clickListener?.itemClicked(it, position)
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList!!.size
    }

    interface ClickListener {
        fun itemClicked(view: View?, position: Int)
    }
}