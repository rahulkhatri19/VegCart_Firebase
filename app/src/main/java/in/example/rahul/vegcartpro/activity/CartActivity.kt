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

        cartList.add(CartModel("Apple", "125", "2", "", "https://firebasestorage.googleapis.com/v0/b/vegcartpro.appspot.com/o/food%2Fapple.jpg?alt=media&token=362c76b4-9991-4e8d-b8fc-bfa2915ce7c4"))
        cartList.add(CartModel("Broccoli", "110", "2.5", "", "https://firebasestorage.googleapis.com/v0/b/vegcartpro.appspot.com/o/food%2Fbroccoli.jpg?alt=media&token=d1cb439d-af6f-40c7-ae62-72d7bef2bd64"))

        rvCart.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvCart.adapter = CartAdapter(cartList, this)
    }
}