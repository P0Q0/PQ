package pkg.what.a_0.ui.notification

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import org.jetbrains.annotations.NotNull

class ViewNotyDead : Activity() {

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        Log.d("$javaClass" , "onCreate")
        super.finishAndRemoveTask()
        super.finishAffinity()
        super.finish()
    }

    companion object {
        @JvmStatic fun exit(@NotNull ctx: Context) : Intent =
            Intent(ctx,ViewNotyDead::class.java)
                .apply {  flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or
                            Intent.FLAG_ACTIVITY_NO_ANIMATION or
                                Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS }
    }
}