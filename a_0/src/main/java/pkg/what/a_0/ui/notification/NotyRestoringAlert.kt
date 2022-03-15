package pkg.what.a_0.ui.notification

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import pkg.what.a_0.ui.messages.Snacks
import pkg.what.a_0.ui.view.ViewPQ
import pkg.what.pq.R
import pkg.what.pq.databinding.LayoutViewRegularAlertDetailsBinding

class NotyRestoringAlert : AppCompatActivity() {
    private lateinit var bind: LayoutViewRegularAlertDetailsBinding

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        this.bind = LayoutViewRegularAlertDetailsBinding.inflate(layoutInflater).also { setContentView(it.root) }
        Snacks(bind.root.rootView, getString(R.string.ui_view_regular_alert_details), Snackbar.LENGTH_LONG,this.javaClass)

    }
}