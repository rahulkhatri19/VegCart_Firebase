package in.example.rahul.vegcartpro.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import in.example.rahul.vegcartpro.R;

public class ContactActivity extends AppCompatActivity {
    ImageView ib1,ib2,ib3,ib4,ib5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ib1 =  findViewById(R.id.img_btn1);
        ib2 =  findViewById(R.id.img_btn2);
        ib3 =  findViewById(R.id.img_btn3);
        ib4 =  findViewById(R.id.img_btn4);
        ib5 =  findViewById(R.id.image);

        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.facebook.com/rahulkhatri19";
                Uri web=Uri.parse(url);
                Intent intent=new Intent(Intent.ACTION_VIEW,web);
                if (intent.resolveActivity(getPackageManager()) != null){
                    startActivity(intent);
                }
            }
        });
        ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="https://github.com/rahulkhatri19";
                Uri web=Uri.parse(url);
                Intent intent=new Intent(Intent.ACTION_VIEW,web);
                if (intent.resolveActivity(getPackageManager()) != null){
                    startActivity(intent);
                }
            }
        });

        ib3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="https://linkedin.com/in/rahulkhatri19";
                Uri web=Uri.parse(url);
                Intent intent=new Intent(Intent.ACTION_VIEW,web);
                if (intent.resolveActivity(getPackageManager()) != null){
                    startActivity(intent);
                }
            }
        });
        // https://plus.google.com/u/0/115167433632871269546
        ib4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="https://plus.google.com/u/0/115167433632871269546";
                Uri web=Uri.parse(url);
                Intent intent=new Intent(Intent.ACTION_VIEW,web);
                if (intent.resolveActivity(getPackageManager()) != null){
                    startActivity(intent);
                }
            }
        });
    }
}
