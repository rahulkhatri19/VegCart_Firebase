package `in`.rahulkhatri.vegcartpro.activity

import `in`.rahulkhatri.vegcartpro.model.BucketModel
import `in`.rahulkhatri.vegcartpro.utils.Constants.CART
import `in`.rahulkhatri.vegcartpro.utils.Utility
import `in`.rahulkhatri.vegcartpro.R
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class MyCartActivity : AppCompatActivity() {
    var recyclerView: RecyclerView? = null
    //RecyclerView.LayoutManager layoutManager;
    var layoutManager: LinearLayoutManager? = null
    var database: FirebaseDatabase? = null
    var allFoodData: DatabaseReference? = null
    //  String name,nameHindi,mName,ImageUrl, det,det2,det3,det4,det5;
    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_cart)
        database = FirebaseDatabase.getInstance()
        allFoodData = database!!.getReference(CART).child(Utility.getDeviceId(this))
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }
        recyclerView = findViewById(R.id.recyclerView)
        progressDialog = ProgressDialog(this)
        progressDialog?.setMessage("Please wait ...")
        progressDialog?.setCancelable(false)
        progressDialog?.show()
        recyclerView?.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        layoutManager?.reverseLayout = true
        layoutManager?.stackFromEnd = true
        recyclerView?.setLayoutManager(layoutManager)
        loadData()
        //  loadImages();
    }

    private fun loadData() {
        val options: FirebaseRecyclerOptions<BucketModel> = FirebaseRecyclerOptions.Builder<BucketModel>().setQuery(allFoodData!!, BucketModel::class.java).setLifecycleOwner(this).build()
        val adapter: FirebaseRecyclerAdapter<BucketModel, FoodViewHolder> = object : FirebaseRecyclerAdapter<BucketModel, FoodViewHolder>(options) {
            override fun onBindViewHolder(viewHolder: FoodViewHolder, position: Int, model: BucketModel) {
                viewHolder.tvName.text = model.foodName
                viewHolder.tvPrice.text = model.price.toString()
                viewHolder.tvQuantity.text = model.quantity.toString()
//                viewHolder.tvAddress.text = model.address
                if(!model.foodImage.equals("")){
                    Picasso.with(this@MyCartActivity).load(model.foodImage).placeholder(R.drawable.placeholder).into(viewHolder.ivFood)
                }
                val stCart = "name: ${model.foodName} price: ${model.price} qut: ${model.quantity}, image: ${model.foodImage}"
                Log.e("My cart",stCart)

                progressDialog!!.dismiss()
            }

            override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): FoodViewHolder {
                val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.foodcart, viewGroup, false)
                return FoodViewHolder(view)
            }
        }
        recyclerView?.adapter = adapter

    }

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView
        var tvPrice: TextView
        var tvQuantity: TextView
        var tvAddress: TextView
        var ivFood: ImageView

        init {
            tvName = itemView.findViewById(R.id.tv_name)
            tvPrice = itemView.findViewById(R.id.tv_price)
            tvQuantity = itemView.findViewById(R.id.tv_quantity)
            tvAddress = itemView.findViewById(R.id.tv_address)
            ivFood = itemView.findViewById(R.id.iv_food)
        }
    }

    override fun onResume() {
        super.onResume()
        val ab: ActionBar? = supportActionBar
        if (ab != null) {
            ab.title = "My Cart"
            ab.setDisplayHomeAsUpEnabled(true)
        }
    }
}