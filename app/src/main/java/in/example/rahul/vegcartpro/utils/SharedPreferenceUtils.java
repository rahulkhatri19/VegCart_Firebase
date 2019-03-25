package in.example.rahul.vegcartpro.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtils {

    Context mContext;
    SharedPreferences pref;

    public SharedPreferenceUtils(Context context){
        this.mContext= context;
        try {
            pref= this.mContext.getSharedPreferences("Preference", Context.MODE_PRIVATE);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void setFlowerVeg(String veg){
        SharedPreferences.Editor editor= pref.edit();
        editor.putString("id", veg);
        editor.commit();
    }
    public String getFlowerVeg(){
       if (pref.contains("id")) return pref.getString("id", "");
       else return "null";
    }
}
