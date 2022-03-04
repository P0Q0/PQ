package pkg.what.a_6

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import pkg.what.pq.R
import pkg.what.pq.databinding.LayoutViewSpecialAlertDetailsBinding

class ViewSpecialAlertDetailsActivity : AppCompatActivity() {
    private lateinit var bind: LayoutViewSpecialAlertDetailsBinding

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        this.bind = LayoutViewSpecialAlertDetailsBinding.inflate(layoutInflater).also { setContentView(it.root) }
        snack(getString(R.string.ui_view_special_alert_details))
    }

    /** @desc file specific for short snackbar */
    private fun snack(msg: String) =
        Snackbar.make(
            findViewById(R.id.layout_view_special_alert_details)
            ,msg
            , Snackbar.LENGTH_SHORT)
            .setTextColor(getColor(R.color.colorLight))
            .setAction(getString(R.string.sb_dismiss))
            { Log.d(ViewA6.LOG_DEBUG_TAG,"$localClassName , ${resources.getString(R.string.sb_on_click)}") }
            .show()
}