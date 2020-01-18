package `in`.example.rahul.vegcartpro.activity

import `in`.example.rahul.vegcartpro.R
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView

class ContactActivity : AppCompatActivity() {
    private lateinit var ib1: ImageView
    private lateinit var ib2: ImageView
    private lateinit var ib3: ImageView
    private lateinit var ib4: ImageView
    private lateinit var ib5: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)
        ib1 = findViewById(R.id.img_btn1)
        ib2 = findViewById(R.id.img_btn2)
        ib3 = findViewById(R.id.img_btn3)
        ib4 = findViewById(R.id.img_btn4)
//        ib5 = findViewById(R.id.image)
        ib1.setOnClickListener {
            val url = "https://www.facebook.com/rahulkhatri19"
            val web = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, web)
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }
        ib2.setOnClickListener {
            val url = "https://github.com/rahulkhatri19"
            val web = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, web)
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }
        ib3.setOnClickListener {
            val url = "https://linkedin.com/in/rahulkhatri19"
            val web = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, web)
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }
        ib4.setOnClickListener {
            val url = "https://twitter.com/rahulkhatri019"
            val web = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, web)
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }
    }
}