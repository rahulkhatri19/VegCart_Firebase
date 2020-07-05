package `in`.example.rahul.vegcartpro.view.interfaces

import android.view.View
import com.google.firebase.database.FirebaseDatabase

interface ItemClickListener {
    fun onClick(view: View?, position: Int, isLongClick: Boolean)
}

//public interface ItemClickListener {
//    void onClick(View view, int position, boolean isLongClick);
//}