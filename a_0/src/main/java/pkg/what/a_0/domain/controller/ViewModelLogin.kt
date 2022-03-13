package pkg.what.a_0.domain.controller

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import pkg.what.a_0.data.model.LoginsModel
import pkg.what.a_0.domain.config.ConfigPQ
import pkg.what.a_0.domain.service.NotificationOnStopWorker

class ViewModelLogin(context: Context) : ViewModel(){

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

    private val builder:
        GoogleSignInOptions.Builder by lazy {
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(ConfigPQ.getClientId()) }

    private val model = LoginsModel()

    fun getModel(): LoginsModel {
        return model
    }

    fun getVmLoginBuilder(): GoogleSignInOptions.Builder {
        return this.builder
    }
}