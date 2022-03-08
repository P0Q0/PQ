package pkg.what.a_9

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import pkg.what.a_9.vitals.VitalTools
import pkg.what.a_9.vitals.vitalcore.FamilyANR
import pkg.what.a_9.vitals.vitalcore.FamilyCrash
import pkg.what.a_9.vitals.vitalother.FamilyRender
import pkg.what.pq.R
import pkg.what.pq.databinding.LayoutA9Binding

/**
 *  @WARNING use of this class should be done with care, it *will* cause BAD behavior.
 **/

class ViewA9 : AppCompatActivity() {
    private lateinit var bind: LayoutA9Binding

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)

        /** @note, enable vital tools to monitor/catch anr culprits **/
        VitalTools()

        this.bind = LayoutA9Binding.inflate(layoutInflater).also { setContentView(it.root) }
        snack(
            R.string.purpose_a9
            ,"$localClassName , ${resources.getString(R.string.sb_on_click)}")
        setListeners()
    }

    private fun setListeners(){
        bind.a9DeadlockBtn.setOnClickListener { FamilyANR(this) }
        bind.a9LinearlayoutBtn.setOnClickListener { FamilyRender() }
        bind.a9NpeBtn.setOnClickListener { FamilyCrash() }
    }

    /** @desc file specific for short snackbar */
    private fun snack(ui_msg: Int, log_msg: String) =
        Snackbar.make(
            findViewById(R.id.layout_a9)
            ,getString(ui_msg)
            , Snackbar.LENGTH_SHORT)
            .setTextColor(getColor(R.color.colorDark))
            .setAction(getString(R.string.sb_dismiss))
            { Log.d(LOG_INFO_TAG,log_msg) }
            .show()

    /** @desc file specific definitions, states, logging, strings */
    companion object {
        const val LOG_DEBUG_TAG = "VIEW_A9_DEBUG_TAG"
        const val LOG_INFO_TAG = "VIEW_A9_INFO_TAG"
    }
}