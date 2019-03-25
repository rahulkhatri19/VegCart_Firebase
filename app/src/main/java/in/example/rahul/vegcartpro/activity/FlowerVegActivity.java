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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import in.example.rahul.vegcartpro.Interface.ItemClickListener;
import in.example.rahul.vegcartpro.Model.AllFood;
import in.example.rahul.vegcartpro.R;
import in.example.rahul.vegcartpro.utils.SharedPreferenceUtils;

public class FlowerVegActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference allFoodData;
    RelativeLayout rlNoData;
    String name, nameHindi, mName, ImageUrl, det, det2, det3, det4, det5;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flower_veg);
        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        rlNoData = findViewById(R.id.rl_no_data);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        rlNoData.setVisibility(View.GONE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        if (!new SharedPreferenceUtils(this).getFlowerVeg().equals("null")) {
            rlNoData.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            database = FirebaseDatabase.getInstance();
            // allFoodData= database.getReference("AllFood/flower");
            allFoodData = database.getReference("AllFood/" + new SharedPreferenceUtils(this).getFlowerVeg());
            progressDialog.show();
        } else {
            rlNoData.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            progressDialog.dismiss();
        }

        // loadImages();
        loadData();
    }

    public void loadData() {
        FirebaseRecyclerOptions<AllFood> options = new FirebaseRecyclerOptions.Builder<AllFood>().setQuery(allFoodData, AllFood.class).setLifecycleOwner(this).build();
        final FirebaseRecyclerAdapter<AllFood, UserHolder> adapter = new FirebaseRecyclerAdapter<AllFood, UserHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull UserHolder viewHolder, int position, @NonNull AllFood model) {
                Picasso.with(getBaseContext()).load(model.getImage()).placeholder(R.drawable.placeholder).into(viewHolder.imageView);
                viewHolder.txtname.setText(model.getName());
                viewHolder.txtnameHindi.setText(model.getNameHindi());
                Typeface face2 = Typeface.createFromAsset(getAssets(), "fonts/K11.TTF");
               // Log.e("prog", "3");
                progressDialog.dismiss();
                viewHolder.txtnameHindi.setTypeface(face2);
                final AllFood clickItem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        name = clickItem.getName();
                        nameHindi = clickItem.getNameHindi();
                        ImageUrl = clickItem.getImage();
                        det = clickItem.getAdvt();
                        det2 = clickItem.getVam();
                        det3 = clickItem.getDis();
                        det4 = clickItem.getDat();
                        det5 = clickItem.getPrice();

                        mName = name + "\n" + nameHindi + "\n" + det5;
                        //  Toast.makeText(FlowerVegActivity.this,mName,Toast.LENGTH_SHORT).show();
                        Log.e("Data in flower:", mName);
                        Intent intent = new Intent(FlowerVegActivity.this, Detail.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("foodName", name);
                        bundle.putString("imageurl", ImageUrl);
                        bundle.putString("deta", det);
                        bundle.putString("deta2", det2);
                        bundle.putString("deta3", det3);
                        bundle.putString("deta4", det4);
                        bundle.putString("deta5", det5);

                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }


            @NonNull
            @Override
            public UserHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.food, viewGroup, false);
                return new UserHolder(view);
            }
        };
       // Log.e("prog", "1");
        recyclerView.setAdapter(adapter);
      //  Log.e("prog", "2");
      //  progressDialog.dismiss();
    }


    /* public static class UserHolder extends RecyclerView.ViewHolder{
         View view;

     }*/
    public class UserHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txtname, txtnameHindi;
        public ImageView imageView;
        private ItemClickListener itemClickListener;

        public UserHolder(View itemView) {
            super(itemView);
            txtname = itemView.findViewById(R.id.name);
            txtnameHindi = itemView.findViewById(R.id.nameHindi);
            imageView = itemView.findViewById(R.id.image);

            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }

    }
   /* private void loadImages() {
        FirebaseRecyclerAdapter<AllFood, MenuViewHolder> adapter= new FirebaseRecyclerAdapter<AllFood, MenuViewHolder>(AllFood.class, R.layout.food, MenuViewHolder.class,allFoodData) {
            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, AllFood model, int position) {
                Picasso.with(getBaseContext()).load(model.getImage()).placeholder(R.drawable.placeholder).into(viewHolder.imageView);
                viewHolder.txtname.setText(model.getName());
                viewHolder.txtnameHindi.setText(model.getNameHindi());
                Typeface face2=Typeface.createFromAsset(getAssets(),"fonts/K11.TTF");

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

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
        ActionBar ab = getSupportActionBar ();
        if (ab != null) {
            ab.setTitle ( new SharedPreferenceUtils(this).getFlowerVeg().substring(0,1).toUpperCase()+new SharedPreferenceUtils(this).getFlowerVeg().substring(1) );
            ab.setDisplayHomeAsUpEnabled ( true );
        }
    }
}
