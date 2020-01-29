package `in`.example.rahul.vegcartpro.activity

import `in`.example.rahul.vegcartpro.Interface.ItemClickListener
import `in`.example.rahul.vegcartpro.Model.AllFoodModel
import `in`.example.rahul.vegcartpro.R
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class CategoryItemActivity : AppCompatActivity() {

    var recyclerView: RecyclerView? = null
    var database: FirebaseDatabase? = null
    var allFoodData: DatabaseReference? = null
    var rlNoData: RelativeLayout? = null
    var foodName = ""
    var foodNameHindi = ""
    var imageUrl = ""
    var foodAdvantage = ""
    var diseaseHeal = ""
    var vitamins = ""
    var precautions = ""
    var price = ""
    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_item)
    }

    fun loadData() {
        val options: FirebaseRecyclerOptions<AllFoodModel> = FirebaseRecyclerOptions.Builder<AllFoodModel>().setQuery(allFoodData!!, AllFoodModel::class.java).setLifecycleOwner(this).build()
        val adapter: FirebaseRecyclerAdapter<AllFoodModel, FlowerVegActivity.UserHolder> = object : FirebaseRecyclerAdapter<AllFoodModel, FlowerVegActivity.UserHolder>(options) {
            protected override fun onBindViewHolder(viewHolder: FlowerVegActivity.UserHolder, position: Int, model: AllFoodModel) {
                Picasso.with(baseContext).load(model.Image).placeholder(R.drawable.placeholder).into(viewHolder.imageView)
                viewHolder.txtName.setText(model.Name)
                viewHolder.txtNameHindi.setText(model.NameHindi)
                val face2 = Typeface.createFromAsset(assets, "fonts/K11.TTF")
                // Log.e("prog", "3");
                progressDialog!!.dismiss()
                viewHolder.txtNameHindi.typeface = face2
                val clickItem: AllFoodModel = model
                viewHolder.setItemClickListener(object : ItemClickListener {
                    override fun onClick(view: View?, position: Int, isLongClick: Boolean) {
                        foodName = clickItem.Name
                        foodNameHindi = clickItem.NameHindi
                        imageUrl = clickItem.Image
                        foodAdvantage = clickItem.Advt
                        vitamins = clickItem.Vam
                        diseaseHeal = clickItem.Dis
                        precautions = clickItem.Dat
                        price = clickItem.Price
                       val foodNamePrice = "$foodName\n$foodNameHindi\n$price"
                        //  Toast.makeText(FlowerVegActivity.this,mName,Toast.LENGTH_SHORT).show();
//                        Log.e("Data in flower:", foodNamePrice)
                        val intent = Intent(this@CategoryItemActivity, DetailActivity::class.java)
                        val bundle = Bundle()
                        bundle.putString("foodName", foodName)
                        bundle.putString("imageurl", imageUrl)
                        bundle.putString("deta", foodAdvantage)
                        bundle.putString("deta2", vitamins)
                        bundle.putString("deta3", diseaseHeal)
                        bundle.putString("deta4", precautions)
                        bundle.putString("deta5", price)
                        intent.putExtras(bundle)
                        startActivity(intent)
                    }

                })
            }

            override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): FlowerVegActivity.UserHolder {
                val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.food, viewGroup, false)
                return FlowerVegActivity.UserHolder(view)
            }
        }
        // Log.e("prog", "1");
        recyclerView!!.adapter = adapter
        //  Log.e("prog", "2");
//  progressDialog.dismiss();
    }
}
