package pkg.what.a_0.domain.service

import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.util.Log
import androidx.core.content.getSystemService
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import pkg.what.a_0.domain.core.OsSafeGuard
import pkg.what.a_0.domain.service.NotySpecialLocalAlert.Companion.I_AM_KILL_APP
import pkg.what.a_0.domain.service.NotySpecialLocalAlert.Companion.I_AM_KILL_APP_KEY
import pkg.what.a_0.domain.service.NotySpecialLocalAlert.Companion.I_WILL_KILL_APP
import pkg.what.a_0.domain.service.NotySpecialLocalAlert.Companion.I_WILL_KILL_APP_KEY
import pkg.what.a_0.ui.notification.ViewNotyDead

class NotySpecialRemoteAlert : Service() {

    companion object {
        @JvmField var isNotySpecialRemoteAlertRunning: Boolean = true
    }

    override fun onCreate() {
        Log.d("$javaClass","onBind")
        isNotySpecialRemoteAlertRunning = true
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("$javaClass","onStartCommand")
        val localBroadcastManager = LocalBroadcastManager.getInstance(this)

        val broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                localBroadcastManager.unregisterReceiver(this)
                Log.i("$javaClass", "onReceive : ${intent?.getStringExtra(I_WILL_KILL_APP_KEY)}")

                val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.deleteNotificationChannel(getString(pkg.what.pq.R.string.ui_channel_name))
                notificationManager.cancelAll()

                context?.let { us -> ViewNotyDead.exit(us) }


                /** @STRATEGY
                 * TODO: NotySpecialRemoteAlert,
                 *  PEEK AT PS
                 *  WATCH THE PS
                 *  IDENTIFY ITS BEHAVIOR
                 *  HANDLE STUFF HERE
                 *  BACKTRACK ROOT CAUSE
                 *  FIX
                 *   NOTE AT THE MOMENT THERE IS A BUG
                 *   "RECENT HISTORY" BUTTON, THE SQUARE,
                 *   IT IS NOT CLEARING THE APP,
                 *   RATHER IT IS LAUNCHING IT,
                 *   THIS IS STILL IN PROGRESS
                 */
                OsSafeGuard().forcefulKill()


                context?.let { us -> us.stopService(Intent(us, NotySpecialRemoteAlert::class.java)) }
                Log.d("$javaClass", "stopService")
            }
        }
        localBroadcastManager.registerReceiver(broadcastReceiver , IntentFilter(I_WILL_KILL_APP))

        localBroadcastManager.sendBroadcast(
            Intent(I_AM_KILL_APP).apply { putExtra(I_AM_KILL_APP_KEY,I_AM_KILL_APP) })
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        Log.d("$javaClass","onDestroy")
        isNotySpecialRemoteAlertRunning = false
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d("$javaClass","onBind")
        return null
    }
}