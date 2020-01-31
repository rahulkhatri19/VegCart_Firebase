package `in`.example.rahul.vegcartpro.activity

import `in`.example.rahul.vegcartpro.Interface.ItemClickListener
import `in`.example.rahul.vegcartpro.Model.AllFoodModel
import `in`.example.rahul.vegcartpro.R
import `in`.example.rahul.vegcartpro.utils.CustomProgressBar
import `in`.example.rahul.vegcartpro.utils.SharedPreferenceUtils
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
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
//    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_item)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }
        recyclerView = findViewById(R.id.recyclerView)
        rlNoData = findViewById(R.id.rl_no_data)
        CustomProgressBar.ProgressBar(this, "Please Wait ...")
//        progressDialog = ProgressDialog(this)
//        progressDialog?.setMessage("Please wait ...")
//        progressDialog?.setCancelable(false)
//        progressDialog?.show()

        rlNoData?.visibility = View.GONE
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        if (SharedPreferenceUtils(this).getCategoryItem() != "null") {
            rlNoData?.visibility = View.GONE
            recyclerView?.visibility = View.VISIBLE
            database = FirebaseDatabase.getInstance()
            // allFoodData= database.getReference("AllFood/flower");
            allFoodData = database!!.getReference("AllFood/" + SharedPreferenceUtils(this).getCategoryItem())
//            progressDialog?.show()
            CustomProgressBar.ProgressBar(this, "Please Wait ...")
        } else {
            rlNoData?.visibility = View.VISIBLE
            recyclerView?.visibility = View.GONE
//            progressDialog!!.dismiss()
            CustomProgressBar.DismissProgressBar()
        }
        // loadImages();
        loadData()
    }

    fun loadData() {
        val options: FirebaseRecyclerOptions<AllFoodModel> = FirebaseRecyclerOptions.Builder<AllFoodModel>().setQuery(allFoodData!!, AllFoodModel::class.java).setLifecycleOwner(this).build()

        val adapter: FirebaseRecyclerAdapter<AllFoodModel, CategoryItemHolder> = object : FirebaseRecyclerAdapter<AllFoodModel, CategoryItemHolder>(options) {

            override fun onBindViewHolder(viewHolder: CategoryItemHolder, position: Int, model: AllFoodModel) {

                Picasso.with(baseContext).load(model.Image).placeholder(R.drawable.placeholder).into(viewHolder.imageView)
                viewHolder.txtName.setText(model.Name)
                viewHolder.txtNameHindi.setText(model.NameHindi)
                val face2 = Typeface.createFromAsset(assets, "fonts/K11.TTF")
                // Log.e("prog", "3");
//                progressDialog!!.dismiss()
                CustomProgressBar.DismissProgressBar()
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
                        bundle.putString("advantage", foodAdvantage)
                        bundle.putString("vitamins", vitamins)
                        bundle.putString("diseaseHeal", diseaseHeal)
                        bundle.putString("precautions", precautions)
                        bundle.putString("price", price)

                        intent.putExtras(bundle)
                        startActivity(intent)
                    }

                })
            }

            override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CategoryItemHolder {
                val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.food, viewGroup, false)
                return CategoryItemHolder(view)
            }
        }
        // Log.e("prog", "1");
        recyclerView!!.adapter = adapter
        //  Log.e("prog", "2");
//  progressDialog.dismiss();
    }

    class CategoryItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var txtName: TextView
        var txtNameHindi: TextView
        var imageView: ImageView
        private var itemClickListener: ItemClickListener? = null
        fun setItemClickListener(itemClickListener: ItemClickListener?) {
            this.itemClickListener = itemClickListener
        }

        override fun onClick(v: View) {
            itemClickListener?.onClick(v, adapterPosition, false)
        }

        init {
            txtName = itemView.findViewById(R.id.name)
            txtNameHindi = itemView.findViewById(R.id.nameHindi)
            imageView = itemView.findViewById(R.id.image)
            itemView.setOnClickListener(this)
        }
    }

    override fun onResume() {
        super.onResume()
//        CustomProgressBar.ProgressBar(this, "Please Wait ...")
        /*progressDialog = ProgressDialog(this)
        progressDialog?.setMessage("Please wait ...")
        progressDialog?.setCancelable(false)
        progressDialog?.show()
        loadData()*/
        val ab = supportActionBar
        if (ab != null) {
            ab.setTitle(SharedPreferenceUtils(this).getCategoryItem()?.substring(0, 1)?.toUpperCase() + SharedPreferenceUtils(this).getCategoryItem()?.substring(1))
            ab.setDisplayHomeAsUpEnabled(true)
        }
    }
}
