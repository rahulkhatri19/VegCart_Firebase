package `in`.example.rahul.vegcartpro.activity

import `in`.example.rahul.vegcartpro.Model.CartModel
import `in`.example.rahul.vegcartpro.R
import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

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
        allFoodData = database!!.getReference("Cart")
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

    fun loadData() {
        val options: FirebaseRecyclerOptions<CartModel> = FirebaseRecyclerOptions.Builder<CartModel>().setQuery(allFoodData!!, CartModel::class.java).setLifecycleOwner(this).build()
        val adapter: FirebaseRecyclerAdapter<CartModel, FoodViewHolder> = object : FirebaseRecyclerAdapter<CartModel, FoodViewHolder>(options) {
            protected override fun onBindViewHolder(viewHolder: FoodViewHolder, position: Int, model: CartModel) {
                viewHolder.tvName.setText(model.Name)
                viewHolder.tvPrice.setText(model.Price)
                viewHolder.tvQuantity.setText(model.Quantity)
                viewHolder.tvAddress.setText(model.Address)
                progressDialog!!.dismiss()
            }

            override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): FoodViewHolder {
                val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.foodcart, viewGroup, false)
                return FoodViewHolder(view)
            }
        }
        // Log.e("prog", "1");
        recyclerView?.adapter = adapter
        //  Log.e("prog", "2");
//  progressDialog.dismiss();
    }

    /*   private void loadImages() {
        FirebaseRecyclerAdapter<Cart, FoodViewHolder> adapter= new FirebaseRecyclerAdapter<Cart, FoodViewHolder>(Cart.class, R.layout.foodcart, FoodViewHolder.class,allfoodData) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Cart model, int position) {
                //Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.imageView);
                viewHolder.tvName.setText(model.getName());
                viewHolder.tvPrice.setText(model.getPrice());
                viewHolder.tvQuantity.setText(model.getQuantity());
                viewHolder.tvAddress.setText(model.getAddress());
                //Typeface face2=Typeface.createFromAsset(getAssets(),"fonts/K11.TTF");

               // viewHolder.txtnameHindi.setTypeface(face2);
                // final AllFood clickItem= model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                   */
/*     name=clickItem.getName();
                        nameHindi=clickItem.getNameHindi();
                        ImageUrl=clickItem.getImage();
                        det=clickItem.getAdvt();
                        det2=clickItem.getVam();
                        det3=clickItem.getDis();
                        det4=clickItem.getDat();
                        det5=clickItem.getPrice();

                        mName= name+"\n"+nameHindi+"\n"+det5;
                        Toast.makeText(MyCart.this,mName,Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(MyCart.this, Detail.class);
                        Bundle bundle= new Bundle();
                        bundle.putString("imageurl",ImageUrl);
                        bundle.putString("deta",det);
                        bundle.putString("deta2",det2);
                        bundle.putString("deta3",det3);
                        bundle.putString("deta4",det4);
                        bundle.putString("deta5",det5);

                        intent.putExtras(bundle);
                        startActivity(intent);
                        */
/*
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }*/
    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView
        var tvPrice: TextView
        var tvQuantity: TextView
        var tvAddress: TextView

        init {
            tvName = itemView.findViewById(R.id.tv_name)
            tvPrice = itemView.findViewById(R.id.tv_price)
            tvQuantity = itemView.findViewById(R.id.tv_quantity)
            tvAddress = itemView.findViewById(R.id.tv_address)
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