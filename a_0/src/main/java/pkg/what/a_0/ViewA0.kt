package pkg.what.a_0

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import pkg.what.pq.R
import pkg.what.pq.databinding.LayoutA0Binding

class ViewA0 : AppCompatActivity() {
    private lateinit var bind: LayoutA0Binding

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        this.bind = LayoutA0Binding.inflate(layoutInflater).also { setContentView(it.root) }
        Snackbar.make(
            findViewById(R.id.layout_a0)
            ,getString(R.string.purpose_a0)
            ,Snackbar.LENGTH_SHORT)
            .setTextColor(getColor(R.color.colorLight))
            .setAction(getString(R.string.sb_dismiss))
            { Log.d(LOG_DEBUG_TAG,"$localClassName , Snackbar, lambda onClick(...).") }
            .show()
        setListeners()
    }

    private fun setListeners(){
        //TODO: setListeners ViewA0
    }

    /** @desc file specific definitions, states, logging, strings */
    companion object{
        const val LOG_DEBUG_TAG = "VIEW_A0_DEBUG_TAG"
    }
}