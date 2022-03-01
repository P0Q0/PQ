package pkg.what.a_5

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import pkg.what.pq.R
import pkg.what.pq.databinding.LayoutA5Binding

class ViewA5 : AppCompatActivity() {
    private lateinit var bind: LayoutA5Binding

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        this.bind = LayoutA5Binding.inflate(layoutInflater).also { setContentView(it.root) }
        Snackbar.make(
            findViewById(R.id.layout_a5)
            ,getString(R.string.purpose_a5)
            ,Snackbar.LENGTH_SHORT)
            .setTextColor(getColor(R.color.colorLight))
            .setAction(getString(R.string.sb_dismiss))
            { Log.d(LOG_DEBUG_TAG,"$localClassName , Snackbar, lambda onClick(...).") }
            .show()
        setListeners()
    }

    private fun setListeners(){
        //TODO: setListeners ViewA5
    }

    /** @desc file specific definitions, states, logging, strings */
    companion object{
        const val LOG_DEBUG_TAG = "VIEW_A5_DEBUG_TAG"
    }
}