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
import pkg.what.a_0.ui.notification.NotifyIf.Helper.noty
import pkg.what.a_0.ui.notification.UiNotifications.Companion.CHANNEL_ID_UI

class NotificationWorker(private val ctx: Context, params: WorkerParameters) : Worker(ctx,params) , NotifyIf {

    private var specialIntent: Intent? = null
    private var specialPendingIntent: PendingIntent? = null

    private var regularIntent: Intent? = null
    private var regularPendingIntent: PendingIntent? = null

    override fun doWork(): Result {
        this.prepIntents()
        uiNotifier(
            UiNotifierStates.Unknown(UiNotifierStates.TAG_UNKNOWN)
        )
        val builder = noty.generateContent(
            ctx.getString(pkg.what.pq.R.string.ui_content_title),
            ctx.getString(pkg.what.pq.R.string.ui_content_text),
            NotificationCompat.PRIORITY_DEFAULT,
            ctx
        )
        noty.generateChannel(
            CHANNEL_ID_UI
            , ctx.getString(pkg.what.pq.R.string.ui_channel_name)
            , ctx.getString(pkg.what.pq.R.string.ui_describe_channel_name)
            , NotificationManager.IMPORTANCE_DEFAULT
            , ctx
        )
        regularPendingIntent?.let { rpi ->
            specialPendingIntent?.let { spi ->
                noty.generateAction(
                    builder, rpi, spi, ctx
                )
            }
        }
        noty.showNotification(
            builder
            , ctx
        )
        return Result.success()
    }

    private fun prepIntents(){
        specialIntent = Intent(ctx, ViewSpecialAlert(applicationContext)::class.java)
            .apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        specialPendingIntent = PendingIntent.getActivity(
            ctx, UiNotifications.PENDING_SPECIAL_REQUEST_CODE, specialIntent,
            (PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        )

        regularIntent = Intent(ctx, ViewRegularAlert::class.java)
        val stackBuilder: TaskStackBuilder = TaskStackBuilder.create(ctx)
        stackBuilder.addParentStack(ViewRegularAlert::class.java)
        stackBuilder.addNextIntent(regularIntent!!)
        regularPendingIntent =
            PendingIntent.getActivity(ctx,
                UiNotifications.PENDING_REGULAR_REQUEST_CODE, regularIntent,
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