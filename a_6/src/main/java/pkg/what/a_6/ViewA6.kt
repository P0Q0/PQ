package pkg.what.a_6

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import pkg.what.pq.R
import pkg.what.pq.databinding.LayoutA6Binding

class ViewA6 : AppCompatActivity() {
    private lateinit var bind: LayoutA6Binding

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        this.bind = LayoutA6Binding.inflate(layoutInflater).also { setContentView(it.root) }
        snack(getString(R.string.purpose_a6))

        uiNotifier(UiNotifierStates.Unknown("unknown"))
    }

    // TODO: design your notification to open your app and specific activities
    private fun invokeApp(){}
    private fun specifyAct(){}

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
    }

    private fun uiStatusBarNotifier(){}

    private fun uiHeadsUpNotifier(){}

    private fun uiLockScreenNotifier(){}

    private fun uiBadgeNotifier(){}

    private fun uiNotifier(event: UiNotifierStates) = when(event) {
        is UiNotifierStates.Unknown -> {
            event.note = "unknown"
            NoteMsg.UNKNOWN.msg(event.note)
            event.state = event.note
        }
        is UiNotifierStates.Emitter -> {
            event.note = "emitting"
            NoteMsg.LONG.msg(event.note)
            event.state = event.note
        }
        is UiNotifierStates.Omitter -> {
            event.note = "omitting"
            NoteMsg.SHORT.msg(event.note)
            event.state = event.note
        }
    }
}

typealias NoteMsg = UiNotifierStates.UiNotifierMsg

/** @desc immutable type set of notification states as classes */
/** @note semantics help clarify the intentions of the constructor */
@Suppress("ConvertSecondaryConstructorToPrimary")
sealed class UiNotifierStates : UiNotifier {

    var state: String? = null

    private constructor(){ this.state = "DEFAULT STEADY STATE WITH UNKNOWN ZZZ" }

    data class Unknown(var note: String) : UiNotifierStates()

    data class Omitter(var note: String) : UiNotifierStates()

    data class Emitter(var note: String) : UiNotifierStates()

    /** @desc immutable type set of notification messages as values */
    enum class UiNotifierMsg {
        UNKNOWN { override fun msg(s: String) { UiNotifierFlow.NfFl.emit("UNKNOWN: $s") } },
        SHORT { override fun msg(s: String) { UiNotifierFlow.NfFl.emit("SHORT: $s") } },
        LONG { override fun msg(s: String) { UiNotifierFlow.NfFl.emit("LONG: $s") } };
        abstract fun msg(s : String)
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