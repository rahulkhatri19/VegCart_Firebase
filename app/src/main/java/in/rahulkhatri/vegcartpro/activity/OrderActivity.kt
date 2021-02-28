package `in`.rahulkhatri.vegcartpro.activity

import `in`.rahulkhatri.vegcartpro.model.CartModel
import `in`.rahulkhatri.vegcartpro.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import java.text.NumberFormat

class OrderActivity : AppCompatActivity() {
    /*  onclick cart image on detail activity this will open
  * Use: To put order size and cost  */
    lateinit var tvQuantity: TextView
    lateinit var tvPrice: TextView
    var numberOfVeg = 1.0
    var priceOfVeg = 0.0
    var foodprice = ""
    var foodname = ""
    lateinit var address: EditText
    private val TAG = OrderActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        tvQuantity = findViewById(R.id.quantity_text_view)
        tvPrice = findViewById(R.id.price_text_view)
        address = findViewById(R.id.et_add)
        val bundle = intent.extras
        foodprice = bundle?.getString("pricefood")!!
        foodname = bundle.getString("namefood")!!
        priceOfVeg = foodprice.toDouble()
        tvPrice.text = "₹ $foodprice"
    }

    fun submitOrder(v: View?) { // String mPrice="You order "+numberOfVeg+" kg of Vegetable \n Thank you !";
//textView2.setText(String.valueOf(mPrice));
        val quantityVeg = tvQuantity.text.toString()
        val priceVeg = tvPrice.text.toString()
        val addVeg = address.text.toString()
        //String nameVeg= ;
        if (address.equals(" ") || address.length() == 0) {
            Toast.makeText(baseContext, "Please Enter Address \n Cant leave blank", Toast.LENGTH_SHORT).show()
        } else {
            val database = FirebaseDatabase.getInstance()
            val ref = database.getReference("Cart")
            val newPostRef = ref.push()
            newPostRef.setValue(CartModel(foodname, tvPrice.getText().toString(), tvQuantity.text.toString(), address.text.toString()))
            Toast.makeText(baseContext, "Order Placed Successfully \n Thank you", Toast.LENGTH_SHORT).show()
        }
        //Intent likeIntent = new Intent(this, LikeServices.class);
// likeIntent.putExtra(NOTIFICATION_ID_EXTRA,notificationId);
        val token = FirebaseInstanceId.getInstance().token
        Log.d(TAG, "Token: $token")
        Toast.makeText(this@OrderActivity, token, Toast.LENGTH_SHORT).show()
    }

    fun increment(v: View?) {
        numberOfVeg = numberOfVeg + 0.5
        // Increment of 500gm will done onclick + button
        display(numberOfVeg)
        displayPrice(numberOfVeg * priceOfVeg)
    }

    fun decrement(v: View?) {
        if (numberOfVeg >= 1) {
            numberOfVeg = numberOfVeg - 0.5
        }
        // Decrement of 500gm will done onclick - button
        display(numberOfVeg)
        displayPrice(numberOfVeg * priceOfVeg)
    }

    // display the quantity between + and - button
    private fun display(i: Double) {
        tvQuantity.text = "$i"
    }

    // display price onclick +,- button
    private fun displayPrice(number: Double) {
        tvPrice.text = NumberFormat.getCurrencyInstance().format(number)
    }
}