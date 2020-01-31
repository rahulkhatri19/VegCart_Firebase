package `in`.example.rahul.vegcartpro.utils

import `in`.example.rahul.vegcartpro.R
import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.progressbar_layout.view.*

class CustomProgressBar {

    companion object {
        lateinit var alertDialog: AlertDialog
        fun ProgressBar(context: Context, progressText: String) {
            val layoutBuilder = LayoutInflater.from(context).inflate(R.layout.progressbar_layout, null)
            val builder: AlertDialog.Builder = AlertDialog.Builder(context).setView(layoutBuilder)
            builder.setCancelable(false)
            alertDialog = builder.create()
            layoutBuilder.tv_progress_bar.text = progressText
            layoutBuilder.tv_progress_bar.setOnClickListener {
                alertDialog.dismiss()
            }
            alertDialog.show()
        }

        fun DismissProgressBar() {
            if (alertDialog != null) {
                alertDialog.dismiss()
            }
        }
    }
}