package pkg.what.a_6

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.material.snackbar.Snackbar
import pkg.what.a_6.UiNotifierStates.Companion.TAG_EMITTER
import pkg.what.a_6.UiNotifierStates.Companion.TAG_OMITTER
import pkg.what.a_6.UiNotifierStates.Companion.TAG_UNKNOWN
import pkg.what.pq.R
import pkg.what.pq.databinding.LayoutA6Binding

class ViewA6 : AppCompatActivity() {
    private lateinit var bind: LayoutA6Binding

    private lateinit var tempExplicitIntent: Intent

    private lateinit var tempPendingIntent: PendingIntent

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        this.bind = LayoutA6Binding.inflate(layoutInflater).also { setContentView(it.root) }
        snack(getString(R.string.purpose_a6))
        fireUiNotifier()
    }

    private fun fireUiNotifier(){
        this.tempExplicitIntent = Intent(this, ViewRegularAlertDetailsActivity::class.java)
            .apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK }
        this.tempPendingIntent = PendingIntent.getActivity(
            this, PENDING_REQUEST_CODE, intent, PendingIntent.FLAG_IMMUTABLE)

        uiNotifier(
            UiNotifierStates.Unknown(TAG_UNKNOWN))
        val builder = generateContent(
            getString(R.string.ui_content_title)
            , getString(R.string.ui_content_text)
            , NotificationCompat.PRIORITY_DEFAULT)
        generateChannel(
            CHANNEL_ID_UI
            , getString(R.string.ui_channel_name)
            , getString(R.string.ui_describe_channel_name)
            , NotificationManager.IMPORTANCE_DEFAULT)
        generateAction(
            builder
        )
        showNotification(
            builder
        )
    }

    @Suppress("SameParameterValue") //TODO: can remove after implementing other fx that call this
    private fun generateContent(title: String, text: String, worth: Int): NotificationCompat.Builder {
        return NotificationCompat.Builder(this, CHANNEL_ID_UI)
            .setSmallIcon(androidx.core.R.drawable.notification_icon_background)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(worth)
    }

    @Suppress("SameParameterValue") //TODO: can remove after implementing other fx that call this
    private fun generateChannel(id: String, name: String, details: String, worth: Int){
        if(supportsChannelApi()){
            val channel = NotificationChannel(id, name, worth).apply { description = details }
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        } else {
            throw RuntimeException("ERROR: build version is currently ${Build.VERSION.SDK_INT}" +
                    "which is not >= ${Build.VERSION_CODES.O} and thus we can't use Channel Api.")
        }
    }

    private fun generateAction(builder: NotificationCompat.Builder) {
        builder
            .setContentIntent(tempPendingIntent)
            .setAutoCancel(true)
    }

    private fun showNotification(builder: NotificationCompat.Builder) {
        with(NotificationManagerCompat.from(this)) {
            notify(NOTIFICATION_ID_UI, builder.build())
        }
    }

    // TODO: design your notification to open your app and specific activities
    private fun invokeApp(){}

    // TODO: design your notification to open your app and specific activities
    private fun specifyAct(){}

    private fun uiStatusBarNotifier(){ TODO(reason = "uiStatusBarNotifier under development") }

    private fun uiHeadsUpNotifier(){ TODO(reason = "uiHeadsUpNotifier under development") }

    private fun uiLockScreenNotifier(){ TODO(reason = "uiLockScreenNotifier under development") }

    private fun uiBadgeNotifier(){ TODO(reason = "uiBadgeNotifier under development") }

    private fun uiNotifier(event: UiNotifierStates) = when(event) {
        is UiNotifierStates.Unknown -> {
            event.note = TAG_UNKNOWN
            Note.UNKNOWN.msg(event.note)
            event.state = event.note
        }
        is UiNotifierStates.Emitter -> {
            event.note = TAG_EMITTER
            Note.LONG.msg(event.note)
            event.state = event.note
        }
        is UiNotifierStates.Omitter -> {
            event.note = TAG_OMITTER
            Note.SHORT.msg(event.note)
            event.state = event.note
        }
    }

    /** @note version check is good practice, regardless what the current configuration is
     * , until there's an alternative when using modern api's, this will remain */
    @SuppressLint("ObsoleteSdkInt")
    private fun supportsChannelApi(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
    }

    /** @desc file specific for short snackbar */
    private fun snack(msg: String) =
        Snackbar.make(
            findViewById(R.id.layout_a6)
            ,msg
            ,Snackbar.LENGTH_SHORT)
            .setTextColor(getColor(R.color.colorLight))
            .setAction(getString(R.string.sb_dismiss))
            { Log.d(LOG_DEBUG_TAG,"$localClassName , ${resources.getString(R.string.sb_on_click)}") }
            .show()

    /** @desc file specific definitions, states, logging, strings */
    companion object{
        const val LOG_DEBUG_TAG = "VIEW_A6_DEBUG_TAG"
        const val CHANNEL_ID_UI = "0x00"
        const val CHANNEL_ID_STATUS_BAR = "0x01"
        const val CHANNEL_ID_HEADS_UP = "0x02"
        const val CHANNEL_ID_BADGE = "0x03"
        const val NOTIFICATION_ID_UI = 0x00
        const val NOTIFICATION_ID_STATUS_BAR = 0x01
        const val NOTIFICATION_ID_HEADS_UP = 0x02
        const val NOTIFICATION_ID_BADGE = 0x03
        const val PENDING_REQUEST_CODE = 0xFF
    }
}

/** @desc shorten alias with a meaningful representation */
typealias Note = UiNotifierStates.UiNotifierMsg

/** @desc immutable type set of notification states as classes */
/** @note semantics help clarify the intentions of the constructor */
@Suppress("ConvertSecondaryConstructorToPrimary")
sealed class UiNotifierStates : UiNotifier {

    var state: String? = null

    private constructor(){ this.state = TAG_DEFAULT }

    data class Unknown(var note: String) : UiNotifierStates()

    data class Omitter(var note: String) : UiNotifierStates()

    data class Emitter(var note: String) : UiNotifierStates()

    /** @desc immutable type set of notification messages as values */
    enum class UiNotifierMsg {
        UNKNOWN { override fun msg(s: String) { UiNotifierFlow.NfFl.emit("$TAG_UNKNOWN: $s") } },
        SHORT { override fun msg(s: String) { UiNotifierFlow.NfFl.emit("$TAG_SHORT: $s") } },
        LONG { override fun msg(s: String) { UiNotifierFlow.NfFl.emit("$TAG_LONG: $s") } };
        abstract fun msg(s : String)
    }

    companion object {
        const val TAG_DEFAULT = "zzz default zzz"
        const val TAG_UNKNOWN = "unknown"
        const val TAG_OMITTER = "omitter"
        const val TAG_EMITTER = "emitter"
        const val TAG_SHORT = "short"
        const val TAG_LONG = "long"
    }
}

/** @desc definition type for ui notifications wishing for precise message data flow */
sealed interface UiNotifier: UiNotifierFlow

/** @desc contractual requirement to grant access to message flow control */
sealed interface UiNotifierFlow {
    object NfFl: NotifierFlow()
}

/** @desc definition for the flow, it omits and it emits */
sealed class NotifierFlow : NotificationFlowControl {
    override fun omit(status: String){ status.plus("inject omit") }
    override fun emit(status: String){ status.plus("inject emit") }
}

/** @desc definition for the flow control */
sealed interface NotificationFlowControl {
    fun omit(status: String)
    fun emit(status: String)
}