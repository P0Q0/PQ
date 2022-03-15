package pkg.what.a_0.ui.notification

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import pkg.what.a_0.ui.messages.Snacks
import pkg.what.pq.R
import pkg.what.pq.databinding.LayoutViewSpecialAlertDetailsBinding

class NotyKillerAlert : AppCompatActivity() {
    private lateinit var bind: LayoutViewSpecialAlertDetailsBinding

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        this.bind = LayoutViewSpecialAlertDetailsBinding.inflate(layoutInflater).also { setContentView(it.root) }
        Snacks(bind.root.rootView, getString(R.string.ui_view_special_alert_details), Snackbar.LENGTH_LONG,this.javaClass)
    }
}