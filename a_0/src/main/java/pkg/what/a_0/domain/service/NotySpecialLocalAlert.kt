package pkg.what.a_0.domain.service

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class NotySpecialLocalAlert : Service(){

    companion object {
        const val I_WILL_KILL_APP = "I:WILL:KILL:APP"
        const val I_WILL_KILL_APP_KEY = "msgVSA"
        const val I_AM_KILL_APP = "I:AM:KILL:APP"
        const val I_AM_KILL_APP_KEY = "msgSVC"
        const val ACTION_CLOSE = "pkg.what.a_0.domain.service.ACTION_CLOSE"
    }

    override fun onCreate() {
        Log.d("$javaClass","onCreate")

        val localBroadcastManager = LocalBroadcastManager.getInstance(this)

        val broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                localBroadcastManager.unregisterReceiver(this)
                Log.d("$javaClass", "onReceive : ${intent?.extras!!.getString(I_AM_KILL_APP_KEY)}")

                localBroadcastManager.sendBroadcast(
                    Intent(I_WILL_KILL_APP).putExtra(I_WILL_KILL_APP_KEY, I_WILL_KILL_APP))

                context?.let{ us -> us.stopService(Intent(us, NotySpecialLocalAlert::class.java)) }
                Log.d("$javaClass", "stopService")
            }
        }
        localBroadcastManager.registerReceiver(broadcastReceiver, IntentFilter(I_AM_KILL_APP))

        this.startService(Intent(this, NotySpecialRemoteAlert::class.java))
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("$javaClass","onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        Log.d("$javaClass","onDestroy")
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d("$javaClass","onBind")
        return null
    }
}