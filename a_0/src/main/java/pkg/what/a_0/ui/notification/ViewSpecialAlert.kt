package pkg.what.a_0.ui.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import pkg.what.a_0.domain.service.SpecialAlertService

class ViewSpecialAlert(context: Context){

    private val remoteBroadcastRx: BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d("ViewSpecialAlert","onReceive")
            Log.d("ViewSpecialAlert", "msg: ${intent?.extras!!.getString("msg")}")
            context!!.stopService(Intent(context, SpecialAlertService::class.java))
            Log.d("ViewSpecialAlert","stopService")
            context.unregisterReceiver(this)
            Log.d("ViewSpecialAlert","unregisterRx")
        }
    }

    init {
        Log.d("ViewSpecialAlert","registerRx")
//        val killer = Intent("KILL:WHO:NOTAPP").apply { putExtra("msg","NOTkiller") }
//        val localBroadcastManager: LocalBroadcastManager = LocalBroadcastManager.getInstance(context)
//        localBroadcastManager.sendBroadcast(killer)
        context.registerReceiver(remoteBroadcastRx, IntentFilter("KILL:WHO:APP"))
        val service = Intent(context, SpecialAlertService::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        context.startService(service)
    }
}