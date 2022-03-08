package pkg.what.a_8

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import pkg.what.pq.R
import pkg.what.pq.databinding.LayoutA8Binding

class ViewA8 : AppCompatActivity() {
    private lateinit var bind: LayoutA8Binding

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        this.bind = LayoutA8Binding.inflate(layoutInflater).also { setContentView(it.root) }
        snack(
            R.string.purpose_a8
            ,"$localClassName , ${resources.getString(R.string.sb_on_click)}")
    }

    /** @desc file specific for short snackbar */
    private fun snack(ui_msg: Int, log_msg: String) =
        Snackbar.make(
            findViewById(R.id.layout_a8)
            ,getString(ui_msg)
            , Snackbar.LENGTH_SHORT)
            .setTextColor(getColor(R.color.colorDark))
            .setAction(getString(R.string.sb_dismiss))
            { Log.d(LOG_INFO_TAG,log_msg) }
            .show()

    /** @desc file specific definitions, states, logging, strings */
    companion object {
        const val LOG_DEBUG_TAG = "VIEW_A8_DEBUG_TAG"
        const val LOG_INFO_TAG = "VIEW_A8_INFO_TAG"
    }
}