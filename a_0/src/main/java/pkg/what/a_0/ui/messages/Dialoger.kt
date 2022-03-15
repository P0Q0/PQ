package pkg.what.a_0.ui.messages

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import pkg.what.a_0.ui.view.ViewProfile

/** @note mindful that [caller] could potentially leak if used incorrectly */
//TODO:Dialoger, better handling of constructor parameter 'caller' as it could lead to leaks
internal class Dialoger(private val caller: ViewProfile) : DialogFragment() {

    @Suppress("MemberVisibilityCanBePrivate")
    internal lateinit var callback: DialogerListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try { callback = caller as DialogerListener }
        catch (e: ClassCastException) { throw ClassCastException(
            ("$context must implement DialogerListener, ${e.printStackTrace()}")) }
    }

    override fun onCreateDialog(state: Bundle?): Dialog {
        return activity?.let {
            AlertDialog.Builder(it).setMessage(pkg.what.pq.R.string.camerax_dialog_message)
                .setPositiveButton(pkg.what.pq.R.string.dialog_okay) { _, id ->
                    callback.onDialogPositiveClick(caller,this,id.toString())
                }
                .setNegativeButton(pkg.what.pq.R.string.dialog_cancel) { d, id ->
                    callback.onDialogNegativeClick(caller,this,id.toString())
                    d.dismiss()
                }
                .create()
        } ?: throw IllegalStateException("$javaClass, activity is null")
    }
}

interface DialogerListener {
    fun onDialogPositiveClick(caller: ViewProfile, dialog: DialogFragment, id: String)
    fun onDialogNegativeClick(caller: ViewProfile, dialog: DialogFragment, id: String)
}