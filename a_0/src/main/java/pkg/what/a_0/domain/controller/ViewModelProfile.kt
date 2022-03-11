package pkg.what.a_0.domain.controller

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import pkg.what.a_0.domain.service.NotificationWorker

class ViewModelProfile(application: Context) : ViewModel(){

    private val workManager by lazy {  WorkManager.getInstance(application) }

    /** @desc call this from onDestroy() for a fragment, due to view model persistent lifecycle */
    internal fun applyNotification(){
        workManager.enqueue(OneTimeWorkRequest.from(NotificationWorker::class.java))
    }
}