package pkg.what.a_0.domain.service

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class SpecialAlertService : Service() {

//    private val remoteBroadcastRx: BroadcastReceiver = object : BroadcastReceiver(){
//        override fun onReceive(context: Context?, intent: Intent?) {
//            Log.d("SpecialAlertService","onReceive")
//            Log.d("SpecialAlertService", "msg: ${intent?.extras!!.getString("msg")}")
//            context!!.stopService(Intent(context, SpecialAlertService::class.java))
//            Log.d("SpecialAlertService","stopService")
//            context.unregisterReceiver(this)
//            Log.d("SpecialAlertService","unregisterRx")
//        }
//    }

    companion object {
        @JvmField var isSpecialAlertServiceRunning: Boolean = true
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d("SpecialAlertService","onBind")
        return null
    }

    override fun onCreate() {
        Log.d("SpecialAlertService","onBind")
        isSpecialAlertServiceRunning = true
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        Log.d("SpecialAlertService","registerRx")
//        this.registerReceiver(remoteBroadcastRx, IntentFilter("KILL:WHO:NOTAPP"))

        Log.d("SpecialAlertService","onStartCommand")
        val killer = Intent("KILL:WHO:APP").apply { putExtra("msg","killer") }
        val localBroadcastManager: LocalBroadcastManager = LocalBroadcastManager.getInstance(this)
        localBroadcastManager.sendBroadcast(killer)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        Log.d("SpecialAlertService","onDestroy")
        isSpecialAlertServiceRunning = false
        super.onDestroy()
    }
}