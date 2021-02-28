package `in`.rahulkhatri.vegcartpro.activity

import `in`.rahulkhatri.vegcartpro.R
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_contact.*

class ContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        tv_facebook.setOnClickListener {
        }
        tv_github.setOnClickListener {
            val web = Uri.parse("https://github.com/rahulkhatri19")
            openBrowser(web)
        }
        tv_linkedin.setOnClickListener {
            val web = Uri.parse("https://linkedin.com/in/rahulkhatri19")
            openBrowser(web)
        }
        tv_twitter.setOnClickListener {
            val web = Uri.parse("https://twitter.com/rahulkhatri019")
            openBrowser(web)
        }
        tv_gmail.setOnClickListener {
            val web = Uri.parse("mailto:rkhatri.dev@gmail.com")
            openBrowser(web)
        }
    }

    private fun openBrowser(web: Uri?) {
        val intent = Intent(Intent.ACTION_VIEW, web)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}