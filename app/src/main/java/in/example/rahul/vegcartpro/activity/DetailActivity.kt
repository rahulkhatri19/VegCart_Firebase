package `in`.example.rahul.vegcartpro.activity

import `in`.example.rahul.vegcartpro.Model.CartModel
import `in`.example.rahul.vegcartpro.R
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.*
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import java.text.NumberFormat

class DetailActivity : AppCompatActivity() {
    /*  On click recycler view item this activity will open
     *  Use: detail of a particular product, Nutrition fact */
    var image = ""
    var detail = ""
    var detail1 = ""
    var detail2 = ""
    var detail3 = ""
    var detail4 = ""
    var detail5 = ""
    var imageView: ImageView? = null
    lateinit var textView: TextView
    lateinit var textView2: TextView
    lateinit var textView3: TextView
    lateinit var textView4: TextView
    lateinit var textView5: TextView
    var numberOfVeg = 1.0
    var priceOfVeg = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }
        imageView = findViewById(R.id.imageView)
        textView = findViewById(R.id.tv_d1)
        textView2 = findViewById(R.id.tv_d2)
        textView3 = findViewById(R.id.tv_d3)
        textView4 = findViewById(R.id.tv_d4)
        textView5 = findViewById(R.id.tv_price)
        val bundle = intent.extras
        image = bundle?.getString("imageurl")!!
        detail = bundle.getString("foodName")!!
        detail1 = bundle.getString("deta")!!
        detail2 = bundle.getString("deta2")!!
        detail3 = bundle.getString("deta3")!!
        detail4 = bundle.getString("deta4")!!
        detail5 = bundle.getString("deta5")!!
        val price = "₹ $detail5 per KG"
        Picasso.with(baseContext).load(image).into(imageView)
        textView.setText(detail1)
        textView2.setText(detail2)
        textView3.setText(detail3)
        textView4.setText(detail4)
        textView5.setText(price)
        //  5
    }

    // Onclick cart image send to Order Activity
    fun orderCart(view: View?) { /*Intent intent= new Intent(Detail.this, OrderActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("namefood",detail);
        bundle.putString("pricefood",detail5);
        intent.putExtras(bundle);
        startActivity(intent);*/
        val builder = AlertDialog.Builder(this)
        val alterLayout: View = layoutInflater.inflate(R.layout.alert_order_layout, null)
        builder.setView(alterLayout)
        val tvQuantity = alterLayout.findViewById<TextView>(R.id.tv_quantity)
        val tvPrice = alterLayout.findViewById<TextView>(R.id.tv_price)
        val btnIncrement = alterLayout.findViewById<Button>(R.id.btn_increment)
        val btnDecrement = alterLayout.findViewById<Button>(R.id.btn_decrement)
        val btnOk = alterLayout.findViewById<Button>(R.id.btn_ok)
        val btnCancel = alterLayout.findViewById<Button>(R.id.btn_cancel)
        val etDeliveryAdd = alterLayout.findViewById<EditText>(R.id.et_delivery_add)
        priceOfVeg = detail5.toDouble()
        tvPrice.text = "₹ $detail5"
        btnIncrement.setOnClickListener {
            numberOfVeg = numberOfVeg + 0.5
            // Increment of 500gm will done onclick + button
// display(numberOfVeg);
            tvQuantity.text = numberOfVeg.toString()
            //  displayPrice(numberOfVeg*priceOfVeg);
            tvPrice.text = NumberFormat.getCurrencyInstance().format(numberOfVeg * priceOfVeg)
        }
        btnDecrement.setOnClickListener {
            if (numberOfVeg >= 1) {
                numberOfVeg = numberOfVeg - 0.5
            }
            // Decrement of 500gm will done onclick - button
//  display(numberOfVeg);
            tvQuantity.text = numberOfVeg.toString()
            // displayPrice(numberOfVeg*priceOfVeg);
            tvPrice.text = NumberFormat.getCurrencyInstance().format(numberOfVeg * priceOfVeg)
        }
        btnOk.setOnClickListener {
            if (etDeliveryAdd.text.toString().trim { it <= ' ' } == "") {
                etDeliveryAdd.error = "Please Enter Delivery Address"
                etDeliveryAdd.requestFocus()
                // Toast.makeText(Detail.this, "Yes", Toast.LENGTH_SHORT).show();
            } else { // Toast.makeText(Detail.this, "Price: " + tvPrice.getText().toString() + "\nQuantity: " + tvQuantity.getText().toString() + "\nAddress: " + etDeliveryAdd.getText().toString(), Toast.LENGTH_SHORT).show();
                val database = FirebaseDatabase.getInstance()
                val ref = database.getReference("Cart")
                val newPostRef = ref.push()
                newPostRef.setValue(CartModel(detail, tvPrice.text.toString(), tvQuantity.text.toString(), etDeliveryAdd.text.toString()))
                Toast.makeText(baseContext, "Order Placed Successfully \n Thank you", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, HomeActivity::class.java))
            }
        }
        builder.setCancelable(false)
        val alert = builder.create()
        btnCancel.setOnClickListener { alert.dismiss() }
        alert.show()
    }

    override fun onResume() {
        super.onResume()
        val ab = supportActionBar
        if (ab != null) {
            ab.title = "Product Detail"
            ab.setDisplayHomeAsUpEnabled(true)
        }
    }

    // display the quantity between + and - button
/*
    private void display(double i) {
        tvQuantity.setText(""+i);
    }
*/
    // display price onclick +,- button
    /* private void displayPrice(double number) {

        tvPrice.setText(NumberFormat.getCurrencyInstance().format(number));
    }*/
}