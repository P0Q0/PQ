package pkg.what.a_1

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import pkg.what.pq.R
import pkg.what.pq.databinding.LayoutA1Binding

class ViewA1 : AppCompatActivity() {
    private lateinit var bind: LayoutA1Binding

    private lateinit var stashedMemory: String

    private lateinit var stashedDisk: String

    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        Log.i(LOG_INFO_TAG,LOG_CREATE)
        this.sharedPref = this.getSharedPreferences(SHARED_PREFERENCES_DISK_KEY, Context.MODE_PRIVATE)
        this.stashedMemory = state?.getString(STATE_MEMORY_STASH).toString()
        this.stashedDisk = state?.getString(STATE_DISK_STASH).toString()
        this.bind = LayoutA1Binding.inflate(layoutInflater).also { setContentView(it.root) }
        snack(
            R.string.purpose_a1
            ,"$localClassName , ${resources.getString(R.string.sb_on_click)}")
        setListeners()
    }

    override fun onStart() {
        super.onStart()
        Log.i(LOG_INFO_TAG,LOG_START)
    }

    override fun onResume() {
        super.onResume()
        Log.i(LOG_INFO_TAG,LOG_RESUME)
    }

    override fun onPause() {
        super.onPause()
        Log.i(LOG_INFO_TAG,LOG_PAUSE)
    }

    override fun onStop() {
        super.onStop()
        Log.i(LOG_INFO_TAG,LOG_STOP)
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(LOG_INFO_TAG,LOG_RESTART)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(LOG_INFO_TAG,LOG_DESTROY)
    }

    override fun onSaveInstanceState(state: Bundle) {
        state.run { putString(STATE_MEMORY_STASH,stashedMemory) }
        with(sharedPref.edit()){ putString(STATE_DISK_STASH,stashedDisk) }.apply()
        super.onSaveInstanceState(state)
        Log.i(LOG_INFO_TAG,LOG_SAVE)
    }

    override fun onRestoreInstanceState(state: Bundle) {
        super.onRestoreInstanceState(state)
        state.run {
            stashedMemory = getString(STATE_MEMORY_STASH).toString()
            bind.a1Tiet1.setText(stashedMemory)
        }
        stashedDisk = sharedPref.getString(STATE_DISK_STASH,SHARED_PREFERENCES_NULL).toString()
        bind.a1Tiet2.setText(stashedDisk)
        Log.i(LOG_INFO_TAG,LOG_RESTORE)
    }

    private fun setListeners(){
        val tL1 = this.bind.a1Til1
        val tE1 = this.bind.a1Tiet1
        tL1.setOnClickListener {
            if(tE1.text?.isEmpty() == false){
                tE1.setText(R.string.is_empty)
                stashedMemory = getString(R.string.is_empty)
            }
        }
        tE1.setOnClickListener{
            if(tE1.text?.isEmpty() == false){
                tE1.setText(R.string.is_empty)
                stashedMemory = getString(R.string.is_empty)
            }
        }
        tE1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(sq: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(sq: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(str: Editable?) {
                stashedMemory = str.toString()
            }
        })

        val tL2 = this.bind.a1Til2
        val tE2 = this.bind.a1Tiet2
        tL2.setOnClickListener {
            if(tE2.text?.isEmpty() == false){
                tE2.setText(R.string.is_empty)
                stashedDisk = getString(R.string.is_empty)
            }
        }
        tE2.setOnClickListener{
            if(tE2.text?.isEmpty() == false){
                tE2.setText(R.string.is_empty)
                stashedDisk = getString(R.string.is_empty)
            }
        }
        tE2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(sq: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(sq: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(str: Editable?) {
                stashedDisk = str.toString()
            }
        })
    }

    /** @desc file specific for short snackbar */
    private fun snack(ui_msg: Int, log_msg: String) =
        Snackbar.make(
            findViewById(R.id.layout_a1)
            ,getString(ui_msg)
            , Snackbar.LENGTH_SHORT)
            .setTextColor(getColor(R.color.colorDark))
            .setAction(getString(R.string.sb_dismiss))
            { Log.d(LOG_INFO_TAG,log_msg) }
            .show()

    /** @desc file specific definitions, states, logging, strings */
    companion object {
        const val SHARED_PREFERENCES_DISK_KEY = "pkg.what.PREFERENCE_FILE_KEY"
        const val SHARED_PREFERENCES_NULL     = "NULL_SHARED_PREFERENCES"
        const val STATE_MEMORY_STASH  =   "STATE_MEMORY_STASH"
        const val STATE_DISK_STASH    =   "STATE_DISK_STASH"
        const val LOG_INFO_TAG  =   "VIEW_A1_INFO_TAG"
        const val LOG_DEBUG_TAG =   "VIEW_A1_DEBUG_TAG"
        const val LOG_CREATE    =   "ON_CREATE"
        const val LOG_START     =   "ON_START"
        const val LOG_RESUME    =   "ON_RESUME"
        const val LOG_PAUSE     =   "ON_PAUSE"
        const val LOG_STOP      =   "ON_STOP"
        const val LOG_RESTART   =   "ON_RESTART"
        const val LOG_DESTROY   =   "ON_DESTROY"
        const val LOG_SAVE      =   "ON_SAVE"
        const val LOG_RESTORE   =   "ON_RESTORE"
    }
}