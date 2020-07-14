package `in`.example.rahul.vegcartpro.activity

import `in`.example.rahul.vegcartpro.R
import `in`.example.rahul.vegcartpro.adapter.CartAdapter
import `in`.example.rahul.vegcartpro.model.CartModel
import `in`.example.rahul.vegcartpro.model.ItemModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val cartList = mutableListOf<CartModel>()

        rvCart.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvCart.adapter = CartAdapter(cartList, this)
    }
}