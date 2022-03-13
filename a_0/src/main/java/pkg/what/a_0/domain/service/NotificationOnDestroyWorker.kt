package pkg.what.a_0.domain.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.work.Worker
import androidx.work.WorkerParameters
import pkg.what.a_0.ui.notification.*

/** @desc worker that is invoked when onDestroy launches notifications
 * , stash the current fragment in the backstack
 * , essentially we'll launch the activity again
 * , filter backstack, if fragment doesn't exist, create it again
 * , otherwise continue
 * , then navigate to the fragment
 * , after restore the fragment state
 */
class NotificationOnDestroyWorker(
    private val ctx: Context, params: WorkerParameters) : Worker(ctx,params) , NotifyIf {

    private var specialDestroyingIntent: Intent? = null
    private var specialDestroyingPendingIntent: PendingIntent? = null

    private var regularDestroyingIntent: Intent? = null
    private var regularDestroyingPendingIntent: PendingIntent? = null

    override fun doWork(): Result {
        this.prepIntents()
        uiNotifier(
            UiNotifierStates.Unknown(UiNotifierStates.TAG_UNKNOWN)
        )
        val builder = NotifyIf.Helper.noty.generateContent(
            ctx.getString(pkg.what.pq.R.string.ui_content_title)
            , ctx.getString(pkg.what.pq.R.string.ui_content_text)
            , NotificationCompat.PRIORITY_DEFAULT
            , ctx
        )
        NotifyIf.Helper.noty.generateChannel(
            UiNotifications.CHANNEL_ID_UI
            , ctx.getString(pkg.what.pq.R.string.ui_channel_name)
            , ctx.getString(pkg.what.pq.R.string.ui_describe_channel_name)
            , NotificationManager.IMPORTANCE_DEFAULT
            , ctx
        )
        NotifyIf.Helper.noty.generateAction(
            builder
            , regularDestroyingPendingIntent!!
            , specialDestroyingPendingIntent!!
            , ctx
        )
        NotifyIf.Helper.noty.showNotification(
            builder
            , ctx
        )
        return Result.success()
    }

    private fun prepIntents(){
        specialDestroyingIntent = Intent(ctx, NotySpecialLocalAlert::class.java)
            .apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                action = NotySpecialLocalAlert.ACTION_CLOSE
            }
        specialDestroyingPendingIntent = PendingIntent.getService(
            ctx, UiNotifications.PENDING_SPECIAL_REQUEST_CODE, specialDestroyingIntent!!,
            (PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        )

        regularDestroyingIntent = Intent(ctx, NotyRestoringAlert::class.java)
        val stackBuilder: TaskStackBuilder = TaskStackBuilder.create(ctx)
        stackBuilder.addParentStack(NotyRestoringAlert::class.java)
        stackBuilder.addNextIntent(regularDestroyingIntent!!)
        regularDestroyingPendingIntent =
            PendingIntent.getActivity(ctx,
                UiNotifications.PENDING_REGULAR_REQUEST_CODE, regularDestroyingIntent,
                (PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE))
    }

    override fun uiNotifier(event: UiNotifierStates) = when(event) {
        is UiNotifierStates.Unknown -> {
            event.note = UiNotifierStates.TAG_UNKNOWN
            Note.UNKNOWN.msg(event.note)
            event.state = event.note
        }
        is UiNotifierStates.Emitter -> {
            event.note = UiNotifierStates.TAG_EMITTER
            Note.LONG.msg(event.note)
            event.state = event.note
        }
        is UiNotifierStates.Omitter -> {
            event.note = UiNotifierStates.TAG_OMITTER
            Note.SHORT.msg(event.note)
            event.state = event.note
        }
    }
}