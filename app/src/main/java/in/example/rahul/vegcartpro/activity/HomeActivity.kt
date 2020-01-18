package `in`.example.rahul.vegcartpro.activity

import `in`.example.rahul.vegcartpro.Model.ItemModel
import `in`.example.rahul.vegcartpro.MyAdapter
import `in`.example.rahul.vegcartpro.R
import `in`.example.rahul.vegcartpro.utils.SharedPreferenceUtils
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.daimajia.slider.library.Animations.DescriptionAnimation
import com.daimajia.slider.library.SliderLayout
import com.daimajia.slider.library.SliderTypes.BaseSliderView
import com.daimajia.slider.library.SliderTypes.BaseSliderView.OnSliderClickListener
import com.daimajia.slider.library.SliderTypes.TextSliderView
import com.daimajia.slider.library.Tricks.ViewPagerEx
import java.util.*

class HomeActivity : AppCompatActivity(), OnSliderClickListener, ViewPagerEx.OnPageChangeListener, MyAdapter.ClickListener, NavigationView.OnNavigationItemSelectedListener {
    private val itemList: MutableList<ItemModel> = mutableListOf()
    private var recyclerView: RecyclerView? = null
    var adapter: MyAdapter? = null
    private val main: LinearLayout? = null
    var sliderLayout: SliderLayout? = null
    var HashMapForURL: HashMap<String, String>? = null
    var HashMapForLocal: HashMap<String, Int> = HashMap()
    var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = "Menu"
        sliderLayout = findViewById(R.id.slider)
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (!isOnline()) {
            alertDialog()
        }
        val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        val headerView = navigationView.getHeaderView(0)
        val txtFullName = headerView.findViewById<TextView>(R.id.txtFullName)
        // recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
// layoutManager.
// Call below method if you want images online
//AddImagesUrlOnline();
// Call below method if you want images offline
        AddImageUrlLocal()
        // Call below method if you want to stop Automatic sliding
//sliderLayout.stopAutoCycle();
        for (name in HashMapForLocal.keys) {
            val textSliderView = TextSliderView(this@HomeActivity)
            textSliderView.description(name).image(HashMapForLocal[name]!!).setScaleType(BaseSliderView.ScaleType.CenterCrop).setOnSliderClickListener(this)
            textSliderView.bundle(Bundle())
            textSliderView.bundle.putString("extra", name)
            sliderLayout?.addSlider(textSliderView)
        }
        sliderLayout?.setPresetTransformer(SliderLayout.Transformer.DepthPage)
        sliderLayout?.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom)
        sliderLayout?.setCustomAnimation(DescriptionAnimation())
        sliderLayout?.setDuration(3000)
        sliderLayout?.addOnPageChangeListener(this@HomeActivity)
        // Bottom
        recyclerView = findViewById(R.id.recyclerView)
        prepareItem()
        adapter = MyAdapter(itemList)
        adapter?.setClickListener(this)
        adapter?.setClickListener(this)
        //RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getApplicationContext());
// Try Grid layout for Recycler view.
        recyclerView?.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        recyclerView?.itemAnimator = DefaultItemAnimator()
        recyclerView?.adapter = adapter
    }

    fun AddImagesUrlOnline() {
        HashMapForURL = HashMap()
        HashMapForURL!!["All Veg"] = "http://"
    }

    fun AddImageUrlLocal() {
        HashMapForLocal = HashMap()
        HashMapForLocal["Fruits"] = R.drawable.fruit
        HashMapForLocal["Cauliflower"] = R.drawable.cauliflower
        HashMapForLocal["Fruit and Vegi"] = R.drawable.giphy
        HashMapForLocal["Dancing Vegi"] = R.drawable.vegjig
    }

    override fun onStop() {
        sliderLayout!!.stopAutoCycle()
        super.onStop()
    }

    override fun onSliderClick(slider: BaseSliderView) {

        Log.e("silder clicked:", " ${slider.bundle["extra"]}")
        //  Toast.makeText(this,slider.getBundle().get("extra")+ "",Toast.LENGTH_SHORT).show();
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) {
        Log.d("Slider Demo", "Page Change:$position")
    }

    override fun onPageScrollStateChanged(state: Int) {}

    /*public void foodVisit(View view) {
        Intent intent = new Intent(HomeActivity.this, FoodActivity.class);
        startActivity(intent);
    }*/

    // Bottom

    /*public void foodVisit(View view) {
        Intent intent = new Intent(HomeActivity.this, FoodActivity.class);
        startActivity(intent);
    }*/
// Bottom
    private fun prepareItem() {
//        var item = Item(R.drawable.shutterstock, "Flower", "Price is 30")
//        itemList.add(item)
        itemList.add(ItemModel(R.drawable.shutterstock, "Flower", "Price is 30"))
        itemList.add(ItemModel(R.drawable.apple, "Fruit", "Price is 150"))
        itemList.add(ItemModel(R.drawable.lettuce2, "Leaves", "Price is 70"))
        itemList.add(ItemModel(R.drawable.carrot, "Root", "Price is 30"))
        itemList.add(ItemModel(R.drawable.tomato, "Salad", "Price is 20"))
    }

    override fun itemClicked(view: View?, position: Int) {

        when (position) {
            0 -> SharedPreferenceUtils(this).setFlowerVeg("flower")
            1 -> SharedPreferenceUtils(this).setFlowerVeg("fruit")
            2 -> SharedPreferenceUtils(this).setFlowerVeg("leaves")
            3 -> SharedPreferenceUtils(this).setFlowerVeg("root")
            4 -> SharedPreferenceUtils(this).setFlowerVeg("salad")
            else -> Log.e("position :", position.toString())
        }
        startActivity(Intent(this@HomeActivity, FlowerVegActivity::class.java).putExtra("ItemPosition ", position))
    }

    // Navigation Drawer
    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed()
                moveTaskToBack(true)
                return
            }
            doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()
            Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
            //  super.onBackPressed();
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.fruit -> {
                SharedPreferenceUtils(this).setFlowerVeg("fruit")
                startActivity(Intent(this@HomeActivity, FlowerVegActivity::class.java))
            }
            R.id.salid -> {
                SharedPreferenceUtils(this).setFlowerVeg("salad")
                startActivity(Intent(this@HomeActivity, FlowerVegActivity::class.java))
            }
            R.id.flower -> {
                SharedPreferenceUtils(this).setFlowerVeg("flower")
                startActivity(Intent(this@HomeActivity, FlowerVegActivity::class.java))
            }
            R.id.leaves -> {
                SharedPreferenceUtils(this).setFlowerVeg("leaves")
                startActivity(Intent(this@HomeActivity, FlowerVegActivity::class.java))
            }
            R.id.root -> {
                SharedPreferenceUtils(this).setFlowerVeg("root")
                startActivity(Intent(this@HomeActivity, FlowerVegActivity::class.java))
            }
            R.id.nav_send -> startActivity(Intent(this@HomeActivity, ContactActivity::class.java))
            R.id.mycart -> startActivity(Intent(this@HomeActivity, MyCartActivity::class.java))
        }
        val drawerLayout = findViewById<androidx.drawerlayout.widget.DrawerLayout>(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun alertDialog() {
        val builder = AlertDialog.Builder(this@HomeActivity)
        builder.setMessage("Please Connect to Internet")
        builder.setCancelable(false).setIcon(R.drawable.ic_internet).setTitle("No Internet Connection")
        builder.setPositiveButton("OK"
        ) { dialog, id ->
            dialog.cancel()
            finish()
        }.setNegativeButton("Retry") { dialog, which ->
            if (!isOnline()) {
                alertDialog()
            } else {
                dialog.dismiss()
            }
        }
        val alert = builder.create()
        alert.show()
    }

    private fun isOnline(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected
    }
}