package `in`.rahulkhatri.vegcartpro.activity

import `in`.rahulkhatri.vegcartpro.interfaces.ItemClickListener
import `in`.rahulkhatri.vegcartpro.model.AllFoodModel
import `in`.rahulkhatri.vegcartpro.utils.SharedPreferenceUtils
import `in`.rahulkhatri.vegcartpro.R
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class FlowerVegActivity : AppCompatActivity() {
    var recyclerView: RecyclerView? = null
    var database: FirebaseDatabase? = null
    var allFoodData: DatabaseReference? = null
    var rlNoData: RelativeLayout? = null
    var name = ""
    var nameHindi = ""
    var mName = ""
    var ImageUrl = ""
    var det = ""
    var det2 = ""
    var det3 = ""
    var det4 = ""
    var det5 = ""
    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flower_veg)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }
        recyclerView = findViewById(R.id.recyclerView)
        rlNoData = findViewById(R.id.rl_no_data)
        progressDialog = ProgressDialog(this)
        progressDialog!!.setMessage("Please wait ...")
        progressDialog!!.setCancelable(false)
        progressDialog!!.show()

        rlNoData?.visibility = View.GONE
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        if (SharedPreferenceUtils(this).getFlowerVeg() != "null") {
            rlNoData?.visibility = View.GONE
            recyclerView?.visibility = View.VISIBLE
            database = FirebaseDatabase.getInstance()
            // allFoodData= database.getReference("AllFood/flower");
            allFoodData = database!!.getReference("AllFood/" + SharedPreferenceUtils(this).getFlowerVeg())
            progressDialog!!.show()
        } else {
            rlNoData?.visibility = View.VISIBLE
            recyclerView?.visibility = View.GONE
            progressDialog!!.dismiss()
        }
        // loadImages();
        loadData()
    }

    fun loadData() {
        val options: FirebaseRecyclerOptions<AllFoodModel> = FirebaseRecyclerOptions.Builder<AllFoodModel>().setQuery(allFoodData!!, AllFoodModel::class.java).setLifecycleOwner(this).build()
        val adapter: FirebaseRecyclerAdapter<AllFoodModel, UserHolder> = object : FirebaseRecyclerAdapter<AllFoodModel, UserHolder>(options) {
            protected override fun onBindViewHolder(viewHolder: UserHolder, position: Int, model: AllFoodModel) {
                Picasso.with(baseContext).load(model.Image).placeholder(R.drawable.placeholder).into(viewHolder.imageView)
                viewHolder.txtName.setText(model.Name)
                viewHolder.txtNameHindi.setText(model.NameHindi)
                val face2 = Typeface.createFromAsset(assets, "")
                // Log.e("prog", "3");
                progressDialog!!.dismiss()
                viewHolder.txtNameHindi.typeface = face2
                val clickItem: AllFoodModel = model
                viewHolder.setItemClickListener(object : ItemClickListener{
                    override fun onClick(view: View?, position: Int, isLongClick: Boolean) {
                        name = clickItem.Name
                        nameHindi = clickItem.NameHindi
                        ImageUrl = clickItem.Image
                        det = clickItem.Advt
                        det2 = clickItem.Vam
                        det3 = clickItem.Dis
                        det4 = clickItem.Dat
                        det5 = clickItem.Price
//                        mName = name + "\n" + nameHindi + "\n" + det5
                        mName = "$name\n$nameHindi\n$det5"
                        //  Toast.makeText(FlowerVegActivity.this,mName,Toast.LENGTH_SHORT).show();
                        Log.e("Data in flower:", mName)
                        val intent = Intent(this@FlowerVegActivity, DetailActivity::class.java)
                        val bundle = Bundle()
                        bundle.putString("foodName", name)
                        bundle.putString("imageurl", ImageUrl)
                        bundle.putString("deta", det)
                        bundle.putString("deta2", det2)
                        bundle.putString("deta3", det3)
                        bundle.putString("deta4", det4)
                        bundle.putString("deta5", det5)
                        intent.putExtras(bundle)
                        startActivity(intent)
                    }

                })
            }

            override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): UserHolder {
                val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.food_layout, viewGroup, false)
                return UserHolder(view)
            }
        }
        // Log.e("prog", "1");
        recyclerView!!.adapter = adapter
        //  Log.e("prog", "2");
//  progressDialog.dismiss();
    }


    /* public static class UserHolder extends RecyclerView.ViewHolder{
         View view;

     }*/
    class UserHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
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
            txtName = itemView.findViewById(R.id.tv_name)
            txtNameHindi = itemView.findViewById(R.id.tv_name_hindi)
            imageView = itemView.findViewById(R.id.iv_food)
            itemView.setOnClickListener(this)
        }
    }
    /* private void loadImages() {
        FirebaseRecyclerAdapter<AllFood, MenuViewHolder> adapter= new FirebaseRecyclerAdapter<AllFood, MenuViewHolder>(AllFood.class, R.layout.food_layout, MenuViewHolder.class,allFoodData) {
            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, AllFood model, int position) {
                Picasso.with(getBaseContext()).load(model.getImage()).placeholder(R.drawable.placeholder).into(viewHolder.imageView);
                viewHolder.txtname.setText(model.getName());
                viewHolder.txtnameHindi.setText(model.getNameHindi());
                Typeface face2=Typeface.createFromAsset(getAssets(),"fonts/KrutiHindi.TTF");

                viewHolder.txtnameHindi.setTypeface(face2);
                final AllFood clickItem= model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        name=clickItem.getName();
                        nameHindi=clickItem.getNameHindi();
                        ImageUrl=clickItem.getImage();
                        det=clickItem.getAdvt();
                        det2=clickItem.getVam();
                        det3=clickItem.getDis();
                        det4=clickItem.getDat();
                        det5=clickItem.getPrice();

                        mName= name+"\n"+nameHindi+"\n"+det5;
                      //  Toast.makeText(FlowerVegActivity.this,mName,Toast.LENGTH_SHORT).show();
                        Log.e("Data in flower:", mName);
                        Intent intent= new Intent(FlowerVegActivity.this, Detail.class);
                        Bundle bundle= new Bundle();
                        bundle.putString("foodName",name);
                        bundle.putString("imageurl",ImageUrl);
                        bundle.putString("deta",det);
                        bundle.putString("deta2",det2);
                        bundle.putString("deta3",det3);
                        bundle.putString("deta4",det4);
                        bundle.putString("deta5",det5);

                        intent.putExtras(bundle);
                        startActivity(intent);

                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }*/

    /* private void loadImages() {
        FirebaseRecyclerAdapter<AllFood, MenuViewHolder> adapter= new FirebaseRecyclerAdapter<AllFood, MenuViewHolder>(AllFood.class, R.layout.food_layout, MenuViewHolder.class,allFoodData) {
            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, AllFood model, int position) {
                Picasso.with(getBaseContext()).load(model.getImage()).placeholder(R.drawable.placeholder).into(viewHolder.imageView);
                viewHolder.txtname.setText(model.getName());
                viewHolder.txtnameHindi.setText(model.getNameHindi());
                Typeface face2=Typeface.createFromAsset(getAssets(),"fonts/KrutiHindi.TTF");

                viewHolder.txtnameHindi.setTypeface(face2);
                final AllFood clickItem= model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        name=clickItem.getName();
                        nameHindi=clickItem.getNameHindi();
                        ImageUrl=clickItem.getImage();
                        det=clickItem.getAdvt();
                        det2=clickItem.getVam();
                        det3=clickItem.getDis();
                        det4=clickItem.getDat();
                        det5=clickItem.getPrice();

                        mName= name+"\n"+nameHindi+"\n"+det5;
                      //  Toast.makeText(FlowerVegActivity.this,mName,Toast.LENGTH_SHORT).show();
                        Log.e("Data in flower:", mName);
                        Intent intent= new Intent(FlowerVegActivity.this, Detail.class);
                        Bundle bundle= new Bundle();
                        bundle.putString("foodName",name);
                        bundle.putString("imageurl",ImageUrl);
                        bundle.putString("deta",det);
                        bundle.putString("deta2",det2);
                        bundle.putString("deta3",det3);
                        bundle.putString("deta4",det4);
                        bundle.putString("deta5",det5);

                        intent.putExtras(bundle);
                        startActivity(intent);

                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }*/
    override fun onResume() {
        super.onResume()
        loadData()
        val ab = supportActionBar
        if (ab != null) {
            ab.setTitle(SharedPreferenceUtils(this).getFlowerVeg()?.substring(0, 1)?.toUpperCase() + SharedPreferenceUtils(this).getFlowerVeg()?.substring(1))
            ab.setDisplayHomeAsUpEnabled(true)
        }
    }
}