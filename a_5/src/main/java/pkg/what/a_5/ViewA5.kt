package pkg.what.a_5

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import pkg.what.pq.R
import pkg.what.pq.databinding.LayoutA5Binding

class ViewA5 : AppCompatActivity() {
    private lateinit var bind: LayoutA5Binding

    private val source by lazy { DataSource("p","r") }

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        this.bind = LayoutA5Binding.inflate(layoutInflater).also { setContentView(it.root) }
        Snackbar.make(
            findViewById(R.id.layout_a5)
            ,getString(R.string.purpose_a5)
            ,Snackbar.LENGTH_SHORT)
            .setTextColor(getColor(R.color.colorLight))
            .setAction(getString(R.string.sb_dismiss))
            { Log.d(LOG_DEBUG_TAG,"$localClassName , ${resources.getString(R.string.sb_on_click)}") }
            .show()
        setupData()
        setupUi()
    }

    private fun setupData(){
        source.q = "q"
        if(source.q != null) source.d.add(source.q!!)
    }

    private fun setupUi(){
        bind.a5Rv.adapter = CardAdapter(source)
    }

    /** @desc file specific definitions, states, logging, strings */
    companion object{
        const val LOG_DEBUG_TAG = "VIEW_A5_DEBUG_TAG"
        const val BUTTON_SNACK_MSG = "CardAdapter.ViewHolder"
    }
}