package in.example.rahul.vegcartpro.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.text.NumberFormat;

import in.example.rahul.vegcartpro.Model.Cart;
import in.example.rahul.vegcartpro.R;

public class OrderActivity extends AppCompatActivity {
/*  onclick cart image on detail activity this will open
  * Use: To put order size and cost  */
    TextView tvQuantity,tvPrice;
    public double numberOfVeg=1 ;
    public double priceOfVeg ;
    public String foodprice;
    public String foodname;
    EditText address;
    private static final String TAG = "OrderActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        tvQuantity=findViewById(R.id.quantity_text_view);
        tvPrice=findViewById(R.id.price_text_view);
        address=findViewById(R.id.et_add);
        Bundle bundle=getIntent().getExtras();
        foodprice= bundle.getString("pricefood");
        foodname=bundle.getString("namefood");
        priceOfVeg= Double.parseDouble(foodprice);
        tvPrice.setText("₹ "+String.valueOf(foodprice));
    }
    public void submitOrder(View v){

       // String mPrice="You order "+numberOfVeg+" kg of Vegetable \n Thank you !";
        //textView2.setText(String.valueOf(mPrice));
        String quantityVeg=tvQuantity.getText().toString();
        String priceVeg= tvPrice.getText().toString();
        String addVeg=address.getText().toString();
        //String nameVeg= ;
        if (address.equals("") || address.length()==0 ){
            Toast.makeText(getBaseContext(),"Please Enter Address \n Cant leave blank",Toast.LENGTH_SHORT).show();
        }
       else {
        final FirebaseDatabase database=FirebaseDatabase.getInstance();

        final DatabaseReference ref=database.getReference("Cart");

        DatabaseReference newPostRef=ref.push();

        newPostRef.setValue(new Cart(foodname,tvPrice.getText().toString(),tvQuantity.getText().toString(),address.getText().toString()));

        Toast.makeText(getBaseContext(),"Order Placed Successfully \n Thank you",Toast.LENGTH_SHORT).show();   }

        //Intent likeIntent = new Intent(this, LikeServices.class);
       // likeIntent.putExtra(NOTIFICATION_ID_EXTRA,notificationId);

        String token= FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG,"Token: "+token);
        Toast.makeText(OrderActivity.this,token,Toast.LENGTH_SHORT).show();
    }
    public void increment(View v){
        numberOfVeg=numberOfVeg+0.5;
        // Increment of 500gm will done onclick + button
        display(numberOfVeg);
        displayPrice(numberOfVeg*priceOfVeg);
    }
    public void decrement(View v){
        if (numberOfVeg >= 1) {
            numberOfVeg=numberOfVeg-0.5;
        } else {
        }

        // Decrement of 500gm will done onclick - button
        display(numberOfVeg);
        displayPrice(numberOfVeg*priceOfVeg);
    }
    // display the quantity between + and - button
    private void display(double i) {
        tvQuantity.setText(""+i);
    }
    // display price onclick +,- button
    private void displayPrice(double number) {

        tvPrice.setText(NumberFormat.getCurrencyInstance().format(number));
    }
}
