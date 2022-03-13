package pkg.what.a_0.ui.notification

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import pkg.what.a_0.ui.view.ViewPQ
import pkg.what.pq.R
import pkg.what.pq.databinding.LayoutViewRegularAlertDetailsBinding

class NotyRestoringAlert : AppCompatActivity() {
    private lateinit var bind: LayoutViewRegularAlertDetailsBinding

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        this.bind = LayoutViewRegularAlertDetailsBinding.inflate(layoutInflater).also { setContentView(it.root) }
        snack(getString(R.string.ui_view_regular_alert_details))
    }

    /** @desc file specific for short snackbar */
    private fun snack(msg: String) =
        Snackbar.make(
            findViewById(R.id.layout_view_regular_alert_details)
            ,msg
            , Snackbar.LENGTH_SHORT)
            .setTextColor(getColor(R.color.colorDark))
            .setAction(getString(R.string.sb_dismiss))
            { Log.d(ViewPQ.LOG_DEBUG_TAG,"$localClassName , ${resources.getString(R.string.sb_on_click)}") }
            .show()
}