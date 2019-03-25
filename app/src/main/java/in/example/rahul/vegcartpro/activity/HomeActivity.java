package in.example.rahul.vegcartpro.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.example.rahul.vegcartpro.Model.Item;
import in.example.rahul.vegcartpro.MyAdapter;
import in.example.rahul.vegcartpro.R;
import in.example.rahul.vegcartpro.utils.SharedPreferenceUtils;

public class HomeActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener, MyAdapter.ClickListener, NavigationView.OnNavigationItemSelectedListener {

    private List<Item> itemList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private LinearLayout main;
    SliderLayout sliderLayout;
    HashMap<String, String> HashMapForURL;
    HashMap<String, Integer> HashMapForLocal;
    boolean doubleBackToExitPressedOnce= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        sliderLayout = findViewById(R.id.slider);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (!isOnline()) {
            alertDialog();
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView txtFullName = headerView.findViewById(R.id.txtFullName);
        // recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        // layoutManager.
        // Call below method if you want images online
        //AddImagesUrlOnline();

        // Call below method if you want images offline
        AddImageUrlLocal();

        // Call below method if you want to stop Automatic sliding
        //sliderLayout.stopAutoCycle();

        for (String name : HashMapForLocal.keySet()) {
            TextSliderView textSliderView = new TextSliderView(HomeActivity.this);

            textSliderView.description(name).image(HashMapForLocal.get(name)).setScaleType(BaseSliderView.ScaleType.CenterCrop).setOnSliderClickListener(this);

            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra", name);
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.DepthPage);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(3000);
        sliderLayout.addOnPageChangeListener(HomeActivity.this);

        // Bottom
        recyclerView = findViewById(R.id.recyclerView);
        prepareItem();

        adapter = new MyAdapter(itemList);
        adapter.setClickListener(this);

        //RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getApplicationContext());
// Try Grid layout for Recycler view.

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void AddImagesUrlOnline() {
        HashMapForURL = new HashMap<String, String>();

        HashMapForURL.put("All Veg", "http://");
    }

    public void AddImageUrlLocal() {
        HashMapForLocal = new HashMap<String, Integer>();

        HashMapForLocal.put("Fruits", R.drawable.fruit);
        HashMapForLocal.put("Cauliflower", R.drawable.cauliflower);
        HashMapForLocal.put("Fruit and Vegi", R.drawable.giphy);
        HashMapForLocal.put("Dancing Vegi", R.drawable.vegjig);


    }

    @Override
    protected void onStop() {
        sliderLayout.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Log.e("silder clicked:", slider.getBundle().get("extra") + "");
        //  Toast.makeText(this,slider.getBundle().get("extra")+ "",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        Log.d("Slider Demo", "Page Change:" + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /*public void foodVisit(View view) {
        Intent intent = new Intent(HomeActivity.this, FoodActivity.class);
        startActivity(intent);
    }*/

    // Bottom

    private void prepareItem() {
        Item item = new Item(R.drawable.shutterstock, "Flower", "Price is 30");
        itemList.add(item);

        item = new Item(R.drawable.apple, "Fruit", "Price is 150");
        itemList.add(item);

        item = new Item(R.drawable.lettuce2, "Leaves", "Price is 70");
        itemList.add(item);
        item = new Item(R.drawable.carrot, "Root", "Price is 30");
        itemList.add(item);

        item = new Item(R.drawable.tomato, "Salad", "Price is 20");
        itemList.add(item);

    }

    @Override
    public void itemClicked(View view, int position) {
        if (position == 0) {
// flower
            new SharedPreferenceUtils(this).setFlowerVeg("flower");
            startActivity(new Intent(HomeActivity.this, FlowerVegActivity.class).putExtra("ItemPosition ", position));
        } else if (position == 1) {
            new SharedPreferenceUtils(this).setFlowerVeg("fruit");
            startActivity(new Intent(HomeActivity.this, FlowerVegActivity.class).putExtra("ItemPosition ", position));
        } else if (position == 2) {
            new SharedPreferenceUtils(this).setFlowerVeg("leaves");
            startActivity(new Intent(HomeActivity.this, FlowerVegActivity.class).putExtra("ItemPosition ", position));
        } else if (position == 3) {
            new SharedPreferenceUtils(this).setFlowerVeg("root");
            startActivity(new Intent(HomeActivity.this, FlowerVegActivity.class).putExtra("ItemPosition ", position));
        } else if (position == 4) {
            new SharedPreferenceUtils(this).setFlowerVeg("salad");
            startActivity(new Intent(HomeActivity.this, FlowerVegActivity.class).putExtra("ItemPosition ", position));
        } else {
            Log.e("position :", String.valueOf(position));
        }
    }

    // Navigation Drawer
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                moveTaskToBack(true);
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
          //  super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.fruit) {
            new SharedPreferenceUtils(this).setFlowerVeg("fruit");
            startActivity(new Intent(HomeActivity.this, FlowerVegActivity.class));
        } else if (id == R.id.salid) {
            new SharedPreferenceUtils(this).setFlowerVeg("salad");
            startActivity(new Intent(HomeActivity.this, FlowerVegActivity.class));
        } else if (id == R.id.flower) {
            new SharedPreferenceUtils(this).setFlowerVeg("flower");
            startActivity(new Intent(HomeActivity.this, FlowerVegActivity.class));
        } else if (id == R.id.leaves) {
            new SharedPreferenceUtils(this).setFlowerVeg("leaves");
            startActivity(new Intent(HomeActivity.this, FlowerVegActivity.class));
        } else if (id == R.id.root) {
            new SharedPreferenceUtils(this).setFlowerVeg("root");
            startActivity(new Intent(HomeActivity.this, FlowerVegActivity.class));
        } else if (id == R.id.nav_send) {

            startActivity(new Intent(HomeActivity.this, ContactActivity.class));
        } else if (id == R.id.mycart) {

            startActivity(new Intent(HomeActivity.this, MyCart.class));
        }
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void alertDialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(HomeActivity.this);
        builder1.setMessage("Please Connect to Internet");
        builder1.setCancelable(false).setIcon(R.drawable.ic_internet).setTitle("No Internet Connection");
        builder1.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                }).setNegativeButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!isOnline()) {
                    alertDialog();
                } else {
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alert = builder1.create();
        alert.show();
    }

    private boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
