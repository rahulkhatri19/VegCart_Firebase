package `in`.rahulkhatri.vegcartpro.adapter

import `in`.rahulkhatri.vegcartpro.R
import `in`.rahulkhatri.vegcartpro.activity.DetailActivity
import `in`.rahulkhatri.vegcartpro.model.FoodDetailModel
import `in`.rahulkhatri.vegcartpro.utils.Constants.ADVANTAGE
import `in`.rahulkhatri.vegcartpro.utils.Constants.DISEASE_HEAL
import `in`.rahulkhatri.vegcartpro.utils.Constants.FOOD_NAME
import `in`.rahulkhatri.vegcartpro.utils.Constants.IMAGE_URL
import `in`.rahulkhatri.vegcartpro.utils.Constants.PRECAUTIONS
import `in`.rahulkhatri.vegcartpro.utils.Constants.PRICE
import `in`.rahulkhatri.vegcartpro.utils.Constants.VITAMINS
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

/**
 * Created by Rahul Khatri on 26, January, 2021.
 * github.com/rahulkhatri19
 **/
class CategoryItemAdapter (val itemList: List<FoodDetailModel>, val context: Context) : RecyclerView.Adapter<CategoryItemAdapter.MyViewHolder>() {

    class MyViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {
        var tvName: TextView = parent.findViewById(R.id.tv_name)
        var tvNameHindi: TextView = parent.findViewById(R.id.tv_name_hindi)
        var ivFood: ImageView = parent.findViewById(R.id.iv_food)
        var main: LinearLayout = parent.findViewById(R.id.llMain)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.food_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val row = itemList[position]
        holder.tvName.text = row.foodName
        val typeface = ResourcesCompat.getFont(context, R.font.krutihindi)
        holder.tvNameHindi.typeface = typeface
        holder.tvNameHindi.text = row.foodNameHindi
        Picasso.with(context).load(row.foodImage).placeholder(R.drawable.placeholder).into(holder.ivFood)
        holder.main.setOnClickListener {
            Log.e("Position adapter:", position.toString())
            val intent = Intent(context, DetailActivity::class.java)
            val bundle = Bundle()
            bundle.putString(FOOD_NAME, row.foodName)
            bundle.putString(IMAGE_URL, row.foodImage)
            bundle.putString(ADVANTAGE, row.advantages)
            bundle.putString(VITAMINS, row.vitamins)
            bundle.putString(DISEASE_HEAL, row.diseaseHeal)
            bundle.putString(PRECAUTIONS, row.precaution)
            bundle.putString(PRICE, row.price)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}