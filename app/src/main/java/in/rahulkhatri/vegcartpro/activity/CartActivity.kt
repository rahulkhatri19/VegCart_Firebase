package `in`.rahulkhatri.vegcartpro.activity

import `in`.rahulkhatri.vegcartpro.adapter.CartAdapter
import `in`.rahulkhatri.vegcartpro.model.BucketModel
import `in`.rahulkhatri.vegcartpro.utils.Constants
import `in`.rahulkhatri.vegcartpro.utils.Utility
import `in`.rahulkhatri.vegcartpro.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : AppCompatActivity() {
    val cartList = mutableListOf<BucketModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        getCardData(this)
        rvCart.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        btnCheckout.setOnClickListener {
            val database = FirebaseDatabase.getInstance()
            val ref = database.getReference(Constants.CART)
//            val newPostRef = ref.push()
            ref.child(Utility.getDeviceId(this)).setValue(cartList)
            removeBucketProduct()
        }
    }

    //  remove product from Bucket after adding in cart.
    private fun removeBucketProduct() {
        val bucketReference = FirebaseDatabase.getInstance().getReference("${Constants.BUCKET}/${Utility.getDeviceId(this)}")
        bucketReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children){
                    data.ref.removeValue()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    //  get data from api and arrange them in view.
    private fun getCardData(activity: CartActivity) {
        val bucketReference = FirebaseDatabase.getInstance().getReference("${Constants.BUCKET}/${Utility.getDeviceId(this)}")
        cartList.clear()
        bucketReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children){
                    cartList.add(data.getValue(BucketModel::class.java)!!)
                }
                rvCart.adapter = CartAdapter(cartList, activity)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}