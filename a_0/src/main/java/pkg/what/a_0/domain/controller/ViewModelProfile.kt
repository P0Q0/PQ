package pkg.what.a_0.domain.controller

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import pkg.what.a_0.domain.service.NotificationOnStopWorker

class ViewModelProfile(context: Context) : ViewModel(){

    private val workManager by lazy {  WorkManager.getInstance(context) }

    /** @desc call this from onDestroy() for a fragment */
    internal fun cancelNotification(){
        Log.d("$javaClass","cancelNotification ...")
        workManager.cancelAllWork()
    }

    /** @desc call this from onStop() or onDetach() for a fragment */
    internal fun applyNotification(){
        Log.d("$javaClass","applyNotification ...")
        workManager.enqueue(OneTimeWorkRequest.from(NotificationOnStopWorker::class.java))
    }
}