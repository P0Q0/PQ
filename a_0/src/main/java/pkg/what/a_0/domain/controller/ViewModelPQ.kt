package pkg.what.a_0.domain.controller

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import pkg.what.a_0.domain.service.NotificationWorker

/** @desc passing context is acceptable. holding a reference in the vm is absolutely not okay
 * , argument is screen rotates and vm persists, so the ctx is null, therefore causing crashes */
class ViewModelPQ(application: Context) : ViewModel(){

    private val workManager by lazy {  WorkManager.getInstance(application) }

    /** @desc call this from onStop() for an activity, due to view model persistent lifecycle */
    internal fun applyNotification(){
        workManager.enqueue(OneTimeWorkRequest.from(NotificationWorker::class.java))
    }


}