package in.example.rahul.vegcartpro.utils;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by hifzaZainul on 2/2/2017.
 */

public class FireIDService extends FirebaseInstanceIdService {
    public static String tokenFireBase="";
    @Override
    public void onTokenRefresh() {
        tokenFireBase = FirebaseInstanceId.getInstance().getToken();
        Log.d("Not","Token ["+tokenFireBase+"]");

    }

}