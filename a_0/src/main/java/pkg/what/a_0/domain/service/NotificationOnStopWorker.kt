package pkg.what.a_0.domain.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import pkg.what.a_0.domain.service.NotySpecialLocalAlert.Companion.ACTION_CLOSE
import pkg.what.a_0.ui.notification.*
import pkg.what.a_0.ui.notification.NotifyIf.Helper.noty
import pkg.what.a_0.ui.notification.UiNotifications.Companion.CHANNEL_ID_UI
import pkg.what.a_0.ui.view.ViewPQ
import pkg.what.pq.ApplicationPQ

/** @desc worker that is invoked when onStop launches notifications
 * , essentially we know a view model is still alive
 * , therefore expose an observable that will bring the activity back to the front
 * , after restore the fragment state
 */
class NotificationOnStopWorker(
    private val ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx,params) , NotifyIf {

    private var specialCancellingIntent: Intent? = null
    private var specialCancellingPendingIntent: PendingIntent? = null

    private var regularRestoringIntent: Intent? = null
    private var regularRestoringPendingIntent: PendingIntent? = null

    override suspend fun doWork(): Result {
        this.prepIntents()
        uiNotifier(
            UiNotifierStates.Unknown(UiNotifierStates.TAG_UNKNOWN)
        )
        val builder = noty.generateContent(
            ctx.getString(pkg.what.pq.R.string.ui_content_title)
            , ctx.getString(pkg.what.pq.R.string.ui_content_text)
            , NotificationCompat.PRIORITY_DEFAULT
            , ctx
        )
        noty.generateChannel(
            CHANNEL_ID_UI
            , ctx.getString(pkg.what.pq.R.string.ui_channel_name)
            , ctx.getString(pkg.what.pq.R.string.ui_describe_channel_name)
            , NotificationManager.IMPORTANCE_DEFAULT
            , ctx
        )
        noty.generateAction(
            builder
            , regularRestoringPendingIntent!!
            , specialCancellingPendingIntent!!
            , ctx
        )
        noty.showNotification(
            builder
            , ctx
        )
        return Result.success()
    }

    private fun prepIntents(){
        specialCancellingIntent = Intent(ctx, NotySpecialLocalAlert::class.java)
            .apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                action = ACTION_CLOSE
            }
        specialCancellingPendingIntent = PendingIntent.getService(
            ctx, UiNotifications.PENDING_SPECIAL_REQUEST_CODE, specialCancellingIntent!!,
            (PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        )

        regularRestoringIntent = Intent(ctx, ViewPQ::class.java)
            .apply { flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT }
        val stackBuilder: TaskStackBuilder = TaskStackBuilder.create(ctx)
        stackBuilder.addParentStack(ViewPQ::class.java)
        stackBuilder.addNextIntent(regularRestoringIntent!!)
        regularRestoringPendingIntent =
            PendingIntent.getActivity(ctx,
                UiNotifications.PENDING_REGULAR_REQUEST_CODE, regularRestoringIntent,
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