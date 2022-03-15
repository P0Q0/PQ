package pkg.what.a_0.domain.controller

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.*
import pkg.what.a_0.domain.service.NotificationOnDestroyWorker
import pkg.what.a_0.domain.service.NotificationOnStopWorker

/** @desc passing context is acceptable. holding a reference in the vm is absolutely not okay
 * , argument is screen rotates and vm persists, so the ctx is null, therefore causing crashes */
class ViewModelPQ(application: Context) : ViewModel(){

    private val awakenLiveData: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    fun getAwakenLiveData(): LiveData<String> { return awakenLiveData }

    private val vmPqWorkManager by lazy {  WorkManager.getInstance(application) }

    /** @desc call this from onStop() for an activity */
    internal fun loadOnStopWork(lifecycleOwner: LifecycleOwner){
        // TODO:ViewModelPQ, add progress dialog for loadOnStopWork
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
    internal fun loadOnDestroyWork(lifecycleOwner: LifecycleOwner, destination: String){
        cancelNotification()
        // TODO:ViewModelPQ, add progress dialog for loadOnDestroyWork
        val statusOnDestroyWorkRequest: WorkRequest
            = OneTimeWorkRequestBuilder<NotificationOnDestroyWorker>()
                .setInputData(
                    workDataOf(Pair("KEY_DESTINATION",destination)))
                .build()
        vmPqWorkManager.enqueue(statusOnDestroyWorkRequest)
        val rdId = statusOnDestroyWorkRequest.id
        vmPqWorkManager.getWorkInfoByIdLiveData(rdId).observe(lifecycleOwner) { info ->
            if(info.state.isFinished){
                Log.d("$javaClass","info_id : ${info.id} , info : $info")
            }
        }
    }

    private fun cancelNotification(){
        Log.d("$javaClass","cancelNotification ...")
        vmPqWorkManager.cancelAllWork()
    }
}