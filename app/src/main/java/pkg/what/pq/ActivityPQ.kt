package pkg.what.pq

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import pkg.what.pq.databinding.LayoutPqBinding

class ActivityPQ : AppCompatActivity() {

    private lateinit var bind: LayoutPqBinding

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        this.bind = LayoutPqBinding.inflate(layoutInflater).also { setContentView(it.root) }
        snack(
            R.string.feature_title_PQ
            ,"$localClassName , ${resources.getString(R.string.sb_on_click)}")
        setListeners()
    }

    private fun setListeners(){
        this.bind.pqA0.setOnClickListener {
            Intent().setClassName(packageName, getString(R.string.class_a0)).also {
                /** @note, ensure backstack is cleared before proceeding
                 * , either clear the top or run the logic, reset task or clear task
                 * , single activity compliance, from here on out, only fragments, and this activity **/
                it.flags =
                    (Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK) xor
                            Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(it)
                this.finishAffinity()
                this.finish()
            }
        }
        this.bind.pqA1.setOnClickListener {
            Intent().setClassName(packageName, getString(R.string.class_a1)).also { startActivity(it) }
        }
        this.bind.pqA2.setOnClickListener {
            Intent().setClassName(packageName, getString(R.string.class_a2)).also { startActivity(it) }
        }
        this.bind.pqA3.setOnClickListener {
            Intent().setClassName(packageName, getString(R.string.class_a3)).also { startActivity(it) }
        }
        this.bind.pqA4.setOnClickListener {
            Intent().setClassName(packageName, getString(R.string.class_a4)).also { startActivity(it) }
        }
        this.bind.pqA5.setOnClickListener {
            Intent().setClassName(packageName, getString(R.string.class_a5)).also { startActivity(it) }
        }
        this.bind.pqA6.setOnClickListener {
            Intent().setClassName(packageName, getString(R.string.class_a6)).also { startActivity(it) }
        }
        this.bind.pqA7.setOnClickListener {
            Intent().setClassName(packageName, getString(R.string.class_a7)).also { startActivity(it) }
        }
        this.bind.pqA8.setOnClickListener {
            Intent().setClassName(packageName, getString(R.string.class_a8)).also { startActivity(it) }
        }
        this.bind.pqA9.setOnClickListener {
            Intent().setClassName(packageName, getString(R.string.class_a9)).also { startActivity(it) }
        }
    }

    /** @desc file specific for short snackbar */
    private fun snack(ui_msg: Int, log_msg: String) =
        Snackbar.make(
            findViewById(R.id.layout_pq)
            ,getString(ui_msg)
            , Snackbar.LENGTH_SHORT)
            .setTextColor(getColor(R.color.colorLight))
            .setAction(getString(R.string.sb_dismiss))
            { Log.d(LOG_INFO_TAG,log_msg) }
            .show()

    /** @desc file specific definitions, states, logging, strings */
    companion object {
        const val LOG_DEBUG_TAG = "VIEW_APP_DEBUG_TAG"
        const val LOG_INFO_TAG = "VIEW_APP_INFO_TAG"
    }
}