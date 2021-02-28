package `in`.rahulkhatri.vegcartpro.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceUtils(var mContext: Context?) {
//    var mContext: Context? = null
    var pref: SharedPreferences? = null
init {
//    mContext = context
    try {
        pref = mContext?.getSharedPreferences("Preference", Context.MODE_PRIVATE)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
   /* fun SharedPreferenceUtils(context: Context?) {
        mContext = context
        try {
            pref = mContext!!.getSharedPreferences("Preference", Context.MODE_PRIVATE)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }*/

    fun setFlowerVeg(veg: String?) {
        val editor = pref?.edit()
        editor?.putString("id", veg)
        editor?.apply()
    }

    fun getFlowerVeg(): String? {
        return if (pref!!.contains("id")) pref?.getString("id", "") else "null"
    }

    fun setCategoryItem(veg: String?) {
        val editor = pref?.edit()
        editor?.putString("id", veg)
        editor?.apply()
    }

    fun getCategoryItem(): String? {
        return if (pref!!.contains("id")) pref?.getString("id", "") else "null"
    }
}