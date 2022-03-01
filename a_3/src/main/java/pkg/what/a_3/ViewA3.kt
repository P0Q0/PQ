package pkg.what.a_3

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import pkg.what.pq.R
import pkg.what.pq.databinding.LayoutA3Binding

class ViewA3 : AppCompatActivity() {
    private lateinit var bind: LayoutA3Binding

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        this.bind = LayoutA3Binding.inflate(layoutInflater).also { setContentView(it.root) }
        Snackbar.make(
            findViewById(R.id.layout_a3)
            ,getString(R.string.purpose_a3)
            ,Snackbar.LENGTH_SHORT)
            .setTextColor(getColor(R.color.colorLight))
            .setAction(getString(R.string.sb_dismiss))
            { Log.d(LOG_DEBUG_TAG,"$localClassName , Snackbar, lambda onClick(...).") }
            .show()
        setListeners()
    }

    private fun setListeners(){
        //TODO: setListeners ViewA3
    }

    /** @desc file specific definitions, states, logging, strings */
    companion object{
        const val LOG_DEBUG_TAG = "VIEW_A3_DEBUG_TAG"
    }
}