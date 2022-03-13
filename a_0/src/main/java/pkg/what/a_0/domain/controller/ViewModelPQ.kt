package pkg.what.a_0.domain.controller

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import pkg.what.a_0.domain.service.NotificationOnStopWorker

/** @desc passing context is acceptable. holding a reference in the vm is absolutely not okay
 * , argument is screen rotates and vm persists, so the ctx is null, therefore causing crashes */
class ViewModelPQ(application: Context) : ViewModel(){

    private val workManager by lazy {  WorkManager.getInstance(application) }

    /** @desc call this from onDestroy() for an activity */
    internal fun cancelNotification(){
        Log.d("$javaClass","cancelNotification ...")
        workManager.cancelAllWork()
    }

    /** @desc call this from onStop() or onDestroy() for an activity */
    internal fun applyNotification(){
        Log.d("$javaClass","applyNotification ...")
        workManager.enqueue(OneTimeWorkRequest.from(NotificationOnStopWorker::class.java))
    }
}