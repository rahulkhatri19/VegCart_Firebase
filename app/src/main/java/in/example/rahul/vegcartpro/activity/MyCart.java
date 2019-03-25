package in.example.rahul.vegcartpro.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import in.example.rahul.vegcartpro.Interface.ItemClickListener;
import in.example.rahul.vegcartpro.Model.AllFood;
import in.example.rahul.vegcartpro.Model.Cart;
import in.example.rahul.vegcartpro.R;
import in.example.rahul.vegcartpro.ViewHolder.FoodViewHolder;
import in.example.rahul.vegcartpro.ViewHolder.MenuViewHolder;

public class MyCart extends AppCompatActivity {
    RecyclerView recyclerView;
    //RecyclerView.LayoutManager layoutManager;
    LinearLayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference allFoodData;
  //  String name,nameHindi,mName,ImageUrl, det,det2,det3,det4,det5;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        database= FirebaseDatabase.getInstance();
        allFoodData= database.getReference("Cart");
        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView=findViewById(R.id.recyclerView);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(layoutManager);
        loadData();

      //  loadImages();
    }  public void loadData() {
        FirebaseRecyclerOptions<Cart> options = new FirebaseRecyclerOptions.Builder<Cart>().setQuery(allFoodData, Cart.class).setLifecycleOwner(this).build();
        final FirebaseRecyclerAdapter<Cart, FoodViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, FoodViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FoodViewHolder viewHolder, int position, @NonNull Cart model) {
                viewHolder.tvName.setText(model.getName());
                viewHolder.tvPrice.setText(model.getPrice());
                viewHolder.tvQuantity.setText(model.getQuantity());
                viewHolder.tvAddress.setText(model.getAddress());
                progressDialog.dismiss();
            }


            @NonNull
            @Override
            public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.foodcart, viewGroup, false);
                return new FoodViewHolder(view);
            }
        };
        // Log.e("prog", "1");
        recyclerView.setAdapter(adapter);
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
                   *//*     name=clickItem.getName();
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
                        *//*
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }*/
    public class FoodViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName,tvPrice,tvQuantity,tvAddress;
        public FoodViewHolder(View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tv_name);
            tvPrice=itemView.findViewById(R.id.tv_price);
            tvQuantity=itemView.findViewById(R.id.tv_quantity);
            tvAddress=itemView.findViewById(R.id.tv_address);

        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        ActionBar ab = getSupportActionBar ();
        if (ab != null) {
            ab.setTitle ( "My Cart" );
            ab.setDisplayHomeAsUpEnabled ( true );
        }
    }
}
