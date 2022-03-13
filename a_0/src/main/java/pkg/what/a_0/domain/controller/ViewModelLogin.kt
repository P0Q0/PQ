package pkg.what.a_0.domain.controller

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import pkg.what.a_0.data.model.LoginsModel
import pkg.what.a_0.domain.config.ConfigPQ
import pkg.what.a_0.domain.service.NotificationWorker

class ViewModelLogin(context: Context) : ViewModel(){

    private val workManager by lazy {  WorkManager.getInstance(context) }

    /** @desc call this from onDestroy() for a fragment, due to view model persistent lifecycle */
    internal fun applyNotification(){
        workManager.enqueue(OneTimeWorkRequest.from(NotificationWorker::class.java))
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