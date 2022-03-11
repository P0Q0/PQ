package pkg.what.a_0.ui.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class ViewSpecialAlert : AppCompatActivity() {

    private val remoteBroadcastRx: BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d("ViewSpecialAlert","onReceive")
            if(intent != null){
                Log.d("ViewSpecialAlert", "msg: ${intent.extras!!.getString("msg")}")
                finishAffinity()
                finish()
            }
        }
    }

    override fun onCreate(state: Bundle?) {
        Log.d("ViewSpecialAlert","onCreate")
        super.onCreate(state)
        registerReceiver(remoteBroadcastRx, IntentFilter("KILL:WHO:APP"))
    }

    override fun onDestroy() {
        Log.d("ViewSpecialAlert","onDestroy")
        super.onDestroy()
        unregisterReceiver(remoteBroadcastRx)
    }
}