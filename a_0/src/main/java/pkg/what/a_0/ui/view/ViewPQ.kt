package pkg.what.a_0.ui.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import pkg.what.a_0.domain.controller.ViewModelPQ
import pkg.what.pq.R
import pkg.what.pq.databinding.LayoutA0Binding

class ViewPQ : AppCompatActivity() {
    private lateinit var bind: LayoutA0Binding

    /** @STRATEGY_FOR_INVOKING_WORKER
     * 1) fragment is in the background explicitly, activity is in the background implicitly
     * 2) fragment is in the foreground, activity is in the foreground implicitly
     * 3) fragment is in process death, activity is in process life
     * 4) activity is in destroyed */
    private lateinit var vmPQ: ViewModelPQ

    private fun di() {
        vmPQ = ViewModelPQ(this.applicationContext)
    }

    override fun onCreate(state: Bundle?) {
        Log.d(LOG_DEBUG_TAG,"$javaClass , onCreate")
        super.onCreate(state)
        di()
        this.bind = LayoutA0Binding.inflate(layoutInflater).also { setContentView(it.root) }
        snack(
            R.string.purpose_a0
            ,"$localClassName , ${resources.getString(R.string.sb_on_click)}")
    }

    override fun onStart() {
        super.onStart()
        Log.i(LOG_INFO_TAG,"$javaClass , onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i(LOG_INFO_TAG,"$javaClass , onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i(LOG_INFO_TAG,"$javaClass , onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i(LOG_INFO_TAG,"$javaClass , onStop")
        vmPQ.applyNotification()
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(LOG_INFO_TAG,"$javaClass , onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(LOG_INFO_TAG,"$javaClass , onDestroy")
        vmPQ.cancelNotification()
        vmPQ.applyNotification()
    }

    override fun onSaveInstanceState(state: Bundle) {
        super.onSaveInstanceState(state)
        Log.i(LOG_INFO_TAG,"$javaClass , onSaveInstanceState")
    }

    override fun onRestoreInstanceState(state: Bundle) {
        super.onRestoreInstanceState(state)
        Log.i(LOG_INFO_TAG,"$javaClass , onRestoreInstanceState")
    }

    /** @desc file specific for short snackbar */
    private fun snack(ui_msg: Int, log_msg: String) =
        Snackbar.make(
            findViewById(R.id.layout_a0)
            ,getString(ui_msg)
            , Snackbar.LENGTH_SHORT)
            .setTextColor(getColor(R.color.colorDark))
            .setAction(getString(R.string.sb_dismiss))
            { Log.d(LOG_INFO_TAG,log_msg) }
            .show()

    /** @desc file specific definitions, states, logging, strings */
    companion object{
        const val LOG_DEBUG_TAG = "VIEW_PQ_A0_DEBUG_TAG"
        const val LOG_INFO_TAG = "VIEW_PQ_A0_INFO_TAG"
    }
}