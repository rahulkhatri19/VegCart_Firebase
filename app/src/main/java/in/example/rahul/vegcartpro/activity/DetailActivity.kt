package `in`.example.rahul.vegcartpro.activity

import `in`.example.rahul.vegcartpro.model.CartModel
import `in`.example.rahul.vegcartpro.R
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity() {
    /*  On click recycler view item this activity will open
     *  Use: detail of a particular product, Nutrition fact */
    var image = ""
    var foodName = ""
    var foodAdvantage = ""
    var diseaseHeal = ""
    var vitamins = ""
    var precautions = ""
    var pricefood = ""
    var numberOfVeg = 1.0
    var priceOfVeg = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }
        val bundle = intent.extras
        image = bundle?.getString("imageurl")!!
        foodName = bundle.getString("foodName")!!
        foodAdvantage = bundle.getString("advantage")!!
        vitamins = bundle.getString("vitamins")!!
        diseaseHeal = bundle.getString("diseaseHeal")!!
        precautions = bundle.getString("precautions")!!
        pricefood = bundle.getString("price")!!
        val price = "₹ $pricefood per KG"
        Picasso.with(baseContext).load(image).into(iv_food)

        tv_detail_advt.text = foodAdvantage
        tv_detail_vitamin.text = vitamins
        tv_detail_disease.text = diseaseHeal
        tv_detail_precaution.text = precautions
        tv_price.text = price
    }

    // Onclick cart image send to Order Activity
    fun orderCart(view: View?) {
        /*Intent intent= new Intent(Detail.this, OrderActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("namefood",foodName);
        bundle.putString("pricefood",pricefood);
        intent.putExtras(bundle);
        startActivity(intent);*/
        val builder = AlertDialog.Builder(this)
        val alertOrderLayout: View = layoutInflater.inflate(R.layout.alert_order_layout, null)
        builder.setView(alertOrderLayout)
        val tvQuantity = alertOrderLayout.findViewById<TextView>(R.id.tv_quantity)
        val tvPrice = alertOrderLayout.findViewById<TextView>(R.id.tv_price)
        val btnIncrement = alertOrderLayout.findViewById<Button>(R.id.btn_increment)
        val btnDecrement = alertOrderLayout.findViewById<Button>(R.id.btn_decrement)
        val btnOk = alertOrderLayout.findViewById<Button>(R.id.btn_ok)
        val btnCancel = alertOrderLayout.findViewById<Button>(R.id.btn_cancel)
//        val etDeliveryAdd = alertOrderLayout.findViewById<EditText>(R.id.et_delivery_add)
        priceOfVeg = pricefood.toDouble()
        tvPrice.text = "₹ $pricefood"
        btnIncrement.setOnClickListener {
            numberOfVeg = numberOfVeg + 0.5
            // Increment of 500gm will done onclick + button
// display(numberOfVeg);
            tvQuantity.text = numberOfVeg.toString()
            //  displayPrice(numberOfVeg*priceOfVeg);
            val price = numberOfVeg * priceOfVeg
            tvPrice.text = "₹ $price" //  NumberFormat.getCurrencyInstance().format(numberOfVeg * priceOfVeg)
        }
        btnDecrement.setOnClickListener {
            if (numberOfVeg >= 1) {
                numberOfVeg -= 0.5
            }
            // Decrement of 500gm will done onclick - button
//  display(numberOfVeg);
            tvQuantity.text = numberOfVeg.toString()
            // displayPrice(numberOfVeg*priceOfVeg);
            val price = numberOfVeg * priceOfVeg
            tvPrice.text = "₹ $price"
//            tvPrice.text = NumberFormat.getCurrencyInstance().format(numberOfVeg * priceOfVeg)
        }
        btnOk.setOnClickListener {
//            if (etDeliveryAdd.text.toString().trim { it <= ' ' } == "") {
//                etDeliveryAdd.error = "Please Enter Delivery Address"
//                etDeliveryAdd.requestFocus()
//                // Toast.makeText(Detail.this, "Yes", Toast.LENGTH_SHORT).show();
//            } else {
                val database = FirebaseDatabase.getInstance()
                val ref = database.getReference("Cart")
                val newPostRef = ref.push()
                newPostRef.setValue(CartModel(foodName, tvPrice.text.toString(), tvQuantity.text.toString(), "Cart", image))
                Toast.makeText(baseContext, "Order Added to Cart Successfully \n Thank you", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, HomeActivity::class.java))
        }
        builder.setCancelable(false)
        val alert:AlertDialog = builder.create()
        btnCancel.setOnClickListener { alert.dismiss() }
        alert.show()
    }

    override fun onResume() {
        super.onResume()
        val ab = supportActionBar
        if (ab != null) {
            if(foodName.equals("")){
                ab.title = "Food Description"
            } else {
                ab.title = foodName
            }

            ab.setDisplayHomeAsUpEnabled(true)
        }
    }
}