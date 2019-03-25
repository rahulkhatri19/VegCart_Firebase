package in.example.rahul.vegcartpro.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import in.example.rahul.vegcartpro.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
     //   ActionBar actionBar=getSupportActionBar();
       // actionBar.hide();
        new CountDownTimer(1000,1000){
            @Override
            public void onTick(long millisUntilFinished){

            }

            @Override
            public void onFinish() {
                Intent intent=new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }.start();
    }
}
