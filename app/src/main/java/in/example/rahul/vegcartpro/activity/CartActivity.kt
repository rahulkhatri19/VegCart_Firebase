package `in`.example.rahul.vegcartpro.activity

import `in`.example.rahul.vegcartpro.R
import `in`.example.rahul.vegcartpro.adapter.CartAdapter
import `in`.example.rahul.vegcartpro.model.BucketModel
import `in`.example.rahul.vegcartpro.model.CartModel
import `in`.example.rahul.vegcartpro.model.MyCartModel
import `in`.example.rahul.vegcartpro.utils.Constants
import `in`.example.rahul.vegcartpro.utils.Utility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val bucketReference = FirebaseDatabase.getInstance().getReference("${Constants.BUCKET}/${Utility.getDeviceId(this)}")
        bucketReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.e("CartAct:", "snap${snapshot.value}")
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        val cartList = mutableListOf<BucketModel>()

        cartList.add(BucketModel("abcd", "https://firebasestorage.googleapis.com/v0/b/vegcartpro.appspot.com/o/food%2Fapple.jpg?alt=media&token=362c76b4-9991-4e8d-b8fc-bfa2915ce7c4", "Apple", 55.0, 2.0, 110.0))
        cartList.add(BucketModel("", "https://firebasestorage.googleapis.com/v0/b/vegcartpro.appspot.com/o/food%2Fbroccoli.jpg?alt=media&token=d1cb439d-af6f-40c7-ae62-72d7bef2bd64", "Broccoli", 45.0, 2.5, 112.5))

        rvCart.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvCart.adapter = CartAdapter(cartList, this)
    }
}