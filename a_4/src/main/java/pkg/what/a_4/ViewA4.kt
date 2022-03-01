package pkg.what.a_4

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import pkg.what.pq.R
import pkg.what.pq.databinding.LayoutA4Binding

class ViewA4 : AppCompatActivity() {
    private lateinit var bind: LayoutA4Binding

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        this.bind = LayoutA4Binding.inflate(layoutInflater).also { setContentView(it.root) }
        Snackbar.make(
            findViewById(R.id.layout_a4)
            ,getString(R.string.purpose_a4)
            ,Snackbar.LENGTH_SHORT)
            .setTextColor(getColor(R.color.colorLight))
            .setAction(getString(R.string.sb_dismiss))
            { Log.d(LOG_DEBUG_TAG,"$localClassName , ${resources.getString(R.string.sb_on_click)}") }
            .show()
        setListeners()
    }

    private fun setListeners(){
        //TODO: setListeners ViewA4
    }

    /** @desc file specific definitions, states, logging, strings */
    companion object{
        const val LOG_DEBUG_TAG = "VIEW_A4_DEBUG_TAG"
    }
}