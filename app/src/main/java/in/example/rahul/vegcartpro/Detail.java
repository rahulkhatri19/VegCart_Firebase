package in.example.rahul.vegcartpro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Detail extends AppCompatActivity {
/*  On click recycler view item this activity will open
*  Use: detail of a particular product, Nutrition fact */
    String image,detail,detail1,detail2,detail3,detail4,detail5;
    ImageView imageView;
    TextView textView, textView2, textView3, textView4, textView5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        imageView=(ImageView)findViewById(R.id.imageView);
        textView=(TextView)findViewById(R.id.tv_d1);
        textView2=(TextView)findViewById(R.id.tv_d2);
        textView3=(TextView)findViewById(R.id.tv_d3);
        textView4=(TextView)findViewById(R.id.tv_d4);
        textView5=(TextView)findViewById(R.id.tv_price);

        Bundle bundle=getIntent().getExtras();
        image=bundle.getString("imageurl");
        detail=bundle.getString("foodName");
        detail1=bundle.getString("deta");
        detail2=bundle.getString("deta2");
        detail3=bundle.getString("deta3");
        detail4=bundle.getString("deta4");
        detail5=bundle.getString("deta5");

        String price="₹ "+String.valueOf(detail5)+" per KG";
        Picasso.with(getBaseContext()).load(image).into(imageView);
        textView.setText(detail1);
        textView2.setText(detail2);
        textView3.setText(detail3);
        textView4.setText(detail4);
        textView5.setText(price);
//  5
    }
    // Onclick cart image send to Order Activity
    public void orderCart(View view){
        Intent intent= new Intent(Detail.this, OrderActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("namefood",detail);
        bundle.putString("pricefood",detail5);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
