package in.example.rahul.vegcartpro.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;

import in.example.rahul.vegcartpro.Model.Cart;
import in.example.rahul.vegcartpro.R;

public class Detail extends AppCompatActivity {
    /*  On click recycler view item this activity will open
     *  Use: detail of a particular product, Nutrition fact */
    String image, detail, detail1, detail2, detail3, detail4, detail5;
    ImageView imageView;
    TextView textView, textView2, textView3, textView4, textView5;
    public double numberOfVeg = 1;
    public double priceOfVeg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.tv_d1);
        textView2 = findViewById(R.id.tv_d2);
        textView3 = findViewById(R.id.tv_d3);
        textView4 = findViewById(R.id.tv_d4);
        textView5 = findViewById(R.id.tv_price);

        Bundle bundle = getIntent().getExtras();
        image = bundle.getString("imageurl");
        detail = bundle.getString("foodName");
        detail1 = bundle.getString("deta");
        detail2 = bundle.getString("deta2");
        detail3 = bundle.getString("deta3");
        detail4 = bundle.getString("deta4");
        detail5 = bundle.getString("deta5");

        String price = "₹ " + String.valueOf(detail5) + " per KG";
        Picasso.with(getBaseContext()).load(image).into(imageView);
        textView.setText(detail1);
        textView2.setText(detail2);
        textView3.setText(detail3);
        textView4.setText(detail4);
        textView5.setText(price);
//  5
    }

    // Onclick cart image send to Order Activity
    public void orderCart(View view) {
        /*Intent intent= new Intent(Detail.this, OrderActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("namefood",detail);
        bundle.putString("pricefood",detail5);
        intent.putExtras(bundle);
        startActivity(intent);*/
        AlertDialog.Builder builder = new AlertDialog.Builder(Detail.this);
        View alterLayout = getLayoutInflater().inflate(R.layout.alert_order_layout, null);
        builder.setView(alterLayout);
        final TextView tvQuantity = alterLayout.findViewById(R.id.tv_quantity);
        final TextView tvPrice = alterLayout.findViewById(R.id.tv_price);
        Button btnIncrement = alterLayout.findViewById(R.id.btn_increment);
        Button btnDecrement = alterLayout.findViewById(R.id.btn_decrement);
        Button btnOk = alterLayout.findViewById(R.id.btn_ok);
        Button btnCancel = alterLayout.findViewById(R.id.btn_cancel);
        final EditText etDeliveryAdd = alterLayout.findViewById(R.id.et_delivery_add);
        priceOfVeg = Double.parseDouble(detail5);
        tvPrice.setText("₹ " + detail5);
        btnIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOfVeg = numberOfVeg + 0.5;
                // Increment of 500gm will done onclick + button
                // display(numberOfVeg);
                tvQuantity.setText(String.valueOf(numberOfVeg));
                //  displayPrice(numberOfVeg*priceOfVeg);
                tvPrice.setText(NumberFormat.getCurrencyInstance().format(numberOfVeg * priceOfVeg));
            }
        });
        btnDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOfVeg >= 1) {
                    numberOfVeg = numberOfVeg - 0.5;
                }

                // Decrement of 500gm will done onclick - button
                //  display(numberOfVeg);
                tvQuantity.setText(String.valueOf(numberOfVeg));
                // displayPrice(numberOfVeg*priceOfVeg);
                tvPrice.setText(NumberFormat.getCurrencyInstance().format(numberOfVeg * priceOfVeg));
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etDeliveryAdd.getText().toString().trim().equals("")) {
                    etDeliveryAdd.setError("Please Enter Delivery Address");
                    etDeliveryAdd.requestFocus();
                   // Toast.makeText(Detail.this, "Yes", Toast.LENGTH_SHORT).show();
                } else {
                   // Toast.makeText(Detail.this, "Price: " + tvPrice.getText().toString() + "\nQuantity: " + tvQuantity.getText().toString() + "\nAddress: " + etDeliveryAdd.getText().toString(), Toast.LENGTH_SHORT).show();

                    final FirebaseDatabase database=FirebaseDatabase.getInstance();

                    final DatabaseReference ref=database.getReference("Cart");

                    DatabaseReference newPostRef=ref.push();

                    newPostRef.setValue(new Cart(detail,tvPrice.getText().toString(),tvQuantity.getText().toString(),etDeliveryAdd.getText().toString()));

                    Toast.makeText(getBaseContext(),"Order Placed Successfully \n Thank you",Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(Detail.this, HomeActivity.class));
                }
            }
        });
        builder.setCancelable(false);
        final AlertDialog alert = builder.create();
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
        alert.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Product Detail");
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    // display the quantity between + and - button
/*
    private void display(double i) {
        tvQuantity.setText(""+i);
    }
*/
    // display price onclick +,- button
   /* private void displayPrice(double number) {

        tvPrice.setText(NumberFormat.getCurrencyInstance().format(number));
    }*/
}
