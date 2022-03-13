package pkg.what.a_0.domain.controller

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.work.*
import pkg.what.a_0.domain.service.NotificationOnDestroyWorker
import pkg.what.a_0.domain.service.NotificationOnStopWorker

/** @desc passing context is acceptable. holding a reference in the vm is absolutely not okay
 * , argument is screen rotates and vm persists, so the ctx is null, therefore causing crashes */
class ViewModelPQ(application: Context) : ViewModel(){

    private val vmPqWorkManager by lazy {  WorkManager.getInstance(application) }

    /** @desc call this from onStop() for an activity */
    internal fun loadOnStopWork(lifecycleOwner: LifecycleOwner){
        val statusOnStopWorkRequest: WorkRequest
        = OneTimeWorkRequestBuilder<NotificationOnStopWorker>().build()
        vmPqWorkManager.enqueue(statusOnStopWorkRequest)
        val rdId = statusOnStopWorkRequest.id
        vmPqWorkManager.getWorkInfoByIdLiveData(rdId).observe(lifecycleOwner) { info ->
            if(info.state.isFinished){
                Log.d("$javaClass","info_id : ${info.id} , info : $info")
            }
        }
    }

    /** @desc call this from onDestroy() for an activity */
    internal fun applyOnDestroyNotification(){
        Log.d("$javaClass","applyOnDestroyNotification ...")
        vmPqWorkManager.enqueue(OneTimeWorkRequest.from(NotificationOnDestroyWorker::class.java))
    }

    /** @desc call this from onDestroy() for an activity */
    internal fun cancelNotification(){
        Log.d("$javaClass","cancelNotification ...")
        vmPqWorkManager.cancelAllWork()
    }
}