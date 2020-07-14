package `in`.example.rahul.vegcartpro.adapter

import `in`.example.rahul.vegcartpro.R
import `in`.example.rahul.vegcartpro.model.CartModel
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_cart_layout.view.*

/**
 * Created by Rahul Khatri on 14-07-2020.
 * github.com/rahulkhatri19
 **/
class CartAdapter(private val cartList: MutableList<CartModel>, val context: Context) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cl_main = view.cl_main
        val ivProduct = view.ivProduct
        val tvProductName = view.tvProductName
        val tvProductQuantity = view.tvProductQuantity
        val tvProductPrice = view.tvProductPrice
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_cart_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listItem = cartList[position]

        Picasso.with(context).load(listItem.foodImage).placeholder(R.drawable.placeholder).into(holder.ivProduct)
        holder.tvProductName.text = listItem.name
        holder.tvProductQuantity.text = listItem.quantity
        holder.tvProductPrice.text = listItem.price
    }
}