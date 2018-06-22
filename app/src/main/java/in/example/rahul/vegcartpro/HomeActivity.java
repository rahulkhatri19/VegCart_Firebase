package in.example.rahul.vegcartpro;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

public class HomeActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener, MyAdapter.ClickListener, NavigationView.OnNavigationItemSelectedListener {

    private List<Item> itemList= new ArrayList<>();
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private LinearLayout main;
    SliderLayout sliderLayout;
    HashMap<String, String> HashMapForURL;
    HashMap<String, Integer> HashMapForLocal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        sliderLayout=(SliderLayout)findViewById(R.id.slider);
        DrawerLayout drawer=(DrawerLayout) findViewById(R.id.drawer_layout);

       ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView=navigationView.getHeaderView(0);
       TextView txtFullName=(TextView)headerView.findViewById(R.id.txtFullName);
        // recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
       // layoutManager.
        // Call below method if you want images online
        //AddImagesUrlOnline();

        // Call below method if you want images offline
       AddImageUrlLocal();

        // Call below method if you want to stop Automatic sliding
        //sliderLayout.stopAutoCycle();

        for (String name : HashMapForLocal.keySet()){
            TextSliderView textSliderView= new TextSliderView(HomeActivity.this);

            textSliderView.description(name).image(HashMapForLocal.get(name)).setScaleType(BaseSliderView.ScaleType.CenterCrop).setOnSliderClickListener(this);

            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra",name);
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.DepthPage);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(3000);
        sliderLayout.addOnPageChangeListener(HomeActivity.this);

        // Bottom
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        prepareItem();

        adapter= new MyAdapter(itemList);
        adapter.setClickListener(this);

        //RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getApplicationContext());
// Try Grid layout for Recycler view.
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
    public void AddImagesUrlOnline(){
        HashMapForURL= new HashMap<String, String>();

        HashMapForURL.put("All Veg","http://");
    }
    public void AddImageUrlLocal(){
        HashMapForLocal= new HashMap<String, Integer>();

        HashMapForLocal.put("Fruits",R.drawable.fruit);
        HashMapForLocal.put("Cauliflower",R.drawable.cauliflower);
        HashMapForLocal.put("Fruit and Vegi",R.drawable.giphy);
        HashMapForLocal.put("Dancing Vegi",R.drawable.vegjig);


    }

    @Override
    protected void onStop() {
        sliderLayout.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this,slider.getBundle().get("extra")+ "",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        Log.d("Slider Demo", "Page Change:" +position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    public void foodVisit(View view){
        Intent intent= new Intent(HomeActivity.this, FoodActivity.class);
        startActivity(intent);
    }

    // Bottom

    private void prepareItem(){
        Item item= new Item(R.drawable.shutterstock,"Flower","Price is 30");
        itemList.add(item);

        item= new Item(R.drawable.apple,"Fruit","Price is 150");
        itemList.add(item);

        item= new Item(R.drawable.lettuce2,"Leaves","Price is 70");
        itemList.add(item);
        item= new Item(R.drawable.carrot,"Root","Price is 30");
        itemList.add(item);

        item= new Item(R.drawable.tomato,"Salad","Price is 20");
        itemList.add(item);

    }

    @Override
    public void itemClicked(View view, int position){
        if (position==0){
            Intent intent= new Intent(HomeActivity.this, FlowerVegActivity.class);
            intent.putExtra("ItemPosition ", position);
            startActivity(intent);
        }
        else if (position == 1){
            Intent intent= new Intent(HomeActivity.this, FruitActivity.class);
            intent.putExtra("ItemPosition ", position);
            startActivity(intent);
        }
        else if (position == 2){
            Intent intent= new Intent(HomeActivity.this, LeavesActivity.class);
            intent.putExtra("ItemPosition ", position);
            startActivity(intent);
        }
        else if (position == 3){
            Intent intent= new Intent(HomeActivity.this, RootActivity.class);
            intent.putExtra("ItemPosition ", position);
            startActivity(intent);
        }
        else if (position == 4){
            Intent intent= new Intent(HomeActivity.this, SaladActivity.class);
            intent.putExtra("ItemPosition ", position);
            startActivity(intent);
        }
        else {
            System.out.println("position..."+position);
        }
    }

    // Navigation Drawer
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home,menu);
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

        if (id == R.id.fruit){
           Intent intent = new Intent(HomeActivity.this,FruitActivity.class);

           startActivity(intent);
        }
        else if (id == R.id.salid){
            Intent intent = new Intent(HomeActivity.this,SaladActivity.class);

            startActivity(intent);
        }
        else if (id == R.id.flower){
           Intent intent = new Intent(HomeActivity.this,FlowerVegActivity.class);

           startActivity(intent);
        }
        else if (id == R.id.leaves){
            Intent intent = new Intent(HomeActivity.this,LeavesActivity.class);

            startActivity(intent);
        }
        else if (id == R.id.root){
            Intent intent = new Intent(HomeActivity.this,RootActivity.class);

            startActivity(intent);
        }
        else if (id == R.id.nav_send){
            Intent intent = new Intent(HomeActivity.this,ContactActivity.class);

            startActivity(intent);
        }
        else if (id == R.id.mycart){
            Intent intent = new Intent(HomeActivity.this,MyCart.class);

            startActivity(intent);
        }
        DrawerLayout drawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
