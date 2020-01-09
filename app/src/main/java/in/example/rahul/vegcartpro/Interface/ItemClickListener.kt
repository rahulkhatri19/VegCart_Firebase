package `in`.example.rahul.vegcartpro.Interface

import android.view.View

interface ItemClickListener {
    fun onClick(view: View?, position: Int, isLongClick: Boolean)
}

//public interface ItemClickListener {
//    void onClick(View view, int position, boolean isLongClick);
//}