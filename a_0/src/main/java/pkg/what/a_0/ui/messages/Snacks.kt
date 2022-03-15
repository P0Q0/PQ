package pkg.what.a_0.ui.messages

import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar
import pkg.what.pq.R

class Snacks(ctx: View, msg: String, length: Int, caller: Class<*>) {
    init {
        Snackbar.make(ctx, msg, length)
            .setTextColor(ctx.resources.getColor(R.color.colorDark,null))
            .setAction(ctx.resources.getString(R.string.sb_dismiss)) {
                Log.d(
                    "$caller . LOG_INFO_TAG",
                    "${caller.name} , " + " ${ctx.resources.getString(R.string.sb_on_click)}"
                )
            }
            .show()
    }
}