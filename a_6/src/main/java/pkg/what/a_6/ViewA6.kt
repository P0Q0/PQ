package pkg.what.a_6

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import pkg.what.pq.R
import pkg.what.pq.databinding.LayoutA6Binding

class ViewA6 : AppCompatActivity() {
    private lateinit var bind: LayoutA6Binding

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        this.bind = LayoutA6Binding.inflate(layoutInflater).also { setContentView(it.root) }
        Snackbar.make(
            findViewById(R.id.layout_a6)
            ,getString(R.string.purpose_a6)
            ,Snackbar.LENGTH_SHORT)
            .setTextColor(getColor(R.color.colorLight))
            .setAction(getString(R.string.sb_dismiss))
            { Log.d(LOG_DEBUG_TAG,"$localClassName , ${resources.getString(R.string.sb_on_click)}") }
            .show()
        setListeners()
    }

    private fun setListeners(){
        //TODO: setListeners ViewA6
    }

    /** @desc file specific definitions, states, logging, strings */
    companion object{
        const val LOG_DEBUG_TAG = "VIEW_A6_DEBUG_TAG"
    }
}