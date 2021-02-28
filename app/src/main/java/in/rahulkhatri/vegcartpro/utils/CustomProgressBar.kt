package `in`.rahulkhatri.vegcartpro.utils

import `in`.rahulkhatri.vegcartpro.R
import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.progressbar_layout.view.*

class CustomProgressBar {

    companion object {
        lateinit var alertDialog: AlertDialog
        lateinit var builder: AlertDialog.Builder
        fun progressBar(context: Context, progressText: String) {
            val layoutBuilder = LayoutInflater.from(context).inflate(R.layout.progressbar_layout, null)
            builder = AlertDialog.Builder(context)
            builder.setCancelable(false)
            builder.setView(layoutBuilder)
            layoutBuilder.tv_progress_bar.text = progressText
            alertDialog = builder.create()
            alertDialog.show()
        }

        fun dismissProgressBar() {
            if (alertDialog != null && alertDialog.isShowing) {
                alertDialog.dismiss()
            }
        }
        fun showProgressbar(){
            if (alertDialog != null && !alertDialog.isShowing) {
                alertDialog.show()
            }
        }
    }
}