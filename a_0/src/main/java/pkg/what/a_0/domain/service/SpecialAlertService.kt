package pkg.what.a_0.domain.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager


class SpecialAlertService : Service() {

    companion object {
        @JvmField var isSpecialAlertServiceRunning: Boolean = true
    }

    private var localBroadcastManager: LocalBroadcastManager = LocalBroadcastManager.getInstance(this)

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
        Log.d("SpecialAlertService","onStartCommand")
        val killer = Intent("KILL:WHO:APP").apply { putExtra("msg","killer") }
        localBroadcastManager.sendBroadcast(killer)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        Log.d("SpecialAlertService","onDestroy")
        isSpecialAlertServiceRunning = false
        super.onDestroy()
    }
}